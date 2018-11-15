//package cn.com.adtech.config;
//
//
//import cn.com.adtech.component.UserinfoService;
//import cn.com.adtech.domain.login.LoginSuccessUserinfo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.StringUtils;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Map;
//
//public class WebSecurityConfig  extends HandlerInterceptorAdapter {
//    public WebSecurityConfig(UserinfoService service)
//    {
//        userinfoService=service;
//    }
//
//    UserinfoService userinfoService;
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
//        Map<String, String> headers = new HashMap<>();
//        Enumeration headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String key = (String) headerNames.nextElement();
//            String value = request.getHeader(key);
//            headers.put(key, value);
//        }
//        LoginSuccessUserinfo user=null;
//        String loginType = request.getHeader("loginType");
//        if (!StringUtils.isEmpty(loginType) && "h5".equals(loginType.toLowerCase())) {
//            String token = request.getHeader("token");
//            user=userinfoService.loadH5LoginUserinfo();
//        }
//        else {
//            user=userinfoService.loadWebLoginUserinfo();
//        }
//
//
//
//        if (null == user) {
//            // 未登录，重定向到登录页
//            response.sendRedirect("/login");
//            return false;
//        }
//        return true;
//    }
//}
