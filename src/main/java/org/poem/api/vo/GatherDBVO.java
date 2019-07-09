package org.poem.api.vo;

import lombok.Data;

import java.util.List;
@Data
public class GatherDBVO {

    private  DbVO dbVO;

    List<GatherTableVO> gatherTableVOS;
}
