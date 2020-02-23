package com.bbgu.zmz.community.mapper;


import com.bbgu.zmz.community.model.WeekList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserExtMapper {
     List<WeekList> getTopUsers();
     void rewardUserAdd(@Param("kissNum") Long kissNum,
                        @Param("userId") Long userId);
     void rewardUserSub(@Param("kissNum") Long kissNum,
                        @Param("userId") Long userId);
}