package com.spring_javafx.spring_javafx;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockUiApplication {
    public static void main(String[] args) {
        Application.launch(Launcher.class, args);
    }
}
