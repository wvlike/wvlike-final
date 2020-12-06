package com.ismyself.testDmo.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * package com.ismyself.testDmo.pojo;
 *
 * @auther txw
 * @create 2020-03-23  21:31
 * @description：
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@TableName("tb_user_sign_0001")
public class UserSignPojo {

    @TableId
    private Long id;
    private Long userId;
    private Integer year;
    private Integer month;
    private byte[] signRecord;
    private byte[] retroactiveRecord;
    private Date createTime;
    private Date updateTime;

}
