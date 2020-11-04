# 作业
---
代码完整可运行，以下说明关键代码片段
## 整合上次次作业的 httpclient/okhttp
1.重构：整理公共方法到抽象类，HttpClientOutboundHandler继承抽象类，实现相关方法
- OutboundHandlerAdapter
 ```java
@Slf4j
public abstract class OutboundHandlerAdapter {
    protected final int cores = Runtime.getRuntime().availableProcessors() * 2;
    protected final ExecutorService proxyService = new ThreadPoolExecutor(cores, cores * 4, 30, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1024), new ThreadPoolExecutor.CallerRunsPolicy());
    // 转发请求前的filter
    private final List<HttpRequestFilter> preHandlingFilters = new ArrayList<>();
    // 路由，默认随机策略
    private HttpEndpointRouter router = new RandomRouter();

    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        String url = router.route(Config.SERVER_LIST) + fullRequest.uri();
        log.info("请求后端地址: {}", url);
        for (HttpRequestFilter preHandlingFilter : preHandlingFilters) {
            preHandlingFilter.filter(fullRequest, ctx);
        }
        proxyService.submit(() -> doRequest(url, fullRequest, ctx));
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

    public void setRouter(HttpEndpointRouter router) {
        this.router = router;
    }

    protected abstract byte[] getResponseBody(Object obj) throws IOException;

    protected abstract void doRequest(String url, final FullHttpRequest inbound, final ChannelHandlerContext ctx);
```
2.实现用okhttp做转发的处理类
- OkhttpOutboundHandler
```java
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
```
## 实现过滤器
- AddHeaderRequestFilter
```java
@Slf4j
public class AddHeaderRequestFilter implements HttpRequestFilter {
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        log.info("filter添加请求头");
        fullRequest.headers().set("nio", "RyanZhong");
    }
}
```

## 实现路由
- RandomRouter
```java
public class RandomRouter implements HttpEndpointRouter {
    @Override
    public String route(List<String> endpoints) {
        int index = new Random().nextInt(endpoints.size());
        return endpoints.get(index);
    }
}
```
- RoundRobinRouter
```java
@Slf4j
public class RoundRobinRouter implements HttpEndpointRouter {

    @Override
    public String route(List<String> endpoints) {
        String key = DigestUtils.md5Hex(String.join("", endpoints));
        int index = Config.ROUND_ROBIN_MAP.getOrDefault(key, 0);
        log.info("key: {}, index: {}", key, index);
        Config.ROUND_ROBIN_MAP.put(key, (index + 1) % endpoints.size());
        return endpoints.get(index);
    }

}
```
## 过滤器和路由的设置
- HttpInboundHandler
```java
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
```

## 过滤器和路由调用
```java
    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        String url = router.route(Config.SERVER_LIST) + fullRequest.uri();
        log.info("请求后端地址: {}", url);
        for (HttpRequestFilter preHandlingFilter : preHandlingFilters) {
            preHandlingFilter.filter(fullRequest, ctx);
        }
        proxyService.submit(() -> doRequest(url, fullRequest, ctx));
    }
```