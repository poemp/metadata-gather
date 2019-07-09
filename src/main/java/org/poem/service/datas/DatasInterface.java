package org.poem.service.datas;

import org.poem.api.vo.column.entity.DataSetVO;
import org.poem.api.vo.page.Page;
import org.poem.api.vo.table.entity.TableEntity;

import java.sql.Connection;

/**
 * @author Administrator
 */
public interface DatasInterface {

    /**
     * 获取数据表的数据
     *
     * @param tableEntity 　数据表
     * @return
     */
    DataSetVO getTableColumnsDatas(TableEntity tableEntity, Connection connection);

    /**
     * 所有数据的个数
     *
     * @param tableEntity
     * @return
     */
    long countTableDatas(TableEntity tableEntity, Connection connection);


    /**
     * 分页
     *
     * @param tableEntity
     * @param page
     * @return
     */
    DataSetVO getTableColumnsDatas(TableEntity tableEntity, Page page, Connection connection);

}
