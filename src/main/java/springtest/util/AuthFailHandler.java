package springtest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ligson on 2016/8/29.
 * 认证失败
 */
@Component
public class AuthFailHandler implements AuthenticationFailureHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthFailHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        LOGGER.error("auth fail..");
    }
}
