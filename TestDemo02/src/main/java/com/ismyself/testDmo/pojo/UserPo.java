package com.ismyself.testDmo.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * package com.ismyself.testDmo.pojo;
 *
 * @auther txw
 * @create 2020-02-11  13:57
 * @description：
 */
@Data
@Table(name = "tb_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String sex;

    private Integer age;

}

