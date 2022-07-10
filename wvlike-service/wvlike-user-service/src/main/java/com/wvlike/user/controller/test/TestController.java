package com.wvlike.user.controller.test;

import com.ismyself.common.base.result.ResultDTO;
import com.wvlike.user.service.test.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * package com.wvlike.user.controller.test;
 *
 * @auther txw
 * @create 2022-06-30  21:32
 * @description：
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private TestService testService;

    @PostMapping("/success")
    public ResultDTO<String> test() {
        return ResultDTO.success(testService.test());
    }

    @PostMapping("/facade/success")
    public ResultDTO<String> testFacade() {
        long start = System.currentTimeMillis();
        String result = testService.testFacade();
        log.info("TestController testFacade rt:{} ms", (System.currentTimeMillis() - start));
        return ResultDTO.success(result);
    }

}
