package org.poem.code.service.connect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Administrator
 */
public class GatherConnection implements Closeable {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger( GatherConnection.class );
    /**
     * 地址
     */
    private String url;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 数据类型
     */
    private String userName;

    /**
     * 数据连接
     */

    private Connection connection;

    /**
     * 初始化
     *
     * @param url
     * @param password
     * @param userName
     */
    public GatherConnection(String url, String password, String userName) {
        this.url = url;
        this.password = password;
        this.userName = userName;
        try {
            connection = DriverManager.getConnection( url, userName, password );
        } catch (SQLException e) {
            logger.error( e.getMessage(), e );
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    public Connection getConnection() {
        return connection;
    }


    /**
     * 必须关闭
     *
     * @throws IOException
     */
    @Override
    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            logger.error( e.getMessage(), e );
            e.printStackTrace();
        }
    }
}
