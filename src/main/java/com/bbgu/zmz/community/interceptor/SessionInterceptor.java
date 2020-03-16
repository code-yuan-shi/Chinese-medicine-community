package com.bbgu.zmz.community.interceptor;


import com.alibaba.fastjson.JSON;
import com.bbgu.zmz.community.dto.Result;
import com.bbgu.zmz.community.enums.MsgEnum;
import com.bbgu.zmz.community.mapper.UserMapper;
import com.bbgu.zmz.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


@Component
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserMapper userMapper;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("请求路过拦截器！");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(request.getRequestURI().lastIndexOf("/")==0){
            Cookie[] cookies = request.getCookies();
            if(cookies != null && cookies.length != 0) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        String token = cookie.getValue();
                        Example example = new Example(User.class);
                        example.createCriteria().andEqualTo("token",token);
                        List<User> users = userMapper.selectByExample(example);
                        if (users.size() != 0) {
                            request.getSession().setAttribute("user", users.get(0));
                            return true;
                        }
                    }
                }
            }
            return true;
        }

        if (user == null){
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("<script>alert('请登录！');window.location='/user/login'</script>");
            return false;
        }else {
            if(user.getStatus() == 0){
                if(request.getRequestURI().indexOf("user/set")>=0){
                    return true;
                }
                if(request.getRequestURI().indexOf("modifyuserinfo")>=0){
                    return true;
                }
                String xRequestedWith=request.getHeader("X-Requested-With");
                if(xRequestedWith!= null && xRequestedWith.indexOf("XMLHttpRequest")!=-1){
                    response.setContentType("text/html;charset=utf-8");
                    response.getWriter().write(JSON.toJSONString(new Result().error(MsgEnum.ALLOWLIMIT)));
                }else {
                    response.setContentType("text/html;charset=utf-8");
                    response.getWriter().write("<script>window.location='/user/activate'</script>");
                }
                return false;
            }
            return true;    //如果session里有user，表示该用户已经登陆，放行，用户即可继续调用自己需要的接口
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {


    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
