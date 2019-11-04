package org.poem.hadoop.rdbs;


import org.poem.hadoop.hive.HiveDataType;

/**
 * Created by sai.luo on 2017-5-19.
 */
public class OracleDataType {

    public static final String TINYINT ="TINYINT";
    public static final String SMALLINT ="SMALLINT";
    public static final String MEDIUMINT ="MEDIUMINT";
    public static final String INT ="INT";
    public static final String INTEGER ="INTEGER";
    public static final String BINARY_FLOAT ="BINARY_FLOAT";
    public static final String BINARY_DOUBLE ="BINARY_DOUBLE";
    public static final String DECIMAL ="DECIMAL";
    public static final String NUMERIC ="NUMERIC";
    public static final String CHAR ="CHAR";
    public static final String NCHAR ="NCHAR";
    public static final String VARCHAR ="VARCHAR";
    public static final String VARCHAR2 ="VARCHAR2";
    public static final String NVARCHAR ="NVARCHAR";
    public static final String NVARCHAR2 ="NVARCHAR2";
    public static final String DATE ="DATE";
    public static final String DATETIME ="DATETIME";
    public static final String TIMESTAMP ="TIMESTAMP";

    public static String OracleDataTypetoHiveDataType(String databaseDataType){
        switch (databaseDataType){
            case OracleDataType.TINYINT:
                return HiveDataType.TINYINT;
            case OracleDataType.SMALLINT:
                return HiveDataType.SMALLINT;
            case OracleDataType.MEDIUMINT:
            case OracleDataType.INTEGER:
            case OracleDataType.INT:
                return HiveDataType.INT;
            case OracleDataType.BINARY_DOUBLE:
                return HiveDataType.DOUBLE;
            case OracleDataType.DECIMAL:
                return HiveDataType.DECIMAL;
            case OracleDataType.BINARY_FLOAT:
                return HiveDataType.FLOAT;
            case OracleDataType.NUMERIC:
                return HiveDataType.DOUBLE;
            case OracleDataType.CHAR:
            case OracleDataType.NCHAR:
            case OracleDataType.VARCHAR:
            case OracleDataType.VARCHAR2:
            case OracleDataType.NVARCHAR:
            case OracleDataType.NVARCHAR2:
                return HiveDataType.STRING;
            case OracleDataType.DATE:
                return HiveDataType.DATE;
            case OracleDataType.DATETIME:
                return HiveDataType.TIMESTAMP;
            case OracleDataType.TIMESTAMP:
                return HiveDataType.TIMESTAMP;
            default:
                return HiveDataType.STRING;
        }
    }
}
