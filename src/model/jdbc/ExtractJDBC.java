package model.jdbc;

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
import javafx.scene.control.Alert;
import model.entities.Extract;
import view.util.Alerts;
import model.dao.ExtractDao;
import view.CodeUserTemp;

public class ExtractJDBC implements ExtractDao{

    private Connection conn;
    private final String codeUser = CodeUserTemp.getCode();
    
    public ExtractJDBC(Connection conn) {
        this.conn = conn;
    }
    
    @Override
    public void insert(Extract obj) {
        PreparedStatement st = null;
        try {
            String query = String.format("INSERT INTO tb_history_%s(data_list, date_save) VALUES (?, ?)", codeUser);
            st = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getDataList());
            st.setDate(2, new java.sql.Date(obj.getDate().getTime()));
            
            int rowsAffected = st.executeUpdate();
            
            if (rowsAffected > 0) {
                Alerts.showAlert("Info", "", "Salvo com success", Alert.AlertType.INFORMATION);
            }
        } catch (SQLException e) {
            throw new DbException("Erro:"+e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }
    
    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            String query = String.format("DELETE FROM tb_history_%s WHERE id = ?", codeUser);
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
    public List<Extract> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = String.format("SELECT * FROM tb_history_%s", codeUser);
            st = conn.prepareStatement(query);
            rs = st.executeQuery();
            
            List<Extract> list = new ArrayList<>();
            while (rs.next()) {
                Extract history = new Extract(rs.getInt("id"),rs.getString("data_list"),rs.getDate("date_save"));
                list.add(history);
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
