package com.wvlike.msg.controller;

import com.wvlike.common.base.result.ResultDTO;
import com.wvlike.msg.facade.msg.test.MsgTestFacade;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * package com.wvlike.msg.controller;
 *
 * @auther txw
 * @create 2022-07-02  11:16
 * @description：
 */
@RestController
@RequestMapping("/test")
@Primary
public class MsgTestController implements MsgTestFacade {

    @Override
    @PostMapping("/success")
    public ResultDTO<String> test() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {

        }
        return ResultDTO.success("test-success");
    }

}
