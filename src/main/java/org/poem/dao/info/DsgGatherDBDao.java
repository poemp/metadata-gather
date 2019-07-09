package org.poem.dao.info;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.poem.dao.BaseDaoImpl;
import org.poem.entities.tables.DsgGatherDb;
import org.poem.entities.tables.records.DsgGatherDbRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DsgGatherDBDao extends BaseDaoImpl<DsgGatherDbRecord, String> {


    @Autowired
    private DSLContext dslContext;

    public DsgGatherDBDao() {
        super( DsgGatherDb.DSG_GATHER_DB );
    }

    @Override
    public Configuration getConfiguration() {
        return dslContext.configuration();
    }
}
