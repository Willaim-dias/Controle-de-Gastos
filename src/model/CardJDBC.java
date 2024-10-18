package model;

import config.DB;
import config.DbException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.entities.Account;
import model.entities.Card;

public class CardJDBC {
    
    private Connection conn;
    
    public CardJDBC() {
        conn = DB.getConnection();
    }
    
    public void insert(Card obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO Card (value,date) VALUES (?,?)",Statement.RETURN_GENERATED_KEYS);
            st.setDouble(0, obj.getValue());
            st.setDate(1, (Date) obj.getDate());
             
            int rowsAffected = st.executeUpdate();
            
            if (rowsAffected > 0) {
                
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }
    
    public void update(Card obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE Account SET value = ?,date = ?, WHERE id = ?",Statement.RETURN_GENERATED_KEYS);      
            st.setDouble(1, obj.getValue());
            st.setDate(1, (Date) obj.getDate());
            st.setInt(2, obj.getId());
            int rowsAffected = st.executeUpdate();
            
            if (rowsAffected > 0) {
                
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }
    
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM Card WHERE id = ?");
            st.setInt(0, id);
            st.executeUpdate();
        } catch(SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }
    
    public List<Account> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM Card");
            rs = st.executeQuery();
            
            List<Account> list = new ArrayList<>();
            while (rs.next()) {
                
                Account account = new Account(rs.getInt("id"),rs.getString("value"),rs.getDouble("date"));
                list.add(account);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
