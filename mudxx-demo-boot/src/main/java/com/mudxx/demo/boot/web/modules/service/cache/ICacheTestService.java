package com.mudxx.demo.boot.web.modules.service.cache;

public interface ICacheTestService {

    String getEntity(String id);

    void saveEntity(String id);

    void deleteEntity(String id);

}
