package com.spring_javafx.spring_javafx.controllers;

import com.spring_javafx.spring_javafx.Navigation;
import com.spring_javafx.spring_javafx.UI.Animations;
import com.spring_javafx.spring_javafx.models.user.UserDaoImp;
import com.spring_javafx.spring_javafx.models.user.UserVo;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class LoginController implements Initializable {
    @Autowired
    private UserDaoImp userDaoImp;
    @Autowired
    private Animations animation;
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
        animation.btnHoverEffects(accessButton);
        animation.btnHoverEffects(cancelButton);

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

    @FXML
    private void cleanFields(){
        passInput.setText("");
        passTextHidden.setText("");
        usernameInput.setText("");
    }

    @FXML
    private void validate(){
        String username = this.usernameInput.getText();
        String pass = this.passInput.getText();

        if(!username.equals("") && !pass.equals("")) {
            try {
                UserVo user = userDaoImp.getUser(username);
                if(user != null) {
                    if(user.getUsername().equals(username) && user.getPassword().equals(pass)){
                        navigation.showDashboardView();
                    }else {
                        errorText.setText("Usuario o contraseña invalido");
                    }
                }else {
                    errorText.setText("Este ususario no existe");
                }
            } catch ( Exception e1) {
                e1.printStackTrace();
            }
        }else{
            errorText.setText("Escribe nombre de usuario y contraseña");
        }
    }
}
