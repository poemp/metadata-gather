package org.poem.code.service.connect;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GatherBuilder {

    private static final Logger logger = LoggerFactory.getLogger( GatherBuilder.class );
    /**
     * 地址
     */
    private String ip;

    /**
     * 端口号
     */
    private String port;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String password;
    /**
     * s
     * 数据类型
     */
    private DataType dataType;

    /**
     * oracle 服务名字
     */
    private String serviceName;

    /**
     * @param ip
     * @return
     */
    public GatherBuilder ip(String ip) {
        this.ip = ip;
        return this;
    }

    public GatherBuilder port(String port) {
        this.port = port;
        return this;
    }

    public GatherBuilder user(String user) {
        this.userName = user;
        return this;
    }

    public GatherBuilder password(String password) {
        this.password = password;
        return this;
    }

    public GatherBuilder dataType(DataType dataType) {
        this.dataType = dataType;
        return this;
    }

    public GatherBuilder serverName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }


    /**
     * @return
     * @throws ClassNotFoundException
     */
    public GatherConnection budder() {
        if (StringUtils.isBlank( ip )) {
            throw new IllegalArgumentException( "ip not null !!!!!!!" );
        }
        if (StringUtils.isBlank( password )) {
            throw new IllegalArgumentException( "password not null !!!!!!! " );
        }
        if (StringUtils.isBlank( userName )) {
            throw new IllegalArgumentException( "user not null !!!!!!! " );
        }
        if (StringUtils.isBlank( port )) {
            throw new IllegalArgumentException( "port not null !!!!!!! " );
        }
        if (dataType == null) {
            throw new IllegalArgumentException( "dataType not null !!!!!!!" );
        }
        if (StringUtils.isBlank( serviceName ) && dataType.eq( DataType.ORACLE )) {
            throw new IllegalArgumentException( "oracle server name can\'t  be null !!!!!!!" );
        }
        String url = null;
        try {
            if (dataType.equals( DataType.MYSQL )) {
                Class.forName( "com.mysql.cj.jdbc.Driver" );
                url = "jdbc:mysql://" + ip + ":" + port + "?useSSL=false&serverTimezone=UTC";
            } else if (dataType.eq( DataType.ORACLE )) {
                Class.forName( "oracle.jdbc.driver.OracleDriver" );
                //jdbc:oracle:thin:@//localhost:1521:orcl
                //url = "jdbc:oracle:thin:@" + ip + ":" + port + ":orcl";
                url = "jdbc:oracle:thin:@(description=(address=(protocol=tcp)(port=" + port + ")(host=" + ip + "))(connect_data=(service_name=" + serviceName + ")))";
            } else {
                throw new IllegalArgumentException( "data type[" + dataType + "] is not  unrecognizable !!!!!!" );
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            logger.error( e.getLocalizedMessage(), e );
        }
        if (url == null) {
            throw new NullPointerException( "url is null!!!!!!" );
        }
        return new GatherConnection( url, password, userName );
    }
}
