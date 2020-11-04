package pers.ryan.gateway.router.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import pers.ryan.gateway.config.Config;
import pers.ryan.gateway.router.HttpEndpointRouter;

import java.util.List;

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
