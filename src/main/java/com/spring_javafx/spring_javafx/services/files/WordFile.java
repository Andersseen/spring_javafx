package com.spring_javafx.spring_javafx.services.files;

import com.spring_javafx.spring_javafx.models.historical.HistoricalDaoImp;
import com.spring_javafx.spring_javafx.models.historical.HistoricalVo;
import com.spring_javafx.spring_javafx.models.patient.PatientVo;
import com.spring_javafx.spring_javafx.services.Feedback;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class WordFile {

    @Lazy
    @Autowired
    private Feedback feedback;

    @Lazy
    @Autowired
    private HistoricalDaoImp historicalDaoImp;

    private PatientVo patient;
    private String historicalText;

    public void getPatientAndHistorical(PatientVo patient,String historicalText ){
        this.patient = patient;
        this.historicalText = historicalText;
    }

    public void getPatient(PatientVo patient) {
        this.patient = patient;
    }

    public void exportWord(){
        try (XWPFDocument doc = new XWPFDocument()) {

            // create a paragraph
            XWPFParagraph p1 = doc.createParagraph();
            p1.setAlignment(ParagraphAlignment.CENTER);

            // set font
            XWPFRun r1 = p1.createRun();
            r1.setBold(true);
            r1.setFontSize(20);
            r1.setFontFamily("Nunito");
            r1.setText("Historico de paciente " + this.patient.getName() + " " + this.patient.getLastName());

            // create a paragraph
            XWPFParagraph p2 = doc.createParagraph();
            p2.setAlignment(ParagraphAlignment.LEFT);

            // set font
            XWPFRun r2 = p2.createRun();
            r2.setFontSize(16);
            r2.setFontFamily("Nunito");
            r2.setText(this.historicalText);

            // save it to .docx file
            File file = feedback.windowSaveFile(false);
            String message;
            if(file != null) {
                try (FileOutputStream out = new FileOutputStream(file.getAbsolutePath())) {
                    doc.write(out);
                    doc.close();
                    message = "Has exportado el archivo con exito!";
                    feedback.alertInformation(message);
                }catch (Exception ignored){
                    message = "Ha pasado un error mientras el proceso";
                    feedback.alertInformation(message);
                }
            }else{
                message = "Ha pasado un error mientras el proceso";
                feedback.alertInformation(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importWord ()  {

        File file = feedback.windowOpenFile(false);
        String message;
        if (file != null) {
            try (FileInputStream fileInput = new FileInputStream(file.getAbsolutePath())) {
                XWPFDocument doc = new XWPFDocument(fileInput);
                List<XWPFParagraph> paragraphs = doc.getParagraphs();
                doc.close();

                HistoricalVo historical = historicalDaoImp.getHistoricalByPatientId(patient.getId());

                if(historical != null){
                    String textBefore = historical.getHistorical();
                    AtomicReference<String> textHistorical = new AtomicReference<>("");
                    paragraphs.forEach(paragraph -> textHistorical.updateAndGet(v -> v + paragraph.getText() + "\n" ));
                    historical.setHistorical(textBefore + "\n" + "(**Texto importado**)\n" + textHistorical);
                }else {
                    AtomicReference<String> textHistorical = new AtomicReference<>("");
                    paragraphs.forEach(paragraph -> textHistorical.updateAndGet(v -> v + paragraph.getText() + "\n" ));
                    historicalText = "(**Texto importado**)\n" + textHistorical;
                    historical = new HistoricalVo();
                    historical.setIdCustomer(patient.getId());
                    historical.setName(patient.getName());
                    historical.setLastName(patient.getLastName());
                    historical.setHistorical(historicalText);
                }
                historicalDaoImp.saveHistorical(historical);
                message = "Has importado el archivo con exito!";
                feedback.alertInformation(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            message = "Ha pasado un error mientras el proceso";
            feedback.alertInformation(message);
        }
    }
}
