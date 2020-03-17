package com.bbgu.zmz.community.baidu.dto;


import lombok.Data;

import java.util.List;

@Data
public class DataMsg {

    private Integer type;
    private Integer subType;
    private String conclusion;
    private Long conclusionType;
    private String msg;
    private List<Hits> hits;

}
