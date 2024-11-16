package model.services;

import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entities.User;

public class UserServices {
    
    private UserDao dao = DaoFactory.CreateUserDao();
    
    public User findById(int id) {
        return dao.findById(id);
    }
    
    public void saveOrUpdate(User obj) {
        if (obj.getId() == null) {
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
    }
    
    public void remove(User obj) {
        dao.deleteById(obj.getId());
    }
    
}
