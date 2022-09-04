package com.spring_javafx.spring_javafx.controllers;

import com.spring_javafx.spring_javafx.models.historical.HistoricalVo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class HistoricalController implements Initializable {
    private HistoricalVo historical;
    public HistoricalController() {
    }

    public HistoricalController(HistoricalVo historical) {
        this.historical = historical;
    }
    @Lazy
    @Autowired
    private DashboardController dashboardCL;
    @FXML
    private Button addBtn;
    @FXML
    private Button editBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextArea historyInput;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label nameLabel;

    @FXML
    private Button btnExportHistorical;
    @FXML
    private Button btnImportHistorical;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nameLabel.setText(historical.getName());
        lastNameLabel.setText(historical.getLastName());

        cancelBtn.setOnMouseClicked((MouseEvent event) -> {
            try {
                dashboardCL.switchPage("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
