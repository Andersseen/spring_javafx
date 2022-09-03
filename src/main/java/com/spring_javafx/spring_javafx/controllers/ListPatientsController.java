package com.spring_javafx.spring_javafx.controllers;

import com.spring_javafx.spring_javafx.models.patient.PatientVo;
import com.spring_javafx.spring_javafx.models.user.UserDaoImp;
import com.spring_javafx.spring_javafx.repository.PatientRepository;
import com.spring_javafx.spring_javafx.services.Feedback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

@Component
public class ListPatientsController implements Initializable {
    private Feedback feedback;
    private String message;

    @Autowired
    PatientRepository patientRepository;

    @FXML
    private TableView<PatientVo> table;
    @FXML
    private TableColumn<PatientVo, Integer> id;
    @FXML
    private TableColumn<PatientVo, String> name;
    @FXML
    private TableColumn<PatientVo, String> lastName;
    @FXML
    private TableColumn<PatientVo, String> birthday;
    @FXML
    private TableColumn<PatientVo, String> date;
    @FXML
    private TableColumn<PatientVo, String> email;
    @FXML
    private TableColumn<PatientVo, String> note;
    @FXML
    private TableColumn<PatientVo, String> phone;
    @FXML
    private TableColumn<PatientVo, String> sex;
    @FXML
    private TableColumn<PatientVo, String> action;

    ObservableList<PatientVo> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try{
            ArrayList<PatientVo> clients = (ArrayList<PatientVo>) patientRepository.findAll();
            if(clients != null){
                list.addAll(clients);
            }else{
                list = null;
            }
        } catch (Exception ex) {
            list = null;
        }
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        birthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        note.setCellValueFactory(new PropertyValueFactory<>("note"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        Callback<TableColumn<PatientVo, String>, TableCell<PatientVo, String>> cellFactory = (TableColumn<PatientVo, String> param) -> new TableCell() {

            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);

                } else {

                    //**************
                    HBox managebtn = new HBox();
                    managebtn.setStyle("-fx-alignment:center");

                    setGraphic(managebtn);
                }
                setText(null);
            }
        };
        action.setCellFactory(cellFactory);
        table.setItems(list);
    }
}
