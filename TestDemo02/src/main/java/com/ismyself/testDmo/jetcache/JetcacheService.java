package com.ismyself.testDmo.jetcache;

import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * package com.ismyself.testDmo.jetcache;
 *
 * @auther txw
 * @create 2020-10-06  11:20
 * @description：
 */
@Slf4j
@Service
public class JetcacheService {

    private static List<String> list = new ArrayList<>();
    private static final String CACHENAME = "listOfJetcache";

    static {
        Collections.addAll(list, "13", "134", "sadf", "asdfasd");
    }

    @Cached(name = CACHENAME, expire = 3000)
    public List<String> getCacheList() {
        return list;
    }

    @CacheUpdate(name = CACHENAME, value = "value")
    public void updateCache(int index, String value) {
        if (index > list.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        list.add(index, value);
    }


}
