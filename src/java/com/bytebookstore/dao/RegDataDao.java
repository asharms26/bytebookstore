/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytebookstore.dao;

import com.bytebookstore.models.Author;
import com.bytebookstore.models.RegData;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author wjlax
 */
public interface RegDataDao {
    public void setDataSource(DataSource ds);
    public boolean create(RegData model);
    public RegData getERModel(Integer id);
    public List<RegData> getAllERModels(Integer id);
    public boolean delete(RegData model);
    public boolean update(RegData model);
}
