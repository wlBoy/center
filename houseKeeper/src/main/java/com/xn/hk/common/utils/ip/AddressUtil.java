package com.xn.hk.common.utils.ip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.xn.hk.common.utils.http.HttpClientUtil;
import com.xn.hk.common.utils.string.StringUtil;

/**
 * 获取地址类
 * 
 * @author ruoyi
 */
public class AddressUtil
{
    private static final Logger log = LoggerFactory.getLogger(AddressUtil.class);

    public static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";

    public static String getRealAddressByIP(String ip)
    {
        String address = "XX XX";
        // 内网不查询
        if (IpUtil.internalIp(ip))
        {
            return "内网IP";
        }
        String rspStr = String.valueOf(HttpClientUtil.httpPost(IP_URL, "ip=" + ip));
        if (StringUtil.isEmpty(rspStr))
        {
            log.error("获取地理位置异常 {}", ip);
            return address;
        }
        JSONObject obj = JSONObject.parseObject(rspStr);
        JSONObject data = obj.getObject("data", JSONObject.class);
        String region = data.getString("region");
        String city = data.getString("city");
        address = region + " " + city;
        return address;
    }
}
