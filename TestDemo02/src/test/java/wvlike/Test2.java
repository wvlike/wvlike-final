package wvlike;

import com.ismyself.testDmo.TestDemoApp;
import com.ismyself.testDmo.common.monggo.Customer;
import com.ismyself.testDmo.common.monggo.CustomerRepository;
import com.ismyself.testDmo.common.rabbitmq.MyRabbitMqProducer;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther txw
 * @create 2020-04-08  21:52
 * @description：
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestDemoApp.class)
@WebAppConfiguration
public class Test2 {

    @Test
    public void test001() {
        Ser bean = new Ser();
        System.out.println(bean.getMyName());
    }

    @Component
    public static class Ser {
        @Value("${myName}")
        private String myName;

        public String getMyName() {
            return myName;
        }

        public void setMyName(String myName) {
            this.myName = myName;
        }
    }


//    @Autowired
//    private RestTemplate restTemplate;

    //RestTemplate例子
    @Test
    public void test002() throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://127.0.0.1:18081/test/user/findById";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Content-Type", "application/json");
        JSONObject param = new JSONObject();
        param.put("id", 1);
        HttpEntity<String> request = new HttpEntity<>(param.toString(), headers);
        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(url, request, JSONObject.class);
        System.out.println(responseEntity.toString());
        JSONObject body = responseEntity.getBody();
        System.out.println(body);

    }

    @Autowired
    private MyRabbitMqProducer myRabbitMqProducer;

    @Test
    public void testMyMqSend() {
        try {
            myRabbitMqProducer.sendMyMqMsg();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Autowired
    private CustomerRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void tsetMonggo() {
        Customer customer = new Customer();
        customer.set_id("2");
        customer.setCarNumber("wvlike");
        repository.insert(customer);
        List<Customer> all = repository.findAll();
        System.out.println("monggo ************* " + all);
    }



    @Test
    public void testMongoTemplate() {
        Customer customer = new Customer();
        customer.set_id("1");
        customer.setCarNumber("wvlike2");
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(customer.get_id()));
        Update update = new Update();
        update.set("carNumber", customer.getCarNumber());
        long matchedCount = mongoTemplate.updateFirst(query, update, Customer.class).getMatchedCount();
        System.out.println(matchedCount);
    }


    @Test
    public void testMongoFindAll(){
        List<Customer> all = mongoTemplate.findAll(Customer.class);
        System.out.println(all);
    }

}



