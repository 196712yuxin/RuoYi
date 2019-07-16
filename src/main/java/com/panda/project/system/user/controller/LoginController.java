package com.panda.project.system.user.controller;

import com.panda.common.utils.ServletUtils;
import com.panda.common.utils.StringUtils;
import com.panda.framework.web.controller.BaseController;
import com.panda.framework.web.domain.AjaxResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.parser.Entity;
import java.util.*;

/**
 * 登录验证
 * 
 * @author panda
 */
@Controller
public class LoginController extends BaseController
{
    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response)
    {
        // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request))
        {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }

        return "login";
    }
    @GetMapping("/register")
    public String register(HttpServletRequest request, HttpServletResponse response)
    {
 /*       // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request))
        {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }*/

        return "register";
    }
    @GetMapping("/testMap")
    public  Map<String,Object> testMap(HttpServletRequest request, HttpServletResponse response)
    {
        Map<String, Object> map = new HashMap<>();
        for(String setd:map.keySet()){
            map.get(setd);
        }
        for(Map.Entry<String, Object> en:map.entrySet()){
            en.getKey();
            en.getValue();
        }
        List<String> slist = new LinkedList<>();
        slist.add("12132");
        map.put("success", success());
        Set<Map.Entry<String,Object>> set =map.entrySet();
        List<Map.Entry<String, Object>> lists = new ArrayList<>(set);
        Collections.sort(lists, new Comparator<Map.Entry<String, Object>>() {
            @Override
            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                return 0;
            }
        });
        return map;
    }
    @PostMapping("/login")
    @ResponseBody
    public AjaxResult ajaxLogin(String username, String password, Boolean rememberMe)
    {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try
        {
            subject.login(token);
            return success();
        }
        catch (AuthenticationException e)
        {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage()))
            {
                msg = e.getMessage();
            }
            return error(msg);
        }
    }

    @GetMapping("/unauth")
    public String unauth()
    {
        return "/error/unauth";
    }
}
