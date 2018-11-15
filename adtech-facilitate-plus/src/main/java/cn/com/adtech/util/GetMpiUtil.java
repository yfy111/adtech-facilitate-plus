package cn.com.adtech.util;

import cn.com.adtech.component.UserinfoService;
import cn.com.adtech.domain.login.LoginSuccessUserinfo;
import cn.com.adtech.domain.vo.FamilyMemberVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class GetMpiUtil {
    @Autowired
    private UserinfoService userinfoService;

    /**
     * 获取MPI-就诊记录
     * @param identityCard
     * @return
     */
    public String getMpisForMedical(String identityCard){
        String mpis[] =mpisArray(identityCard);
        if (mpis==null) return "";
        StringBuilder sbmp = new StringBuilder("(");
        for(int i =0;i<mpis.length;i++){
            sbmp.append("'").append(mpis[i]).append("'");
            if(i<mpis.length-1){
                sbmp.append(",");
            }
        }
        sbmp.append(")");
        return sbmp.toString();
    }

    /**
     * 获取MPI list
     * @param identityCard
     * @return
     */
    public List<String> getMpiByList(String identityCard){
        String mpis[] =mpisArray(identityCard);
        List<String> list = new ArrayList<>();
        for(String s : mpis){
            list.add(s);
        }
        return list;
    }


    private  String[] mpisArray(String identityCard){
        LoginSuccessUserinfo userinfo = userinfoService.loadLoginUserinfo();
        String mpi = null;
        if(userinfo.getIdentity().equals(identityCard)){
            mpi = userinfo.getExtmpi();
        }else{
            List<FamilyMemberVo> members = userinfo.getFamilyMembers();
            for(FamilyMemberVo vo : members){
                if(identityCard.equals(vo.getIdentity())){
                    mpi = vo.getExtmpi();
                }
            }
        }
        if(mpi==null) return null;
        return mpi.split(",");
    }
}
