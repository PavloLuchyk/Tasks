package com;

import com.dao.CommentDao;
import com.dao.CrudDao;
import com.dao.DataSource;
import com.dao.SortingOrder;
import com.dao.mongo.AdvertisementDaoMongo;
import com.dao.postgres.AdvertisementDaoPostgres;
import com.dao.postgres.CommentDaoPostgres;
import com.serialization.Serializer;
import com.entities.Advertisement;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        AdvertisementDaoPostgres advertisementDaoPostgres = new AdvertisementDaoPostgres(DataSource.getConnection());

    }
}
