package org.poem.api.vo.table.entity;


import org.poem.api.vo.databases.entity.DateBaseEntity;

/**
 * @author Administrator
 */
public class TableEntity extends DateBaseEntity {

    /**
     * 表名
     */
    private String tableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
