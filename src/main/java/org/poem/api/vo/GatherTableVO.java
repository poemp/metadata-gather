package org.poem.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class GatherTableVO {

    private TableVO tableVO;

    private List<TableFieldsVO> tableFieldsVOS;
}
