package com.spring_javafx.spring_javafx.controllers;

import com.spring_javafx.spring_javafx.Navigation;
import com.spring_javafx.spring_javafx.models.patient.PatientDaoImp;
import com.spring_javafx.spring_javafx.models.patient.PatientVo;
import com.spring_javafx.spring_javafx.services.Feedback;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

@Component
public class EditController implements Initializable {
//    private static final String EDIT_PATIENT = "/fxml/EditPatient.fxml";
//    private int id;
    private PatientVo patientVo;

//    public EditController(PatientVo patientVo) {
//        this.patientVo = patientVo;
//    }

    @Lazy
    @Autowired
    private Feedback feedback;
    private String message;

//    @Autowired
//    PatientDaoImp patientDaoImp;

    @FXML
    private AnchorPane historicalPane;
    @FXML
    private Button editBtn;

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
    private ChoiceBox<String> sexInput;
    private final String[] sex = {"Hombre",  "Mujer"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sexInput.getItems().addAll(sex);
        historicalPane.setVisible(false);

//        id = patientVo.getId();
        if(patientVo.getBirthday() != null){
            LocalDate lclBirthday = patientVo.getBirthday().toLocalDate();
            birthdayInput.setValue(lclBirthday);
        }
        if(patientVo.getDate() != null){
            LocalDate lclDate = patientVo.getDate().toLocalDate();
            dateInput.setValue(lclDate);
        }
        emailInput.setText(patientVo.getEmail());
        lastNameInput.setText(patientVo.getLastName());
        nameInput.setText(patientVo.getName());
        noteInput.setText(patientVo.getNote());
        phoneInput.setText(patientVo.getPhone());
        sexInput.setValue(patientVo.getSex());
    }


    @FXML
    public void onClickClearFields( ){
        birthdayInput.setValue(null);
        dateInput.setValue(null);
        emailInput.setText("");
        lastNameInput.setText("");
        nameInput.setText("");
        noteInput.setText("");
        phoneInput.setText("");
        sexInput.setValue(null);
    }

    public void onClickEditCustomer( ){
        Date birthday = null;
        Date date = null;
        if(birthdayInput.getValue() != null){
            birthday = Date.valueOf(birthdayInput.getValue());
        }
        if(dateInput.getValue() != null){
            date = Date.valueOf(dateInput.getValue());
        }

        PatientVo patient = new PatientVo();
        patient.setId(this.patientVo.getId());
        patient.setName(nameInput.getText());
        patient.setLastName(lastNameInput.getText());
        patient.setSex(sexInput.getValue());
        patient.setBirthday(birthday);
        patient.setPhone(phoneInput.getText());
        patient.setEmail(emailInput.getText());
        patient.setNote(noteInput.getText());
        patient.setDate(date);

        message = "Estas seguro que quieres editar este cliente?";
        feedback.alertInformation(message);

//        if (feedback.alertConfirmation(message)){
//
//            try {
//                patientDaoImp.updatePatient(patient);
//            }catch(Exception ex){
//                feedback.alertInformation("Ha pasado un error!");
//            }
//        }
    }
}
