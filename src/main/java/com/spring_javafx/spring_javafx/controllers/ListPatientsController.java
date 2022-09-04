package com.spring_javafx.spring_javafx.controllers;


import com.spring_javafx.spring_javafx.models.historical.HistoricalDaoImp;
import com.spring_javafx.spring_javafx.models.historical.HistoricalVo;
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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

@Component
public class ListPatientsController implements Initializable {
    private String EDIT_PATIENT = "/fxml/EditPatient.fxml";
    private String DASHBOARD_VIEW = "/fxml/Dashboard.fxml";

    @Lazy
    @Autowired
    private DashboardController dashboardCL;

    @Lazy
    @Autowired
    private Feedback feedback;
    private String message;
    @Lazy
    @Autowired
    PatientDaoImp patientDaoImp;
    @Lazy
    @Autowired
    HistoricalDaoImp historicalDaoImp;

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
                        PatientVo patient = table.getSelectionModel().getSelectedItem();
                        patientDaoImp.deletePatient(patient.getId());
                        message = "Estas seguro que quieres eliminar este cliente?";

                        if (feedback.alertConfirmation(message)) {
                            System.out.println("delete");
                        }
                    });
                    //**************
                    editIcon.setOnMouseClicked((MouseEvent event) -> {
                        PatientVo patient = table.getSelectionModel().getSelectedItem();
                        try {
                            dashboardCL.getEditPage(patient);
                        } catch (IOException e) {
                            e.printStackTrace();
                            feedback.alertInformation("Ha pasado un error, no puedes editar este paciente");
                        }
                    });
                    //**************
                    historicalIcon.setOnMouseClicked((MouseEvent event) -> {
                        PatientVo patient = table.getSelectionModel().getSelectedItem();
                        HistoricalVo historical =  historicalDaoImp.getHistorical(patient.getId());
                        boolean haveHistorical;
                        haveHistorical = historical != null;
                        try {
                            dashboardCL.getHistoricalPage(patient,haveHistorical);
                        } catch (IOException e) {
                            e.printStackTrace();
                            feedback.alertInformation("Ha pasado un error, no puedes ver historico de este paciente");
                        }
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
        HBox.setMargin(historicalIcon, new Insets(0, 5, 0, 0));

        HBox.setMargin(editIcon, new Insets(0, 5, 0, 5));
        HBox.setMargin(deleteIcon, new Insets(0, 0, 0, 5));
        return managebtn;
    }
    private ImageView createIcon(String src){
        ImageView icon = new ImageView(src);
        icon.setFitHeight(18);
        icon.setFitWidth(18);
        return icon;
    }
}
