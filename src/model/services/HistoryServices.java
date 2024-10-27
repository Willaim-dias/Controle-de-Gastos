package model.services;

import java.util.List;
import model.dao.AccountDao;
import model.dao.DaoFactory;
import model.dao.HistoryDao;
import model.entities.Account;
import model.entities.History;

public class HistoryServices {
    
    private HistoryDao dao = DaoFactory.createHistoryDao();
    
    public List<History> findAll() {
        return dao.findAll();
    }
    
    public void save(History obj) {
        if (obj.getId() == null) {
            dao.insert(obj);
        } 
    }
    
    public void remove(History obj) {
        dao.deleteById(obj.getId());
    }

}
