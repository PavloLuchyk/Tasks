package com;

import com.dao.CrudDao;
import com.dao.DBConnector;
import com.dao.postgres.AdvertisementDaoPostgres;
import com.dao.postgres.CommentDaoPostgres;
import com.dao.postgres.UserDaoPostgres;
import com.entities.User;
import com.serialization.Serializator;
import com.entities.Advertisement;
import com.entities.Comment;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        DBConnector dbConnector = new DBConnector();
        CrudDao<Advertisement> advertisementCrudDao = new AdvertisementDaoPostgres(dbConnector.getConnection());
        Serializator serializator = new Serializator();
        serializator.serialize(advertisementCrudDao.readAll());
        serializator.serialize(new UserDaoPostgres(dbConnector.getConnection()).readById(2));
        System.out.println(serializator.deserialize(new File("json/User2.json"), User.class));;
        dbConnector.closeConnection();
    }
}
