package com.wvlike.common.core.converter;

import cn.hutool.core.date.DatePattern;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.wvlike.common.core.converter.time.LocalDateTimeCodec;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;

/**
 * package com.wvlike.testDmo.myselfFramework.converter;
 *
 * @auther txw
 * @create 2021-01-05  20:56
 * @description：
 */
//@Configuration
@Profile("local")
public class MyMessageConverterConfig {

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastJsonConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        //序列化
        SerializeConfig serializeConfig = SerializeConfig.getGlobalInstance();
        //将Long序列化成String，解决前端Long类型长度过长精度丢失
        //serializeConfig.put(Long.class, ToStringSerializer.instance);
        //自动义LocalDateTime
        serializeConfig.put(LocalDateTime.class, LocalDateTimeCodec.instance);
        fastJsonConfig.setSerializeConfig(serializeConfig);
        //反序列化
        //自动义LocalDateTime
        ParserConfig parserConfig = ParserConfig.getGlobalInstance();
        parserConfig.putDeserializer(LocalDateTime.class, LocalDateTimeCodec.instance);
        fastJsonConfig.setParserConfig(parserConfig);
        
        fastJsonConfig.setDateFormat(DatePattern.NORM_DATETIME_PATTERN);
        fastJsonConfig.setCharset(StandardCharsets.UTF_8);
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty
        );
        fastJsonConverter.setFastJsonConfig(fastJsonConfig);
        fastJsonConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        StringHttpMessageConverter converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        return new HttpMessageConverters(fastJsonConverter, converter);
    }
}
