package com.bbgu.zmz.community.service;

import com.bbgu.zmz.community.mapper.AdMapper;
import com.bbgu.zmz.community.model.Ad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class AdService {

    @Autowired
    private AdMapper adMapper;

    public List<Ad> list(String pos){
        Example example = new Example(Ad.class);
        example.createCriteria()
                .andEqualTo("status",1)
                .andEqualTo("pos",pos)
                .andLessThan("adStart",System.currentTimeMillis())
                .andGreaterThan("adEnd",System.currentTimeMillis());
        return  adMapper.selectByExample(example);
    }

}
