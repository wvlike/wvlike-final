package com.wvlike.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date: 2024/11/21
 * @Author: tuxinwen
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSimpleInfoDTO {

    private String name;

    private Integer age;

    private String address;

    private Boolean isChange;

}
