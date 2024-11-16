package model.jdbc;

import java.sql.Connection;
import model.dao.UserDao;
import model.entities.User;

public class UserJDBC implements UserDao{

    private Connection conn;
    
    public UserJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(User obj) {
        
    }

    @Override
    public void update(User obj) {
        
    }

    @Override
    public void deleteById(Integer id) {
        
    }

    @Override
    public User findById(Integer id) {
        return null;
    }

    
}
