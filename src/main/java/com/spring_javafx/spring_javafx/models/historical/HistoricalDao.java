package com.spring_javafx.spring_javafx.models.historical;

import java.util.List;

public interface HistoricalDao {
    List<HistoricalVo> getHistoricals();
    HistoricalVo getHistorical(int id);
    HistoricalVo getHistoricalByPatientId(int idPatient);
    void saveHistorical(HistoricalVo historical);
    void deleteHistorical(HistoricalVo historical);
}
