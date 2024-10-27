package model.dao;

import java.util.List;
import model.entities.History;

public interface HistoryDao {
    
    void insert(History obj);
    void deleteById(Integer id);
    List<History> findAll();
    
}
