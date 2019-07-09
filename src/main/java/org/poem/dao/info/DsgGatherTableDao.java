package org.poem.dao.info;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.poem.dao.BaseDaoImpl;
import org.poem.entities.tables.DsgGatherTable;
import org.poem.entities.tables.records.DsgGatherTableRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 */
@Repository
public class DsgGatherTableDao extends BaseDaoImpl<DsgGatherTableRecord , String> {


    @Autowired
    private DSLContext dslContext;

    public DsgGatherTableDao() {
        super( DsgGatherTable.DSG_GATHER_TABLE);
    }

    @Override
    public Configuration getConfiguration() {
        return dslContext.configuration();
    }
}
