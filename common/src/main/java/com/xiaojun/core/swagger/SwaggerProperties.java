package com.xiaojun.core.swagger;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author f00lish
 * @version 2018/7/16
 * Created By IntelliJ IDEA.
 * Qun:530350843
 */


@Data
@ConfigurationProperties("swagger")
public class SwaggerProperties {

    private static DiscoveryClient discoveryClient;

    private Boolean enabled;

    /**
     * 标题
     **/
    private String title = "";
    /**
     * 描述
     **/
    private String description = "";
    /**
     * 版本
     **/
    private String version = "";

    private Contact contact = new Contact();

    private String basePackage = "";


    private List<String> basePath = new ArrayList<>();

    private List<String> excludePath = new ArrayList<>();

    private String host = "";

    private Authorization authorization = new Authorization();


    public SwaggerProperties(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Data
    @NoArgsConstructor
    public static class Contact {

        /**
         * 联系人
         **/
        private String name = "";
        /**
         * 联系人url
         **/
        private String url = "";
        /**
         * 联系人email
         **/
        private String email = "";

    }

    @Data
    @NoArgsConstructor
    public static class Authorization {

        private String authHost;

        private String clientId = "xcloud-swagger";

        private String clientSecret = "123456";

        private String type = "oauth2";

        private String serverId = "xcloud-gate";

        private String authorizationUrl = "/oauth/authorize";

        private String tokenUrl = "/oauth/token";

        private String tokenName = "access_token";

        private String scopeCode = "USER_INFO";

        private String scopeDesc = "用户信息";

        public String getAuthorizationUrl() {
            return this.getAuthHost() + authorizationUrl;
        }

        public String getTokenUrl() {
            return this.getAuthHost() + tokenUrl;
        }

        public String getAuthHost() {
            if (authHost == null && discoveryClient != null) {
                List<ServiceInstance> gateClients = discoveryClient.getInstances(serverId);
                if (gateClients != null && gateClients.size() > 0) {
                    authHost = (gateClients.get(0).getUri().toString()) + "/api/auth";
                } else {
                    List<ServiceInstance> authClients = discoveryClient.getInstances("xcloud-auth");
                    if (authClients != null && authClients.size() > 0) {
                        authHost = (authClients.get(0).getUri().toString());
                    }
                }
            }
            return authHost;
        }
    }


}
