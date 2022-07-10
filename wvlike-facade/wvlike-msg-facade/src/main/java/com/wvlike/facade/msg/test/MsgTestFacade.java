package com.wvlike.facade.msg.test;

import com.ismyself.common.base.result.ResultDTO;
import com.wvlike.fallback.msg.test.MsgTestFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * package com.wvlike.facade.msg.test;
 *
 * @auther txw
 * @create 2022-07-02  10:47
 * @description：MsgTestFacade
 */
@FeignClient(value = "wvlike-msg", path = "/msg/test", fallback = MsgTestFallback.class)
public interface MsgTestFacade {

    @PostMapping("/success")
    ResultDTO<String> test();


}
