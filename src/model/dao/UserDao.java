package model.dao;

import model.entities.User;

public interface UserDao {
    
    void insert(User obj);
    void update(User obj);
    void deleteById(Integer id);
    User findById(Integer id);
}
