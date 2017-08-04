/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytebookstore.dao;

import com.bytebookstore.models.Author;
import com.bytebookstore.models.Transaction;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author wjlax
 */
public interface TransactionDao {
    public void setDataSource(DataSource ds);
    public boolean create(Transaction model);
    public Transaction getERModel(Integer id);
    public List<Transaction> getAllERModels(Integer id);
    public boolean delete(Transaction model);
    public boolean update(Transaction model);
}
