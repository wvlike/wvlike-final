package com.wvlike.gateway.controller;

import com.ismyself.common.base.result.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * package com.wvlike.gateway.controller;
 *
 * @auther txw
 * @create 2021-02-17  16:13
 * @description：
 */
@Slf4j
@RestController
public class FallbackController {

    @PostMapping("/fallback")
    public ResultDTO fallback() {
        return ResultDTO.fail("网关错误");
    }

}
