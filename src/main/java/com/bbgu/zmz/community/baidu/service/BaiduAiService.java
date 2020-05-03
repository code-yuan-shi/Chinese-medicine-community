package com.bbgu.zmz.community.baidu.service;

import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.contentcensor.AipContentCensor;
import com.baidu.aip.contentcensor.EImgType;
import com.bbgu.zmz.community.baidu.dto.BaiduCheck;
import com.bbgu.zmz.community.dto.Result;
import com.bbgu.zmz.community.enums.MsgEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class BaiduAiService {
    public static final String APP_ID = "18886701";
    public static final String API_KEY = "ElUC4sTMVzG71v9P6oV1OlOA";
    public static final String SECRET_KEY = "U4PDMrzV3Tj8zG2hmYOK0GdgVFaoMM2T";

    /*
    文本审核
     */
    public Result checkText(String text) {
        AipContentCensor client = new AipContentCensor(APP_ID, API_KEY, SECRET_KEY);
        String json = client.textCensorUserDefined(text).toString();
        BaiduCheck baiduCheck = JSONObject.parseObject(json, BaiduCheck.class);
        String str = "";
        String temp = "";
        if (baiduCheck.getConclusionType() != 1) {
            for (int i = 0; i < baiduCheck.getData().size(); i++) {
                str = baiduCheck.getData().get(i).getMsg() + "<br>";
                temp += str;
            }
            return new Result(1, temp);
        }
        return null;
    }

    /*
    图片审核
     */
    public Result checkImg(byte[] imgByte) {
        AipContentCensor client = new AipContentCensor(APP_ID, API_KEY, SECRET_KEY);
        String json = client.imageCensorUserDefined(imgByte,null).toString();
        BaiduCheck baiduCheck = JSONObject.parseObject(json, BaiduCheck.class);
        if (baiduCheck.getConclusionType() != 1) {
            return new Result(1, "上传失败：" + baiduCheck.getData().get(0).getMsg());
        }
        return null;
    }
}
