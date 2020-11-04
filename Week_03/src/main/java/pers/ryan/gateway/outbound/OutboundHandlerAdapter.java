package pers.ryan.gateway.outbound;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import pers.ryan.gateway.Config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class OutboundHandlerAdapter {
    protected final int cores = Runtime.getRuntime().availableProcessors() * 2;
    protected final ExecutorService proxyService = new ThreadPoolExecutor(cores, cores, 1000, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(2048), new NamedThreadFactory("proxyService"), new ThreadPoolExecutor.CallerRunsPolicy());


    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        final String url = Config.SERVER_LIST.get(0) + fullRequest.uri();
        log.info("请求后端地址: {}", url);
        doRequest(url, fullRequest, ctx);
    }

    protected abstract void doRequest(String url, final FullHttpRequest fullRequest, final ChannelHandlerContext ctx);
}
