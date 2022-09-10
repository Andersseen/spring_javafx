package com.spring_javafx.spring_javafx.services.files;

import com.spring_javafx.spring_javafx.models.patient.PatientDaoImp;
import com.spring_javafx.spring_javafx.models.patient.PatientVo;
import com.spring_javafx.spring_javafx.services.Feedback;
import com.spring_javafx.spring_javafx.services.threads.ExportTaskService;
import com.spring_javafx.spring_javafx.services.threads.ImportTaskService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@EnableScheduling
@Service
public class ExcelFile {
    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;


    @Lazy
    @Autowired
    private Feedback feedback;
    @Lazy
    @Autowired
    private PatientDaoImp patientDaoImp;
    @Lazy
    @Autowired
    private ImportTaskService importService;
    @Lazy
    @Autowired
    private ExportTaskService exportService;
    private File file;
    private String message;

    @Async
    public void exportFile() throws InstantiationException, IllegalAccessException {
        Workbook workbook = new XSSFWorkbook();
        ArrayList<PatientVo> clients = patientDaoImp.getArrayPatients();
//        exportService = new ExportTaskService();

        exportService.getPatientsAndWorkbook(clients, workbook);


        exportService.setOnScheduled(event ->{
            System.out.println("Start");
        });

        exportService.setOnRunning(event ->{
            System.out.println("Running");
        });

        exportService.setOnReady(event ->{
            System.out.println("Ready");
        });


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
                importService.getPatientsSheet(sheet);


//                importService.getOnRunning();

                importService.setOnScheduled(event ->{
                    System.out.println("Start");
                });

                importService.setOnRunning(event ->{
                    System.out.println("Running");
                });

                importService.setOnReady(event ->{
                    System.out.println("Ready");
                });

                importService.setOnSucceeded(event -> {
                    System.out.println("Done");
                    message = "Se ha terminado el proceso de importacion con exito";
                    feedback.alertInformation(message);

                });
                importService.setOnFailed(event -> {
                    System.out.println("Failed");
                    message = "No se puede importar archivo";
                    feedback.alertInformation(message);

                });
                importService.start();
            } catch (IOException ex) {
                Logger.getLogger(ExcelFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            message = "Ha ocurrido error en importacion archivo";
            feedback.alertInformation(message);
        }
    }
}