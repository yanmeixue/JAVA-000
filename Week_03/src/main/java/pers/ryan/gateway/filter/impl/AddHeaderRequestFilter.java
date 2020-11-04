package pers.ryan.gateway.filter.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import pers.ryan.gateway.filter.HttpRequestFilter;

@Slf4j
public class AddHeaderRequestFilter implements HttpRequestFilter {
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        log.info("filter添加请求头");
        fullRequest.headers().set("nio", "RyanZhong");
    }
}
