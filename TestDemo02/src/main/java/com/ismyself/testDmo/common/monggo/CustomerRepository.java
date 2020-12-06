package com.ismyself.testDmo.common.monggo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * package com.ismyself.testDmo.common.monggo;
 *
 * @auther txw
 * @create 2020-07-11  15:39
 * @description：
 */
@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {


}
