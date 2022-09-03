package com.spring_javafx.spring_javafx.controllers;

import com.spring_javafx.spring_javafx.Launcher;
import com.spring_javafx.spring_javafx.Navigation;
import com.spring_javafx.spring_javafx.models.user.UserDaoImp;
import com.spring_javafx.spring_javafx.models.user.UserVo;
import com.spring_javafx.spring_javafx.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class LoginController implements Initializable {
    @Autowired
    UserDaoImp userDaoImp;
    @Lazy
    @Autowired
    private Navigation navigation;

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
            validate();
        });
        cancelButton.setOnAction(ActionEvent ->{
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        });
        checkboxPass.setOnAction(ActionEvent ->{
            if(checkboxPass.isSelected()){
                passInput.setVisible(false);
                passTextHidden.setDisable(false);
                passTextHidden.setVisible(true);
                passTextHidden.setText(passInput.getText());
                return;
            }
            passInput.setText(passTextHidden.getText());
            passTextHidden.setVisible(false);
            passTextHidden.setDisable(true);
            passInput.setVisible(true);
        });
    }

    public void validate(){
        String username = this.usernameInput.getText();
        String pass = this.passInput.getText();

        if(!username.equals("") && !pass.equals("")) {
            try {
                UserVo user = userDaoImp.getUser(username);
                if(user != null) {
                    errorText.setText("Yeah");
                    navigation.showDashboardView();

                }else {
                    errorText.setText("Usuario o contraseña invalido");
                }
            } catch ( Exception e1) {
                e1.printStackTrace();
            }
        }else{
            errorText.setText("Escribe nombre de usuario y contraseña");
        }
    }
}
