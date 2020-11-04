package pers.ryan.gateway.outbound.httpclient4;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.util.EntityUtils;
import pers.ryan.gateway.outbound.OutboundHandlerAdapter;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class HttpClientOutboundHandler extends OutboundHandlerAdapter {
    private final CloseableHttpAsyncClient httpclient;

    public HttpClientOutboundHandler() {
        IOReactorConfig ioConfig = IOReactorConfig.custom()
                .setConnectTimeout(1000)
                .setSoTimeout(1000)
                .setIoThreadCount(cores)
                .setRcvBufSize(32 * 1024)
                .build();
        httpclient = HttpAsyncClients.custom().setMaxConnTotal(40)
                .setMaxConnPerRoute(8)
                .setDefaultIOReactorConfig(ioConfig)
                .setKeepAliveStrategy((response, context) -> 6000)
                .build();
        httpclient.start();
    }

    @Override
    protected byte[] getResponseBody(Object obj) throws IOException {
        HttpResponse endpointResponse = (HttpResponse) obj;
        return EntityUtils.toByteArray(endpointResponse.getEntity());
    }

    @Override
    protected void doRequest(String url, FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        proxyService.submit(() -> fetchGet(fullRequest, ctx, url));
    }

    private void fetchGet(FullHttpRequest inbound, ChannelHandlerContext ctx, String url) {
        final HttpGet httpGet = new HttpGet(url);
        for (Map.Entry<String, String> header : inbound.headers()) {
            log.info("添加请求头, key: {}, val: {}", header.getKey(), header.getValue());
            httpGet.setHeader(header.getKey(), header.getValue());
        }
        httpclient.execute(httpGet, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(final HttpResponse endpointResponse) {
                handleResponse(inbound, ctx, endpointResponse);
            }

            @Override
            public void failed(final Exception ex) {
                httpGet.abort();
                log.error("failed, ", ex);
            }

            @Override
            public void cancelled() {
                httpGet.abort();
            }
        });
    }

}
