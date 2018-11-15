package cn.com.adtech.util;

import cn.com.adtech.annotation.DesensitizationProperty;
import cn.com.adtech.service.RedisLoadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 将空值设为自定义值
 */
@Slf4j
@Component
public class SetNullToStringUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedisLoadService redisLoadService;

//    String rep;
//    T t;
//
//    public SetNullToStringUtil(T t, String rep) {
//        this.rep = rep;
//        this.t = t;
//    }

    public <T> void setValue(T t,String rep){
        try {
            Field[] fields = t.getClass().getDeclaredFields();
            DesensitizationProperty property = null;
            //获取参数名字
            for(Field f :fields){
                Object o = invokeGet(f.getName(),t);
                if(!StringUtils.isEmpty(o)){
                    //获得注解
                    property = AnnotatedElementUtils.getMergedAnnotation(f,DesensitizationProperty.class);
//                    property = f.getAnnotation(DesensitizationProperty.class);
                    //如果规则不为空
                    if(property!=null&&!StringUtils.isEmpty(property.rule())){
                        if(CommonVariable.DECTIONALRY_CAST.equals(property.doSomeThing())){
                            //进行字典转换
                            //上线前，清除对应redis
                            //redisTemplate.delete(property.rule()+o.toString());
                            String value = redisTemplate.opsForValue().get(property.rule()+o.toString());
                            if(value==null){
                                //刷新字典
                                this.reloadRedis(property.loadTableName(),property.rule(),o.toString());
                                //加载后重新赋值
                                value = redisTemplate.opsForValue().get(property.rule()+o);
                                if(value==null)
                                    //当value依旧为空，则记录该数据
                                    log.info("记录转换值：{},{},{}",o,value,property.doSomeThing());
                            }
                            invokeSet(f.getName(),value,f,t);
                        }else if(property.doSomeThing().equals(CommonVariable.DESENSITIZATION)){
                            String value  = o.toString();
                            //进行脱敏
                            if(CommonVariable.DESENSITIZATION_NAME.equals(property.rule())){
                                //姓名脱敏
                                StringBuilder sb = new StringBuilder();
                                sb.append(value.substring(0,1));
                                for(int i=0;i<value.length()-1;i++){
                                    sb.append("*");
                                }
                                o = sb.toString();
                            }else if(CommonVariable.DESENSITIZATION_ID_CARD.equals(property.rule())){
                                //身份证脱敏
                                o=value.substring(0,3)+"*******"+value.substring(value.length()-4);
                            }
                            invokeSet(f.getName(),o,f,t);
                        }
                    }
                }
                //空值转换
                if(StringUtils.isEmpty(o)){
                    invokeSet(f.getName(),rep,f,t);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 刷新内存
     * @param dTable:哪张字典表
     * @param code 传入对应值
     */
    public void reloadRedis(String dTable,String key,String code){
        //医院刷新
        if(CommonVariable.LOAD_TABLE_NAME_ORGANIZATION.equals(dTable)){
            redisLoadService.getHosptalName(code);
        }else if(CommonVariable.LOAD_TABLE_NAME_RRS_DIC_CODE.equals(dTable)){
            //RRS_DIC_CODE 对应字段刷新
            redisLoadService.loadRRS_DIC_CODE(code,key);
        }else if(CommonVariable.LOAD_TABLE_NAME_REGION.equals(dTable)){
            //行政区对应刷新
            //因为数据规则(区域表为12位，其他数据位数不确定)，需要补全code
            int len = 12-code.length();
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<len;i++){
                sb.append("0");
            }
            code = code+sb;
            redisLoadService.loadCountry(key,code);
        }
    }

    /**
     * 执行get方法
     *
     * @param fieldName 属性
     */
    private <T> Object invokeGet(String fieldName,T t) {
        try {
            Method method = createGetMethod(fieldName,t);
            return method.invoke(t, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 执行set方法
     *
     * @param fieldName 属性
     * @param value 值
     */
    private <T> void invokeSet(String fieldName, Object value,Field field,T t) {
        Method method = createSetMethod(fieldName,field,t);
        try {
            method.invoke(t, new Object[] { value });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 拼装get方法名字
     */
    private <T> Method createGetMethod(String name,T t){
        StringBuffer sb = new StringBuffer();
        sb.append("get");
        sb.append(name.substring(0, 1).toUpperCase());
        sb.append(name.substring(1));
        try {
            return t.getClass().getMethod(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 拼装set方法名字
     */
    private <T> Method createSetMethod(String name,Field field,T t){
        Class[] parameterTypes = new Class[1];
        parameterTypes[0] = field.getType();
        StringBuffer sb = new StringBuffer();
        sb.append("set");
        sb.append(name.substring(0, 1).toUpperCase());
        sb.append(name.substring(1));
        try {
           return t.getClass().getMethod(sb.toString(), parameterTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
