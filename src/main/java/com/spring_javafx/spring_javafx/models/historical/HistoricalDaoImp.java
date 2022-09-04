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
        return null;
    }

    @Override
    public HistoricalVo getHistorical(int id) {
        return repo.findByIdCustomer(id);
    }

    @Override
    public void updateHistorical(HistoricalVo historical) {

    }

    @Override
    public void saveHistorical(HistoricalVo historical) {

    }

    @Override
    public void deleteHistorical(int id) {

    }
}
