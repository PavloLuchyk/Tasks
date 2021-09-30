package com;

import com.dao.CommentDao;
import com.dao.CrudDao;
import com.dao.DataSource;
import com.dao.SortingOrder;
import com.dao.postgres.AdvertisementDaoPostgres;
import com.dao.postgres.CommentDaoPostgres;
import com.serialization.Serializer;
import com.entities.Advertisement;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        CrudDao<Advertisement> advertisementCrudDao = new AdvertisementDaoPostgres(DataSource.getConnection());
        Serializer serializer = new Serializer();
        System.out.println(advertisementCrudDao.readAll());
        serializer.serialize(advertisementCrudDao.readById(2));
        System.out.println(serializer.deserialize(new File("json/Advertisement1.json"), Advertisement.class));
        CommentDao commentDao = new CommentDaoPostgres(DataSource.getConnection());
        System.out.println(commentDao.getAllCommentsByUserId(2, SortingOrder.ASC));
    }
}
