package org.poem.service.databases;

import com.google.common.collect.Lists;
import org.poem.api.vo.column.entity.DataSetVO;
import org.poem.api.vo.databases.entity.DateBaseEntity;
import org.poem.api.vo.table.entity.TableEntity;
import org.poem.service.databases.abstracts.AbstractGatherDataBaseInter;
import org.poem.utils.GatherDataSourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 */
@Service(OrcaleGatherData.BEAN)
public class OrcaleGatherData extends AbstractGatherDataBaseInter {

    public static final String BEAN = "orcale_gather_data";

    private static final Logger logger = LoggerFactory.getLogger( OrcaleGatherData.class );


    /**
     * @param connection
     * @return
     */
    @Override
    public List<DateBaseEntity> getDataBases(Connection connection) {
        List<DateBaseEntity> databases = Lists.newArrayList();
        if (connection != null) {
            Statement stmt = null;
            ResultSet res = null;
            try {
                stmt = connection.createStatement();
                res = stmt.executeQuery( "select DISTINCT OWNER from all_tab_comments where TABLE_TYPE='TABLE' " );
                while (res.next()) {
                    DateBaseEntity entity = new DateBaseEntity();
                    entity.setDatabaseName( res.getString( 1 ) );
                    databases.add( entity );
                }
            } catch (SQLException e) {
                logger.error( e.getMessage(), e );
                e.printStackTrace();
            } finally {
                GatherDataSourceUtils.release( stmt, res );
            }
        }
        return databases;
    }

    /**
     *
     * @param schema
     * @param conn
     * @return
     */
    @Override
    public List<TableEntity> getAllTableName(String schema, Connection conn) {
        Statement stmt = null;
        ResultSet res = null;
        if (conn == null) {
            return Lists.newArrayList();
        }
        List<TableEntity> tableEntities = Lists.newArrayList();
        try {
            boolean cl = conn.isClosed();
            if (!cl) {
                stmt = conn.createStatement();
                res = stmt.executeQuery( "select TABLE_NAME, COMMENTS from all_tab_comments where TABLE_TYPE='TABLE' and OWNER = '" + schema + "'" );
                while (res.next()) {
                    TableEntity tableEntity = new TableEntity();
                    tableEntity.setDatabaseName( schema );
                    tableEntity.setComment( res.getString( 2 ) );
                    tableEntity.setTableName( res.getString( 1 ) );
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
            colRet = databaseMetaData.getColumns( null, databases, table.toUpperCase(), "%" );
            while (colRet.next()) {
                columnName = colRet.getString( "COLUMN_NAME" );
                columnType = colRet.getString( "TYPE_NAME" );
                List<Object> datass = Arrays.asList( columnName, columnType.toLowerCase(), colRet.getString( "REMARKS" ) );
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
