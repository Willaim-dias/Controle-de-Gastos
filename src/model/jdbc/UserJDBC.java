package model.jdbc;

import config.DB;
import config.DbException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.Alert;
import model.dao.UserDao;
import model.entities.User;
import view.util.Alerts;

public class UserJDBC implements UserDao{

    private Connection conn;
    
    public UserJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean insert(User obj) {
        PreparedStatement st = null;
        try {      
            st = conn.prepareStatement("INSERT INTO tb_user(user, password ,user_code) VALUES (?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getUserName());
            st.setString(2, obj.getPassword());
            st.setString(3, obj.getCodeUser());
            criateTable(obj.getCodeUser());
            return (st.executeUpdate()!= 0);            
        } catch (SQLException e) {
            Alerts.showAlert("Erro","",e.getMessage(), Alert.AlertType.ERROR);
            throw new DbException("Erro:"+e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(User obj) {
        
    }

    @Override
    public void deleteById(String id) {
        
    }

    @Override
    public User findById(String userName) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT user, password, user_code FROM tb_user WHERE user == ?");
            st.setString(1, userName);
            rs = st.executeQuery();
            
            User user = new User(rs.getString("user"), rs.getString("password"), rs.getString("user_code"));
            
            return user;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    
    public void criateTable(String codeUser) {
        // Comandos SQL para criar as tabelas
        String createTableAccount = String.format("""
            CREATE TABLE IF NOT EXISTS tb_account_%s (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                account TEXT NOT NULL,
                value REAL NOT NULL
            );
        """,codeUser);

        String createTableCard = String.format("""
            CREATE TABLE IF NOT EXISTS tb_card_%s (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                value REAL NOT NULL,
                date DATE NOT NULL
            );
        """,codeUser);
        
        String createTableHistory = String.format("""
            CREATE TABLE "tb_history_%s" (
                "id"	INTEGER,
                "data_list"	TEXT NOT NULL,
                "Date_save"	DATE NOT NULL,
                PRIMARY KEY("id" AUTOINCREMENT)
            );
        """,codeUser);

        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(createTableAccount);
            statement.executeUpdate(createTableCard);
            statement.executeUpdate(createTableHistory);
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }
}
