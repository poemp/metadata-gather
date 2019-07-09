package org.poem.service;


import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.poem.MetadataGatherApp;
import org.poem.api.GatherService;
import org.poem.api.MetadataService;
import org.poem.api.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@SpringJUnitWebConfig(classes = {MetadataGatherApp.class})
@RunWith(SpringRunner.class)
public class MetadataServiceImplTest {


    @Autowired
    private MetadataService metadataService;

    @Autowired
    private GatherService gatherService;

    @org.junit.Test
    public void savgeGather() {
        GatherVO gatherVO = new GatherVO();
        gatherVO.setIp( "192.168.24.250" );
        gatherVO.setUserName( "root" );
        gatherVO.setPassword( "123456" );
        gatherVO.setType( "MYSQL" );
        gatherVO.setPort( "3306" );
        gatherVO.setName( "测试Mysql 数据库" );
        metadataService.savgeGather( gatherVO );
    }

    @org.junit.Test
    public void savgeOrcaleGather() {
        GatherVO gatherVO = new GatherVO();
        gatherVO.setIp( "192.168.23.210" );
        gatherVO.setUserName( "dsg" );
        gatherVO.setPassword( "dsg" );
        gatherVO.setType( "ORACLE" );
        gatherVO.setPort( "1521" );
        gatherVO.setName( "测试 ORACLE 数据库" );
        metadataService.savgeGather( gatherVO );
    }

    @org.junit.Test
    public void getAllGather() {
        List<GatherVO> gatherVOList = this.metadataService.getAllGather();
        gatherVOList.forEach(
                o -> {
                    System.out.println( JSONObject.toJSONString( o ) );
                }
        );
    }

    @org.junit.Test
    public void saveDB() {
        List<DbVO> dbVOS = gatherService.getSchema( "346086619914309632" );
        for (DbVO dbVO : dbVOS) {
            System.out.println( JSONObject.toJSONString( dbVO ) );
        }
        this.metadataService.deleteAllDataGatherInfoId( "346086619914309632" );
        this.metadataService.saveDB( dbVOS, "346086619914309632" );
    }


    @org.junit.Test
    public void getAllDB() {
        List<DbVO> dbVOS = metadataService.getAllDB( "346086619914309632" );
        for (DbVO dbVO : dbVOS) {
            System.out.println( JSONObject.toJSONString( dbVO ) );
        }
    }

    @org.junit.Test
    public void saveTable() {
        GatherVO gatherVO = this.metadataService.getGatherVOById( "346086619914309632" );
        gatherVO.setDbId( "346000843771875330" );
        List<TableVO> tableVOS = this.gatherService.getTable( gatherVO );
        for (TableVO tableVO : tableVOS) {
            System.out.println( JSONObject.toJSONString( tableVO ) );
        }
        this.metadataService.saveTable( tableVOS, "346000843771875330" );
    }

    @org.junit.Test
    public void getAllTable() {
        List<TableVO> tableVOS = metadataService.getTable( new TableQueryVO( "346000843771875330", null, null ) );
        for (TableVO tableVO : tableVOS) {
            System.out.println( JSONObject.toJSONString( tableVO ) );
        }
    }

    @org.junit.Test
    public void saveTableFields() {
        TableVO tableVO = this.gatherService.getTableById( "346006017080954881" );
        List<TableFieldsVO> tableFieldsVOS = gatherService.getTableFieles( tableVO );
        for (TableFieldsVO tableFieldsVO : tableFieldsVOS) {
            System.out.println( JSONObject.toJSONString( tableFieldsVO ) );
        }
        this.metadataService.saveTableFields( tableFieldsVOS, "346006017080954881" );
    }


    @Test
    public void getAllDataTableFiels() {
        List<GatherVO> gatherVOList = this.metadataService.getAllGather();
        gatherVOList.forEach(
                o -> {
                    String gatherId = o.getGatherId();
                    GatherDBTableFieldsVO gatherDBTableFieldsVO = this.gatherService.getAllGatherDBTableFieldsVO( gatherId );
                    this.metadataService.saveGather( gatherDBTableFieldsVO );
                }
        );
    }



    @Test
    public void getAllDataTableFiels1() {
        GatherDBTableFieldsVO gatherDBTableFieldsVO = this.gatherService.getAllGatherDBTableFieldsVO( "346086619914309632" );
        this.metadataService.saveGather( gatherDBTableFieldsVO );
    }
}
