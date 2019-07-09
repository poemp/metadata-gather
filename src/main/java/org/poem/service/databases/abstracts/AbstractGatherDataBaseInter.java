package org.poem.service.databases.abstracts;

import com.google.common.collect.Lists;
import org.poem.api.vo.column.entity.DataSetVO;
import org.poem.api.vo.databases.entity.DateBaseEntity;
import org.poem.api.vo.table.entity.TableEntity;
import org.poem.service.databases.GatherDataBaseInter;
import org.poem.utils.GatherDataSourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

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
@Service
public class AbstractGatherDataBaseInter implements GatherDataBaseInter {

    private static final Logger logger = LoggerFactory.getLogger( AbstractGatherDataBaseInter.class );

    /**
     *
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
                    tableEntities.add( tableEntity );
                }

            }

        } catch (SQLException e) {
            logger.error( e.getMessage(), e );
            e.printStackTrace();
        } finally {
            GatherDataSourceUtils.release(stmt, res );
        }
        return tableEntities;
    }

    /**
     *
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

}
