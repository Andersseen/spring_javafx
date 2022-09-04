package com.spring_javafx.spring_javafx.repository;

import com.spring_javafx.spring_javafx.models.patient.PatientVo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PatientRepository extends CrudRepository<PatientVo, Integer> {
    List<PatientVo> findAll();
    PatientVo findById(int id);
}
