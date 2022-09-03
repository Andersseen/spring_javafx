package com.spring_javafx.spring_javafx;

import com.spring_javafx.spring_javafx.controllers.AddPatientController;
import com.spring_javafx.spring_javafx.controllers.DashboardController;
import com.spring_javafx.spring_javafx.controllers.ListPatientsController;
import com.spring_javafx.spring_javafx.controllers.LoginController;
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
    private final String LIST_PATIENTS = "/fxml/ListPatients.fxml";
    private final String ADD_PATIENT = "/fxml/AddPatient.fxml";

    private Stage stage;
    @Autowired
    private LoginController loginController;
    @Autowired
    private DashboardController dashboardController;
    @Autowired
    private ListPatientsController listPatientsController;
    @Autowired
    private AddPatientController addPatientController;

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
            case LIST_PATIENTS -> listPatientsController;
            case ADD_PATIENT -> addPatientController;
            default -> loginController;

        };
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
