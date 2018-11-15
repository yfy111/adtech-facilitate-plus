import cn.com.adtech.FacilitatePlusApplication;
import cn.com.adtech.domain.family.request.FamilyMemberQueryParameter;
import cn.com.adtech.domain.family.response.FamilyMemberShowData;
import cn.com.adtech.service.IndexService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FacilitatePlusApplication.class)
public class test {



    @Autowired
    private IndexService indexService;

    @Test
    public void findFamily()
    {
//        FamilyMemberQueryParameter parameter=new FamilyMemberQueryParameter();
//        parameter.setMemberIden("F");
//        parameter.setPageSize("5");
//        parameter.setUserId("11");
//
//
//        List<FamilyMemberShowData> list=indexService.findFamilyMember(parameter);
    }
}
