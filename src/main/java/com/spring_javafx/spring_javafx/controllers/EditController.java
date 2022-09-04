package com.spring_javafx.spring_javafx.controllers;


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
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

@Component
public class EditController implements Initializable {
    private PatientVo patient;
    @Lazy
    @Autowired
    private DashboardController dashboardCL;
    @Lazy
    @Autowired
    private Feedback feedback;
    @Lazy
    @Autowired
    private PatientDaoImp patientDaoImp;

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
    private final String[] sex = {"Hombre",  "Mujer", ""};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sexInput.getItems().addAll(sex);
        historicalPane.setVisible(false);

        if(patient.getBirthday() != null){
            LocalDate lclBirthday = patient.getBirthday().toLocalDate();
            birthdayInput.setValue(lclBirthday);
        }
        if(patient.getDate() != null){
            LocalDate lclDate = patient.getDate().toLocalDate();
            dateInput.setValue(lclDate);
        }
        emailInput.setText(patient.getEmail());
        lastNameInput.setText(patient.getLastName());
        nameInput.setText(patient.getName());
        noteInput.setText(patient.getNote());
        phoneInput.setText(patient.getPhone());
        sexInput.setValue(patient.getSex());

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
    @FXML
    public void onClickEditCustomer( ){
        try {
            Date birthday = null;
            Date date = null;
            if(birthdayInput.getValue() != null){
                birthday = Date.valueOf(birthdayInput.getValue());
            }
            if(dateInput.getValue() != null){
                date = Date.valueOf(dateInput.getValue());
            }
            patient.setName(nameInput.getText());
            patient.setLastName(lastNameInput.getText());
            patient.setSex(sexInput.getValue());
            patient.setBirthday(birthday);
            patient.setPhone(phoneInput.getText());
            patient.setEmail(emailInput.getText());
            patient.setNote(noteInput.getText());
            patient.setDate(date);

            String message = "Estas seguro que quieres editar este cliente?";
            if(feedback.alertConfirmation(message)){
                patientDaoImp.savePatient(patient);
            }
            onClickGoBack();
        } catch (IOException e) {
            System.out.println("error en onClickEditCustomer en edit page");
            e.printStackTrace();
        }
    }

    public void onClickGoBack() throws IOException {
        dashboardCL.switchPage("");
    }

    public void getPatient(PatientVo patientVo){
        this.patient = patientVo;
    }
}
