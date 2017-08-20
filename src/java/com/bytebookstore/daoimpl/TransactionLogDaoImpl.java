/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytebookstore.daoimpl;

import com.bytebookstore.dao.TransactionLogDao;
import com.bytebookstore.models.TransactionLog;
import com.bytebookstore.utilities.DBUtility;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author wjlax
 */
public class TransactionLogDaoImpl implements TransactionLogDao{

    @Override
    public void setDataSource(DataSource ds) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(TransactionLog model) {
        boolean valid = true;
        try(Connection conn = DBUtility.ds.getConnection()){
            
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            valid = false;
        }
        return valid;
    }

    @Override
    public TransactionLog getTransactionLogModel(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TransactionLog> getAllTransactionLogModels(Integer id) {
        boolean valid = true;
        List<TransactionLog> transactions = new ArrayList<>();
        try(Connection conn = DBUtility.ds.getConnection()){
            CallableStatement cStmt = conn.prepareCall("{call spGetOrderHistory(?)}");
            cStmt.setInt(1, id);
            ResultSet rs = cStmt.executeQuery();
            while(rs.next()){
                TransactionLog transaction = new TransactionLog();
                transaction.setTid(rs.getInt("tid"));
                transaction.setStatus(rs.getString("status"));
                transaction.setDate(rs.getDate("date"));
                transactions.add(transaction);
            }
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            valid = false;
        }
        return transactions;
    }

    @Override
    public boolean delete(TransactionLog model) {
        boolean valid = true;
        try(Connection conn = DBUtility.ds.getConnection()){
            
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            valid = false;
        }
        return valid;
    }

    @Override
    public boolean update(TransactionLog model) {
        boolean valid = true;
        try(Connection conn = DBUtility.ds.getConnection()){
            
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            valid = false;
        }
        return valid;
    }
    
}
