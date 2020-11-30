package com.xn.hk.common.utils.http;

import okhttp3.*;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @ClassName: OkHttpUtil
 * @Package: com.xn.hk.common.utils.http
 * @Description: 基于okhttp发送http请求的工具类
 * @Author: wanlei
 * @Date: 2020年11月30日 下午12:06:52
 */
public class OkHttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);

    public final static int READ_TIMEOUT = 100;
    public final static int CONNECT_TIMEOUT = 60;
    public final static int WRITE_TIMEOUT = 60;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final String CONTENT_TYPE = "Content-Type";

    private static final byte[] LOCKER = new byte[0];
    private static OkHttpUtil mInstance;
    private OkHttpClient okHttpClient;


    private OkHttpUtil() {
        //todo 由于是静态工具类，只会创建client一次，如果以后需要不同请求不同超时时间，不能这样使用
        okhttp3.OkHttpClient.Builder clientBuilder = new okhttp3.OkHttpClient.Builder();
        //设置读取超市时间
        clientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        //设置超时连接时间
        clientBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        //设置写入超时时间
        clientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        //支持HTTPS请求，跳过证书验证
        clientBuilder.sslSocketFactory(createSSLSocketFactory());
        clientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        okHttpClient = clientBuilder.build();
    }

    /**
     * 单例模式获取NetUtils
     *
     * @return
     */
    public static OkHttpUtil getInstance() {
        if (mInstance == null) {
            synchronized (LOCKER) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtil();
                }
            }
        }
        return mInstance;
    }


    /**
     * get请求，同步方式，获取网络数据，是在主线程中执行的，需要新起线程，将其放到子线程中执行
     *
     * @param url
     * @return
     */
    public Response getData(String url) {
        return getData(url, null);
    }


    /**
     * get请求，同步方式，获取网络数据，是在主线程中执行的，需要新起线程，将其放到子线程中执行
     *
     * @param url
     * @return
     */
    public Response getData(String url, Map<String, String> headerMap) {
        //1 构造Request
        Request.Builder builder = new Request.Builder().get().url(url);
        //Request request = builder.get().url(url).build();
        //builder = builder.get().url(url);
        /*if (headerMap != null && headerMap.isEmpty()) {
            for (Map.Entry<String, String> headerEntry : headerMap.entrySet()) {
                builder.addHeader(headerEntry.getKey(), headerEntry.getValue());
            }
        }*/
        addHeaders(headerMap,builder);
        Request request = builder.build();


        //2 将Request封装为Call
        Call call = okHttpClient.newCall(request);
        //3 执行Call，得到response
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * post form表单 请求，同步方式，提交数据，是在主线程中执行的，需要新起线程，将其放到子线程中执行
     *
     * @param url
     * @param bodyParams
     * @return
     */
    /*public Response postFormData(String url, Map<String, String> bodyParams) {
        //1构造RequestBody
        RequestBody body = setRequestBody(bodyParams);
        //2 构造Request
        Request.Builder requestBuilder = new Request.Builder();
        Request request = requestBuilder.post(body).addHeader(CONTENT_TYPE, HttpContentTypeEnum.FORM.contentTypeValue).url(url).build();
        //3 将Request封装为Call
        Call call = mOkHttpClient.newCall(request);
        //4 执行Call，得到response
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }*/

    /**
     * post方式
     *
     * @param url        请求url
     * @param bodyParams requestbody
     * @param headerMap  请求头信息
     * @return
     * @throws IOException
     */
    public Response postData(String url, Map<String, Object> bodyParams, Map<String, String> headerMap) throws Exception {
        //1构造RequestBody
        RequestBody body = setRequestBody(bodyParams, headerMap);
        //2 构造Request
        Request.Builder requestBuilder = new Request.Builder().post(body).url(url);
        //requestBuilder = requestBuilder.post(body).url(url);
        addHeaders(headerMap, requestBuilder);
        Request request = requestBuilder.build();
        //Request request = requestBuilder.post(body).addHeader(CONTENT_TYPE, HttpContentTypeEnum.JSON.contentTypeValue).url(url).build();
        //3 将Request封装为Call
        Call call = okHttpClient.newCall(request);
        //4 执行Call，得到response
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    /**
     * put请求方式
     *
     * @param url
     * @param bodyParams
     * @return
     */
    public Response putData(String url, Map<String, Object> bodyParams, Map<String, String> headerMap) throws Exception {
        //1构造RequestBody
        RequestBody body = setRequestBody(bodyParams, headerMap);
        //2 构造Request
        Request.Builder requestBuilder = new Request.Builder().put(body).url(url);
        addHeaders(headerMap, requestBuilder);
        Request request = requestBuilder.build();
        //3 将Request封装为Call
        Call call = okHttpClient.newCall(request);
        //4 执行Call，得到response
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;

    }


    /**
     * @param url
     * @param bodyParams
     * @return
     */
    public Response delData(String url, Map<String, Object> bodyParams, Map<String, String> headerMap) throws Exception {
        //1构造RequestBody
        RequestBody body = setRequestBody(bodyParams, headerMap);
        //2 构造Request
        Request.Builder requestBuilder = new Request.Builder().delete(body).url(url);
        addHeaders(headerMap, requestBuilder);
        Request request = requestBuilder.build();
        //3 将Request封装为Call
        Call call = okHttpClient.newCall(request);
        //4 执行Call，得到response
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    /**
     * post的请求参数，构造RequestBody
     *
     * @param bodyParams
     * @return
     */
    private RequestBody setRequestBody(Map<String, Object> bodyParams, Map<String, String> headerMap) throws Exception {
        //判断请求头中是否存在 content-type字段
        if (headerMap == null || !headerMap.containsKey(CONTENT_TYPE)) {
            throw new Exception("请求头信息配置中无 Content-Type配置，请先配置");
        }

        String contentType = headerMap.get(CONTENT_TYPE);
        if ("application/x-www-form-urlencoded".equals(contentType)) {
            //表单提交 就是key-value 都是字符串型
            //转换
            Map<String, String> strBodyParamMap = new HashMap<>();
            if (bodyParams != null && !bodyParams.isEmpty()) {
                bodyParams.forEach((key, value) -> {
                    if (value != null) {
                        strBodyParamMap.put(key, (String) value);
                    }
                });
            }
            return buildRequestBodyByMap(strBodyParamMap);
        } else {
            //json
            return buildRequestBodyByJson(com.alibaba.fastjson.JSON.toJSONString(bodyParams));
        }

    }

    /**
     * 表单方式提交构建
     *
     * @param bodyParams
     * @return
     */
    private RequestBody buildRequestBodyByMap(Map<String, String> bodyParams) {
        RequestBody body = null;
        okhttp3.FormBody.Builder formEncodingBuilder = new okhttp3.FormBody.Builder();
        if (bodyParams != null) {
            Iterator<String> iterator = bodyParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                formEncodingBuilder.add(key, bodyParams.get(key));
                logger.info(" 请求参数：{}，请求值：{} ", key, bodyParams.get(key));
            }
        }
        body = formEncodingBuilder.build();
        return body;
    }

    /**
     * json方式提交构建
     *
     * @param jsonStr
     * @return
     */
    private RequestBody buildRequestBodyByJson(String jsonStr) {
        return RequestBody.create(JSON, jsonStr);
    }


    /**
     * 生成安全套接字工厂，用于https请求的证书跳过
     *
     * @return
     */
    public SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }
        return ssfFactory;
    }


    /**
     * 针对json post处理
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public String postJson(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public void postJsonAsyn(String url, String json, final MyNetCall myNetCall) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        //2 构造Request
        Request.Builder requestBuilder = new Request.Builder();
        Request request = requestBuilder.post(body).url(url).build();
        //3 将Request封装为Call
        Call call = okHttpClient.newCall(request);
        //4 执行Call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                myNetCall.failed(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                myNetCall.success(call, response);

            }
        });
    }

    /**
     * 自定义网络回调接口
     */
    public interface MyNetCall {
        void success(Call call, Response response) throws IOException;

        void failed(Call call, IOException e);
    }

    /**
     * 构建http get请求，将参数拼接到url后面
     *
     * @param url
     * @param para
     * @return
     * @throws URISyntaxException
     * @throws MalformedURLException
     */
    public static String buildHttpGet(String url, Map<String, Object> para)
            throws URISyntaxException, MalformedURLException {
        URIBuilder builder = new URIBuilder(url);
        Set<String> set = para.keySet();
        for (String key : set) {
            builder.setParameter(key, String.valueOf(para.get(key)));
        }
        return builder.build().toURL().toString();
    }


    /**
     * 添加header信息
     *
     * @param headerMap
     * @param builder
     * @return
     */
    private static Request.Builder addHeaders(Map<String, String> headerMap, Request.Builder builder) {
        if (headerMap != null && !headerMap.isEmpty()) {
            headerMap.forEach((key, value) -> builder.addHeader(key, value));
        }
        return builder;
    }

    /**
     * 用于信任所有证书
     */
    class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

}