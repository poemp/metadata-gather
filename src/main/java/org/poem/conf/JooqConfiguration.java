package org.poem.conf;


import com.mysql.cj.jdbc.MysqlDataSource;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jdbc.repository.config.JdbcConfiguration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

/**
 * @author Administrator
 */
@Configuration
@EnableJdbcRepositories
@Import(JdbcConfiguration.class)
public class JooqConfiguration {

    @Autowired
    private MysqlDataSource dataSource;

    @Bean
    public DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
    }

    @Bean
    DefaultDSLContext dsl() {

        Settings settings = new Settings();
        settings.withRenderSchema(false);
        org.jooq.Configuration conf = new DefaultConfiguration();
        TransactionAwareDataSourceProxy proxy = new TransactionAwareDataSourceProxy(dataSource);
        conf.set(new DataSourceConnectionProvider(proxy))
                .set(settings)
                .set( SQLDialect.MYSQL);
        DefaultDSLContext defaultDSLContext = new DefaultDSLContext(conf);
        return defaultDSLContext;
    }

    public DefaultConfiguration configuration() {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.set(connectionProvider());
        return jooqConfiguration;
    }

}
