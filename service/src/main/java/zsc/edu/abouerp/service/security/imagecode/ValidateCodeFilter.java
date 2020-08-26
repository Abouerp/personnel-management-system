package zsc.edu.abouerp.service.security.imagecode;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import zsc.edu.abouerp.service.exception.ValidateCodeException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ValidateCodeFilter extends OncePerRequestFilter {

    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final StringRedisTemplate stringRedisTemplate;


    public ValidateCodeFilter(AuthenticationFailureHandler authenticationFailureHandler,
                              StringRedisTemplate stringRedisTemplate) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.stringRedisTemplate = stringRedisTemplate;
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
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),"imageCode");
        String code = stringRedisTemplate.opsForValue().get(codeInRequest);
        if(!StringUtils.hasText(codeInRequest)){
            throw new ValidateCodeException("验证码的值不能为空！");
        }
        if(code == null){
            throw new ValidateCodeException("验证码不存在！");
        }
        if(!code.equals(codeInRequest)){
            throw new ValidateCodeException("验证码不正确！");
        }
        stringRedisTemplate.delete(code);
    }
}