package com.dao.mongo;

import com.entities.Advertisement;
import com.entities.Comment;
import com.entities.User;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DocumentParser {

    public static Advertisement parseToAdvertisement(Document document) {
        Advertisement advertisement = new Advertisement();
        advertisement.setId(document.getInteger("id"))
                .setTitle(document.getString("title"))
                .setDescription(document.getString("description"))
                .setOwner(parseToUser((Document) document.get("owner")))
                .setCreationDate(LocalDateTime.parse(document.getString("creationDate")));
        List<Document> documents = document.getList("comments", Document.class);
        List<Comment> comments = new ArrayList<>();
        for (Document document1: documents) {
            comments.add(parseToComment(document1, advertisement));
        }
        advertisement.setComments(comments);
        return advertisement;
    }

    public static User parseToUser(Document document) {
        User user = new User();
        user.setId(document.getInteger("id"))
                .setFullName(document.getString("fullName"));
        return user;
    }

    public static Comment parseToComment(Document document, Advertisement advertisement) {
        Comment comment = new Comment();
        comment.setId(document.getInteger("id"))
                .setText(document.getString("text"))
                .setCreationDate(LocalDateTime.parse(document.getString("creationDate")))
                .setOwner(parseToUser((Document) document.get("owner")))
                .setAdvertisement(advertisement);
        return comment;
    }
}
