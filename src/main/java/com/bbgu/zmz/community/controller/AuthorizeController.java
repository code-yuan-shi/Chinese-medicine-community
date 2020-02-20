package com.bbgu.zmz.community.controller;

import com.alibaba.fastjson.JSON;
import com.bbgu.zmz.community.dto.AccessTokenDTO;
import com.bbgu.zmz.community.dto.GithubUser;
import com.bbgu.zmz.community.mapper.UserMapper;
import com.bbgu.zmz.community.model.User;
import com.bbgu.zmz.community.model.UserExample;
import com.bbgu.zmz.community.provider.GithubProvider;
import com.bbgu.zmz.community.service.UserService;
import com.bbgu.zmz.community.util.MD5Utils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;


    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String url,
                           HttpServletRequest request,HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(url);
        String accessToken = githubProvider.getAccessTokenDTO(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser !=null && githubUser.getId() != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(githubUser.getId());
            user.setAvatarUrl(githubUser.getAvatar_url());
            user.setStatus(1);
            if(githubUser.getId() == 56061468){
                user.setRole("社区管理员");
            }else{
                user.setRole("社区管理员");
            }
            userService.createOrUpdate(user);
            UserExample userExample = new UserExample();
            userExample.createCriteria().andAccountIdEqualTo(githubUser.getId());
            List<User> users = userMapper.selectByExample(userExample);
            request.getSession().setAttribute("user",users.get(0));
            Cookie cookie = new Cookie("token",user.getToken());
            cookie.setMaxAge(60*60*24*7);
            response.addCookie(cookie);
            int index = url.lastIndexOf("/");
            String str = url.substring(index+1);
            if(str.equals("reg") || str.equals("login") || str.equals("github")){
                return "redirect:/";
            }else{
                return "redirect:"+url;
            }

        }else{
            return "redirect:"+url;
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";

    }

}
