package pers.ryan.gateway;

import java.util.Arrays;
import java.util.List;

public class Config {
    // 简单模拟一下配置
    public static final List<String> SERVER_LIST = Arrays.asList("http://localhost:8801", "http://localhost:8802", "http://localhost:8803");
    public static final int HANDLER_TYPE_HTTP_CLIENT = 0;
    public static final int HANDLER_TYPE_OK_HTTP = 1;
}
