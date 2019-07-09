/*
 * This file is generated by jOOQ.
 */
package org.poem.entities.tables;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.poem.entities.Indexes;
import org.poem.entities.Keys;
import org.poem.entities.Kylo;
import org.poem.entities.tables.records.DsgGatherDbRecord;


/**
 * 元数据数据库信息
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DsgGatherDb extends TableImpl<DsgGatherDbRecord> {

    private static final long serialVersionUID = 1727846891;

    /**
     * The reference instance of <code>kylo.dsg_gather_db</code>
     */
    public static final DsgGatherDb DSG_GATHER_DB = new DsgGatherDb();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DsgGatherDbRecord> getRecordType() {
        return DsgGatherDbRecord.class;
    }

    /**
     * The column <code>kylo.dsg_gather_db.id</code>. id
     */
    public final TableField<DsgGatherDbRecord, String> ID = createField("id", org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "id");

    /**
     * The column <code>kylo.dsg_gather_db.gather_id</code>. 元数据采集信息
     */
    public final TableField<DsgGatherDbRecord, String> GATHER_ID = createField("gather_id", org.jooq.impl.SQLDataType.VARCHAR(500).nullable(false), this, "元数据采集信息");

    /**
     * The column <code>kylo.dsg_gather_db.schema</code>. schema
     */
    public final TableField<DsgGatherDbRecord, String> SCHEMA = createField("schema", org.jooq.impl.SQLDataType.VARCHAR(500), this, "schema");

    /**
     * The column <code>kylo.dsg_gather_db.data_type</code>. 数据类型
     */
    public final TableField<DsgGatherDbRecord, String> DATA_TYPE = createField("data_type", org.jooq.impl.SQLDataType.VARCHAR(225), this, "数据类型");

    /**
     * The column <code>kylo.dsg_gather_db.description</code>. 描述
     */
    public final TableField<DsgGatherDbRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR(225), this, "描述");

    /**
     * The column <code>kylo.dsg_gather_db.create_time</code>.
     */
    public final TableField<DsgGatherDbRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>kylo.dsg_gather_db.update_time</code>.
     */
    public final TableField<DsgGatherDbRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * Create a <code>kylo.dsg_gather_db</code> table reference
     */
    public DsgGatherDb() {
        this(DSL.name("dsg_gather_db"), null);
    }

    /**
     * Create an aliased <code>kylo.dsg_gather_db</code> table reference
     */
    public DsgGatherDb(String alias) {
        this(DSL.name(alias), DSG_GATHER_DB);
    }

    /**
     * Create an aliased <code>kylo.dsg_gather_db</code> table reference
     */
    public DsgGatherDb(Name alias) {
        this(alias, DSG_GATHER_DB);
    }

    private DsgGatherDb(Name alias, Table<DsgGatherDbRecord> aliased) {
        this(alias, aliased, null);
    }

    private DsgGatherDb(Name alias, Table<DsgGatherDbRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("元数据数据库信息"));
    }

    public <O extends Record> DsgGatherDb(Table<O> child, ForeignKey<O, DsgGatherDbRecord> key) {
        super(child, key, DSG_GATHER_DB);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Kylo.KYLO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.DSG_GATHER_DB_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<DsgGatherDbRecord> getPrimaryKey() {
        return Keys.KEY_DSG_GATHER_DB_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<DsgGatherDbRecord>> getKeys() {
        return Arrays.<UniqueKey<DsgGatherDbRecord>>asList(Keys.KEY_DSG_GATHER_DB_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DsgGatherDb as(String alias) {
        return new DsgGatherDb(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DsgGatherDb as(Name alias) {
        return new DsgGatherDb(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public DsgGatherDb rename(String name) {
        return new DsgGatherDb(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public DsgGatherDb rename(Name name) {
        return new DsgGatherDb(name, null);
    }
}
