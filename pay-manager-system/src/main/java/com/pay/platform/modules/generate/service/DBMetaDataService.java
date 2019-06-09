package com.pay.platform.modules.generate.service;

import com.pay.platform.modules.generate.model.ColumnModel;
import com.pay.platform.modules.generate.model.TableModel;

import java.util.List;

/**
 * User:
 * DateTime: 2018/1/5 15:52
 * <p>
 * 数据库元数据获取
 */
public interface DBMetaDataService {

    /**
     * 获取所有表名称
     *
     * @return
     */
    public List<String> getAllTableNameList() throws Exception;

    /**
     * 获取单张表 - 元信息
     *
     * @return
     */
    public TableModel getTableMetaData(String tableName) throws Exception;

    /**
     * 获取所有表 - 元信息
     *
     * @return
     */
    public List<TableModel> getAllTableMetaData() throws Exception;

    /**
     * 根据表名称获取列信息
     *
     * @return
     */
    public List<ColumnModel> getColumnListByTableName(String tableName) throws Exception;


}