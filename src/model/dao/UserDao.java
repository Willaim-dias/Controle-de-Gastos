package model.dao;

import model.entities.User;

public interface UserDao {
    
    boolean insert(User obj);
    void update(User obj);
    void deleteById(String codeUser);
    User findById(String userName);
}
