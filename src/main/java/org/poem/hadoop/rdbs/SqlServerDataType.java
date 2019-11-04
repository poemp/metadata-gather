package org.poem.hadoop.rdbs;


import org.poem.hadoop.hive.HiveDataType;

/**
 * Created by sai.luo on 2017-5-19.
 */
public class SqlServerDataType {
    public static final String TINYINT = "TINYINT";
    public static final String SMALLINT = "SMALLINT";
    public static final String MEDIUMINT = "MEDIUMINT";
    public static final String INT = "INT";
    public static final String INTEGER = "INTEGER";
    public static final String BIGINT = "BIGINT";
    public static final String REAL = "REAL";
    public static final String DOUBLE = "DOUBLE";
    public static final String FLOAT = "FLOAT";
    public static final String DECIMAL = "DECIMAL";
    public static final String NUMERIC = "NUMERIC";
    public static final String CHAR = "CHAR";
    public static final String VARCHAR = "VARCHAR";
    public static final String NCHAR = "NCHAR";
    public static final String NVARCHAR = "NVARCHAR";
    public static final String DATE = "DATE";
    public static final String DATETIME = "DATETIME";
    public static final String DATETIME2 = "DATETIME2";
    public static final String SMALLDATETIME = "SMALLDATETIME";
    public static final String DATETIMEOFFSET = "DATETIMEOFFSET";
    public static final String TIMESTAMP = "TIMESTAMP";
    public static final String MONEY = "MONEY";
    public static final String SMALLMONEY = "SMALLMONEY";

    public static String sqlServerDataTypetoHiveDataType(String databaseDataType) {
        switch (databaseDataType.toUpperCase()) {
            case SqlServerDataType.TINYINT:
                return HiveDataType.TINYINT;
            case SqlServerDataType.SMALLINT:
                return HiveDataType.SMALLINT;
            case SqlServerDataType.MEDIUMINT:
            case SqlServerDataType.BIGINT:
            case SqlServerDataType.INTEGER:
                return HiveDataType.INT;
            case SqlServerDataType.FLOAT:
            case SqlServerDataType.SMALLMONEY:
                return HiveDataType.FLOAT;
            case SqlServerDataType.DOUBLE:
            case SqlServerDataType.NUMERIC:
            case SqlServerDataType.REAL:
            case SqlServerDataType.MONEY:
                return HiveDataType.DOUBLE;
            case SqlServerDataType.DECIMAL:
                return HiveDataType.DECIMAL;
            case SqlServerDataType.VARCHAR:
            case SqlServerDataType.NVARCHAR:
            case SqlServerDataType.NCHAR:
            case SqlServerDataType.CHAR:
                return HiveDataType.STRING;
            case SqlServerDataType.DATE:
                return HiveDataType.DATE;
            case SqlServerDataType.DATETIME:
            case SqlServerDataType.DATETIME2:
            case SqlServerDataType.SMALLDATETIME:
            case SqlServerDataType.DATETIMEOFFSET:

            case SqlServerDataType.TIMESTAMP:
                return HiveDataType.TIMESTAMP;
            default:
                return HiveDataType.STRING;
        }
    }
}
