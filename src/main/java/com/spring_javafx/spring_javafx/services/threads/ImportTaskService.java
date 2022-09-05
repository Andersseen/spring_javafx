package com.spring_javafx.spring_javafx.services.threads;

import com.spring_javafx.spring_javafx.models.patient.PatientDaoImp;
import com.spring_javafx.spring_javafx.models.patient.PatientVo;
import com.spring_javafx.spring_javafx.services.DateService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImportTaskService extends Service<Void> {
    private final XSSFSheet customerSheet;
    private final int startCounting = 5;

    public ImportTaskService(XSSFSheet customerSheet) {
        this.customerSheet = customerSheet;
    }

    @Lazy
    @Autowired
    private DateService dateService;

    @Lazy
    @Autowired
    private PatientDaoImp patientDaoImp;

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Void call(){
                Row row;
                if (customerSheet != null) {
                    for (int i = startCounting; i < (customerSheet.getLastRowNum() +1) ; i++) {
                        row = customerSheet.getRow(i);
                        if(row != null) {
                            // variables for client
                            Date birthday = null;
                            Date date = null;
                            String sex;
                            String phone;
                            String name = "";
                            String lastName = "";
                            String email;
                            String note;

                            // variables for cells
                            Cell nameCell = row.getCell(1);
                            Cell lastNameCell = row.getCell(2);
                            Cell sexCell = row.getCell(3);
                            Cell birthdayCell = row.getCell(4);
                            Cell phoneCell = row.getCell(5);
                            Cell emailCell = row.getCell(6);
                            Cell noteCell = row.getCell(7);
                            Cell dateCell = row.getCell(8);

                            if (nameCell != null) {
                                name = String.valueOf(nameCell);
                            }
                            if (lastNameCell != null) {
                                lastName = String.valueOf(lastNameCell);
                            }

                            // *** Validate cell of sex ( number 3) ***
                            if(sexCell != null) {
                                if (sexCell.getCellTypeEnum() == CellType.STRING) {
                                    String sexInput = String.valueOf(sexCell);
                                    if (sexInput.length() > 1) {
                                        // capitalize first letter
                                        String sexOutput = sexInput.substring(0, 1).toUpperCase() + sexInput.substring(1);
                                        if (sexOutput.equals("Hombre") || sexOutput.equals("Mujer")) {
                                            sex = sexOutput;
                                        } else {
                                            sex = "";
                                        }
                                    } else {
                                        sex = "";
                                    }
                                } else {
                                    sex = "";
                                }
                            }else {
                                sex = "";
                            }

                            // *** Validate cell of birthday ( number 4) ***
                            if (birthdayCell != null) {
                                String dateString = birthdayCell.toString();
                                if (!dateString.equals("")) {
                                    try {
                                        birthday = dateService.tryParse(dateString);
                                    } catch (Exception ex) {

                                        Logger.getLogger(ImportTaskService.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                            // *** Validate cell of phone ( number 5) ***
                            if(phoneCell != null) {
                                phone = switch (phoneCell.getCellTypeEnum()) {
                                    case STRING -> phoneCell.getStringCellValue();
                                    case NUMERIC -> NumberToTextConverter.toText(phoneCell.getNumericCellValue());
                                    case BOOLEAN -> String.valueOf(phoneCell.getBooleanCellValue());
                                    default -> "";
                                };
                            }else {
                                phone = "";
                            }

                            // *** Validate cell of email ( number 6) ***
                            if(emailCell != null) {
                                email = switch (emailCell.getCellTypeEnum()) {
                                    case STRING -> String.valueOf(emailCell);
                                    case NUMERIC -> NumberToTextConverter.toText(emailCell.getNumericCellValue());
                                    case BOOLEAN -> String.valueOf(emailCell.getBooleanCellValue());
                                    default -> "";
                                };
                            }else {
                                email = "";
                            }

                            // *** Validate cell of note ( number 7) ***
                            if(noteCell != null) {
                                note = switch (noteCell.getCellTypeEnum()) {
                                    case STRING -> String.valueOf(noteCell);
                                    case NUMERIC -> NumberToTextConverter.toText(noteCell.getNumericCellValue());
                                    case BOOLEAN -> String.valueOf(noteCell.getBooleanCellValue());
                                    default -> "";
                                };
                            }else {
                                note = "";
                            }
                            // *** Validate cell of date ( number 8) ***
                            if (dateCell != null) {
                                String dateString = dateCell.toString();
                                if (!dateString.equals("")) {
                                    try {

                                        date = dateService.tryParse(dateString);
                                    } catch (Exception ex) {
                                        Logger.getLogger(ImportTaskService.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }


                            try {
                                PatientVo patient = new PatientVo(name, lastName, sex, birthday, phone, email, note, date);
                                patientDaoImp.savePatient(patient);
//                                customerCL.addClient(name, lastName, sex, birthday, phone, email, note, date);
                            } catch (Exception ex) {
                                Logger.getLogger(ImportTaskService.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            //            updateValue();
                            updateProgress(i, customerSheet.getLastRowNum());
                            updateMessage("Done upto: " + i);
                        }else{
                            break;
                        }
                    }
                }
                return null;
            }
        };
    }

}
