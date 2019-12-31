package com.bbgu.zmz.community.controller;

import com.bbgu.zmz.community.dto.RegRespObj;
import com.bbgu.zmz.community.model.WeekList;
import com.bbgu.zmz.community.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ListService listService;

    @PostMapping("/upload")
    public @ResponseBody RegRespObj upload(@RequestParam MultipartFile file, HttpServletRequest request) throws IOException {
        RegRespObj regRespObj = new RegRespObj();
        if (file.getSize()>0){
            String realPath = request.getServletContext().getRealPath("/upload");
            File file1 = new File(realPath);
            if(!file1.exists()){
                file1.mkdirs();
            }
            UUID uuid = UUID.randomUUID();
            File file2 = new File(realPath+File.separator+uuid+file.getOriginalFilename());
            file.transferTo(file2);
            regRespObj.setUrl(request.getServletContext().getContextPath()+"/upload/"+uuid+file.getOriginalFilename());
            regRespObj.setStatus(0);
            regRespObj.setMsg("上传成功！");
        }else{
            regRespObj.setStatus(1);
            regRespObj.setMsg("上传失败！");
        }
        return regRespObj;

    }

    @PostMapping("/top")
    public @ResponseBody RegRespObj weekList(Long limit){
        List<WeekList> weekListList = listService.weekList();
        RegRespObj regRespObj = new RegRespObj();
        regRespObj.setStatus(0);
        regRespObj.setWeekList(weekListList);
        return  regRespObj;
    }

}
