/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package muni.fi.dp.jz.jbatch.webservice;

import io.hawt.system.ConfigManager;
import java.io.IOException;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Enumeration;
import java.util.logging.Logger;
import javax.security.auth.Subject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import muni.fi.dp.jz.jbatch.hawtio.SessionWatcher;

/**
 *
 * @author jzelezny
 */
@WebFilter("/AuthFilter")
public class AuthFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(AuthFilter.class.getName());
//    private final AuthenticationConfiguration configuration = new AuthenticationConfiguration();

    @Override
    public void init(FilterConfig fc) throws ServletException {
        ConfigManager config = (ConfigManager) fc.getServletContext().getAttribute("ConfigManager");
         if (config != null) {
             String roles = config.get("role", null);
             if (roles == null) {
                roles = config.get("roles", null);
            }
             LOG.info("Roles: " + roles);
         }
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getServletPath();
//        LOG.info("My request for path: " + path);
//        LOG.info("Requeste: " + request.toString());
//        LOG.info("Requeste attrs: " + request.getAttributeNames());
//        LOG.info("Requeste params: " + request.getParameterMap().toString());
//        LOG.info("My request for path: " + path);
//        LOG.info("Response: " + response.toString());
        
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                       LOG.info("Header: " + httpRequest.getHeader(headerNames.nextElement()));
                }
        }
        String cookieHead = httpRequest.getHeader("Cookie");
        String tokenHead = httpRequest.getHeader("Auth-Token");
        LOG.info("\nToken: " + tokenHead);
        LOG.info("\nCookie: " + cookieHead);
        
        
        Cookie[] cookies = httpRequest.getCookies();
         LOG.info("Cookies: ");
        if (cookies !=null){         
        for(Cookie cookie:cookies){                     
                    LOG.info("Cookie: " + cookie.getName());
                 }
        }        
        

//        if (configuration.getRealm() == null || configuration.getRealm().equals("") || !configuration.isEnabled()) {
//            LOG.debug("No authentication needed for path {}", path);
//            chain.doFilter(request, response);
//            return;
//        }

        HttpSession session = httpRequest.getSession(false);
        
        LOG.info("Sessions: " + SessionWatcher.getAllSessions());
//        HttpSession session = SessionWatcher.find("wqpN5mfAUVwuo1HFMlDw8NPO-bewXmlo357R62sd.jzelezny990");
        if( session == null){
        LOG.info("Session null.... ");
        }
        else {
            LOG.info("Session not null yeeey! ");
            Subject subject = (Subject) session.getAttribute("subject");
            if (subject != null) {
                LOG.info("Session subject: " + subject);
                executeAs(request, response, fc, subject);
                return;
            }
        }
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
//    Help
    private static void executeAs(final ServletRequest request, final ServletResponse response, final FilterChain chain, Subject subject) {
        try {
            Subject.doAs(subject, new PrivilegedExceptionAction<Object>() {
                @Override
                public Object run() throws Exception {
                    chain.doFilter(request, response);
                    return null;
                }
            });
        } catch (PrivilegedActionException e) {
            LOG.info("Failed to invoke action " + ((HttpServletRequest) request).getPathInfo() + " due to:" + e);
        }
    }
}
