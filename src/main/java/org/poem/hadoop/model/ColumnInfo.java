package org.poem.hadoop.model;

/**
 *
 * @author sai.luo
 * @date 2017-5-17
 */
public class ColumnInfo {
    private String columnName ;
    private String columnType ;

    public ColumnInfo(String columnName, String columnType) {
        this.columnName = columnName;
        this.columnType = columnType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this==obj) return  true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ColumnInfo columnInfo = (ColumnInfo)obj;
        return this.columnName.equals(columnInfo.columnName);
    }

    @Override
    public int hashCode() {
        return columnName != null ? columnName.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ColumnInfo{" +
                "columnName='" + columnName + '\'' +
                ", columnType='" + columnType + '\'' +
                '}';
    }

}
