package org.poem.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class GatherDBTableFieldsVO {

    private String gratherid;

    private List<GatherDBVO> gatherDBVOS;
}
