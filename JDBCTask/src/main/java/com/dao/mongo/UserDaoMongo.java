package com.dao.mongo;

import com.dao.CrudDao;
import com.entities.User;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class UserDaoMongo implements CrudDao<User> {

    public static MongoCollection<User> userDocument;

    public UserDaoMongo() {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                pojoCodecRegistry);
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();
        MongoClient mongoClient = MongoClients.create(clientSettings);
        MongoDatabase database = mongoClient.getDatabase("advertisements");
        userDocument= database.getCollection("users", User.class);
    }


    @Override
    public User create(User element) {
        userDocument.insertOne(element);
        return element;
    }

    public static void main(String[] args) {
        UserDaoMongo userDaoMongo = new UserDaoMongo();
        //userDaoMongo.create(new User(1, "Ad", "d"));
        userDaoMongo.delete(new User(2 , "f ", " d"));
    }

    @Override
    public User update(User element) {
        Document filterByUserId = new Document("_id", element.getId());
        FindOneAndReplaceOptions findOneAndReplaceOptions = new FindOneAndReplaceOptions()
                                                                .returnDocument(ReturnDocument.AFTER);
        return userDocument.findOneAndReplace(filterByUserId, element, findOneAndReplaceOptions);
    }

    @Override
    public List<User> readAll() {
        List<User> users = new ArrayList<>();
        MongoCursor<User> iterable = userDocument.find().iterator();
        while (iterable.hasNext()) {
            users.add(iterable.next());
        }
        return users;
    }

    @Override
    public User readById(long id) {
        return userDocument.find(Filters.eq("_id", id)).first();
    }

    @Override
    public void delete(User element) {
        Document filterByUserId = new Document("_id", element.getId());
        userDocument.deleteOne(filterByUserId);
    }
}
