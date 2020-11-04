package pers.ryan.gateway.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import pers.ryan.gateway.config.Config;
import pers.ryan.gateway.filter.impl.AddHeaderRequestFilter;
import pers.ryan.gateway.outbound.OutboundHandlerAdapter;
import pers.ryan.gateway.outbound.httpclient4.HttpClientOutboundHandler;
import pers.ryan.gateway.outbound.okhttp.OkhttpOutboundHandler;
import pers.ryan.gateway.router.impl.RoundRobinRouter;

@Slf4j
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private OutboundHandlerAdapter outboundHandler;

    public HttpInboundHandler(int type) {
        switch (type) {
            case Config.HANDLER_TYPE_HTTP_CLIENT:
                outboundHandler = new HttpClientOutboundHandler();
                break;
            case Config.HANDLER_TYPE_OK_HTTP:
                outboundHandler = new OkhttpOutboundHandler();
                outboundHandler.addPreHandlingFilter(new AddHeaderRequestFilter());
                outboundHandler.setRouter(new RoundRobinRouter());
                break;
            default:
                throw new RuntimeException("handler type error");
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            outboundHandler.handle(fullRequest, ctx);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

}
