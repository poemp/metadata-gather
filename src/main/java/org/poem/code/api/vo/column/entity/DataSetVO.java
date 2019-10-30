package org.poem.code.api.vo.column.entity;


import org.poem.code.api.vo.table.entity.TableEntity;

import java.util.List;

/**
 * @author Administrator
 */
public class DataSetVO extends TableEntity {
    /**
     * 表的名字
     */
    private String tableName;

    /**
     * 表头
     */
    private List<String> columns;

    /**
     * 数据
     */
    private List<List<Object>> datas;

    /**
     * 数据量
     */
    private Long total;

    public DataSetVO() {
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public List<List<Object>> getDatas() {
        return datas;
    }

    public void setDatas(List<List<Object>> datas) {
        this.datas = datas;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
