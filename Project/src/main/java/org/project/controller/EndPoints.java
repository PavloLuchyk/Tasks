package org.project.controller;

public class EndPoints {

    public static final String ADVERTISEMENT = "/advertisement";
    public static final String COMMENT = "/comment";
    public static final String AUTHOR = "/author";
    public static final String CATEGORY = "/category";
    public static final String ID = "/{id}";
    public static final String ADD = "/add";
    public static final String PARENT_BY_ID = "/parent/{parent}/{id}";
    public static final String LOGIN = "/login";
    public static final String SORTED = "/sorted/{order}";
    public static final String CHECK = "/check";
    public static final String PARENT_BY_ID_PAGE = "/parent/{parent}/{id}/{pageSize}/{pageNumber}";
    public static final String PARENT_BY_ID_COUNT = "/parent/{parent}/{id}/{pageSize}";
    public static final String PAGES_GET_PAGE = "/page/{number}/{pageNumber}";
    public static final String PAGES_COUNT = "/page/{number}";
    public static final String AUTHOR_GET_BY_EMAIL = "/email/{email}";


    private EndPoints() {

    }
}
