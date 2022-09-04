package com.spring_javafx.spring_javafx.models.historical;

import java.util.List;

public interface HistoricalDao {
    List<HistoricalVo> getHistoricals();
    HistoricalVo getHistorical(int id);
    void updateHistorical(HistoricalVo historical);
    void saveHistorical(HistoricalVo historical);
    void deleteHistorical(int id);
}
