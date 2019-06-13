package com.superwei.utils.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BestMatchSpec;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Map.Entry;

/**
 * http请求封装类.
 * 2.2.5-release 版本时会彻底删除，请注意启用新方法RestApiHttpClient
 */
@SuppressWarnings("deprecation")
public class HttpClientUtil {

    private static Log log = LogFactory.getLog(HttpClientUtil.class);

    private static final int BUFFER_SIZE = 1024;
    
    private static final int INT_VALUE_100000 = 100000;

    private static final int PORT_80 = 80;
    
    private static volatile HttpClientUtil instance;
    
    private ConnectionConfig connConfig;

    private SocketConfig socketConfig;

    private ConnectionSocketFactory plainSf;

    private KeyStore trustStore;

    private SSLContext sslContext;

    private LayeredConnectionSocketFactory sslSf;

    private Registry<ConnectionSocketFactory> registry;

    private PoolingHttpClientConnectionManager connManager;

    private volatile HttpClient client;

    private volatile BasicCookieStore cookieStore;

    public static String defaultEncoding = "UTF8";

    private static List<NameValuePair> paramsConverter(Map<String, String> params) {
        List<NameValuePair> nvps = new LinkedList<NameValuePair>();
        Set<Entry<String, String>> paramsSet = params.entrySet();
        for (Entry<String, String> paramEntry : paramsSet) {
            nvps.add(new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue()));
        }
        return nvps;
    }

    /** 读取输入流.
     * @param in 输入流.
     * @param encoding 编码方式.
     */
    public static String readStream(InputStream in, String encoding) {
        if (in == null) {
            return null;
        }
        try {
            InputStreamReader inReader = null;
            if (encoding == null) {
                inReader = new InputStreamReader(in, defaultEncoding);
            } else {
                inReader = new InputStreamReader(in, encoding);
            }
            char[] buffer = new char[BUFFER_SIZE];
            int readLen = 0;
            StringBuffer sb = new StringBuffer();
            while ((readLen = inReader.read(buffer)) != -1) {
                sb.append(buffer, 0, readLen);
            }
            inReader.close();
            return sb.toString();
        } catch (IOException exception) {
            log.error("读取返回内容出错", exception);
        }
        return null;
    }

    private HttpClientUtil() {
        //设置连接参数
        connConfig = ConnectionConfig.custom().setCharset(Charset.forName(defaultEncoding)).build();
        socketConfig = SocketConfig.custom().setSoTimeout(INT_VALUE_100000).build();
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();
        plainSf = new PlainConnectionSocketFactory();
        registryBuilder.register("http", plainSf);
        //指定信任密钥存储对象和连接套接字工厂
        try {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, new AnyTrustStrategy()).build();
            sslSf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            registryBuilder.register("https", sslSf);
        } catch (KeyStoreException keyStoreException) {
            throw new RuntimeException(keyStoreException);
        } catch (KeyManagementException keyManagementException) {
            throw new RuntimeException(keyManagementException);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException(noSuchAlgorithmException);
        }
        registry = registryBuilder.build();
        // 设置连接管理器
        connManager = new PoolingHttpClientConnectionManager(registry);
        connManager.setDefaultConnectionConfig(connConfig);
        connManager.setDefaultSocketConfig(socketConfig);
        // 指定cookie存储对象
        cookieStore = new BasicCookieStore();
        // 构建客户端
        client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).setConnectionManager(connManager)
                .build();
    }

    /**
     *静态内部类.
     */
    static class HttpClientUtilHolder {
        private static final HttpClientUtil instance = new HttpClientUtil();
    }

    /**
     *获取HttpClientUtil 实例.
     */
    public static HttpClientUtil getInstance() {
        return HttpClientUtilHolder.instance;
    }

    public InputStream doGet(String url) throws URISyntaxException, ClientProtocolException, IOException {
        HttpResponse response = this.doGet(url, null);
        return response != null ? response.getEntity().getContent() : null;
    }

    /**
     * 基本的Get请求.
     * @param url 请求url.
     * @param queryParams 请求头的查询参数.
     */
    public HttpResponse doGet(String url, Map<String, String> queryParams)
            throws URISyntaxException, ClientProtocolException, IOException {
        HttpGet gm = new HttpGet();
        URIBuilder builder = new URIBuilder(url);
        // 填入查询参数
        if (queryParams != null && !queryParams.isEmpty()) {
            builder.setParameters(HttpClientUtil.paramsConverter(queryParams));
        }
        gm.setURI(builder.build());
        return client.execute(gm);
    }
    
    public String doGetForString(String url) throws URISyntaxException, ClientProtocolException, IOException {
        return HttpClientUtil.readStream(this.doGet(url), null);
    }
    
    public String doGetForString(String url, Map<String, String> queryParams)
            throws URISyntaxException, ClientProtocolException, IOException {
        return HttpClientUtil.readStream(this.doGetForStream(url, queryParams), null);
    }
    
    public InputStream doGetForStream(String url, Map<String, String> queryParams)
            throws URISyntaxException, ClientProtocolException, IOException {
        HttpResponse response = this.doGet(url, queryParams);
        return response != null ? response.getEntity().getContent() : null;
    }

    public InputStream doPostForStream(String url, Map<String, String> queryParams, String body)
            throws URISyntaxException, ClientProtocolException, IOException {
        HttpResponse response = this.doPost(url, queryParams, null, defaultEncoding, body);
        return response != null ? response.getEntity().getContent() : null;
    }

    public InputStream doPostForStream(String url, Map<String, String> queryParams, Map<String, String> formParams)
            throws URISyntaxException, ClientProtocolException, IOException {
        HttpResponse response = this.doPost(url, queryParams, formParams, defaultEncoding, null);
        return response != null ? response.getEntity().getContent() : null;
    }

    public InputStream doPostForStream(String url, Map<String, String> queryParams, Map<String, String> formParams,
            String charset, Map<String, File> fileParams)
            throws URISyntaxException, ClientProtocolException, IOException {
        HttpResponse response = this.doPost(url, queryParams, formParams, charset, null, fileParams);
        return response != null ? response.getEntity().getContent() : null;
    }
    
    
    public String doPostForString(String url, Map<String, String> queryParams)
            throws URISyntaxException, ClientProtocolException, IOException {
        return HttpClientUtil.readStream(this.doPostForStream(url, queryParams, ""), null);
    }

    public String doPostForString(String url, Map<String, String> queryParams, String body)
            throws URISyntaxException, ClientProtocolException, IOException {
        return HttpClientUtil.readStream(this.doPostForStream(url, queryParams, body), null);
    }

    public String doPostRetString(String url, Map<String, String> queryParams, Map<String, String> formParams)
            throws URISyntaxException, ClientProtocolException, IOException {
        return HttpClientUtil.readStream(this.doPostForStream(url, queryParams, formParams), null);
    }

    /**
     * 带有文件的post服务调用.
     * @param url 服务地址.
     * @param queryParams 查询参数.
     * @param formParams form参数.
     * @param charset 字符类型设置.
     * @param fileParams 文件参数.
     */
    public String doPostRetString(String url, Map<String, String> queryParams, Map<String, String> formParams,
            String charset, Map<String, File> fileParams)
            throws URISyntaxException, ClientProtocolException, IOException {
        return HttpClientUtil.readStream(this.doPostForStream(url, queryParams, formParams, charset, fileParams), null);
    }
    
    /**
     * 请求cxf reatfull 服务.
     * @param url 服务路径.
     * @param param 参数.
     * @return 操作结果.
     */
    public String doPostRetString(String url, String param) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("content-type","application/json");
        httpPost.setEntity(new StringEntity(param,"utf-8"));
        CloseableHttpResponse response2 = httpclient.execute(httpPost);
        String result = "";
        try {
            result = EntityUtils.toString(response2.getEntity());
        } finally {
            response2.close();
        }
        return result;
    }
    
    /**
     * 基本的Post请求.
     * @param url 请求url.
     * @param queryParams 请求头的查询参数.
     * @param formParams post表单的参数.
     */
    
    public HttpResponse doPost(String url, Map<String, String> queryParams, Map<String, String> formParams,
            String charset, String body) throws URISyntaxException, ClientProtocolException, IOException {
        HttpPost pm = new HttpPost();
        URIBuilder builder = new URIBuilder(url);

        // 填入查询参数
        if (queryParams != null && !queryParams.isEmpty()) {
            builder.setParameters(HttpClientUtil.paramsConverter(queryParams));
        }
        pm.setURI(builder.build());
        if (body != null && !body.isEmpty()) {
            pm.addHeader("Content-Type", "text/json");
            pm.addHeader("Accept", "text/json");
            pm.setEntity(new StringEntity(body, charset));
        }
        // 填入表单参数
        if (formParams != null && !formParams.isEmpty()) {
            pm.setEntity(new UrlEncodedFormEntity(HttpClientUtil.paramsConverter(formParams), charset));
        }
        return client.execute(pm);
    }

    /**
     * 基本的Post请求.
     * @param url 请求url.
     * @param queryParams 请求头的查询参数.
     * @param formParams post表单的参数.
     * @param fileParams post文件的参数.
     */
    public HttpResponse doPost(String url, Map<String, String> queryParams, Map<String, String> formParams,
            String charset, String body, Map<String, File> fileParams)
            throws URISyntaxException, ClientProtocolException, IOException {
        HttpPost pm = new HttpPost();
        URIBuilder builder = new URIBuilder(url);

        // 填入查询参数
        if (queryParams != null && !queryParams.isEmpty()) {
            builder.setParameters(HttpClientUtil.paramsConverter(queryParams));
        }
        pm.setURI(builder.build());
        if (body != null && !body.isEmpty()) {
            pm.addHeader("Content-Type", "text/json");
            pm.addHeader("Accept", "text/json");
            pm.setEntity(new StringEntity(body, charset));
        }
        // 填入文件参数
        if (fileParams != null && !fileParams.isEmpty() && formParams != null && !formParams.isEmpty()) {
            pm.setEntity(HttpClientUtil.fileConverter(fileParams, formParams));
        } else if (formParams != null && !formParams.isEmpty()) {
            // 填入表单参数
            pm.setEntity(new UrlEncodedFormEntity(HttpClientUtil.paramsConverter(formParams), charset));
        }
        return client.execute(pm);
    }
    
    /**
     * 把文件参数set到HttpEntity中.
     * @param params 文件参数 (只支持一个文件).
     * @param formParams form参数.
     */
    
    private static HttpEntity fileConverter(Map<String, File> params, Map<String, String> formParams) {
        HttpEntity reqEntity = null;
        Set<Entry<String, String>> formparamsSet = formParams.entrySet();
        Set<Entry<String, File>> paramsSet = params.entrySet();
        Entry<String, File> paramEntry = paramsSet.iterator().next();
        File file = paramEntry.getValue();
        FileBody fileBody = new FileBody(file); 
        try {
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                                    .addPart(paramEntry.getKey(), fileBody);
            for (Entry<String, String> formParamEntry : formparamsSet) {
                StringBody value = new StringBody(
                        formParamEntry.getValue(), ContentType.create(
                                "text/plain", Consts.UTF_8));
                multipartEntityBuilder.addPart(formParamEntry.getKey(), value);
            }
            reqEntity = multipartEntityBuilder.setCharset(CharsetUtils.get("UTF-8")).build();
        } catch (UnsupportedEncodingException exception) {
            exception.printStackTrace();
        }
        return reqEntity;
    }


    /**
     * 获取当前Http客户端状态中的Cookie.
     * @param domain 作用域.
     * @param port 端口 传null 默认80.
     * @param path Cookie路径 传null 默认"/".
     * @param useSecure Cookie是否采用安全机制 传null 默认false.
     */
    public Map<String, Cookie> getCookie(String domain, Integer port, String path, Boolean useSecure) {
        if (domain == null) {
            return null;
        }
        if (port == null ) {
            port = PORT_80;
        }
        if (path == null) {
            path = "/";
        }
        if (useSecure == null) {
            useSecure = false;
        }
        List<Cookie> cookies = cookieStore.getCookies();
        if (cookies == null || cookies.isEmpty()) {
            return null;
        }

        CookieOrigin origin = new CookieOrigin(domain, port, path, useSecure);
        BestMatchSpec cookieSpec = new BestMatchSpec();
        Map<String, Cookie> retVal = new HashMap<String, Cookie>();
        for (Cookie cookie : cookies) {
            if (cookieSpec.match(cookie, origin)) {
                retVal.put(cookie.getName(), cookie);                
            }
        }
        return retVal;
    }

    /**
     * 批量设置Cookie.
     * @param cookies cookie键值对图.
     * @param domain 作用域 不可为空.
     * @param path 路径 传null默认为"/".
     * @param useSecure 是否使用安全机制 传null 默认为false.
     * @return 是否成功设置cookie.
     */
    public boolean setCookie(Map<String, String> cookies, String domain, String path, Boolean useSecure) {
        synchronized (cookieStore) {
            if (domain == null) {
                return false;
            }
            if (path == null) {
                path = "/";
            }
            if (useSecure == null) {
                useSecure = false;
            }
            if (cookies == null || cookies.isEmpty()) {
                return true;
            }
            Set<Entry<String, String>> set = cookies.entrySet();
            String key = null;
            String value = null;
            for (Entry<String, String> entry : set) {
                key = entry.getKey();
                if (key == null || key.isEmpty() || value == null || value.isEmpty()) {
                    throw new IllegalArgumentException("cookies key and value both can not be empty");
                }
                BasicClientCookie cookie = new BasicClientCookie(key, value);
                cookie.setDomain(domain);
                cookie.setPath(path);
                cookie.setSecure(useSecure);
                cookieStore.addCookie(cookie);
            }
            return true;
        }
    }

    /**
     * 设置单个Cookie.
     * @param key Cookie键.
     * @param value Cookie值.
     * @param domain 作用域 不可为空.
     * @param path 路径 传null默认为"/".
     * @param useSecure 是否使用安全机制 传null 默认为false.
     * @return 是否成功设置cookie.
     */
    public boolean setCookie(String key, String value, String domain, String path, Boolean useSecure) {
        Map<String, String> cookies = new HashMap<String, String>();
        cookies.put(key, value);
        return setCookie(cookies, domain, path, useSecure);
    }

    /**
     * get方式请求（建议不要使用get方式）.
     * 
     * @param url
     *            路径.
     * @param param
     *            参数.
     */
    public static String sendGet(String url, String param) throws Exception {
        String result = "";
        String urlName = url + "?" + param;
        URL url2 = new URL(urlName);
        URLConnection connection = url2.openConnection();
        connection.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        in.close();
        return result;
    }
    
    /**
     * 以post的方式请求.
     */
    public String sendPost(String url, String param) throws Exception {
        String result = "";
        URL httpurl = new URL(url);
        HttpURLConnection httpConn = (HttpURLConnection) httpurl.openConnection();
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);
        PrintWriter out = new PrintWriter(httpConn.getOutputStream());
        out.print(param);
        out.flush();
        out.close();
        BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        in.close();
        return result;
    }
    
}
