package org.ck.teach.teachplatform.util;

import org.ck.teach.teachplatform.entity.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2019/9/27 10:54
 * @description
 */
public class BeanUtils {


    /***
     *
     * @param source 赋值属性对象
     * @param target 目标对象 class
     * @param <T>
     *
     * @return null T
     */
    public static <T> T copyProperties(Object source, Class<T> target) {
        Object o = null;
        try {
            o = target.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        org.springframework.beans.BeanUtils.copyProperties(source, o);
        return (T) o;
    }

    public static void fillNull(Object source, @NotNull String... properties) {
        try {
            Class<?> sourceClass = source.getClass();
            Field field = null;
            for (@NotBlank String pro : properties) {
                field = sourceClass.getDeclaredField(pro);
                field.setAccessible(true);
                field.set(source, null);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static <T> T fillOtherNull(T source, @NotNull String... properties) {
        try {
            Class<?> sourceClass = source.getClass();
            Object newInstance = sourceClass.newInstance();
            for (String field : properties) {
                Field declaredField = sourceClass.getDeclaredField(field);
                if (declaredField!=null) {
                    declaredField.setAccessible(true);
                    declaredField.set(newInstance, declaredField.get(field));
                }
            }
            return (T) newInstance;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }
}
