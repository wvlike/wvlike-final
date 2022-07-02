package com.wvlike.msg.controller;

import com.ismyself.common.base.result.ResultDTO;
import com.wvlike.facade.msg.test.MsgTestFacade;
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
public class MsgTestController implements MsgTestFacade {

    @Override
    @PostMapping("/success")
    public ResultDTO<String> test() {
        return ResultDTO.success("test-success");
    }

}
