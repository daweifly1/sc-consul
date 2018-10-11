package com.xiaojun.core.config.druid;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * @author f00lish
 * @version 2018/1/24
 * Created By IntelliJ IDEA.
 * Qun:530350843
 */
@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/druid/*",
        initParams = {
                @WebInitParam(name = "loginUsername", value = "inner"),// 用户名
                @WebInitParam(name = "loginPassword", value = "admin1234"),// 密码
                @WebInitParam(name = "resetEnable", value = "false")// 禁用HTML页面上的“Reset All”功能
        }
)
public class DruidStatViewServlet extends StatViewServlet {

}
