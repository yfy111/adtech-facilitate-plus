package cn.com.adtech.service;

import cn.com.adtech.domain.po.Organization;
import cn.com.adtech.table.DataTables;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 医院service
 */
@Service
public class OrganizationService {

    @Autowired
    @Qualifier("secondJooq")
    private DSLContext jooq2;

    /**
     * find by org_code
     * @param code
     * @return
     */
    public List<Organization> getOrganization(String code){
        return jooq2.select(DataTables.OrganizationField.orgName,DataTables.OrganizationField.areaCode
                ,DataTables.OrganizationField.orgCode,DataTables.OrganizationField.parentId,DataTables
                        .OrganizationField.orgType).from(DataTables.ORGANIZATION).where(
                                DataTables.OrganizationField.orgCode.eq(code)).fetch().into(Organization.class);

    }
}
