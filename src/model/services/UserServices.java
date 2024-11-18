package model.services;

import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entities.User;

public class UserServices {
    
    private final UserDao dao = DaoFactory.CreateUserDao();
    
    public User findById(String userName) {
        return dao.findById(userName);
    }
    
    public boolean saveOrUpdate(User obj) {
        if (obj.getId() == null) {
             return dao.insert(obj);
        } else {
            dao.update(obj);
        }
        return false;
    }
    
    public void remove(User obj) {
        dao.deleteById(obj.getCodeUser());
    }
    
    
    
}
