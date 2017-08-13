/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytebookstore.dao;

import com.bytebookstore.models.Author;
import com.bytebookstore.models.Inventory;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author wjlax
 */
public interface InventoryDao {
    public void setDataSource(DataSource ds);
    public boolean create(Inventory model);
    public Inventory getInventoryModel(Integer id);
    public List<Inventory> getAllInventoryModels(Integer id);
    public boolean delete(Inventory model);
    public boolean update(Inventory model);
}
