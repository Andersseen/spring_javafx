package com.spring_javafx.spring_javafx.controllers;

import com.spring_javafx.spring_javafx.Navigation;
import com.spring_javafx.spring_javafx.models.patient.PatientVo;
import com.spring_javafx.spring_javafx.services.Feedback;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class DashboardController implements Initializable {
    private static final String ADD_PAGE = "addPage";
    private static final String LIST_PAGE = "listPage";
    @Lazy
    @Autowired
    private Feedback feedback;

    @Lazy
    @Autowired
    private Navigation navigation;

    @FXML
    private Button addPatient;

    @FXML
    private StackPane contentSwitcher;

    @FXML
    private Button patients;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addPatient.setOnAction(ActionEvent ->{
            try {
                switchPage(ADD_PAGE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        patients.setOnAction(ActionEvent ->{
            try {
                switchPage(LIST_PAGE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        try{
            switchPage(LIST_PAGE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void switchPage(String view) throws IOException {
        Parent root;
        if(view.equals(ADD_PAGE)){
            root = navigation.loadPages(ADD_PAGE);
        }else{
            root =navigation.loadPages(LIST_PAGE);
        }
        contentSwitcher.getChildren().removeAll();
        contentSwitcher.getChildren().setAll(root);
    }
    public void getEditPage( PatientVo patient) throws IOException {
        Parent root = navigation.loadEditPage(patient);
        contentSwitcher.getChildren().removeAll();
        contentSwitcher.getChildren().setAll(root);
    }

    public void getHistoricalPage( PatientVo patient, boolean status) throws IOException {
        Parent root = navigation.loadHistoricalPage(patient, status);
        contentSwitcher.getChildren().removeAll();
        contentSwitcher.getChildren().setAll(root);
    }

    @FXML
    public void onClickExport(){
        feedback.alertInformation(" click on export");
    }
    @FXML
    public void onClickImport(){
        feedback.alertInformation(" click on import");
    }

}
