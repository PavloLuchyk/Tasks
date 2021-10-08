package com.dao.mongo;

import com.dao.CrudDao;
import com.entities.Advertisement;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import com.serialization.Serializer;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class AdvertisementDaoMongo implements CrudDao<Advertisement> {

    private static MongoCollection<Document> advertisementMongoCollection;
    private static Serializer<Advertisement> serializer = new Serializer<>();

    public AdvertisementDaoMongo() {
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
        advertisementMongoCollection= database.getCollection("advertisements");
    }

    @Override
    public Advertisement create(Advertisement element) {
        try {
            advertisementMongoCollection.insertOne(Document.parse(serializer.serialize(element)));
        } catch (IOException e){
            e.printStackTrace();
        }
        return element;
    }

    @Override
    public Advertisement update(Advertisement element) {
        Document filterByAdvertisementId = new Document("id", element.getId());
        FindOneAndReplaceOptions findOneAndReplaceOptions = new FindOneAndReplaceOptions()
                .returnDocument(ReturnDocument.AFTER);
        Document document = new Document();
        try {
        document = advertisementMongoCollection.findOneAndReplace(
                filterByAdvertisementId,
                Document.parse(serializer.serialize(element)),
                findOneAndReplaceOptions);
        } catch (IOException e){
            e.printStackTrace();
        }
        return DocumentParser.parseToAdvertisement(document);
    }

    @Override
    public List<Advertisement> readAll() {
        List<Advertisement> advertisements = new ArrayList<>();
        MongoCursor<Document> mongoCursor = advertisementMongoCollection.find().iterator();
        while (mongoCursor.hasNext()) {
            advertisements.add(DocumentParser.parseToAdvertisement(mongoCursor.next()));
        }
        return advertisements;
    }

    @Override
    public Advertisement readById(long id) {
        Document document = advertisementMongoCollection.find(Filters.eq("id", id)).first();
        return  DocumentParser.parseToAdvertisement(document);
    }

    @Override
    public void delete(Advertisement element) {
        Document filterByUserId = new Document("id", element.getId());
        advertisementMongoCollection.deleteOne(filterByUserId);
    }


}
