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

    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
