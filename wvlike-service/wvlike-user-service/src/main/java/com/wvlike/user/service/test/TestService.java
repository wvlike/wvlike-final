package com.wvlike.user.service.test;

import com.wvlike.facade.msg.test.MsgTestFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * package com.wvlike.user.service.test;
 *
 * @auther txw
 * @create 2022-07-02  11:02
 * @description：
 */
@Slf4j
@Service
public class TestService {

    @Resource
    private MsgTestFacade msgTestFacade;

    /**
     * test
     *
     * @return
     */
    public String test() {
        return "success";
    }

    public String testFacade() {
        return msgTestFacade.test().getData();
    }
}
