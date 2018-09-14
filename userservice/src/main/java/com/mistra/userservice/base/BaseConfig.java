package com.mistra.userservice.base;

import cn.hutool.core.util.ReflectUtil;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.ConditionalConverter;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: WangRui
 * @Date: 2018-09-14
 * Time: 下午6:23
 * Description:
 */
@Configuration
public class BaseConfig {


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().getConverters().add(new EnhanceEnumConverter());

        return modelMapper;
    }

    public static class EnhanceEnumConverter implements ConditionalConverter<Object, Enum<?>> {

        private static Map<Pair<Class<?>, Class<?>>, Executable> CACHES = new HashMap<>();

        @Override
        public Enum<?> convert(MappingContext<Object, Enum<?>> context) {
            Object source = context.getSource();
            Executable e = getExecutableOrCreate(context.getSourceType(), context.getDestinationType());

            if (e instanceof Method) {
                return ReflectUtil.invokeStatic((Method) e, source);
            }
            if (e instanceof Constructor) {
                try {
                    return (Enum<?>) ((Constructor)e).newInstance(source);
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

            return null;
        }

        @Override
        public ConditionalConverter.MatchResult match(Class<?> sourceType, Class<?> destinationType) {
            if (destinationType.isEnum()) {
                if (sourceType.isEnum() || sourceType == String.class) {
                    return MatchResult.FULL;
                } else {
                    Executable e = getExecutableOrCreate(sourceType, destinationType);
                    if (Objects.nonNull(e)) {
                        return MatchResult.FULL;
                    }
                }
            }
            return MatchResult.NONE;
        }

        private Executable getExecutableOrCreate(Class<?> sourceType, Class<?> destinationType) {

            Executable e = null;

            Pair<Class<?>, Class<?>> pair = new Pair<>(sourceType, destinationType);

            synchronized (CACHES) {
                e = CACHES.get(pair);
                if (Objects.isNull(e)) {
                    // 先尝试获取valueOf的方法
                    e = ReflectUtil.getMethod(destinationType, "valueOf", sourceType);
                    if (e == null) {
                        // 再尝试获取构造的方法
                        e = ReflectUtil.getConstructor(destinationType, sourceType);
                    }
                    if (e != null) {
                        CACHES.put(pair, e);
                    }
                }
            }

            return e;
        }

        @AllArgsConstructor
        @NoArgsConstructor
        //@Setter
        //@Getter
        @EqualsAndHashCode
        public class Pair<K, V> {
            private K k;
            private V v;
        }
    }
}
