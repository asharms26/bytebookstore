/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytebookstore.dao;

import com.bytebookstore.models.Author;
import com.bytebookstore.models.TransactionLog;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author wjlax
 */
public interface TransactionLogDao {
    public void setDataSource(DataSource ds);
    public boolean create(TransactionLog model);
    public TransactionLog getTransactionLogModel(Integer id);
    public List<TransactionLog> getAllTransactionLogModels(Integer id);
    public boolean delete(TransactionLog model);
    public boolean update(TransactionLog model);
}
