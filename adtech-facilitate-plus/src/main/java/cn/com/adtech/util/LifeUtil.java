package cn.com.adtech.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 生命周期list
 */
public class LifeUtil {
    //childhood,少年:juvenile,青年:youth,中年:middleAge,old:老年
    public static Map<String, Map<String,Integer>> lifeMap = new HashMap<>();
    static{
        for(Integer i=1;i<=5;i++){
            Map<String,Integer> mp = new HashMap<>();
            if(i==1){
                mp.put("begin",0);
                mp.put("end",6);
                lifeMap.put("childhood",mp);
            }else if(i==2){
                mp.put("begin",1);
                mp.put("end",17);
                lifeMap.put("juvenile",mp);
            }else if(i==3){
                mp.put("begin",18);
                mp.put("end",40);
                lifeMap.put("youth",mp);
            }else if(i==4){
                mp.put("begin",41);
                mp.put("end",65);
                lifeMap.put("middleAge",mp);
            }else if(i==5){
                mp.put("begin",66);
                mp.put("end",300);
                lifeMap.put("old",mp);
            }
        }
    }
}
