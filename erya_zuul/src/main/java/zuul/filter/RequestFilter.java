package zuul.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import common.exception.EryaEnum;
import common.vo.Result;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestFilter extends ZuulFilter {
    private StringRedisTemplate stringRedisTemplate;
    private ObjectMapper objectMapper;

    public RequestFilter(StringRedisTemplate stringRedisTemplate, ObjectMapper objectMapper) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.objectMapper = objectMapper;
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

    @lombok.SneakyThrows
    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        if (request.getRequestURI().matches("^/auth/.*")) {
            return null;
        }
        String token = request.getHeader("token");
        if (token == null) {
            Result result = new Result(EryaEnum.REQUEST_INVALID);
            checked(requestContext, result, HttpStatus.BAD_REQUEST);
            return null;
        }
        String roles = stringRedisTemplate.opsForValue().get(token);
        if (roles == null) {
            Result result = new Result(EryaEnum.TOKEN_INVALID);
            checked(requestContext, result, HttpStatus.UNAUTHORIZED);
            return null;
        }
        return null;
    }

    private void checked(RequestContext requestContext, Result result, HttpStatus badRequest) throws JsonProcessingException {
        HttpServletResponse response = requestContext.getResponse();
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(badRequest.value());
        String responseBody = objectMapper.writeValueAsString(result);
        requestContext.setResponse(response);
        requestContext.setResponseBody(responseBody);
    }
}
