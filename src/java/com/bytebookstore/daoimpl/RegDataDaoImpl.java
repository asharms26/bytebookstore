/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytebookstore.daoimpl;

import com.bytebookstore.dao.RegDataDao;
import com.bytebookstore.models.RegData;
import com.bytebookstore.models.User;
import com.bytebookstore.utilities.DBUtility;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author wjlax
 */
public class RegDataDaoImpl implements RegDataDao {

    @Override
    public void setDataSource(DataSource ds) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(RegData model) {
        boolean valid = true;
        try (Connection conn = DBUtility.ds.getConnection()) {
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            valid = false;
        }
        return valid;
    }

    @Override
    public RegData getRegDataModel(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<RegData> getAllRegDataModels(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(RegData model) {
        boolean valid = true;
        try (Connection conn = DBUtility.ds.getConnection()) {

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            valid = false;
        }
        return valid;
    }

    @Override
    public boolean update(RegData model) {
        boolean valid = true;
        try (Connection conn = DBUtility.ds.getConnection()) {

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            valid = false;
        }
        return valid;
    }
    
    public boolean register(RegData regModel, User userModel){
        boolean valid = true;
        try (Connection conn = DBUtility.ds.getConnection()) {
            CallableStatement cStmt = conn.prepareCall("{call spRegdataInsert(?,?,?,?,?)}");
            cStmt.setString(1, userModel.getFirstName());
            cStmt.setString(2, userModel.getLastName());
            cStmt.setString(3, userModel.getEmail());
            cStmt.setBoolean(4, userModel.getPrivilege());
            cStmt.setString(5, regModel.getPw());
            valid = cStmt.execute();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            valid = false;
        }
        return valid;
    }

}
