package model.dao;

import config.DB;
import model.jdbc.AccountJDBC;
import model.jdbc.CardJDBC;
import model.jdbc.ExtractJDBC;

public class DaoFactory {
        
    public static AccountDao createAccountDao() {
        return new AccountJDBC(DB.getConnection());
    }
    
    public static CardDao createCardDao() {
        return new CardJDBC(DB.getConnection());
    }
    
    public static ExtractDao createHistoryDao() {
        return new ExtractJDBC(DB.getConnection());
    }
}
