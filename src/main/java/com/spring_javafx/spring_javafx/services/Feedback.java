package com.spring_javafx.spring_javafx.services;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import org.apache.xmlbeans.SystemProperties;

import java.io.File;
import java.util.Optional;

public class Feedback {
    public boolean alertConfirmation(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Diálogo de información");
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public void alertInformation(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Diálogo de información");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public File windowSaveFile(boolean isExcel){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporta archivo");
        fileChooser.setInitialDirectory(new File(SystemProperties.getProperty("user.home")));
        FileChooser.ExtensionFilter extFilter;
        //Set extension filter to .xlsx files
        if(isExcel){
            extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx", "*.xls");
        }else{
            extFilter = new FileChooser.ExtensionFilter("Word files (*.docx)", "*.docx","*.doc");
        }
        fileChooser.getExtensionFilters().add(extFilter);
        //Show save file dialog
        return fileChooser.showSaveDialog(null);
    }


    public File windowOpenFile(boolean isExcel){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importa archivo");
        fileChooser.setInitialDirectory(new File(SystemProperties.getProperty("user.home")));
        FileChooser.ExtensionFilter extFilter;
        //Set extension filter to .xlsx files

        if(isExcel){
            extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx", "*.xls");
        }else{
            extFilter = new FileChooser.ExtensionFilter("Word files (*.docx)", "*.docx","*.doc");
        }
        fileChooser.getExtensionFilters().add(extFilter);
//        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx", "*.xls");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        return fileChooser.showOpenDialog(null);
    }
}
