package com.pay.platform.modules.generate.service.impl;

import com.pay.platform.common.config.JdbcConfig;
import com.pay.platform.common.util.FieldConvertUtil;
import com.pay.platform.modules.generate.model.ColumnModel;
import com.pay.platform.modules.generate.model.TableModel;
import com.pay.platform.modules.generate.service.DBMetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: zjt
 * DateTime: 2018/1/5 15:53
 */
@Service
public class DBMetaDataServiceImpl implements DBMetaDataService {

    @Autowired
    private JdbcConfig jdbcConfig;

    private static Connection connection;

    private Connection getConnection() {

        if (connection == null) {

            try {
                Driver driver = (Driver) Class.forName(jdbcConfig.getDriver()).newInstance();
                connection = DriverManager.getConnection(jdbcConfig.getUrl() , jdbcConfig.getUsername() , jdbcConfig.getPassword());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("找不到驱动类等等", e);
            }

        }

        return connection;

    }

    /**
     * 获取所有表名称
     *
     * @return
     */
    public List<String> getAllTableNameList() throws Exception {

        List<String> tableNameList = new ArrayList<>();

        //获取数据库表
        DatabaseMetaData metaData = getConnection().getMetaData();
        ResultSet tableResultSet = metaData.getTables(null, "%", "%", new String[]{"TABLE"});
        while (tableResultSet.next()) {
            String tableName = tableResultSet.getString("TABLE_NAME");
            tableNameList.add(tableName);
        }

        return tableNameList;

    }

    /**
     * 获取单张表 - 元信息
     *
     * @return
     */
    public TableModel getTableMetaData(String tableName) throws Exception {

        TableModel table = new TableModel();

        //获取数据库表,只有一张
        DatabaseMetaData metaData = getConnection().getMetaData();
        ResultSet tableResultSet = metaData.getTables(null, "%", tableName, new String[]{"TABLE"});
        while (tableResultSet.next()) {
            table.setTableName(tableResultSet.getString("TABLE_NAME"));
            table.setTableType(tableResultSet.getString("TABLE_TYPE"));
            table.setRemarks(tableResultSet.getString("REMARKS"));
            break;
        }

        //获取表包含的所有字段信息
        table.setColumnList(getColumnListByTableName(tableName));

        return table;

    }

    /**
     * 获取所有表 - 元信息
     *
     * @return
     */
    public List<TableModel> getAllTableMetaData() throws Exception {

        List<TableModel> tableModeList = new ArrayList<>();

        //获取数据库表
        DatabaseMetaData metaData = getConnection().getMetaData();
        ResultSet tableResultSet = metaData.getTables(null, "%", "%", new String[]{"TABLE"});
        while (tableResultSet.next()) {
            TableModel table = new TableModel();
            String tableName = tableResultSet.getString("TABLE_NAME");
            table.setTableName(tableName);
            table.setTableType(tableResultSet.getString("TABLE_TYPE"));
            table.setRemarks(tableResultSet.getString("REMARKS"));

            //包含的元信息
            table.setColumnList(getColumnListByTableName(tableName));
            tableModeList.add(table);
        }

        return tableModeList;

    }

    /**
     * 根据表名称获取列信息
     *
     * @return
     */
    public List<ColumnModel> getColumnListByTableName(String tableName) throws Exception {

        List<ColumnModel> columnModelList = new ArrayList<>();

        DatabaseMetaData metaData = getConnection().getMetaData();
        ResultSet resultSet = metaData.getColumns(getConnection().getCatalog(), "%", tableName, "%");
        while (resultSet.next()) {
            ColumnModel columnModel = new ColumnModel();
            String columnName = resultSet.getString("COLUMN_NAME");
            String columnType = resultSet.getString("TYPE_NAME");

            columnModel.setColumnName(columnName);
            columnModel.setColumnType(columnType);
            columnModel.setFieldName(FieldConvertUtil.parseUnderlineToCamel(columnName));
            columnModel.setFieldType(FieldConvertUtil.parseToJavaType(columnType));
            columnModel.setRemarks(resultSet.getString("REMARKS"));
            columnModel.setSize(resultSet.getString("COLUMN_SIZE"));

            //主键放到第一个
            if ("ID".equalsIgnoreCase(columnName)) {
                columnModelList.add(0, columnModel);
            } else {
                columnModelList.add(columnModel);
            }

        }

        return columnModelList;

    }

}