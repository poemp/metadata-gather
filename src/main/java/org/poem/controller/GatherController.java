package org.poem.controller;

import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.poem.api.GatherService;
import org.poem.api.MetadataService;
import org.poem.api.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/v1/gather")
@Api(value = "/v1/gather", tags = {"01-元数据采集"})
public class GatherController {

    @Autowired
    private GatherService gatherService;

    @Autowired
    private MetadataService metadataService;


    /**
     *
     * @return
     */
    @ApiOperation(value = "0101-获取所有的连接信息", notes = "获取所有的连接信息")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "请求路径没有找到"),
            @ApiResponse(code = 500, message = "数据库发生错误")
    })
    @GetMapping(value = "/getAllGather")
    public List<GatherVO> getAllGather() {
        return this.metadataService.getAllGather();
    }

    /**
     * 保存连接信息
     * @param gatherVO
     */
    @ApiOperation(value = "0102-保存连接信息", notes = "保存连接信息")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "请求路径没有找到"),
            @ApiResponse(code = 500, message = "数据库发生错误")
    })
    @PostMapping(value = "/savgeGather")
    public void savgeGather(@RequestBody GatherVO gatherVO) {
        this.metadataService.savgeGather( gatherVO );
    }

    /**
     * 删除所有保存的连接信息
     * @param gatherId
     */
    @ApiOperation(value = "0103-删除所有保存的连接信息", notes = "删除所有保存的连接信息")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "请求路径没有找到"),
            @ApiResponse(code = 500, message = "数据库发生错误")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gatherId", required = true, value = "连接信息id", dataType = "String", example = "123456"),
    })
    @PostMapping(value = "/deleteAllDataGatherInfoId")
    public void deleteAllDataGatherInfoId(String gatherId) {
        this.metadataService.deleteAllDataGatherInfoId( gatherId );
    }



    /**
     * 根据信息连接信息获取库
     * @param queryGatherDBTableFieldsVO
     * @return
     */
    @ApiOperation(value = "0103-根据信息连接信息获取库、表、字端", notes = "根据信息连接信息获取库、表、字端")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "请求路径没有找到"),
            @ApiResponse(code = 500, message = "数据库发生错误")
    })
    @GetMapping(value = "/getDBAndTableAndFields")
    public GatherDBTableFieldsVO getDBAndTableAndFields(QueryGatherDBTableFieldsVO queryGatherDBTableFieldsVO) {
        if (StringUtils.isBlank( queryGatherDBTableFieldsVO.getGratherid() )){
            return null;
        }
        return this.metadataService.getDBTableAndFields( queryGatherDBTableFieldsVO );
    }

    /**
     * 根据信息连接信息获取库
     * @param gatherId
     * @return
     */
    @ApiOperation(value = "0105-数据库-测试连接是否正常", notes = "数据库-测试连接是否正常")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "请求路径没有找到"),
            @ApiResponse(code = 500, message = "数据库发生错误")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gatherId", required = true, value = "连接信息id", dataType = "String", example = "123456"),
    })
    @GetMapping(value = "/testGather/{gatherId}")
    public boolean testGather(@PathVariable("gatherId") String gatherId){
        return  gatherService.testGather( gatherId );
    }

    /**
     * 获取数据的schema
     *
     * @param gatherId
     * @return
     */

    @ApiOperation(value = "0106-数据库-获取数据的schema", notes = "数据库-获取数据的schema")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "请求路径没有找到"),
            @ApiResponse(code = 500, message = "数据库发生错误")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gatherId", required = true, value = "连接信息id", dataType = "String", example = "123456"),
    })
    @GetMapping(value = "/getSchema/{gatherId}")
    public List<DbVO> getSchema(@PathVariable("gatherId") String gatherId){
        return gatherService.getSchema( gatherId );
    }


    /**
     * 获取数据表字端信息
     * @param gatherVO
     * @return
     */
    @ApiOperation(value = "0107-数据库-获取数据表字端信息", notes = "获取数据表字端信息")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "请求路径没有找到"),
            @ApiResponse(code = 500, message = "数据库发生错误")
    })
    @GetMapping(value = "/getTable")
    public  List<TableVO>  getTable(GatherVO gatherVO){
        return gatherService.getTable( gatherVO );
    }

    /**
     * 获取表的字端信息
     * @param tableVO
     * @return
     */
    @ApiOperation(value = "0108-数据库-获取表的字端信息", notes = "获取表的字端信息")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "请求路径没有找到"),
            @ApiResponse(code = 500, message = "数据库发生错误")
    })
    @GetMapping(value = "/getTableFieles")
    public  List<TableFieldsVO> getTableFieles(TableVO tableVO){
        return gatherService.getTableFieles( tableVO );
    }


    /**
     * 根据信息连接信息获取库
     * @param gatherId
     */
    @ApiOperation(value = "0109-数据库-根据信息连接信息获取库、表、字端,并且保存到数据库中",
            notes = "数据库-根据信息连接信息获取库、表、字端,并且保存到数据库中")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没有填好"),
            @ApiResponse(code = 404, message = "请求路径没有找到"),
            @ApiResponse(code = 500, message = "数据库发生错误")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gatherId", required = true, value = "连接信息id", dataType = "String", example = "123456"),
            @ApiImplicitParam(name = "db", required = false, value = "采集的数据库", dataType = "String", example = "test"),
            @ApiImplicitParam(name = "table", required = false, value = "采集的数据表", dataType = "String", example = "test")
    })
    @PostMapping(value = "/getDbAndTableAndFieldAndSave")
    public void getDbAndTableAndFieldAndSave(String gatherId,String  db, String table) {
        GatherDBTableFieldsVO gatherDBTableFieldsVO = this.gatherService.getAllGatherDBTableFieldsVO( gatherId , db, table);
        this.metadataService.saveGather( gatherDBTableFieldsVO );
    }


}
