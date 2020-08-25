package zsc.edu.abouerp.service.security.filter;

import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import zsc.edu.abouerp.service.exception.ValidateCodeException;
import zsc.edu.abouerp.service.security.controller.ValidateController;
import zsc.edu.abouerp.service.security.validate.ImageCode;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ValidateCodeFilter extends OncePerRequestFilter {

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public SessionStrategy getSessionStrategy() {
        return sessionStrategy;
    }

    public void setSessionStrategy(SessionStrategy sessionStrategy) {
        this.sessionStrategy = sessionStrategy;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (httpServletRequest.getRequestURI().equals("/api/user/login") && httpServletRequest.getMethod().equals("POST")) {
            try {
                validate(new ServletWebRequest(httpServletRequest));

            }catch (ValidateCodeException e){
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
                return;
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);   //如果不是登录请求，直接调用后面的过滤器链
    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request, ValidateController.SESSION_KEY);
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),"imageCode");
        if(!StringUtils.hasText(codeInRequest)){
            throw new ValidateCodeException("验证码的值不能为空！");
        }
        if(codeInSession == null){
            throw new ValidateCodeException("验证码不存在！");
        }
        if(codeInSession.isExpried()){
            sessionStrategy.removeAttribute(request,ValidateController.SESSION_KEY);
            throw new ValidateCodeException("验证码已过期！");
        }
        if(!codeInSession.getCode().equals(codeInRequest)){
            throw new ValidateCodeException("验证码不正确！");
        }
        sessionStrategy.removeAttribute(request,ValidateController.SESSION_KEY);
    }
}