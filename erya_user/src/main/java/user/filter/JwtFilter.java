package user.filter;

import common.exception.EryaEnum;
import common.exception.EryaException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends HandlerInterceptorAdapter {
    private StringRedisTemplate stringRedisTemplate;

    public JwtFilter(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        if (token == null) {
            throw new EryaException(EryaEnum.REQUEST_INVALID);
        }
        String roles = stringRedisTemplate.opsForValue().get(token);
        if (roles == null) {
            throw new EryaException(EryaEnum.TOKEN_INVALID);
        }
        if (!roles.equals("user") && !roles.equals("all")) {
            throw new EryaException(EryaEnum.PERMISSION_REFUSED);
        }
        return true;
    }
}