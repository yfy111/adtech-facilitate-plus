package cn.com.adtech.service;

import cn.com.adtech.component.UserinfoService;
import cn.com.adtech.domain.login.LoginSuccessUserinfo;
import cn.com.adtech.domain.medicalinformation.request.BirthCertificateParameter;
import cn.com.adtech.domain.medicalinformation.response.BirthCertificateShowData;
import cn.com.adtech.table.DataTables;
import cn.com.adtech.util.GetMpiUtil;
import cn.com.adtech.util.SetNullToStringUtil;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectQuery;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Description 出生医学证明页面的业务流程
 * @Author PYH
 * @CreateDate 2018/11/7 14:56
 */
@Service
@Transactional
public class BirthCertificateService {


    @Autowired
    private UserinfoService userinfoService;
    @Autowired
    @Qualifier("secondJooq")
    private DSLContext jooq2;

    @Autowired
    private SetNullToStringUtil setNullToStringUtil;

    @Autowired
    private GetMpiUtil getMpiUtil;

    /**
     * 查询出生医学证明信息
     *
     * @param parameter
     * @return
     * @throws Exception
     */
    public BirthCertificateShowData selectBirthCertificateInfo(BirthCertificateParameter parameter, LoginSuccessUserinfo user) {


//
//        String realName = parameter.getRealName();
//        String appellation = parameter.getAppellation();
//        String identityCard = parameter.getIdentityCard();
//        String pidentityCard = user.getIdentity();
//        if (StringUtils.isEmpty(identityCard)) {
//            identityCard = pidentityCard;
//        }
        BirthCertificateShowData fetch = new BirthCertificateShowData();
//        //是子女关系时候，使用子女姓名+父母身份证号查询
//        if (!StringUtils.isEmpty(appellation) && "子女".equals(appellation)) {
//            StringBuffer sb = new StringBuffer();
//            sb.append("select * from T_HR_B010101");
//            sb.append(" where EXHDSD0001003='" + realName + "'");
//            sb.append(" and EXHDSD0001026='" + pidentityCard + "'");
//            sb.append(" union all ");
//            sb.append(" select * from T_HR_B010101");
//            sb.append(" where EXHDSD0001003='" + realName + "'");
//            sb.append(" and EXHDSD0001037='" + pidentityCard + "'");
//            List<BirthCertificateShowData> list = jooq2.fetch(sb.toString()).into(BirthCertificateShowData.class);
//            if (list != null && list.size() > 0) fetch = list.get(0);
//        }
//        else {
            SelectQuery<Record> selectQuery = jooq2.selectQuery(DataTables.T_HR_CSYXDJ);
            Condition eq = DSL.field("EXTMPI").in(getMpiUtil.getMpiByList(parameter.getIdentityCard()));
            selectQuery.addConditions(eq);
            List<BirthCertificateShowData> record = selectQuery.fetch().into(BirthCertificateShowData.class);
            if (record != null && record.size() > 0) fetch = record.get(0);
//        }
        setNullToStringUtil.setValue(fetch,"-");
        return fetch;

    }
}
