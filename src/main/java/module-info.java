module com.movie_collection.private_movie_collection {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.guice;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;
    requires java.naming;
    requires javafx.media;
    requires org.mybatis;
    requires org.slf4j;
    requires feign.core;
    requires feign.gson;

    exports com.movie_collection.gui.controllers;

    opens com.movie_collection.gui.controllers to javafx.fxml,com.google.guice;

    opens com.movie_collection to javafx.fxml,com.google.guice,org.slf4j;
    exports com.movie_collection.bll.helpers;
    exports com.movie_collection.bll.services;
    exports com.movie_collection.bll.services.interfaces;
    exports com.movie_collection to javafx.graphics;

    exports com.movie_collection.bll.util;
    opens com.movie_collection.bll.util to com.google.guice,javafx.fxml;
    exports com.movie_collection.gui.controllers.abstractController;
    opens com.movie_collection.gui.controllers.abstractController to com.google.guice, javafx.fxml;
    exports com.movie_collection.gui.controllers.controllerFactory;
    opens com.movie_collection.gui.controllers.controllerFactory to com.google.guice, javafx.fxml;
    exports com.movie_collection.config to javafx.graphics;
    opens com.movie_collection.config to com.google.guice, javafx.fxml;
    exports di to javafx.graphics;
    opens di to com.google.guice, javafx.fxml;
    exports com.movie_collection.be to javafx.graphics,org.mybatis;
    exports com.movie_collection.gui.models to javafx.graphics,com.google.guice;
    exports com.movie_collection.dal.dao to com.google.guice;
    exports com.movie_collection.dal.interfaces;
    exports com.movie_collection.bll.utilities;
    opens myBatis to org.mybatis, javafx.fxml,org.slf4j;
    exports myBatis to javafx.fxml;
    opens com.movie_collection.dal.mappers to org.mybatis;
    opens com.movie_collection.bll.services.api to com.google.guice, feign.core,feign.gson ;
    exports com.movie_collection.bll.services.api;
    exports com.movie_collection.gui.DTO;
    exports com.movie_collection.bll.services.api.contract;
    opens com.movie_collection.bll.services.api.contract to com.google.guice, feign.core, feign.gson;


}