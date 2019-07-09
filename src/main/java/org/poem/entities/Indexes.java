/*
 * This file is generated by jOOQ.
 */
package org.poem.entities;


import javax.annotation.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;
import org.poem.entities.tables.DsgGatherDb;
import org.poem.entities.tables.DsgGatherInfo;
import org.poem.entities.tables.DsgGatherTable;
import org.poem.entities.tables.DsgGatherTableFields;


/**
 * A class modelling indexes of tables of the <code>kylo</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index DSG_GATHER_DB_PRIMARY = Indexes0.DSG_GATHER_DB_PRIMARY;
    public static final Index DSG_GATHER_INFO_PRIMARY = Indexes0.DSG_GATHER_INFO_PRIMARY;
    public static final Index DSG_GATHER_TABLE_PRIMARY = Indexes0.DSG_GATHER_TABLE_PRIMARY;
    public static final Index DSG_GATHER_TABLE_FIELDS_PRIMARY = Indexes0.DSG_GATHER_TABLE_FIELDS_PRIMARY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index DSG_GATHER_DB_PRIMARY = Internal.createIndex("PRIMARY", DsgGatherDb.DSG_GATHER_DB, new OrderField[] { DsgGatherDb.DSG_GATHER_DB.ID }, true);
        public static Index DSG_GATHER_INFO_PRIMARY = Internal.createIndex("PRIMARY", DsgGatherInfo.DSG_GATHER_INFO, new OrderField[] { DsgGatherInfo.DSG_GATHER_INFO.ID }, true);
        public static Index DSG_GATHER_TABLE_PRIMARY = Internal.createIndex("PRIMARY", DsgGatherTable.DSG_GATHER_TABLE, new OrderField[] { DsgGatherTable.DSG_GATHER_TABLE.ID }, true);
        public static Index DSG_GATHER_TABLE_FIELDS_PRIMARY = Internal.createIndex("PRIMARY", DsgGatherTableFields.DSG_GATHER_TABLE_FIELDS, new OrderField[] { DsgGatherTableFields.DSG_GATHER_TABLE_FIELDS.ID }, true);
    }
}
