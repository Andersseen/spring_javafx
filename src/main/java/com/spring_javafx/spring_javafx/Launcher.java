package com.spring_javafx.spring_javafx;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class Launcher extends Application {
    private ConfigurableApplicationContext applicationContext;

    @Autowired
    private Navigation navigation;

    @Override
    public void init() {
        SpringApplication.run(StockUiApplication.class)
                .getAutowireCapableBeanFactory().autowireBean(this);
    }
    @Override
    public void start(Stage stage) {
        navigation.setStage(stage);
        navigation.showLoginView();
    }

}
