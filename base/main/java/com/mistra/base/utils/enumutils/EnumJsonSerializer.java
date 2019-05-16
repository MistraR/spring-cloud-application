package com.mistra.base.utils.enumutils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @ Author: WangRui
 * @ Version: 1.0
 * @ Time: 2019-05-09 11:47
 * @ Description:
 */
public class EnumJsonSerializer extends JsonSerializer {

    @Override
    public void serialize(Object obj, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        Class clz = obj.getClass();
        if (IEnum.class.isAssignableFrom(clz)) {
            IEnum iEnum = (IEnum) obj;
            Object val = iEnum.convertValue();
            if (val != null) {
                jsonGenerator.writeObject(val);
            } else {
                jsonGenerator.writeObject(((Enum) obj).ordinal());
            }
        } else {
            jsonGenerator.writeObject(((Enum) obj).ordinal());
        }
    }


}
