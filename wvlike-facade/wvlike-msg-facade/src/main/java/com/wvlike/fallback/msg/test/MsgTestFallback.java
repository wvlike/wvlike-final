package com.wvlike.fallback.msg.test;

import com.ismyself.common.base.result.ResultCode;
import com.ismyself.common.base.result.ResultDTO;
import com.wvlike.facade.msg.test.MsgTestFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * package com.wvlike.fallback.msg.test;
 *
 * @auther txw
 * @create 2022-07-10  9:56
 * @description：MsgTestFallback
 */
@Slf4j
@Component
public class MsgTestFallback implements MsgTestFacade {

    @Override
    public ResultDTO<String> test() {
        log.error("[MsgTestFallback] [test] error");
        return ResultDTO.fail(ResultCode.FALLBACK_COMMON);
    }

}
