package model.jdbc;

import config.DB;
import config.DbException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import model.dao.CardDao;
import model.entities.Card;
import view.CodeUserTemp;
import view.util.Alerts;

public class CardJDBC implements CardDao{
    
    private Connection conn;
    private final String codeUser = CodeUserTemp.getCode();
    
    public CardJDBC(Connection conn) {
        this.conn = conn;
    }
    
    @Override
    public void insert(Card obj) {
        PreparedStatement st = null;
        try {
            String query = String.format("INSERT INTO tb_card_%s (value,date) VALUES (?,?)", codeUser);
            st = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            st.setDouble(1, obj.getValue());
            st.setDate(2, new java.sql.Date(obj.getDate().getTime()));
             
            st.executeUpdate();
        } catch (SQLException e) {
            Alerts.showAlert("Erro","",e.getMessage(), Alert.AlertType.ERROR);
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }
    
    @Override
    public void update(Card obj) {
        PreparedStatement st = null;
        try {
            String query = String.format("UPDATE tb_card_%s SET value = ?,date = ?, WHERE id = ?", codeUser);
            st = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);      
            st.setDouble(1, obj.getValue());
            //st.setDate(2, (Date) obj.getDate());
            st.setInt(3, obj.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }
    
    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            String query = String.format("DELETE FROM tb_card_%s WHERE id = ?", codeUser);
            st = conn.prepareStatement(query);
            st.setInt(1, id);
            st.executeUpdate();
        } catch(SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }
    
    @Override
    public List<Card> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = String.format("SELECT * FROM tb_card_%s", codeUser);
            st = conn.prepareStatement(query);
            rs = st.executeQuery();
            
            List<Card> list = new ArrayList<>();
            while (rs.next()) {
                
                Card card = new Card(rs.getInt("id"),rs.getDate("date"),rs.getDouble("value"));
                list.add(card);
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
