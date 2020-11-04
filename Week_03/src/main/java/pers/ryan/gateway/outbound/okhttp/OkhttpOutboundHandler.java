package pers.ryan.gateway.outbound.okhttp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import pers.ryan.gateway.outbound.OutboundHandlerAdapter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OkhttpOutboundHandler extends OutboundHandlerAdapter {
    private final OkHttpClient okHttpClient;

    public OkhttpOutboundHandler() {
        this.okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.SECONDS)
                .readTimeout(1, TimeUnit.SECONDS)
                .writeTimeout(1, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    @Override
    protected byte[] getResponseBody(Object obj) throws IOException {
        Response response = (Response) obj;
        return response.body().bytes();
    }

    @Override
    protected void doRequest(String url, FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        proxyService.submit(() -> fetch(fullRequest, ctx, url));
    }

    private void fetch(FullHttpRequest fullRequest, ChannelHandlerContext ctx, String url) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        for (Map.Entry<String, String> header : fullRequest.headers()) {
            log.info("添加请求头, key: {}, val: {}", header.getKey(), header.getValue());
            builder.addHeader(header.getKey(), header.getValue());
        }
        okHttpClient.newCall(builder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                log.error("failed, ", e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                handleResponse(fullRequest, ctx, response);
            }
        });
    }
}
