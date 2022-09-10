package com.spring_javafx.spring_javafx.controllers;

import com.spring_javafx.spring_javafx.UI.Animations;
import com.spring_javafx.spring_javafx.models.historical.HistoricalDaoImp;
import com.spring_javafx.spring_javafx.models.historical.HistoricalVo;
import com.spring_javafx.spring_javafx.models.patient.PatientVo;
import com.spring_javafx.spring_javafx.services.Feedback;
import com.spring_javafx.spring_javafx.services.files.WordFile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class HistoricalController implements Initializable {
    @Autowired
    private Animations animation;
    private HistoricalVo historical;
    private PatientVo patient;
    private boolean haveHistorical;
    @Lazy
    @Autowired
    private Feedback feedback;
    @Lazy
    @Autowired
    private WordFile wordFile;
    @Lazy
    @Autowired
    private DashboardController dashboardCL;
    @Lazy
    @Autowired
    private HistoricalDaoImp historicalDaoImp;

    @FXML
    private Button btnImportHistorical;
    @FXML
    private Button btnExportHistorical;
    @FXML
    private Button addBtn;
    @FXML
    private Button editBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button goBack;
    @FXML
    private TextArea historyInput;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label nameLabel;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        animation.btnHoverEffects(addBtn);
        animation.btnHoverEffects(editBtn);
        animation.btnHoverEffects(deleteBtn);
        animation.btnHoverEffects(goBack);
        animation.btnHoverEffects(btnExportHistorical);
        animation.btnHoverEffects(btnImportHistorical);

        HistoricalVo historical = historicalDaoImp.getHistoricalByPatientId(this.patient.getId());
        if(historical != null){
            this.historical = historical;
            historyInput.setText(historical.getHistorical());
        }

        if (haveHistorical) {
            editBtn.setVisible(true);
            deleteBtn.setVisible(true);
            addBtn.setVisible(false);

        } else {
            editBtn.setVisible(false);
            deleteBtn.setVisible(false);
            addBtn.setVisible(true);
            goBack.setTranslateX(-75);
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
    @FXML
    private void onClickAddHistorical(){
        System.out.println("asdasdsadasd");
        try{
            historical = new HistoricalVo();
            historical.setIdCustomer(patient.getId());
            historical.setHistorical(historyInput.getText());
            historical.setName(patient.getName());
            historical.setLastName(patient.getLastName());
            historicalDaoImp.saveHistorical(historical);
            dashboardCL.switchPage("");
        }catch (Exception ex){
            System.out.println("error");
            Logger.getLogger(HistoricalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onClickEditHistorical(){
        historical = historicalDaoImp.getHistoricalByPatientId(patient.getId());
        if(historical != null){
            try{
                historical.setHistorical(historyInput.getText());
                historicalDaoImp.saveHistorical(historical);
                dashboardCL.switchPage("");
            }catch (Exception ex){
                Logger.getLogger(HistoricalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("Problema con onClickEditHistorical en historical controller");
            feedback.alertInformation("No se puede editar este historico");
        }
    }
    @FXML
    public void onClickDeleteHistorical(){
        if(historical != null){
            if(feedback.alertConfirmation("Quieres eliminar este historico?")) {
                try{
                    historicalDaoImp.deleteHistorical(historical);
                    dashboardCL.switchPage("");
                }catch (Exception ex){
                    Logger.getLogger(HistoricalController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else{
            System.out.println("Problema con onClickEditHistorical en historical controller");
            feedback.alertInformation("No se puede editar este historico");
        }
    }
    @FXML
    public void onClickExportHistorical(){
        wordFile.getPatientAndHistorical(this.patient , this.historyInput.getText());
        wordFile.exportWord();
        try {
            dashboardCL.switchPage("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void onClickImportHistorical(){
        wordFile.getPatient(this.patient );
        wordFile.importWord();
        try {
            dashboardCL.getHistoricalPage(this.patient, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
