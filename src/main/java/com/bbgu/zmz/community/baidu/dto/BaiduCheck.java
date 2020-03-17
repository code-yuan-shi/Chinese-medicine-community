package com.bbgu.zmz.community.baidu.dto;


import lombok.Data;

import java.util.List;

@Data
public class BaiduCheck {
    private Long log_id;
    private Long error_code;
    private String error_msg;
    private String conclusion;
    private Long conclusionType;
    private List<DataMsg> data;
}
