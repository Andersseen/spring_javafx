package com.spring_javafx.spring_javafx.models.historical;

import com.spring_javafx.spring_javafx.repository.HistoricalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HistoricalDaoImp implements HistoricalDao {
    @Autowired
    HistoricalRepository repo;

    @Override
    public List<HistoricalVo> getHistoricals() {
        return repo.findAll();
    }

    @Override
    public HistoricalVo getHistoricalByPatientId(int id) {
        return repo.findByIdCustomer(id);
    }

    @Override
    public HistoricalVo getHistorical(int id) {
        return repo.findById(id);
    }

    @Override
    public void saveHistorical(HistoricalVo historical) {
        repo.save(historical);
    }

    @Override
    public void deleteHistorical(HistoricalVo historical) {
        repo.delete(historical);
    }
}
