package com.ismyself.common.core.converter.time;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * package com.ismyself.testDmo.myselfFramework.converter;
 *
 * @auther txw
 * @create 2021-02-01  23:11
 * @description：
 */
public class LocalDateTimeCodec implements ObjectSerializer, ObjectDeserializer {

    public static final LocalDateTimeCodec instance = new LocalDateTimeCodec();
    private static final String defaultPattern = "yyyy-MM-dd HH:mm:ss";

    private LocalDateTimeCodec() {
    }

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull();
        } else {
            LocalDateTime result = (LocalDateTime) object;
            out.writeString(result.format(DateTimeFormatter.ofPattern(defaultPattern)));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public LocalDateTime deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        String value = parser.lexer.numberString();
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(defaultPattern));
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }


}
