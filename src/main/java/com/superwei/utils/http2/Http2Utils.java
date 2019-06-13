package com.superwei.utils.http2;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.NotSupportedException;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * Http/2.0请求工具类.
 * Title:Http2Utils
 */
public class Http2Utils {

    private static final Logger logger = LoggerFactory.getLogger(Http2Utils.class);

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
    public static final String PATCH = "PATCH";

    private static final String UTF8 = "UTF-8";
    private static final String GBK = "GBK";
    /**
     * 支持的请求类型
     */
    private static final String [] SUPPORT_REQ_ARR = new String[] { POST, PUT, DELETE, PATCH };
    
    /**
     * 默认编码.
     */
    private static final String DEFAULT_CHARSET = UTF8;
    /**
     * 默认请求方法.
     */
    private static final String DEFAULT_METHOD = GET;
    /**
     * 默认类型.
     */
    private static final String DEFAULT_MEDIA_TYPE = javax.ws.rs.core.MediaType.APPLICATION_JSON;
    /**
     * 是否打印请求日志.
     */
    private static final boolean DEFAULT_LOG = true;
    /**
     * 最大连接数.
     */
    private static final int MAX_IDLE_CONNECTIONS = Runtime.getRuntime().availableProcessors() << 2;
    /**
     * keepAliveDuration 时间.
     */
    private static final int KEEP_ALIVE_DURATION = 5;
    
    private static SSLContext sslContext;
    
    private static OkHttpClient okHttpClient;
    //https签名证书路径
    private static final String CER_NAME = "";
    
    
    /*private static final OkHttpClient okHttpClient = new OkHttpClient
                        .Builder()
                        .addInterceptor(new RetryInterceptor())
                        .retryOnConnectionFailure(true)
                        .connectionPool(new ConnectionPool(MAX_IDLE_CONNECTIONS, KEEP_ALIVE_DURATION, TimeUnit.MINUTES))
                        .readTimeout(20, TimeUnit.SECONDS)
                        .connectTimeout(20, TimeUnit.SECONDS)
                        .build();*/
    
    
    /**
     *静态内部类.
     */
    static class Http2UtilHolder {
        private static final Http2Utils instance = new Http2Utils();
    }

    /**
     *获取Http2Utils 实例.
     */
    public static Http2Utils getInstance() {
        return Http2UtilHolder.instance;
    }

    @SuppressWarnings("deprecation")
    public Http2Utils() {
        if (StringUtils.isEmpty(CER_NAME)) {
            //忽略所有证书
            ignoreAllCert();
        } else {
            //选择证书
            try {
                Resource resource = new FileSystemResource(CER_NAME);
                InputStream is = resource.getInputStream();//获取
                setCert(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(new RetryInterceptor())//请求重试拦截器
                .retryOnConnectionFailure(true) //失败重试开启
                .connectionPool(new ConnectionPool(MAX_IDLE_CONNECTIONS, KEEP_ALIVE_DURATION, TimeUnit.MINUTES))
                .readTimeout(20, TimeUnit.SECONDS) //读超时
                .connectTimeout(20, TimeUnit.SECONDS) //连接超时
                .sslSocketFactory(sslContext.getSocketFactory())//设置SSL证书
                .hostnameVerifier((hostname, session) -> true)//此处将hostnameVerifier 验证关闭掉,会使SSL的安全性降低,如果想要使用这个验证,请不要使用私签证书,注释掉下面这段代码,运行体验一下
                /*.cookieJar(new CookieJar() { //设置cookie持久化
                    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url.host(), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })*/
                .build();
    }
    
    /**
     * GET请求.
     * @param url  URL地址
     * @return String
     */
    public  String get(String url) {
        OkHttp okHttp = new OkHttp();
        okHttp.setUrl(url);
        return execute(okHttp);
    }
    /**
     * 带编码的GET请求.
     * @param   url      URL地址
     * @param   charset  编码
     * @return  String
     */
    
    public  String get(String url, String charset) {
        OkHttp okHttp = new OkHttp();
        okHttp.setUrl(url);
        okHttp.setResponseCharset(charset);
        return execute(okHttp);
    }

    /**
     * 带查询参数的GET查询.
     * @param url  URL地址
     * @param queryMap 查询参数
     * @return String
     */
    public  String get(String url,  Map<String, String> queryMap) {
        OkHttp okHttp = new OkHttp();
        okHttp.setUrl(url);
        okHttp.setQueryMap(queryMap);
        return execute(okHttp);
    }

    /**
     * 带查询参数的和编码GET查询.
     * @param url URL地址
     * @param queryMap 查询参数
     * @param charset  编码
     * @return String
     */
    public  String get(String url,  Map<String, String> queryMap, String charset) {
        OkHttp okHttp = new OkHttp();
        okHttp.setUrl(url);
        okHttp.setQueryMap(queryMap);
        okHttp.setResponseCharset(charset);
        return execute(okHttp);
    }

    /**
     *  带请求参数的POST请求.
     * application/json
     * @param url URL地址
     * @param obj 数据对象
     * @return String
     */
    public  String postJson(String url, Object obj) {
        OkHttp okHttp = new OkHttp();
        okHttp.setUrl(url);
        okHttp.setMethod(POST);
        okHttp.setData(JSON.toJSONString(obj));
        okHttp.setMediaType(javax.ws.rs.core.MediaType.APPLICATION_JSON);
        return execute(okHttp);
    }
    /**
     * 带请求参数和请求头的POST请求.
     * application/json
     * @param url URL地址
     * @param obj 数据对象
     * @param headerMap 请求头
     * @return String
     */
    
    public  String postJsonWithHeaders(String url, Object obj, Map<String, String> headerMap) {
        OkHttp okHttp = new OkHttp();
        okHttp.setUrl(url);
        okHttp.setMethod(POST);
        okHttp.setData(JSON.toJSONString(obj));
        okHttp.setMediaType(javax.ws.rs.core.MediaType.APPLICATION_JSON);
        okHttp.setHeaderMap(headerMap);
        return execute(okHttp);
    }

    /**
     * 带请求参数的POST请求.
     * application/x-www-form-urlencoded
     * @param url  URL地址
     * @param formMap 表单数据
     * @return String
     */
    public  String postForm(String url, Map<String, String> formMap) {
        String data = "";
        if (MapUtils.isNotEmpty(formMap)) {
            data = formMap.entrySet()
                          .stream()
                          .map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue()))
                          .collect(Collectors.joining("&"));
        }
        OkHttp okHttp = new OkHttp();
        okHttp.setUrl(url);
        okHttp.setMethod(POST);
        okHttp.setData(data);
        okHttp.setMediaType(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED);
        return execute(okHttp);
    }
    /**
     * 带请求参数和请求头的POST请求.
     * application/x-www-form-urlencoded
     * @param url  URL地址
     * @param formMap 表单数据
     * @param headerMap 请求头数据
     * @return String
     */
    
    public  String postFormWithHeaders(String url, Map<String, String> formMap, Map<String, String> headerMap){
        String data = "";
        if (MapUtils.isNotEmpty(formMap)) {
            data = formMap.entrySet()
                    .stream()
                    .map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue()))
                    .collect(Collectors.joining("&"));
        }
        OkHttp okHttp = new OkHttp();
        okHttp.setUrl(url);
        okHttp.setMethod(POST);
        okHttp.setData(data);
        okHttp.setMediaType(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED);
        okHttp.setHeaderMap(headerMap);
        return execute(okHttp);
    }

    /**
     * 带请求数据、类型和编码的POST请求.
     * @param url URL地址
     * @param data 请求体
     * @param mediaType 类型
     * @param charset  编码
     * @return String
     */
    public  String post(String url, String data, String mediaType, String charset){
        OkHttp okHttp = new OkHttp();
        okHttp.setUrl(url);
        okHttp.setMethod(POST);
        okHttp.setData(data);
        okHttp.setMediaType(mediaType);
        okHttp.setResponseCharset(charset);
        return execute(okHttp);
    }
    /**
     * 带请求数据、类型、编码和头信息的POST请求.
     * @param url URL地址
     * @param data 请求体
     * @param mediaType 类型
     * @param charset  编码
     * @param headerMap 请求头数据
     * @return String
     */
    
    public  String postWithHeaders(String url, String data, 
            String mediaType, String charset, Map<String, String> headerMap) {
        OkHttp okHttp = new OkHttp();
        okHttp.setUrl(url);
        okHttp.setMethod(POST);
        okHttp.setData(data);
        okHttp.setMediaType(mediaType);
        okHttp.setResponseCharset(charset);
        okHttp.setHeaderMap(headerMap);
        return execute(okHttp);
    }

    /**
     * 通用执行方法.
     */
    private static String execute(OkHttp okHttp) {

        if (StringUtils.isBlank(okHttp.requestCharset)) {
            okHttp.requestCharset = DEFAULT_CHARSET;
        }
        if (StringUtils.isBlank(okHttp.responseCharset)) {
            okHttp.responseCharset = DEFAULT_CHARSET;
        }
        if (StringUtils.isBlank(okHttp.method)) {
            okHttp.method = DEFAULT_METHOD;
        }
        if (StringUtils.isBlank(okHttp.mediaType)) {
            okHttp.mediaType = DEFAULT_MEDIA_TYPE;
        }
        if (okHttp.requestLog) {
            // 记录请求日志
            logger.info(okHttp.toString());
        }
        //请求url
        String url = okHttp.url;
        Request.Builder request = new Request.Builder();
        
        //设置请求参数
        if (MapUtils.isNotEmpty(okHttp.queryMap)) {

            String queryParams = okHttp.queryMap.entrySet().stream()
                    .map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue()))
                    .collect(Collectors.joining("&"));

            url = String.format("%s%s%s", url, url.contains("?") ? "&" : "?", queryParams);
        }
        
        request.url(url);
        
        //判断请求头map是否为空
        if (MapUtils.isNotEmpty(okHttp.headerMap)) {
            okHttp.headerMap.forEach(request::addHeader);
        }
        //方法转为大写
        String method = okHttp.method.toUpperCase();
        //设置请求方式
        String mediaType = String.format("%s;charset=%s", okHttp.mediaType, okHttp.requestCharset);
        //校验请求方式
        if (StringUtils.equals(method, GET)) {
            request.get();
        } else if (ArrayUtils.contains(SUPPORT_REQ_ARR, method)) {
            RequestBody requestBody = RequestBody.create(MediaType.parse(mediaType), okHttp.data);
            request.method(method, requestBody);
        } else {
            throw new NotSupportedException(String.format("http method:%s not support!", method));
        }

        String result = "";
        try {
            logger.debug("Request Params is ->%s");
            logger.info(String.format("Request Params is ->%s", okHttp.data));
            //System.out.println("Request Params is "+okHttp.data);
            Response response = okHttpClient.newCall(request.build()).execute();
            byte[] bytes = response.body().bytes();
            result = new String(bytes, okHttp.responseCharset);
            if (okHttp.responseLog) {
                // 记录返回日志
                logger.info(String.format("Got response->%s", result));
            }
        } catch (Exception exception) {
            logger.error(okHttp.toString(), exception);
        }
        return result;
    }

    /**
     * 初始化SSLContext.
     * @param certificate
     * @author weidongge
     * @return void
     * 下午2:10:11
     */
    public void setCert(InputStream certificate) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            String defaultType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(defaultType);
            keyStore.load(null);
            String certificateAlias = Integer.toString(0);
            keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
            sslContext = SSLContext.getInstance("TLS");
            final TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init
                    (
                            null,
                            trustManagerFactory.getTrustManagers(),
                            new SecureRandom()
                    );
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    /**
     * 忽略所有https证书
     */
    private void ignoreAllCert() {
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            
            @Override
            public void checkClientTrusted(
                    X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }
        }};
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts,
                    new SecureRandom());
        } catch (Exception e) {
            logger.error("ssl出现异常");
        }

    }    
    
    /**
     * 请求信息封装类.
     * Title:OkHttp
     *
     */
    static class OkHttp {
        private String url;
        private String method = DEFAULT_METHOD;
        private String data;
        private String mediaType = DEFAULT_MEDIA_TYPE;
        private Map<String, String> queryMap;
        private Map<String, String> headerMap;
        private String requestCharset = DEFAULT_CHARSET;
        private boolean requestLog = DEFAULT_LOG;

        private String responseCharset = DEFAULT_CHARSET;
        private boolean responseLog = DEFAULT_LOG;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getMediaType() {
            return mediaType;
        }

        public void setMediaType(String mediaType) {
            this.mediaType = mediaType;
        }

        public Map<String, String> getQueryMap() {
            return queryMap;
        }

        public void setQueryMap(Map<String, String> queryMap) {
            this.queryMap = queryMap;
        }

        public Map<String, String> getHeaderMap() {
            return headerMap;
        }

        public void setHeaderMap(Map<String, String> headerMap) {
            this.headerMap = headerMap;
        }

        public String getRequestCharset() {
            return requestCharset;
        }

        public void setRequestCharset(String requestCharset) {
            this.requestCharset = requestCharset;
        }

        public boolean isRequestLog() {
            return requestLog;
        }

        public void setRequestLog(boolean requestLog) {
            this.requestLog = requestLog;
        }

        public String getResponseCharset() {
            return responseCharset;
        }

        public void setResponseCharset(String responseCharset) {
            this.responseCharset = responseCharset;
        }

        public boolean isResponseLog() {
            return responseLog;
        }

        public void setResponseLog(boolean responseLog) {
            this.responseLog = responseLog;
        }
        
        
    }
}
