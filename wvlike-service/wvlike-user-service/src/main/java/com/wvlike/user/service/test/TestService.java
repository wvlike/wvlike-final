package com.wvlike.user.service.test;

import com.wvlike.facade.msg.test.MsgTestFacade;
import com.wvlike.user.properties.test.MyApolloProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    @Autowired
    private MsgTestFacade msgTestFacade;

    @Value("${my.apollo.test:0001}")
    private String apolloTestStr;

    @Autowired
    private MyApolloProperties myApolloProperties;

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

    public String testApollo() {
        return apolloTestStr;
    }

    public MyApolloProperties testBean() {
        return myApolloProperties;
    }
}
