package cn.com.adtech.service;

import cn.com.adtech.domain.dictionary.DictionaryDetailShowData;
import cn.com.adtech.domain.po.Organization;
import cn.com.adtech.util.CommonVariable;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 加载redis数据
 */
@Service
public class RedisLoadService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private DictionaryDetailService detailService;



    /**
     * get hospitalName by String
     * @param code
     * @return
     */
    public String getHosptalName(String code){
        String key = CommonVariable.REDIS_KEY_HM_HOSPITAL_NAME+code;
        //如果不存在则存储
        if(!hasKey(key)){
            List<Organization> list = organizationService.getOrganization(code);
            for(Organization o : list){
                setStringValue(key,o.getOrgName());
            }
        }
        return redisTemplate.opsForValue().get(key);
    }



    /**
     * 获取就诊类型List
     * @param code
     * @return
     */
    public List<DictionaryDetailShowData> getBltypeName(String code){
        String key = CommonVariable.REDIS_KEY_HM_BTN_TYPE+code;
        //如果不存在则存储
        if(!hasKey(key)){
            List<DictionaryDetailShowData> list = detailService.findDictionaryDetailDataList("documentType");
            redisTemplate.opsForValue().set(key,JSON.toJSONString(list));
        }
        return JSON.parseArray(redisTemplate.opsForValue().get(key),DictionaryDetailShowData.class);
    }


    /**
     *  加载RRS_DIC_CODE 对应参照进入redis
     * @param code
     * @param type
     * @return
     */
    public void loadRRS_DIC_CODE(String code,String type){
        List<DictionaryDetailShowData> list = detailService.loadRRS_DIC_CODE(CommonVariable.getMapping.get(type));
        for(DictionaryDetailShowData data : list){
            redisTemplate.opsForValue().set(type+data.getTypeValue(),data.getTypeName());
        }
    }

    /**
     * 加载区域
     * @param areaCode
     * @param type
     */
    public void loadCountry(String type,String areaCode){
        List<DictionaryDetailShowData> list = detailService.loadCountry(areaCode);
        for(DictionaryDetailShowData data : list){
            redisTemplate.opsForValue().set(type+data.getTypeValue(),data.getTypeName());
        }
    }

    /**
     * 判断是否有key
     */
    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * 存放redis
     * @param key
     * @param value
     */
    public void setStringValue(String key,String value){
        redisTemplate.opsForValue().set(key,value);
    }
}
