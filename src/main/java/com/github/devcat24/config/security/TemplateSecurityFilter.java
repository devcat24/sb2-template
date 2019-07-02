package com.github.devcat24.config.security;

import com.github.devcat24.config.prop.DevUser;
import com.github.devcat24.mvc.svc.JPAService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@SuppressWarnings({"StatementWithEmptyBody", "FieldCanBeLocal", "unused", "UnusedAssignment"})
@Slf4j
public class TemplateSecurityFilter implements Filter {
    private FilterConfig config;
    private JPAService jpaService;
    private String[] excludePattern = {"gif", "jpg", "png", "ico", "css", "js", "html", "htm", "woff", "woff2", "jpeg", "swf", "otf", "tif", "tiff", "map"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;

        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        this.jpaService= ctx.getBean(JPAService.class);
    }
    @Override
    public void destroy() {
    }


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String requestURI = request.getServletPath();
        String requestURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +requestURI;
        String loginUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";

        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if(ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        HttpSession session = request.getSession();

        String requestUser = req.getParameter("userName");

        if(processFiltering(StringUtils.trimToEmpty(requestURI))){
            if(! DevUser.loginAsDevUser){
                /* UserInfo loginUser = (UserInfo)session.getAttribute("loginUser");
                if(loginUser == null){
                    UserInfo user = jpaService.getUserByName(requestUser);
                    session.setAttribute("loginUser", user);
                }   else {
                    if(! StringUtils.equalsIgnoreCase(requestUser, user.getName()) ) {
                        session.invalidate();
                        ((HttpServletResponse)res).sendRedirect(loginUrl);
                    }
                } */
            }   else {
                String newLine = System.getProperty("line.separator");
                String loginModeMsg = newLine
                        + "========================================="        + newLine
                        + " login with Simulation Mode: " + DevUser.userName + newLine
                        + "========================================="        + newLine ;
                log.warn(loginModeMsg);
                 /*//// hard-coded user from application.properties -> template.auth.loginAsDevUser && template.auth.devUserName
                String loginUserName = DevUser.userName;
                UserInfo user = jpaService.getUserByName(loginUserName);
                if(user == null){
                    throw LoginUserNotFoundException("...");
                }
                session.setAttribute("loginUser", user);
                //session.setAttribute("activeUser", user);
                requestUser = loginUserName; */
            }
        }

        /*long start = 0L;
        if(processFiltering(StringUtils.trimToEmpty(requestURI))) {
            // Before request processing
            start = System.currentTimeMillis();
        }*/

        chain.doFilter(req, res);

        /*if(processFiltering(StringUtils.trimToEmpty(requestURI))) {
            // After request processing
            long end = System.currentTimeMillis();
            long elapsedTime = end - start;

            if(enablePerfLog) {
                //String msg = requestURI + ": " + elapsedTime + " ms";
                //msg = "Elapsed Time from SecurityFilter ===> " + msg;
                //servletRequestPerfLogger.debug(msg);
                //config.getServletContext().log(msg);

                //String now = (new SimpleDateFormat("yyyyMMdd HH:mm:ss")).format(new Date());
                //JsonObject jsonObj = new JsonObject();
                //jsonObj.addProperty("date", now);
                //jsonObj.addProperty("url", requestURI);
                //jsonObj.addProperty("elapsedTime", elapsedTime);
                //Gson gson = (new GsonBuilder().setDateFormat("dd/MM/yyyy")).create();
                //servletRequestPerfLogger.debug(gson.toJson(jsonObj));

                String now = (new SimpleDateFormat("yyyyMMdd_HH:mm:ss")).format(new Date());
                String pefLogMsg = ipAddress + ",\t" + requestUser + ",\t" + now + ",\t" + elapsedTime + ",\t" + requestURI;
                servletRequestPerfLogger.debug(pefLogMsg);
            }
        }*/

    }

    private boolean processFiltering(String url){
        if(StringUtils.lastIndexOf(url, ".") > 0){
            for (String anExcludePattern : excludePattern) {
                if (StringUtils.substring(url, StringUtils.lastIndexOf(url, ".") + 1).equals(anExcludePattern)) {
                    return false;
                }
            }
        }
        return true;
    }

}
