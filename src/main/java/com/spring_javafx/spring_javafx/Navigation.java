package com.spring_javafx.spring_javafx;

import com.spring_javafx.spring_javafx.controllers.DashboardController;
import com.spring_javafx.spring_javafx.controllers.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class Navigation {

    private static final String LOGIN_VIEW = "/fxml/Login.fxml";
    private static final String DASHBOARD_VIEW = "/fxml/Dashboard.fxml";

    private Stage stage;
    @Autowired
    private LoginController loginController;
    @Autowired
    private DashboardController dashboardController;

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
        }else{
            scene = new Scene(loadFxml(view), 1200, 600);
        }
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    private Parent loadFxml(String view) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(view));
        loader.setControllerFactory(param -> getViewController(view));
        try {
            loader.load();
        } catch (IOException ex) {
            System.out.println("Error");
        }
        Parent root = loader.getRoot();
        return root;
    }
    private Object getViewController(String view) {
        if (DASHBOARD_VIEW.equals(view)) {
            return dashboardController;
        }
        return loginController;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
