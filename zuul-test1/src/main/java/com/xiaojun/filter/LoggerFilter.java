package com.xiaojun.filter;

import com.netflix.zuul.ZuulFilter;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_RESPONSE_FILTER_ORDER;

/**
 * Created by fp295 on 2018/6/26.
 * 网关日志
 */
@Component
public class LoggerFilter extends ZuulFilter {

//    @Autowired
//    private LogSendService logSendService;

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return SEND_RESPONSE_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
//        logSendService.send(RequestContext.getCurrentContext());
        return null;
    }
}
