package com.spring_javafx.spring_javafx.controllers;

import com.spring_javafx.spring_javafx.Navigation;
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
    private final String LIST_PATIENTS = "/fxml/ListPatients.fxml";
    private final String ADD_PATIENT = "/fxml/AddPatient.fxml";
    private final String EDIT_PATIENT = "/fxml/EditPatient.fxml";

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
                switchPage(ADD_PATIENT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        patients.setOnAction(ActionEvent ->{
            try {
                switchPage(LIST_PATIENTS);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        try{
            switchPage(LIST_PATIENTS);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void switchPage(String view) throws IOException {
        Parent root;
        root = switch (view) {
            case ADD_PATIENT ->  navigation.loadFxml(ADD_PATIENT);
            case EDIT_PATIENT -> navigation.loadFxml(EDIT_PATIENT);
            default -> navigation.loadFxml(LIST_PATIENTS);
        };

//        if(view.equals(LIST_PATIENTS)){
//            root = navigation.loadFxml(LIST_PATIENTS);
//        }else{
//            root = navigation.loadFxml(ADD_PATIENT);
//        }
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
