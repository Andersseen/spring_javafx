package com.spring_javafx.spring_javafx.controllers;

import com.spring_javafx.spring_javafx.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class LoginController implements Initializable {

    @Autowired
    UserRepository userRepository;

    @FXML
    private Button accessButton;

    @FXML
    private Button cancelButton;

    @FXML
    private CheckBox checkboxPass;

    @FXML
    private Label errorText;

    @FXML
    private PasswordField passInput;

    @FXML
    private TextField passTextHidden;

    @FXML
    private TextField usernameInput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        accessButton.setOnAction(ActionEvent ->{
            System.out.println(userRepository.findAll());


        });
        cancelButton.setOnAction(ActionEvent ->{
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        });

    }
}
