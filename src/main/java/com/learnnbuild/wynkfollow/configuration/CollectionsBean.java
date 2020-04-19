package com.learnnbuild.wynkfollow.configuration;

import java.util.Map;

public class CollectionsBean {

    private Map<String, Object> popularityMap;

    public CollectionsBean(Map<String, Object> popularityMap) {
        this.popularityMap = popularityMap;
    }

    public Map<String, Object> getPopularityMap() {
        return popularityMap;
    }
}
