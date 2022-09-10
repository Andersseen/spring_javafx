package com.spring_javafx.spring_javafx.services.files;

import com.spring_javafx.spring_javafx.controllers.DashboardController;
import com.spring_javafx.spring_javafx.models.patient.PatientDaoImp;
import com.spring_javafx.spring_javafx.models.patient.PatientVo;
import com.spring_javafx.spring_javafx.services.Feedback;
import com.spring_javafx.spring_javafx.services.threads.ExportService;
import com.spring_javafx.spring_javafx.services.threads.ImportService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

@EnableScheduling
@Service
public class ExcelFile {
    @Lazy
    @Autowired
    private Feedback feedback;
    @Lazy
    @Autowired
    private DashboardController dashboardCL;
    @Lazy
    @Autowired
    private PatientDaoImp patientDaoImp;
    @Lazy
    @Autowired
    private ImportService importService;
    @Lazy
    @Autowired
    private ExportService exportService;


    public void exportFile()  {
        Workbook workbook = new XSSFWorkbook();
        ArrayList<PatientVo> clients = patientDaoImp.getArrayPatients();

        try {
            exportService.getPatientsAndWorkbook(clients, workbook);
            CompletableFuture<Boolean> taskResult = exportService.initExportingPatients();
            String message;
            if(taskResult.get()){
                File file = feedback.windowSaveFile(true);
                //If file is not null, write to file using output stream.
                if (file != null) {
                    try (FileOutputStream outputStream = new FileOutputStream(file.getAbsolutePath())) {
                        workbook.write(outputStream);
                        // Closing the workbook
                        outputStream.close();
                        workbook.close();
                    }catch (IOException ex) {
                        Logger.getLogger(ExcelFile.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                message = "Exportacion de los pacientes ha pasado correcto";
            }else{
                message = "Ha pasado el error en exportacion de los pacientes";
            }
            feedback.alertInformation(message);

            dashboardCL.switchPage("");
        } catch (InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
            Logger.getLogger(ExcelFile.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void importFile() {
        File file = feedback.windowOpenFile(true);
        //If file is not null, write to file using output stream.
        if (file != null) {
            try (FileInputStream fileInput = new FileInputStream(file.getAbsolutePath())) {
                XSSFWorkbook workbook = new XSSFWorkbook(fileInput);
                Sheet sheet = workbook.getSheetAt(0);

                importService.getPatientsSheet(sheet);
                CompletableFuture<Boolean> taskResult = importService.initImportingPatients();
                String message;
                if(taskResult.get()){
                    message = "Importacion de los pacientes ha pasado correcto";
                }else{
                    message = "Ha pasado el error en importacion de los pacientes";
                }
                feedback.alertInformation(message);

                dashboardCL.switchPage("");

            } catch (IOException | InterruptedException | ExecutionException ex) {
                Logger.getLogger(ExcelFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            String message = "Ha ocurrido error en importacion archivo";
            feedback.alertInformation(message);
        }
    }
}