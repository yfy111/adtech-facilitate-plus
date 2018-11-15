package cn.com.adtech.table;


import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.sql.Date;


/**
 * @Description 数据库字段的常量集合
 * @Author chenguangxue
 * @CreateDate 2018/11/8 11:13
 */
@SuppressWarnings("unchecked")
public class DBFields {

    private static DataType varchar = SQLDataType.VARCHAR;

    // 家庭成员表的字段
    public static class FamilyMember {
        public static Field<String> id = DSL.field("ID", varchar);
        public static Field<String> masterId = DSL.field("MASTER_ID", varchar);
        public static Field<String> familyUserId = DSL.field("FAMILY_USER_ID", varchar);
        public static Field<String> appellation = DSL.field("APPELLATION", varchar);
        public static Field<String> familyUsername = DSL.field("FAMILY_USERNAME", varchar);
        public static Field<String> familyIdcard = DSL.field("FAMILY_IDCARD", varchar);
        public static Field<String> familyUserphone = DSL.field("FAMILY_USERPHONE", varchar);
        public static Field<String> memberIden = DSL.field("MEMBER_IDEN", varchar);
        public static Field<String> extmpi = DSL.field("EXTMPI", varchar);
    }

    // 授权成员表的字段
    public static class AuthoriseMember {
        public static Field<String> id = FamilyMember.id;
        public static Field<String> masterId = FamilyMember.masterId;
        public static Field<String> authoriseUserId = FamilyMember.familyUserId;
        public static Field<String> appellation = FamilyMember.appellation;
        public static Field<String> authoriseUsername = FamilyMember.familyUsername;
        public static Field<String> authoriseIdcard = FamilyMember.familyIdcard;
        public static Field<String> authoriseUserphone = FamilyMember.familyUserphone;
        public static Field<String> memberIden = FamilyMember.memberIden;
    }

    // 反馈问题表的字段
    public static class FeedbackQuestion {
        public static Field<String> id = DSL.field("ID", varchar);
        public static Field<String> questionAllType = DSL.field("QUESTION_ALL_TYPE", varchar);
        public static Field<String> questionDesc = DSL.field("QUESTION_DESC", varchar);
        public static Field<String> feedbackUserId = DSL.field("FEEDBACK_USER_ID", varchar);
        public static Field<Date> feedbackTime = DSL.field("FEEDBACK_TIME", SQLDataType.DATE);
    }

    // 反馈类型表的字段
    public static class FeedbackType {
        public static Field<String> id = DSL.field("ID", varchar);
        public static Field<String> questionId = DSL.field("QUESTION_ID", varchar);
        public static Field<String> questionType = DSL.field("QUESTION_TYPE", varchar);
        public static Field<Date> feedbackTime = DSL.field("FEEDBACK_TIME", SQLDataType.DATE);
    }


    // 机构和基层统计表的字段
    public static class nMediclaInstitutionCount {
        public static Field<String> parentId = DSL.field("PARENT_ID", varchar);
        public static Field<String> cityId = DSL.field("CITY_ID", varchar);
        public static Field<String> extcdaId = DSL.field("EXTCDA_ID", varchar);
        public static Field<String> institutionCode = DSL.field("INSTITUTION_CODE", varchar);
        public static Field<String> institutionName = DSL.field("INSTITUTION_NAME", varchar);
        public static Field<String> institutionType = DSL.field("INSTITUTION_TYPE", varchar);
        public static Field<String> archivesCount = DSL.field("ARCHIVES_COUNT", varchar);
        public static Field<String> readingPeopleCount = DSL.field("READING_PEOPLE_COUNT", varchar);
        public static Field<String> readingCount = DSL.field("READING_COUNT", varchar);
        public static Field<String> status = DSL.field("STATUS", varchar);
        public static Field<Date> lastTime = DSL.field("LAST_TIME", SQLDataType.DATE);
        public static Field<Date> countDate = DSL.field("COUNT_DATE", SQLDataType.DATE);
    }



    // 居民统计表的字段
    public static class nResidentCount {
        public static Field<String> parentId = DSL.field("PARENT_ID", varchar);
        public static Field<String> cityId = DSL.field("CITY_ID", varchar);
        public static Field<String> extcdaId = DSL.field("EXTCDA_ID", varchar);
        public static Field<Integer> queryPeopleCount = DSL.field("QUERY_PEOPLE_COUNT", varchar);
        public static Field<Integer> queryCount = DSL.field("QUERY_COUNT", varchar);
        public static Field<Integer> platformId = DSL.field("PLATFORM_ID", varchar);
        public static Field<Integer> platformCount = DSL.field("PLATFORM_COUNT", varchar);
        public static Field<String> status = DSL.field("STATUS", varchar);
        public static Field<Date> lastTime = DSL.field("LAST_TIME", SQLDataType.DATE);
        public static Field<Date> countDate = DSL.field("COUNT_DATE", SQLDataType.DATE);
    }
}
