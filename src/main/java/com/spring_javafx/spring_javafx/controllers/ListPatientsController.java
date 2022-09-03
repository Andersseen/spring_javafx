package com.spring_javafx.spring_javafx.controllers;


import com.spring_javafx.spring_javafx.models.patient.PatientDaoImp;
import com.spring_javafx.spring_javafx.models.patient.PatientVo;
import com.spring_javafx.spring_javafx.services.Feedback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

@Component
public class ListPatientsController implements Initializable {
    @Lazy
    @Autowired
    private Feedback feedback;
    private String message;

    @Autowired
    PatientDaoImp patientDaoImp;

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
            ArrayList<PatientVo> clients = (ArrayList<PatientVo>) patientDaoImp.getPatients();
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

        Callback<TableColumn<PatientVo, String>, TableCell<PatientVo, String>> cellFactory = (TableColumn<PatientVo, String> param) -> new TableCell<>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);

                } else {
                    ImageView editIcon = createIcon("/img/edit.png");
                    ImageView deleteIcon = createIcon("/img/delete.png");
                    ImageView historicalIcon = createIcon("/img/file.png");

//                    ACCIONES
                    //**************
                    deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                        PatientVo customer = table.getSelectionModel().getSelectedItem();
                        patientDaoImp.deletePatient(customer.getId());


                        message = "Estas seguro que quieres eliminar este cliente?";

                        if (feedback.alertConfirmation(message)) {
                            System.out.println("delete");
                        }
                    });
                    //**************
                    editIcon.setOnMouseClicked((MouseEvent event) -> {
                        PatientVo customer = table.getSelectionModel().getSelectedItem();
                        feedback.alertInformation("edit" + customer.toString());
                    });
                    //**************
                    historicalIcon.setOnMouseClicked((MouseEvent event) -> {
                        PatientVo customer = table.getSelectionModel().getSelectedItem();
                        feedback.alertInformation("historial" + customer.toString());
                    });
                    //**************
                    HBox managebtn = createHBox(historicalIcon, editIcon, deleteIcon);
                    setGraphic(managebtn);
                }
                setText(null);
            }
        };

        action.setCellFactory(cellFactory);
        table.setItems(list);
    }

    private HBox createHBox(ImageView historicalIcon, ImageView editIcon,ImageView deleteIcon){
        HBox managebtn = new HBox(historicalIcon, editIcon, deleteIcon);
        managebtn.setStyle("-fx-alignment:center");
        HBox.setMargin(historicalIcon, new Insets(0, 0, 0, 3));

        HBox.setMargin(editIcon, new Insets(0, 2, 0, 2));
        HBox.setMargin(deleteIcon, new Insets(0, 3, 0, 0));
        return managebtn;
    }
    private ImageView createIcon(String src){
        ImageView icon = new ImageView(src);
        icon.setFitHeight(18);
        icon.setFitWidth(18);
        return icon;
    }
}
