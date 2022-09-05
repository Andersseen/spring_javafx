package com.spring_javafx.spring_javafx.models.patient;

import java.util.ArrayList;
import java.util.List;

public interface PatientDao {
    List<PatientVo> getPatients();
    ArrayList<PatientVo> getArrayPatients();
    PatientVo getPatient(int id);
    void savePatient(PatientVo patient);
    void deletePatient(int id);

}
