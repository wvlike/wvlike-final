package com.ismyself.testDmo.service;

import com.ismyself.testDmo.vo.UserDTO;

/**
 * package com.ismyself.testDmo.service;
 *
 * @auther txw
 * @create 2020-02-11  13:52
 * @description：
 */
public interface MyTestService {
    UserDTO getUserById(Integer id);

    void insert(UserDTO userDTO);
}
