package org.poem.utils;

import org.poem.service.connect.DataType;

/**
 * @author Administrator
 */
public class ThreadUtils {

    /**
     * 数据类型
     */
    private static ThreadLocal<DataType> dataTypeThreadLocal = new ThreadLocal<>();

    private static ThreadLocal<String> taskId = new ThreadLocal<>();

    /**
     * 任务id
     * @return
     */
    public static ThreadLocal<String> getTaskId() {
        return taskId;
    }

    /**
     * 任务id
     * @param taskId
     */
    public static void setTaskId(String taskId) {
        ThreadUtils.taskId.set( taskId );
    }

    /**
     * 数据类型
     * @return
     */
    public static ThreadLocal<DataType> getDataTypeThreadLocal() {
        return ThreadUtils.dataTypeThreadLocal;
    }

    /**
     * 数据类型
     * @param dataType
     */
    public static void setDataTypeThreadLocal(DataType dataType) {
        ThreadUtils.dataTypeThreadLocal.set( dataType );
    }
}
