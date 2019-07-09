package org.poem.dao;


import org.jooq.*;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class BaseDaoImpl<R extends UpdatableRecord<R>, T> implements BaseDao<R, T> {

    private final Table<R> table;

    public BaseDaoImpl(Table<R> table) {
        this.table = table;
    }

    @PostConstruct
    public abstract Configuration getConfiguration();

    @Override
    public void insert(R object) throws DataAccessException {
        DSL.using( getConfiguration() ).insertInto( object.getTable() ).set( object ).execute();
    }

    @Override
    public void insert(Collection<R> objects) throws DataAccessException {
        DSL.using( getConfiguration() ).batchInsert( objects ).execute();

    }

    @Override
    public void update(R object) throws DataAccessException {
        DSL.using( getConfiguration() ).executeUpdate( object );
    }

    @Override
    public <Z> void updateOneFieldById(T id, Field<Z> field, Z value) throws DataAccessException {
        Field<T> primaryField = getPrimaryKey();
        if (primaryField != null) {
            DSL.using( getConfiguration() ).update( table ).set( field, value ).where( primaryField.eq( id ) ).execute();
        }
    }

    @Override
    public <Z, S> void updateOneFieldByField(Field<Z> setField, Z setValue, Field<S> whereField, S whereValue) throws DataAccessException {
        DSL.using( getConfiguration() ).update( table ).set( setField, setValue ).where( whereField.eq( whereValue ) ).execute();
    }

    @Override
    public void delete(R object) throws DataAccessException {
        DSL.using( getConfiguration() ).executeDelete( object );
    }

    @Override
    public void delete(Collection<R> objects) throws DataAccessException {
        DSL.using( getConfiguration() ).batchDelete( objects ).execute();
    }

    @Override
    public void deleteById(Collection<T> ids) throws DataAccessException {
        Field<T> primaryField = getPrimaryKey();
        if (primaryField != null && ids.size() > 0) {
            DSL.using( getConfiguration() ).delete( this.table ).where( primaryField.in( ids ) ).execute();
        }
    }

    @Override
    public R findById(T id) throws DataAccessException {
        Field<T> primaryField = getPrimaryKey();
        if (primaryField != null) {
            return DSL.using( getConfiguration() ).selectFrom( this.table ).where( primaryField.eq( id ) ).fetchAny();
        }
        return null;
    }

    @Override
    public <Z> List<R> fetch(Field<Z> field, Z... values) throws DataAccessException {
        return DSL.using( getConfiguration() ).selectFrom( this.table ).where( new Condition[]{field.in( values )} ).fetch();
    }

    public List<R> findByIds(T... ids) throws DataAccessException {
        Field<T> primaryField = getPrimaryKey();
        if (primaryField != null) {
            return DSL.using( getConfiguration() ).selectFrom( this.table ).where( primaryField.in( ids ) ).fetch();
        }
        return new ArrayList<>();
    }

    public List<R> findByIdList(Collection<T> ids) throws DataAccessException {
        Field<T> primaryField = getPrimaryKey();
        if (primaryField != null) {
            return DSL.using( getConfiguration() ).selectFrom( this.table ).where( primaryField.in( ids ) ).fetch();
        }
        return new ArrayList<>();
    }

    @Override
    public <Z> List<R> fetch(Field<Z> field, Collection<Z> values) throws DataAccessException {
        return DSL.using( getConfiguration() ).selectFrom( this.table ).where( new Condition[]{field.in( values )} ).fetch();
    }

    @Override
    public <Z> R fetchOne(Field<Z> field, Z value) throws DataAccessException {
        return DSL.using( getConfiguration() ).selectFrom( this.table ).where( new Condition[]{field.eq( value )} ).fetchOne();
    }

    @Override
    public <Z> Optional<R> fetchOptional(Field<Z> field, Z value) throws DataAccessException {
        return Optional.ofNullable( this.fetchOne( field, value ) );
    }


    private Field<T> getPrimaryKey() {
        UniqueKey key = this.table.getPrimaryKey();
        if (key == null || key.getFields() == null || key.getFields().isEmpty()) {
            return null;
        }
        List<TableField<R, T>> fields = key.getFields();
        return fields.get( 0 );
    }

    @Override
    public List<R> findByConditions(List<Condition> conditions, Collection<SortField<?>> sortFields) {
        return DSL.using( getConfiguration() ).selectFrom( this.table ).where( conditions ).orderBy( sortFields ).fetch();
    }

    public List<R> findByConditions(List<Condition> conditions) {
        return DSL.using( getConfiguration() ).selectFrom( this.table ).where( conditions ).fetch();
    }

    public List<R> findByCondition(Condition condition) {
        return DSL.using( getConfiguration() ).selectFrom( this.table ).where( condition ).fetch();
    }

    public List<R> findAll() {
        return DSL.using( getConfiguration() ).selectFrom( this.table ).fetch();
    }

    @Override
    public List<R> findByCondition(Condition condition, Collection<SortField<?>> sortFields) {
        return DSL.using( getConfiguration() ).selectFrom( this.table ).where( condition ).orderBy( sortFields ).fetch();
    }

    public void deleteByConditions(Condition... conditions) {
        DSL.using( getConfiguration() ).deleteFrom( this.table ).where( conditions ).execute();
    }

    public void deleteByConditions(Collection<Condition> conditions) {
        DSL.using( getConfiguration() ).deleteFrom( this.table ).where( conditions ).execute();
    }

    public <Z> Optional<R> fetchAny() throws DataAccessException {
        return Optional.ofNullable( DSL.using( getConfiguration() ).selectFrom( this.table ).limit( 1 ).fetchAny() );
    }

    public List<R> findByCondition(Collection<SortField<?>> sortFields) {
        return DSL.using( getConfiguration() ).selectFrom( this.table ).orderBy( sortFields ).fetch();
    }

}
