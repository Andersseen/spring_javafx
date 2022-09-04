package com.spring_javafx.spring_javafx.models.patient;

import com.spring_javafx.spring_javafx.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PatientDaoImp implements PatientDao {

    @Autowired
    PatientRepository repo;

    @Override
    public List<PatientVo> getPatients() {
        return repo.findAll();
    }

    @Override
    public PatientVo getPatient(int id) {
        return repo.findById(id);
    }

    @Override
    public void savePatient(PatientVo patient) {
        repo.save(patient);
    }

    @Override
    public void deletePatient(int id) {
        repo.deleteById(id);
    }
}
