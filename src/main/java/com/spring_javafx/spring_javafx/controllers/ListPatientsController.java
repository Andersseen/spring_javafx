package com.spring_javafx.spring_javafx.controllers;


import com.spring_javafx.spring_javafx.models.historical.HistoricalDaoImp;
import com.spring_javafx.spring_javafx.models.historical.HistoricalVo;
import com.spring_javafx.spring_javafx.models.patient.PatientDaoImp;
import com.spring_javafx.spring_javafx.models.patient.PatientVo;
import com.spring_javafx.spring_javafx.services.Feedback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.SortedMap;

@Component
public class ListPatientsController implements Initializable {
    @Lazy
    @Autowired
    private DashboardController dashboardCL;

    @Lazy
    @Autowired
    private Feedback feedback;

    @Autowired
    PatientDaoImp patientDaoImp;

    @Autowired
    HistoricalDaoImp historicalDaoImp;
    @FXML
    private TextField filterText;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<PatientVo> list = FXCollections.observableArrayList(patientDaoImp.getPatients());
        //Load columns
        setColumnsProperties();
        //Load column actions
        setActionColumnProperties(list);

        FilteredList<PatientVo> filteredData = new FilteredList<>(list, b -> true);

        filterText.textProperty().addListener((observable, oldValue, newValue) ->{
            filteredData.setPredicate(PatientVo ->{

                if(newValue.isEmpty() || newValue.isBlank()){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if(PatientVo.getName().toLowerCase().contains(searchKeyword)){
                    return true;
                }else if (PatientVo.getLastName().toLowerCase().contains(searchKeyword)){
                    return true;
                }else if (PatientVo.getEmail().toLowerCase().contains(searchKeyword)){
                    return true;
                }else if (PatientVo.getPhone().toLowerCase().contains(searchKeyword)){
                    return true;
                }else if (PatientVo.getNote().toLowerCase().contains(searchKeyword)){
                    return true;
                }else {
                    return false;
                }
            });
        });
        SortedList<PatientVo> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedData);
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
    public void onClickHistoricalIcon(PatientVo patient){
        HistoricalVo historical =  historicalDaoImp.getHistoricalByPatientId(patient.getId());
        boolean haveHistorical;
        haveHistorical = historical != null;
        try {
            dashboardCL.getHistoricalPage(patient,haveHistorical);
        } catch (IOException e) {
            e.printStackTrace();
            feedback.alertInformation("Ha pasado un error, no puedes ver historico de este paciente");
        }
    }
    public void onClickEditIcon(PatientVo patient){
        try {
            dashboardCL.getEditPage(patient);
        } catch (IOException e) {
            e.printStackTrace();
            feedback.alertInformation("Ha pasado un error, no puedes editar este paciente");
        }
    }
    public void onClickDeleteIcon(PatientVo patient) {
        String message = "Estas seguro que quieres eliminar este cliente?";
        if (feedback.alertConfirmation(message)) {
            patientDaoImp.deletePatient(patient.getId());
        }
        try {
            dashboardCL.switchPage("");
        } catch (IOException e) {
            System.out.println("Error con onClickDeleteIcon en list page");
            e.printStackTrace();
        }
    }

    private void setColumnsProperties(){
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        birthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        note.setCellValueFactory(new PropertyValueFactory<>("note"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void setActionColumnProperties(ObservableList<PatientVo> list){
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

//                    ACTIONS
                    //**************
                    deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                        PatientVo patient = table.getSelectionModel().getSelectedItem();
                        onClickDeleteIcon(patient);
                    });
                    //**************
                    editIcon.setOnMouseClicked((MouseEvent event) -> {
                        PatientVo patient = table.getSelectionModel().getSelectedItem();
                        onClickEditIcon(patient);
                    });
                    //**************
                    historicalIcon.setOnMouseClicked((MouseEvent event) -> {
                        PatientVo patient = table.getSelectionModel().getSelectedItem();
                        onClickHistoricalIcon(patient);
                    });
                    //**************
                    HBox managebtn = createHBox(historicalIcon, editIcon, deleteIcon);
                    setGraphic(managebtn);
                }
                setText(null);
            }
        };

        action.setCellFactory(cellFactory);
//        table.setItems(list);
    }
}
