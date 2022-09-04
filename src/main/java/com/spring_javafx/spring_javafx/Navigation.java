package com.spring_javafx.spring_javafx;

import com.spring_javafx.spring_javafx.controllers.*;
import com.spring_javafx.spring_javafx.models.historical.HistoricalVo;
import com.spring_javafx.spring_javafx.models.patient.PatientVo;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;


@Component
public class Navigation {

    private static final String LOGIN_VIEW = "/fxml/Login.fxml";
    private static final String DASHBOARD_VIEW = "/fxml/Dashboard.fxml";
    private static final String LIST_VIEW  = "/fxml/ListPatients.fxml";
    private static final String ADD_VIEW = "/fxml/AddPatient.fxml";
    private static final String EDIT_VIEW = "/fxml/EditPatient.fxml";
    private static final String HISTORICAL_VIEW = "/fxml/Historical.fxml";

    private Stage stage;
    @Autowired
    private LoginController loginController;
    @Autowired
    private DashboardController dashboardController;
    @Autowired
    private ListPatientsController listPatientsController;
    @Autowired
    private AddPatientController addPatientController;

    private EditController editController;

    public void showLoginView() {
        show(LOGIN_VIEW);
    }
    public void showDashboardView() {
        show(DASHBOARD_VIEW);
    }
    private void show(String view) {
        Scene scene;
        if(view.equals(LOGIN_VIEW)){
            scene = new Scene(loadFxml(view), 600, 400);
            stage.setTitle("Login");
            stage.setResizable(false);
        }else{
            scene = new Scene(loadFxml(view), 1200, 600);
            stage.setTitle("Dashboard");
            stage.setResizable(true);
        }
        stage.getIcons().add(new Image("/img/logo.png"));
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    public Parent loadFxml(String view) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(view));
        loader.setControllerFactory(param -> getViewController(view));
        try {
            loader.load();
        } catch (IOException ex) {
            System.out.println("Error with loadFxml in Navigation");
        }
        return loader.getRoot();
    }
    private Object getViewController(String view) {

        return switch (view) {
            case DASHBOARD_VIEW -> dashboardController;
            case LIST_VIEW -> listPatientsController;
            case ADD_VIEW  -> addPatientController;
            default -> loginController;
        };
    }
    public Parent loadEditPage(PatientVo patient){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EDIT_VIEW));
        EditController editCL = new EditController(patient);
        loader.setControllerFactory(param -> editCL);
        try {
            loader.load();
        } catch (IOException ex) {
            System.out.println("Error with loadEditPage in Navigation");
        }
        return loader.getRoot();
    }
    public Parent loadHistoricalPage(HistoricalVo historical){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(HISTORICAL_VIEW));
        HistoricalController historicalCL = new HistoricalController(historical);
        loader.setControllerFactory(param -> historicalCL);
        try {
            loader.load();
        } catch (IOException ex) {
            System.out.println("Error with loadHistoricalPage in Navigation");
        }
        return loader.getRoot();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
