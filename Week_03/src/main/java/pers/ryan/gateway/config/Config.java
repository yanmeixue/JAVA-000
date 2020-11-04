package pers.ryan.gateway.config;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public interface Config {
    // 简单模拟一下配置
    List<String> SERVER_LIST = Arrays.asList("http://localhost:8801", "http://localhost:8802", "http://localhost:8803");
    int HANDLER_TYPE_HTTP_CLIENT = 0;
    int HANDLER_TYPE_OK_HTTP = 1;
    ConcurrentHashMap<String, Integer> ROUND_ROBIN_MAP = new ConcurrentHashMap<>();
}
