package com.spring_javafx.spring_javafx.repository;

import com.spring_javafx.spring_javafx.models.historical.HistoricalVo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HistoricalRepository extends CrudRepository<HistoricalVo, Integer> {
    List<HistoricalVo> findAll();
    HistoricalVo findById(int id);
    HistoricalVo findByIdCustomer(int idCustomer);
}
