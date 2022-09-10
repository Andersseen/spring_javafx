package com.spring_javafx.spring_javafx.controllers;

import com.spring_javafx.spring_javafx.UI.Animations;
import com.spring_javafx.spring_javafx.models.patient.PatientDaoImp;
import com.spring_javafx.spring_javafx.models.patient.PatientVo;
import com.spring_javafx.spring_javafx.services.Feedback;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
public class AddPatientController implements Initializable {
    @Autowired
    private Animations animation;
    @Lazy
    @Autowired
    private PatientDaoImp patientDaoImp;
    @Lazy
    @Autowired
    private DashboardController dashboardCL;
    @Lazy
    @Autowired
    private Feedback feedback;
    @FXML
    private DatePicker birthdayInput;
    @FXML
    private DatePicker dateInput;
    @FXML
    private TextField emailInput;
    @FXML
    private TextField lastNameInput;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField noteInput;
    @FXML
    private TextField phoneInput;
    @FXML
    private TextArea historicalTextarea;
    @FXML
    private Label historicalLabel;
    @FXML
    private CheckBox historicalCheckBox;
    @FXML
    private Label msgName;
    @FXML
    private Label msgLastName;
    @FXML
    private Label msgSex;
    @FXML
    private Label msgPhone;
    @FXML
    private Button addBtn;
    @FXML
    private Button cancelBtn;

    @FXML
    private ChoiceBox<String> sexInput;
    private final String[] sex = {"","Hombre",  "Mujer"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        animation.btnHoverEffects(addBtn);
        animation.btnHoverEffects(cancelBtn);

        sexInput.getItems().addAll(sex);
        sexInput.setValue("");

        historicalLabel.setVisible(false);
        historicalTextarea.setVisible(false);

//        historicalCheckBox.setOnAction(ActionEvent  -> {
//            if(historicalCheckBox.isSelected()){
//                historicalLabel.setVisible(true);
//                historicalTextarea.setVisible(true);
//                return;
//            }
//            historicalLabel.setVisible(false);
//            historicalTextarea.setVisible(false);
//        });
        historicalCheckBox.setVisible(false);
        historicalLabel.setVisible(false);
        historicalTextarea.setVisible(false);

    }

    public void onClickClearFields(){
        birthdayInput.setValue(null);
        dateInput.setValue(null);
        emailInput.setText("");
        lastNameInput.setText("");
        nameInput.setText("");
        noteInput.setText("");
        phoneInput.setText("");
        sexInput.setValue("");

        msgName.setVisible(false);
        msgLastName.setVisible(false);
        msgSex.setVisible(false);
        msgPhone.setVisible(false);
    }

    @FXML
    public void onClickAddPatient() {
        try{
            Date birthday = null;
            Date date = null;
            String sex = null;
            if(birthdayInput.getValue() != null){
                birthday = Date.valueOf(birthdayInput.getValue());
            }
            if (dateInput.getValue() != null){
                date = Date.valueOf(dateInput.getValue());
            }
            if (sexInput.getValue() != null){
                sex = sexInput.getValue();
            }
            String email = emailInput.getText();
            String lastName = lastNameInput.getText();
            String name = nameInput.getText();
            String note =noteInput.getText();
            String phone = phoneInput.getText();
//            String historical = historicalTextarea.getText();
            PatientVo patient = new PatientVo(name, lastName, sex,birthday,phone,email,note, date);
            String message;
            if(!name.isEmpty() && !lastName.isEmpty() && !Objects.requireNonNull(sex).isEmpty() && !phone.isEmpty()){
                message = "Estas seguro que quieres agregar este cliente?";
                if(feedback.alertConfirmation(message)){
                    patientDaoImp.savePatient(patient);
                    dashboardCL.switchPage("");
                }
            }else{
                message = "Hay que rellenar los campos obligatorios";
                feedback.alertInformation(message);
                msgName.setVisible(true);
                msgLastName.setVisible(true);
                msgSex.setVisible(true);
                msgPhone.setVisible(true);
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}

