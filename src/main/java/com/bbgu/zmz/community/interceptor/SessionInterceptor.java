package com.bbgu.zmz.community.interceptor;


import com.bbgu.zmz.community.mapper.UserMapper;
import com.bbgu.zmz.community.model.User;
import com.bbgu.zmz.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Component
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserMapper userMapper;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User)request.getSession().getAttribute("user");
       Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria().andTokenEqualTo(token);
                   List<User> users = userMapper.selectByExample(userExample);
                    if(users.size() != 0) {
                        request.getSession().setAttribute("user",users.get(0));
                        return true;
                    }
                }
            }
        }
/*        if(request.getRequestURI().lastIndexOf("/")>=0){
            return true;
        }*/
/*        if(request.getRequestURI().indexOf("/login")>=0){
            return true;
        }
        if(request.getRequestURI().indexOf("/dologin")>=0){
            return true;
        }
        if(request.getRequestURI().indexOf("/doreg")>=0){
            return true;
        }
        if(request.getRequestURI().indexOf("/reg")>=0){
            return true;
        }
        if(request.getRequestURI().indexOf("/detail")>=0){
            return true;
        }
        if (user == null){
            return false;
        }*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
