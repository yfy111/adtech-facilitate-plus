package cn.com.adtech.domain.po;

import cn.com.adtech.annotation.FieldDisplayProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 分页参数传递
 */
@Data
public class Page<T> {

    //每页多少条
    @ApiModelProperty(value = "每页条数")
    private Integer pageSize;

    //页码
    @ApiModelProperty(value = "页码", required = true)
    private Integer pageNum;

    //总条数
    @ApiModelProperty(value = "总条数", required = true)
    private Integer totalCount;

    @ApiModelProperty(value = "数据", required = true)
    private List<T> list;

    private Class<T> clazz;

    // 获取首页
    public int getFirst() {
        return 1;
    }

    // 获取上一页
    public int getFront() {
        if (pageNum > 1) {
            return pageNum - 1;
        }
        else {
            return 1;
        }
    }

    // 获取下一页
    public int getNext() {
        if (pageNum < getLast()) {
            return pageNum + 1;
        }
        else {
            return getLast();
        }
    }

    // 获取尾页
    public int getLast() {
        return (totalCount - 1) / pageSize + 1;
    }

    // 判断字段是否显示
    private Predicate<Field> showField = field -> {
        if (!field.isAnnotationPresent(ApiModelProperty.class)) {
            return false;
        }

        if (!field.isAnnotationPresent(FieldDisplayProperty.class)) {
            return true;
        }

        return !field.getAnnotation(FieldDisplayProperty.class).hidden();
    };

    // 获取显示的标题
    public List<String> getTitle() {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(showField)
                .map(field -> field.getAnnotation(ApiModelProperty.class).value())
                .collect(Collectors.toList());
    }

    // 获取显示的数据
    public List<List<String>> getData() {
        return list.stream().map(o -> {
            Field[] fields = o.getClass().getDeclaredFields();
            return Arrays.stream(fields)
                    .filter(showField)
                    .map(field -> {
                        field.setAccessible(true);
                        try {
                            Object o1 = field.get(o);
                            if (StringUtils.isEmpty(o1)) {
                                return "-";
                            }
                            else {
                                return o1.toString();
                            }
                        }
                        catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        return "-";
                    }).collect(Collectors.toList());
        }).collect(Collectors.toList());
    }
}