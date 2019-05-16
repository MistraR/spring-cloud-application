package com.mistra.base.utils.enumutils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import java.io.IOException;

/**
 * @ Author: WangRui
 * @ Version: 1.0
 * @ Time: 2019-05-09 11:45
 * @ Description:
 */
public class EnumJsonDeserializer extends JsonDeserializer<Enum> implements ContextualDeserializer {

    private Class<? extends Enum> clz;

    public void setClz(Class<? extends Enum> clz) {
        this.clz = clz;
    }

    @Override
    public Enum deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException, JsonProcessingException {
        if (IEnum.class.isAssignableFrom(clz)) {
            // 匹配自定义值
            for (Object enumObj : clz.getEnumConstants()) {
                IEnum iEnum = (IEnum) enumObj;
                String convertValue = String.valueOf(iEnum.convertValue());
                if (convertValue != null && convertValue.equals(jsonParser.getText())) {
                    return (Enum) enumObj;
                }
            }
        }
        return EnumUtil.getEnum(clz, jsonParser.getText());
    }

    /**
     * 获取合适的解析器，把当前解析的属性Class对象存起来，以便反序列化的转换类型
     *
     * @param ctx
     * @param property
     * @return
     * @throws JsonMappingException
     */
    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctx, BeanProperty property) throws JsonMappingException {
        EnumJsonDeserializer deserializer = new EnumJsonDeserializer();
        deserializer.setClz((Class<? extends Enum>) ctx.getContextualType().getRawClass());
        return deserializer;
    }
}
