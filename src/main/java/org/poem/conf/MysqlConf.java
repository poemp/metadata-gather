package org.poem.conf;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 */
@Configuration
public class MysqlConf {

    private static final Logger logger = LoggerFactory.getLogger( MysqlConf.class );
    @Value("${spring.datasource.url}")
    private String connectionUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    /**
     * 配置数据源
     *
     * @return
     */
    @Bean(name = "mysqlDatasource")
    public MysqlDataSource initDataSource() {
        logger.info( "Init Mysql Datasource" );
        MysqlDataSource basicDataSource = new MysqlDataSource();
        basicDataSource.setUrl( connectionUrl );
        basicDataSource.setUser( username );
        basicDataSource.setPassword( password );
        return basicDataSource;
    }

}
