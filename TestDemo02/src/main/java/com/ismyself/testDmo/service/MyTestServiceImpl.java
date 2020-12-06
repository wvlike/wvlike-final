package com.ismyself.testDmo.service;

import com.ismyself.testDmo.mapper.single.UserMapper;
import com.ismyself.testDmo.vo.UserDTO;
import com.ismyself.testDmo.pojo.UserPo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * package com.ismyself.testDmo.service;
 *
 * @auther txw
 * @create 2020-02-11  13:52
 * @description：
 */
@Service
public class MyTestServiceImpl implements MyTestService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDTO getUserById(Integer id) {
        UserPo userPo = userMapper.selectByPrimaryKey(id);
        if (userPo != null) {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(userPo,userDTO);
            return userDTO;
        }
        return null;
    }

    @Override
    public void insert(UserDTO userDTO) {
        UserPo userPo = UserPo.builder()
                .name(userDTO.getName())
                .age(userDTO.getAge())
                .sex(userDTO.getSex()).build();
        int id = userMapper.insert(userPo);
        System.out.println(id);
    }

    public static void main(String[] args) {
        int[] arr = {1,9,3,6,3,4,2};
        List<Integer> list = new ArrayList<>();
        Collections.addAll(list,1,9,3,6,3,4,2);
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            if (next == 3){
                iterator.remove();
                System.out.println("++++++++++");
            }
        }
        System.out.println(list);

    }

}
