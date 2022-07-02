package com.wvlike.facade.msg.test;

import com.ismyself.common.base.result.ResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * package com.wvlike.facade.msg.test;
 *
 * @auther txw
 * @create 2022-07-02  10:47
 * @description：
 */
@FeignClient(value = "wvlike-msg")
@RequestMapping("/msg/test")
public interface MsgTestFacade {

    @PostMapping("/success")
    ResultDTO<String> test();


}
