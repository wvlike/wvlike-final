package com.ismyself.testDmo.common.rest;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.api.R;
import com.google.common.collect.Lists;
import com.ismyself.testDmo.common.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * package com.ismyself.testDmo.common.rest;
 *
 * @auther txw
 * @create 2020-07-12  20:38
 * @description：
 */
@Component
public class TemplateUtil {

    private RestTemplate template = new RestTemplate();

    private ResultDTO postForObject(String url, Map<String, String> body, MultiValueMap<String, String> headers) {
        HttpEntity httpEntity = new HttpEntity(JSON.toJSONString(body), headers);
        ResultDTO resultDTO = template.postForObject(url, httpEntity, ResultDTO.class, body);
        return resultDTO;
    }

}
