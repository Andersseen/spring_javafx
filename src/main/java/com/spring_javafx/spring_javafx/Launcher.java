package com.spring_javafx.spring_javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class Launcher extends Application {
    private ConfigurableApplicationContext applicationContext;


    @Override
    public void start(Stage primaryStage) throws IOException {
        applicationContext = SpringApplication.run(StockUiApplication.class);
        FXMLLoader loader = new FXMLLoader(StockUiApplication.class.getResource("/fxml/Login.fxml"));
        loader.setControllerFactory(applicationContext::getBean);
        Scene scene = new Scene(loader.load(), 600, 400, false, SceneAntialiasing.BALANCED);

        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

//    FXMLLoader loader = new FXMLLoader(getClass.getResource(resource));
//        loader.setController(applicationContext.getBean(controller));


}
