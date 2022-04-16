package com.ismyself.common.core.converter.time;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * package com.ismyself.testDmo.myselfFramework.converter;
 *
 * @auther txw
 * @create 2021-02-01  23:42
 * @description：
 */
@Data
public class LocalDateTimeTestVo {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")            //spring    反序列化    str -> object
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)           //jackson   反序列化
    @JsonSerialize(using = LocalDateTimeSerializer.class)               //jackson   序列化      object -> str
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")                //jackson   序列化
    private LocalDateTime firstTime;

    //fastjson      序列化、反序列化
    @JSONField(serializeUsing = LocalDateTimeCodec.class, deserializeUsing = LocalDateTimeCodec.class)
    private LocalDateTime secondTime;

}
