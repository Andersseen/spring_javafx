package com.spring_javafx.spring_javafx.services.files;

import com.spring_javafx.spring_javafx.models.patient.PatientDaoImp;
import com.spring_javafx.spring_javafx.models.patient.PatientVo;
import com.spring_javafx.spring_javafx.services.Feedback;
import com.spring_javafx.spring_javafx.services.threads.ExportTaskService;
import com.spring_javafx.spring_javafx.services.threads.ImportTaskService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ExcelFile {

    @Lazy
    @Autowired
    private Feedback feedback;
    @Lazy
    @Autowired
    private PatientDaoImp patientDaoImp;
    private File file;
    private String message;

    public void exportFile() {

        Workbook workbook = new XSSFWorkbook();

        ArrayList<PatientVo> clients = patientDaoImp.getArrayPatients();
        ExportTaskService exportService = new ExportTaskService(clients, workbook);
        exportService.setOnSucceeded(event -> {
            System.out.println("Done");
            file = feedback.windowSaveFile(true);
            //If file is not null, write to file using output stream.
            if (file != null) {
                try (FileOutputStream outputStream = new FileOutputStream(file.getAbsolutePath())) {
                    workbook.write(outputStream);
                    // Closing the workbook
                    outputStream.close();
                    workbook.close();
                } catch (IOException ex) {
                    Logger.getLogger(ExcelFile.class.getName()).log(Level.SEVERE, null, ex);
                }
                message = "El proceso de exportacion archivo ha completado";
            } else {
                message = "Ha ocurrido error en exportar archivo";
            }
            feedback.alertInformation(message);

        });
        exportService.setOnFailed(event -> {
            System.out.println("Failed");
            message = "Ha ocurrido error en exportar archivo";
            feedback.alertInformation(message);

        });

        exportService.start();
    }

    public void importFile() {

        File file = feedback.windowOpenFile(true);
        //If file is not null, write to file using output stream.

        if (file != null) {
            try (FileInputStream fileInput = new FileInputStream(file.getAbsolutePath())) {
                XSSFWorkbook workbook = new XSSFWorkbook(fileInput);
                Sheet sheet = workbook.getSheetAt(0);

                ImportTaskService service = new ImportTaskService(sheet);

                service.setOnSucceeded(event -> {
                    System.out.println("Done");
                    message = "Se ha terminado el proceso de importacion con exito";
                    feedback.alertInformation(message);

                });
                service.setOnFailed(event -> {
                    System.out.println("Failed");
                    message = "No se puede importar archivo";
                    feedback.alertInformation(message);

                });
                service.start();
            } catch (IOException ex) {
                Logger.getLogger(ExcelFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            message = "Ha ocurrido error en importacion archivo";
            feedback.alertInformation(message);

        }
    }
}