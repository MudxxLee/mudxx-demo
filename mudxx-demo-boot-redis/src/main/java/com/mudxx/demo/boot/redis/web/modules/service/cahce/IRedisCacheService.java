package com.mudxx.demo.boot.redis.web.modules.service.cahce;

public interface IRedisCacheService {

    String getEntity(String id);

    void saveEntity(String id);

    void deleteEntity(String id);

    void set();

    void get();

    void setStr();

    void getStr();


}
