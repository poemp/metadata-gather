package org.poem.gen;

import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.*;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.Properties;


public class JooqPgGen {

    public static void main(String args[]) {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources( new ClassPathResource( "application-gen.yml" ) );
        Properties properties = yaml.getObject();
        JooqPgGen jg = new JooqPgGen();
        jg.gen( properties );
        System.out.println( "JooqPgGenerator finished." );
    }

    /**
     * Jooq 自动化生成代码
     */
    public void gen(Properties props) {

        String driverClassName = props.getProperty( "spring.datasource.driver-class-name" );
        String username = props.getProperty( "spring.datasource.username" );
        String url = props.getProperty( "spring.datasource.url" );
        String password = props.getProperty( "spring.datasource.password" );
        String schema = "kylo";

        Jdbc jdbc = new Jdbc().withDriver( driverClassName )
                .withUrl( url )
                .withUser( username )
                .withPassword( password );
        Database database = new Database()
                .withName( "org.jooq.meta.mysql.MySQLDatabase" )
                .withExcludes( "schema_version" )
                .withSchemata( new Schema().withInputSchema( schema ) );
        Generator generator = new Generator()
                .withName( "org.jooq.codegen.DefaultGenerator" )
                .withDatabase( database )
                .withGenerate( new Generate()
                )
                .withTarget( new Target()
                        .withPackageName( "org.poem.entities" ).withDirectory( "src/main/java" ) );
        Configuration conf = new Configuration()
                .withJdbc( jdbc )
                .withGenerator( generator );

        try {
            GenerationTool.generate( conf );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
