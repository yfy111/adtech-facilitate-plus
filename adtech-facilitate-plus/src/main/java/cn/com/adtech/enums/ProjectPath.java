package cn.com.adtech.enums;

import lombok.Getter;

/**
 * @Description 项目访问路径
 * @Author chenguangxue
 * @CreateDate 2018/11/14 17:48
 */
@Getter
public enum ProjectPath {

    WEB_INDEX(""),
    WEB_LOGIN(""),

    H5_INDEX(""),
    H5_LOGIN(""),
    ;

    private final String path;

    ProjectPath(String path) {
        this.path = path;
    }
}
