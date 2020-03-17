package com.bbgu.zmz.community.baidu.dto;


import lombok.Data;

import java.util.List;

@Data
public class Hits {
    private float probability;
    private String datasetName;
    private List<String> words;


}
