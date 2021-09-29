package com;

import com.dao.CrudDao;
import com.dao.DBConnector;
import com.dao.postgres.AdvertisementDaoPostgres;
import com.dao.postgres.CommentDaoPostgres;
import com.dao.postgres.UserDaoPostgres;
import com.serialization.Serializator;
import com.entities.Advertisement;
import com.entities.Comment;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        DBConnector dbConnector = new DBConnector();
        CrudDao<Advertisement> advertisementCrudDao = new AdvertisementDaoPostgres(dbConnector.getConnection());
        Serializator serializator = new Serializator();
        serializator.serialize(advertisementCrudDao.readAll());
        dbConnector.closeConnection();
    }
}
