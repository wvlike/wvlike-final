package com.ismyself.testDmo.controller;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.ismyself.testDmo.common.ResultCode;
import com.ismyself.testDmo.common.ResultDTO;
import com.ismyself.testDmo.mapper.tables.UserSignMapper;
import com.ismyself.testDmo.pojo.UserSignPojo;
import com.ismyself.testDmo.vo.UserDTO;
import com.ismyself.testDmo.service.MyTestService;
import com.ismyself.testDmo.vo.Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * package com.ismyself.testDmo.controller;
 *
 * @auther txw
 * @create 2020-02-11  11:12
 * @description：
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class MyTestController {

    @Autowired
    private MyTestService myTestService;

    @RequestMapping("/demo01")
    public ResultDTO demo01() {
        return ResultDTO.success();
    }

    @PostMapping("/user/findById")
    public ResultDTO gerUserById(@RequestBody Param param) {
        if (param == null && param.getId() == null) {
            return ResultDTO.fail(ResultCode.ERRORPARAM.getMessge());
        }
        try {
            UserDTO user = myTestService.getUserById(param.getId());
            return ResultDTO.success(user);
        } catch (Exception e) {
            log.error("MyTestController gerUserById error e={}", e.getMessage(), e);
            return ResultDTO.fail(e.getMessage());
        }
    }

    @PostMapping("/user/insert")
    public ResultDTO insert(@RequestBody UserDTO userDTO) {
        try {
            log.info("MyTestController insert userDTO={}", JSON.toJSONString(userDTO));
            myTestService.insert(userDTO);
            return ResultDTO.success();
        } catch (Exception e) {
            log.error("MyTestController insert error e={}", e.getMessage(), e);
            return ResultDTO.fail(e.getMessage());
        }
    }

    @Autowired(required = false)
    private UserSignMapper userSignMapper;

    @PostMapping("/userSign/insert")
    public ResultDTO insertUserSign(@RequestBody UserSignPojo userSignPojo) {
        try {
            log.info("MyTestController insertUserSign userSignPojo={}", JSON.toJSONString(userSignPojo));
            userSignMapper.insert(userSignPojo);
            return ResultDTO.success();
        } catch (Exception e) {
            log.error("MyTestController insertUserSign error e={}", e.getMessage(), e);
            return ResultDTO.fail(e.getMessage());
        }
    }

    @PostMapping("/helloWorld")
    public ResultDTO helloWorld() {
        initFlowRules();
        System.out.println("HelloWorld");
        return ResultDTO.success("HelloWorld");
    }


    private static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule.setCount(20);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    @Autowired
    private Environment environment;

    @PostMapping("/env")
    public ResultDTO getEnv() {
        String property = environment.getProperty("student.name");
        return ResultDTO.success(property);
    }

    @PostMapping("/aop")
    public Object testAop() {
        return "success";
    }

}
