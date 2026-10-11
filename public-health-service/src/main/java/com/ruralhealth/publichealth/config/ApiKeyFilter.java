package com.ruralhealth.publichealth.config;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
@Component
public class ApiKeyFilter extends OncePerRequestFilter {
 @Value("${publichealth.api-key}") private String expectedKey;
 @Override protected boolean shouldNotFilter(HttpServletRequest request){
   String path=request.getRequestURI();
   return !path.startsWith("/api/") || ("GET".equalsIgnoreCase(request.getMethod()) && "/api/ping".equals(path));
 }
 @Override protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain chain)
   throws ServletException,IOException {
   String supplied=request.getHeader("X-API-KEY");
   if(expectedKey==null || expectedKey.isBlank() || !expectedKey.equals(supplied)){
     response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
     response.setContentType("application/json");
     response.setCharacterEncoding("UTF-8");
     response.getWriter().write("{\"error\":\"Missing or invalid API key\"}");
     return;
   }
   chain.doFilter(request,response);
 }
}
