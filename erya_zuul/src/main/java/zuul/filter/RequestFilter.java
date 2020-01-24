package zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import common.exception.EryaEnum;
import common.exception.EryaException;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.http.HttpServletRequest;

public class RequestFilter extends ZuulFilter {
    private StringRedisTemplate stringRedisTemplate;

    public RequestFilter(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext= RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String token = request.getHeader("token");
        if (token == null) {
            throw new EryaException(EryaEnum.TOKEN_INVALID);
        }
        String roles = stringRedisTemplate.opsForValue().get(token);
        if (roles == null) {
            throw new EryaException(EryaEnum.TOKEN_INVALID);
        }
        return null;
    }
}
