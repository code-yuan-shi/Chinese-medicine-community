package com.bbgu.zmz.community.mapper;


import com.bbgu.zmz.community.model.QiandaoExt;

import java.util.List;


public interface QiandaoExtMapper {
    List<QiandaoExt> getNewSign();
    List<QiandaoExt> getTodaySign();
    List<QiandaoExt> getTotalSign();

}