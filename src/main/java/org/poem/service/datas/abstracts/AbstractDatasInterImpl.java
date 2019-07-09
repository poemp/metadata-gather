package org.poem.service.datas.abstracts;


import org.poem.api.vo.column.entity.DataSetVO;
import org.poem.api.vo.page.Page;
import org.poem.api.vo.table.entity.TableEntity;
import org.poem.service.datas.DatasInterface;
import org.poem.utils.GatherDataSourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Administrator
 */
@Service
public class AbstractDatasInterImpl implements DatasInterface {

    /**
     *
     */
    private static final Logger logger = LoggerFactory.getLogger( AbstractDatasInterImpl.class );

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
