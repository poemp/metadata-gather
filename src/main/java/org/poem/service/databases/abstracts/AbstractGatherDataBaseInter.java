package org.poem.service.databases.abstracts;

import com.google.common.collect.Lists;
import org.poem.api.vo.column.entity.DataSetVO;
import org.poem.api.vo.databases.entity.DateBaseEntity;
import org.poem.api.vo.page.Page;
import org.poem.api.vo.table.entity.TableEntity;
import org.poem.service.databases.GatherDataBaseInter;
import org.poem.utils.GatherDataSourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数据结构
 * 获取数据库
 * 并且获取数据库表
 *
 * @author Administrator
 */
public abstract class AbstractGatherDataBaseInter  implements GatherDataBaseInter {

    private static final Logger logger = LoggerFactory.getLogger( AbstractGatherDataBaseInter.class );

    /**
     * 返回注释信息
     *
     * @param all
     * @return
     */


    public static String parse(String all) {
        String comment = null;
        int index = all.indexOf( "COMMENT='" );
        if (index < 0) {
            return "";
        }
        comment = all.substring( index + 9 );
        comment = comment.substring( 0, comment.length() - 1 );
        return comment;
    }

    /**
     * @param connection
     * @return
     */
    @Override
    public List<DateBaseEntity> getDataBases(Connection connection) {
        List<DateBaseEntity> databases = Lists.newArrayList();
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                ResultSet res = stmt.executeQuery( "show databases " );
                while (res.next()) {
                    DateBaseEntity entity = new DateBaseEntity();
                    entity.setDatabaseName( res.getString( 1 ) );
                    databases.add( entity );
                }
            } catch (SQLException e) {
                logger.error( e.getMessage(), e );
                e.printStackTrace();
            }
        }
        return databases;
    }

    @Override
    public List<TableEntity> getAllTableName(String db, Connection connection) {
        Statement stmt = null;
        ResultSet res = null;
        if (connection == null) {
            return Lists.newArrayList();
        }
        List<TableEntity> tableEntities = Lists.newArrayList();
        try {
            boolean cl = connection.isClosed();
            if (!cl) {
                stmt = connection.createStatement();
                res = stmt.executeQuery( "show tables from " + db );
                while (res.next()) {
                    TableEntity tableEntity = new TableEntity();
                    tableEntity.setDatabaseName( db );
                    tableEntity.setTableName( res.getString( 1 ) );
                    Statement stmtN = connection.createStatement();
                    ResultSet rs = stmtN.executeQuery( "SHOW CREATE TABLE " + db + "." + tableEntity.getTableName() );
                    if (rs != null && rs.next()) {
                        String createDDL = rs.getString( 2 );
                        String comment = parse( createDDL );
                        tableEntity.setComment( comment );
                    }
                    GatherDataSourceUtils.release( stmtN, rs );
                    tableEntities.add( tableEntity );
                }

            }

        } catch (SQLException e) {
            logger.error( e.getMessage(), e );
            e.printStackTrace();
        } finally {
            GatherDataSourceUtils.release( stmt, res );
        }
        return tableEntities;
    }

    /**
     * @param databases
     * @param table
     * @param connection
     * @return
     */
    @Override
    public DataSetVO getTaleFields(String databases, String table, Connection connection) {
        List<String> metas = Arrays.asList( "column_name", "type_name", "content" );
        List<List<Object>> datas = new ArrayList<>();
        DatabaseMetaData databaseMetaData = null;
        ResultSet colRet = null;
        try {
            databaseMetaData = connection.getMetaData();
            String columnName;
            String columnType;
            colRet = databaseMetaData.getColumns( databases, "%", table, "%" );
            while (colRet.next()) {
                columnName = colRet.getString( "COLUMN_NAME" );
                columnType = colRet.getString( "TYPE_NAME" );
                List<Object> datass = Arrays.asList( columnName, columnType.toLowerCase(), colRet.getString( 12 ) );
                datas.add( datass );
            }
        } catch (SQLException e) {
            logger.error( e.getMessage(), e );
            e.printStackTrace();
        } finally {
            GatherDataSourceUtils.release( null, colRet );
        }
        DataSetVO dataSetVO = new DataSetVO();
        dataSetVO.setDatas( datas );
        dataSetVO.setColumns( metas );
        dataSetVO.setDatabaseName( databases );
        dataSetVO.setTableName( table );
        return dataSetVO;
    }

    /**
     * 获取数据表
     *
     * @param tableEntity 　数据表
     * @return
     */
    @Override
    public DataSetVO getTableColumnsDatas(TableEntity tableEntity, Connection connection) {
        List<String> metas = new ArrayList<>();
        List<List<Object>> datas = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "select  * from " + tableEntity.getDatabaseName() + "." + tableEntity.getTableName();
            logger.info( "execute sql :" + sql );
            pstmt = connection.prepareStatement( sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
            rs = pstmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int colsSize = metaData.getColumnCount();
            for (int i = 1; i <= colsSize; i++) {
                metas.add( metaData.getColumnName( i ) );
            }
            List<Object> row;
            while (rs.next()) {
                row = new ArrayList<>();
                Object value;
                for (int i = 1; i <= colsSize; i++) {
                    value = rs.getObject( i );
                    if (value == null) {
                        row.add( null );
                    } else {
                        row.add( String.valueOf( value ) );
                    }
                }
                datas.add( row );
            }
        } catch (SQLException e) {
            logger.error( e.getMessage(), e );
            e.printStackTrace();
        } finally {
            GatherDataSourceUtils.release( pstmt, rs );
        }
        DataSetVO dataSetVO = new DataSetVO();
        dataSetVO.setDatas( datas );
        dataSetVO.setColumns( metas );
        dataSetVO.setDatabaseName( tableEntity.getDatabaseName() );
        dataSetVO.setTableName( tableEntity.getTableName() );
        return dataSetVO;
    }

    /**
     * 获取数据的列表
     *
     * @param tableEntity
     * @return
     */
    @Override
    public long countTableDatas(TableEntity tableEntity, Connection connection) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select  COUNT(1) from " + tableEntity.getDatabaseName() + "." + tableEntity.getTableName();
        long rowCount = 0L;
        try {
            Statement sta = connection.createStatement();
            ResultSet res = sta.executeQuery( sql );
            while (res.next()) {
                rowCount = res.getInt( 1 );
            }
        } catch (SQLException e) {
            logger.error( e.getMessage(), e );
            e.printStackTrace();
        } finally {
            GatherDataSourceUtils.release( pstmt, rs );
        }
        return rowCount;
    }

    /**
     * 分页查询
     *
     * @param tableEntity
     * @param page
     * @return
     */
    @Override
    public DataSetVO getTableColumnsDatas(TableEntity tableEntity, Page page, Connection connection) {
        List<String> metas = new ArrayList<>();
        List<List<Object>> datas = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "select  * from " + tableEntity.getDatabaseName() + "." + tableEntity.getTableName();
            logger.info( "execute sql :" + sql );
            pstmt = connection.prepareStatement( sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
            //关键代码，设置最大记录数为当前页记录的截止下标
            pstmt.setMaxRows( page.getEndIndex() );
            rs = pstmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int colsSize = metaData.getColumnCount();
            for (int i = 1; i <= colsSize; i++) {
                metas.add( metaData.getColumnName( i ) );
            }
            List<Object> row;
            int readRows = 1;
            while (rs.next()) {
                row = new ArrayList<>();
                if (readRows >= page.getBeginIndex()) {
                    Object value;
                    for (int i = 1; i <= colsSize; i++) {
                        value = rs.getObject( i );
                        if (value == null) {
                            row.add( null );
                        } else {
                            row.add( String.valueOf( value ) );
                        }
                    }
                    datas.add( row );
                }
                readRows++;
            }
        } catch (SQLException e) {
            logger.error( e.getMessage(), e );
            e.printStackTrace();
        } finally {
            GatherDataSourceUtils.release( pstmt, rs );
        }
        DataSetVO dataSetVO = new DataSetVO();
        dataSetVO.setDatas( datas );
        dataSetVO.setColumns( metas );
        dataSetVO.setDatabaseName( tableEntity.getDatabaseName() );
        dataSetVO.setTableName( tableEntity.getTableName() );
        return dataSetVO;
    }

}
