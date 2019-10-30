package org.poem.code.dao;


import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.SortField;
import org.jooq.TableRecord;
import org.jooq.exception.DataAccessException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BaseDao<R extends TableRecord<R>, T> {
    void insert(R object) throws DataAccessException;

    void insert(Collection<R> objects) throws DataAccessException;

    void update(R object) throws DataAccessException;

    <Z> void updateOneFieldById(T id, Field<Z> field, Z value) throws DataAccessException;

    <Z, S> void updateOneFieldByField(Field<Z> setField, Z setValue, Field<S> whereField, S whereValue) throws DataAccessException;

    void delete(R object) throws DataAccessException;

    void delete(Collection<R> objects) throws DataAccessException;

    void deleteById(Collection<T> ids) throws DataAccessException;

    R findById(T id) throws DataAccessException;

    <Z> List<R> fetch(Field<Z> field, Z... values) throws DataAccessException;

    <Z> List<R> fetch(Field<Z> field, Collection<Z> values) throws DataAccessException;

    <Z> R fetchOne(Field<Z> field, Z value) throws DataAccessException;

    <Z> Optional<R> fetchOptional(Field<Z> field, Z value) throws DataAccessException;


    List<R> findByConditions(List<Condition> conditions, Collection<SortField<?>> sortFields);

    List<R> findByCondition(Condition condition, Collection<SortField<?>> sortFields);

}
