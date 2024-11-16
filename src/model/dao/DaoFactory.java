package model.dao;

import config.DB;
import model.jdbc.AccountJDBC;
import model.jdbc.CardJDBC;
import model.jdbc.ExtractJDBC;
import model.jdbc.UserJDBC;


public class DaoFactory {
    
    public static UserDao CreateUserDao() {
        return new UserJDBC(DB.getConnection());
    }
    
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
