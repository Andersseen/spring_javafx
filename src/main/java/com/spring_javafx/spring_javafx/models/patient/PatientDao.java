package com.spring_javafx.spring_javafx.models.patient;

import java.util.List;

public interface PatientDao {
    List<PatientVo> getPatients();
    PatientVo getPatient(int id);
    void updatePatient(int id);
    void savePatient(PatientVo patient);
    void deletePatient(int id);

}
