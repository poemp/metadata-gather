package org.poem.code.service.databases;

import org.poem.code.service.databases.abstracts.AbstractGatherDataBaseInter;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service(MysqlGatherData.BEAN)
public class MysqlGatherData extends AbstractGatherDataBaseInter {
    public static final String BEAN = "mysql_gather_data";
}
