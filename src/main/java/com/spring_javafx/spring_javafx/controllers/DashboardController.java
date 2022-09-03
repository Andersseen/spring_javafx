package com.spring_javafx.spring_javafx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class DashboardController implements Initializable {


    @FXML
    private AnchorPane bord;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private Button btnExport;

    @FXML
    private Button btnImport;

    @FXML
    private StackPane contentSwitcher;

    @FXML
    private Label label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
