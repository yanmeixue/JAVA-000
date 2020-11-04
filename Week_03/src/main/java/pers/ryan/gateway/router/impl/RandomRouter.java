package pers.ryan.gateway.router.impl;

import pers.ryan.gateway.router.HttpEndpointRouter;

import java.util.List;
import java.util.Random;

public class RandomRouter implements HttpEndpointRouter {
    @Override
    public String route(List<String> endpoints) {
        int index = new Random().nextInt(endpoints.size());
        return endpoints.get(index);
    }
}
