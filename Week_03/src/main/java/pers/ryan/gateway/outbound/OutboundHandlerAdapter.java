package pers.ryan.gateway.outbound;


import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import pers.ryan.gateway.config.Config;
import pers.ryan.gateway.filter.HttpRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

@Slf4j
public abstract class OutboundHandlerAdapter {
    protected final int cores = Runtime.getRuntime().availableProcessors() * 2;
    protected final ExecutorService proxyService = new ThreadPoolExecutor(cores, cores * 4, 30, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1024), new ThreadPoolExecutor.CallerRunsPolicy());
    // 转发请求前的filter
    private final List<HttpRequestFilter> preHandlingFilters = new ArrayList<>();

    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        final String url = Config.SERVER_LIST.get(0) + fullRequest.uri();
        log.info("请求后端地址: {}", url);
        for (HttpRequestFilter preHandlingFilter : preHandlingFilters) {
            preHandlingFilter.filter(fullRequest, ctx);
        }
        doRequest(url, fullRequest, ctx);
    }


    protected void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final Object endpointResponse) {
        FullHttpResponse response = null;
        try {
            byte[] body = getResponseBody(endpointResponse);
            log.info("请求后端返回数据: {}", new String(body));
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
        } catch (Exception e) {
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            ctx.close();
            log.error("handleResponse error, ", e);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
            ctx.flush();
        }
    }

    public void addPreHandlingFilter(HttpRequestFilter filter) {
        preHandlingFilters.add(filter);
    }

    protected abstract byte[] getResponseBody(Object obj) throws IOException;

    protected abstract void doRequest(String url, final FullHttpRequest fullRequest, final ChannelHandlerContext ctx);

}
