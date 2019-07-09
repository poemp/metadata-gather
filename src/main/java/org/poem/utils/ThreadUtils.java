package org.poem.utils;

import org.poem.service.connect.DataType;

/**
 * @author Administrator
 */
public class ThreadUtils {

    private static ThreadLocal<DataType> dataTypeThreadLocal = new ThreadLocal<>();

    public static ThreadLocal<DataType> getDataTypeThreadLocal() {
        return ThreadUtils.dataTypeThreadLocal;
    }

    public static void setDataTypeThreadLocal(DataType dataType) {
        ThreadUtils.dataTypeThreadLocal.set( dataType );
    }
}
