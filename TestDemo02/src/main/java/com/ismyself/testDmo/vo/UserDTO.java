package com.ismyself.testDmo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * package com.ismyself.testDmo.pojo;
 *
 * @auther txw
 * @create 2020-02-11  13:47
 * @description：
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String name;
    private String sex;
    private Integer age;

}
