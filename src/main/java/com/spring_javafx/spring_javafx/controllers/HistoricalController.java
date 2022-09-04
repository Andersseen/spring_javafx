package com.spring_javafx.spring_javafx.controllers;

import com.spring_javafx.spring_javafx.models.historical.HistoricalDaoImp;
import com.spring_javafx.spring_javafx.models.historical.HistoricalVo;
import com.spring_javafx.spring_javafx.models.patient.PatientVo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class HistoricalController implements Initializable {
    private PatientVo patient;
    private boolean haveHistorical;

    @Autowired
    private DashboardController dashboardCL;
    @Autowired
    private HistoricalDaoImp historicalDaoImp;

    @FXML
    private Button addBtn;
    @FXML
    private Button editBtn;
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

        HistoricalVo historical = historicalDaoImp.getHistorical(this.patient.getId());
        if(historical != null){
            historyInput.setText(historical.getHistorical());
        }

        if (haveHistorical) {
            editBtn.setVisible(true);
            addBtn.setVisible(false);
        } else {
            editBtn.setVisible(false);
            addBtn.setVisible(true);
        }

        nameLabel.setText(patient.getName());
        lastNameLabel.setText(patient.getLastName());

    }

    public void getHistorical(PatientVo patient, boolean haveHistorical){
        this.patient = patient;
        this.haveHistorical = haveHistorical;
    }

    @FXML
    public void onClickGoBack(){
        try {
            dashboardCL.switchPage("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
