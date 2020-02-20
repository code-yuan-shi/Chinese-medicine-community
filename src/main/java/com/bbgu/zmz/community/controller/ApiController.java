package com.bbgu.zmz.community.controller;

import com.bbgu.zmz.community.enums.MsgEnum;
import com.bbgu.zmz.community.dto.Result;
import com.bbgu.zmz.community.model.WeekList;
import com.bbgu.zmz.community.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Controller
@RequestMapping("/api")
public class ApiController {
    private static final long serialVersionUID = 1L;
    private static int WIDTH = 85; //验证码图片宽度
    private static int HEIGHT = 42; //验证码图片高度

    @Autowired
    private ListService listService;

    @PostMapping("/upload")
    @ResponseBody
    public Result upload(@RequestParam MultipartFile file, HttpServletRequest request) throws IOException {
        //RegRespObj regRespObj = new RegRespObj();
        if (file.getSize() > 0) {
            Properties props=System.getProperties(); //获得系统属性集
            String osName = props.getProperty("os.name"); //操作系统名称
            String realPath = "";
            if(osName.indexOf("Win") != -1){
                 realPath = new String("D://upload/");
            }else{
                 realPath = new String("/data/wwwroot/default/upload");
            }
            File file1 = new File(realPath);
            if (!file1.exists()) {
                file1.mkdirs();
            }
            UUID uuid = UUID.randomUUID();
            File file2 = new File(realPath + File.separator + uuid + file.getOriginalFilename());
            file.transferTo(file2);
            //regRespObj.setUrl( "/upload/" + uuid + file.getOriginalFilename());
            //regRespObj.setStatus(0);
            //regRespObj.setMsg("上传成功！");
            Map map = new HashMap();
            map.put("url","/upload/" + uuid + file.getOriginalFilename());
            return new Result().ok(MsgEnum.UPLOAD_SUCCESS,map);
        } else {
            return new Result().error(MsgEnum.UPLOAD_SUCCESS);
           // regRespObj.setStatus(1);
            //regRespObj.setMsg("上传失败！");
        }
        //return regRespObj;

    }

    @PostMapping("/top")
    @ResponseBody
    public Result weekList(Long limit){
        List<WeekList> weekListList = listService.weekList();
       /* RegRespObj regRespObj = new RegRespObj();
        regRespObj.setStatus(0);
        regRespObj.setWeekList(weekListList);*/
        return new Result().ok(MsgEnum.OK,weekListList);
        //return  regRespObj;
    }

    @GetMapping("/check")
    public void checkCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setContentType("text/html;charset=utf-8");
        HttpSession session = request.getSession();
        response.setContentType("image/jpeg");
        ServletOutputStream sos = response.getOutputStream();
        //设置浏览器不要缓存此图片
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);
        //创建内存图象并获得其图形上下文
        BufferedImage image =
                new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        //产生随机的认证码
        char [] rands = generateCheckCode();
        //产生图像
        drawBackground(g);
        drawRands(g,rands);
        //结束图像的绘制过程，完成图像
        g.dispose();
        //将图像输出到客户端
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, "JPEG", bos);
        byte [] buf = bos.toByteArray();
        response.setContentLength(buf.length);
        //下面的语句也可写成：bos.writeTo(sos);
        sos.write(buf);
        bos.close();
        sos.close();
        //将当前验证码存入到Session中
        session.setAttribute("check",new String(rands));
        //直接使用下面的代码将有问题，Session对象必须在提交响应前获得
        //request.getSession().setAttribute("check_code",new String(rands));
    }
    //生成一个4字符的验证码
    private char [] generateCheckCode()
    {
        //定义验证码的字符表
        String chars = "0123456789abcdefghijklmnopqrstuvwxyz";
        char [] rands = new char[4];
        for(int i=0; i<4; i++)
        {
            int rand = (int)(Math.random() * 36);
            rands[i] = chars.charAt(rand);
        }
        return rands;
    }
    private void drawRands(Graphics g , char [] rands)
    {
        g.setColor(Color.BLACK);
        g.setFont(new Font(null,Font.ITALIC|Font.BOLD,25));
        //在不同的高度上输出验证码的每个字符
        g.drawString("" + rands[0],16,30);
        g.drawString("" + rands[1],31,31);
        g.drawString("" + rands[2],46,32);
        g.drawString("" + rands[3],58,30);
        System.out.println(rands);
    }
    private void drawBackground(Graphics g)
    {
        //画背景
        g.setColor(new Color(0xffffff));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        //随机产生120个干扰点
        for(int i=0; i<120; i++)
        {
            int x = (int)(Math.random() * WIDTH);
            int y = (int)(Math.random() * HEIGHT);
            int red = (int)(Math.random() * 255);
            int green = (int)(Math.random() * 255);
            int blue = (int)(Math.random() * 255);
            g.setColor(new Color(red,green,blue));
            g.drawOval(x,y,1,0);
        }
    }

}



