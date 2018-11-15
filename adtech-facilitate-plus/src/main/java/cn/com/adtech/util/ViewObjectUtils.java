package cn.com.adtech.util;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * @Description 转换vo对象的工具类
 * @Author chenguangxue
 * @CreateDate 2018/11/9 11:57
 */
public class ViewObjectUtils {

    private static BiFunction<Object, Field, String> fieldValueGetter = (o, field) -> {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        try {
            Object value = field.get(o);
            if (value == null) {
                return "-";
            }
            else {
                return value.toString();
            }
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "-";
    };

    // 将指定的对象转换为集合
    public static List<JSONObject> convert(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        return Arrays.stream(fields).map(field -> {
            ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
            String title = apiModelProperty.value();
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            String value = fieldValueGetter.apply(object, field);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", title);
            jsonObject.put("content", value);

            return jsonObject;
        }).collect(Collectors.toList());
    }
}
