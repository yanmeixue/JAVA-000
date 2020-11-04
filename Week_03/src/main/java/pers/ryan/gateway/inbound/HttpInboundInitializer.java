package pers.ryan.gateway.inbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import pers.ryan.gateway.config.Config;

public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new HttpServerCodec());
        p.addLast(new HttpObjectAggregator(1024 * 1024));
        // p.addLast(new HttpInboundHandler(Config.HANDLER_TYPE_HTTP_CLIENT));
        p.addLast(new HttpInboundHandler(Config.HANDLER_TYPE_OK_HTTP));
    }
}
