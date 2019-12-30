package com.xn.hk.oss.config;

import com.aliyun.oss.OSSClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by macro on 2018/5/17.
 */
@Configuration
public class OssConfig {
    //# oss对外服务的访问域名
    private String ALIYUN_OSS_ENDPOINT = "oss-cn-shenzhen.aliyuncs.com";
    //# 访问身份验证中用到用户标识
    private String ALIYUN_OSS_ACCESSKEYID = "test";
    //# 用户用于加密签名字符串和oss用来验证签名字符串的密钥
    private String ALIYUN_OSS_ACCESSKEYSECRET = "test";
    @Bean
    public OSSClient ossClient(){
        return new OSSClient(ALIYUN_OSS_ENDPOINT,ALIYUN_OSS_ACCESSKEYID,ALIYUN_OSS_ACCESSKEYSECRET);
    }
}
