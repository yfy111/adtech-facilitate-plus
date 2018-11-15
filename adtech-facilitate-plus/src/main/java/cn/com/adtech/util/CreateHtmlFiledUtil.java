package cn.com.adtech.util;

import cn.com.adtech.annotation.FieldDisplayProperty;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CreateHtmlFiledUtil<T> {

    private static final String T_TITLE = "title";
    private static final String T_CONTENT = "content";
    private static final String T_WIDTH = "width";
    private static final String T_HEIGHT = "height";
    private static final String T_TITEL_WIDTH = "titleWidth";
    private static final String T_TITEL_HEIGHT = "titleHeight";
    private static final String SCALE = "1";

    private T t;
    private String rep;

    public CreateHtmlFiledUtil(T t, String rep) {
        this.t = t;
        this.rep = rep;
    }

    /**
     * 获取字段值
     *
     * @return
     */
    public Function<Field, String> getFieldValue() {

        Function<Field, String> getFieldValue = field -> {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            try {
                Object o = field.get(t);
                if (StringUtils.isEmpty(o)) {
                    return rep;
                } else {
                    return o.toString();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return rep;
        };
        return getFieldValue;
    }

    /**
     * 返回封装数据
     *
     * @return
     */
    public List<JSONObject> create() {
        Field[] fields = t.getClass().getDeclaredFields();
        List<JSONObject> objects = Arrays.stream(fields)
                .filter(field -> {
                    // 第一种情况：没有FieldDisplayProperty注解
                    if (!field.isAnnotationPresent(FieldDisplayProperty.class)) {
                        return true;
                    }
                    // 第二种情况：有FieldDisplayProperty注解，但是hidden为false
                    return !field.getAnnotation(FieldDisplayProperty.class).hidden();
                })
                .map(field -> {
                    JSONObject jsonObject = new JSONObject();
                    ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
                    String title = apiModelProperty.value();
                    String content = getFieldValue().apply(field);
                    jsonObject.put(T_TITLE, title);
                    jsonObject.put(T_CONTENT, content);
                    if (field.isAnnotationPresent(FieldDisplayProperty.class)) {
                        FieldDisplayProperty fieldDisplayProperty = field.getAnnotation(FieldDisplayProperty.class);
                        jsonObject.put(T_WIDTH, fieldDisplayProperty.width());
                        jsonObject.put(T_HEIGHT, fieldDisplayProperty.height());
                        jsonObject.put(T_TITEL_WIDTH,fieldDisplayProperty.titleWidth());
                        jsonObject.put(T_TITEL_HEIGHT,fieldDisplayProperty.titleHeight());
                    } else {
                        jsonObject.put(T_WIDTH, SCALE);
                        jsonObject.put(T_HEIGHT, SCALE);
                        jsonObject.put(T_TITEL_WIDTH,SCALE);
                        jsonObject.put(T_TITEL_HEIGHT,SCALE);
                    }
                    return jsonObject;
                }).collect(Collectors.toList());
        return objects;
    }
}
