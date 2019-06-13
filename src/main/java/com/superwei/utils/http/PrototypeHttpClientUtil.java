package com.superwei.utils.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
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
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharsetUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


/**
 * ClassName:PrototypeHttpClientUtil.
 * Function: HttpClientUtil原型类,每次都创建HttpClient,
 * 主要解决HttpClientUtil单例时java.net.SocketException: Connection reset问题.
 * @author   wsy
 * @version  Ver 1.0
 * @since    Ver 1.0
 *2.2.5-release 版本时会彻底删除，请注意启用新方法RestApiHttpClient
 */
@SuppressWarnings("deprecation")
public class PrototypeHttpClientUtil {

    private static Log log = LogFactory.getLog(PrototypeHttpClientUtil.class);
    public static String defaultEncoding = "UTF8";
    private static final int BUFFER_SIZE = 1024;
    private static final int INT_VALUE_100000 = 100000;
    /**
     * 策略类.
     */

    private class AnyTrustStrategy implements TrustStrategy {

        @Override
        public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            return true;
        }
        
    }
    
    private CloseableHttpClient createCloseableHttpClient() {
        //设置连接参数
        ConnectionConfig connConfig = ConnectionConfig.custom().setCharset(Charset.forName(defaultEncoding)).build();

        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();
        ConnectionSocketFactory plainSf = new PlainConnectionSocketFactory();
        registryBuilder.register("http", plainSf);
        // 指定信任密钥存储对象和连接套接字工厂
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            SSLContext sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, new AnyTrustStrategy())
                    .build();
            LayeredConnectionSocketFactory sslSf = new SSLConnectionSocketFactory(sslContext,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            registryBuilder.register("https", sslSf);
        } catch (KeyStoreException keyStoreException) {
            throw new RuntimeException(keyStoreException);
        } catch (KeyManagementException keyManagementException) {
            throw new RuntimeException(keyManagementException);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException(noSuchAlgorithmException);
        }
        Registry<ConnectionSocketFactory> registry = registryBuilder.build();
        SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(INT_VALUE_100000).build();
        //设置连接管理器
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
        connManager.setDefaultConnectionConfig(connConfig);
        connManager.setDefaultSocketConfig(socketConfig);
        // 指定cookie存储对象
        BasicCookieStore cookieStore = new BasicCookieStore();
        // 构建客户端
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore)
                .setConnectionManager(connManager).build();

        return client;
    }
    
    
    
    /**
     * readStream:读取输入流.
     */
    private String readStream(InputStream in, String encoding) throws Exception {
        if (in == null) {
            return null;
        }

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

    }
    

    /**
     * 带有文件的post服务调用.
     * @param url 服务地址.
     * @param queryParams 查询参数.
     * @param formParams form参数.
     */
    public String doPostRetString(String url, Map<String, String> queryParams, Map<String, String> formParams) {
        
        CloseableHttpClient chc = this.createCloseableHttpClient();
        CloseableHttpResponse  response = null;
        InputStream is = null;
        String result = null;
        try {
            response = this.doPost(url, queryParams, formParams, defaultEncoding, null, chc);
            is = response != null ? response.getEntity().getContent() : null;
            result = this.readStream(is, null);
        } catch (Exception exception) {
            log.error("调用服务[" + url + "]返回数据错误", exception);
        } finally {
            try {
                is.close();
                response.close();
                chc.close();
            } catch (IOException ioException) {
                is = null;
                response = null;
                chc = null;
            }
        }
        return result;
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

        CloseableHttpClient chc = this.createCloseableHttpClient();
        CloseableHttpResponse response = null;
        InputStream is = null;
        String result = null;
        try {
            HttpPost pm = new HttpPost();
            URIBuilder builder = new URIBuilder(url);

            // 填入查询参数
            if (queryParams != null && !queryParams.isEmpty()) {
                builder.setParameters(this.paramsConverter(queryParams));
            }
            pm.setURI(builder.build());
            // 填入文件参数
            if (fileParams != null && !fileParams.isEmpty() && formParams != null && !formParams.isEmpty()) {
                pm.setEntity(this.fileConverter(fileParams, formParams));
            } else if (formParams != null && !formParams.isEmpty()) {
                pm.setEntity(new UrlEncodedFormEntity(this.paramsConverter(formParams), charset));
            }
            response = chc.execute(pm);
            is = response != null ? response.getEntity().getContent() : null;
            result = this.readStream(is, null);
        } catch (Exception exception) {
            log.error("调用服务[" + url + "]返回数据错误", exception);
        } finally {
            try {
                is.close();
                response.close();
                chc.close();
            } catch (IOException ioException) {
                is = null;
                response = null;
                chc = null;
            }
        }
        return result;

    }
    
    /**
     * 基本的Post请求.
     * @param url 请求url.
     * @param queryParams 请求头的查询参数.
     * @param formParams post表单的参数.
     */
    private CloseableHttpResponse doPost(String url, Map<String, String> queryParams, Map<String, String> formParams,
            String charset, String body, CloseableHttpClient httpClient)
            throws URISyntaxException, ClientProtocolException, IOException {
        HttpPost pm = new HttpPost();
        URIBuilder builder = new URIBuilder(url);

        // 填入查询参数
        if (queryParams != null && !queryParams.isEmpty()) {
            builder.setParameters(this.paramsConverter(queryParams));
        }
        pm.setURI(builder.build());
        if (body != null && !body.isEmpty()) {
            pm.addHeader("Content-Type", "text/json");
            pm.addHeader("Accept", "text/json");
            pm.setEntity(new StringEntity(body, charset));
        }
        // 填入表单参数
        if (formParams != null && !formParams.isEmpty()) {
            pm.setEntity(new UrlEncodedFormEntity(this.paramsConverter(formParams), charset));
        }
        return httpClient.execute(pm);

    }
    
    private List<NameValuePair> paramsConverter(Map<String, String> params) {
        List<NameValuePair> nvps = new LinkedList<NameValuePair>();
        Set<Entry<String, String>> paramsSet = params.entrySet();
        for (Entry<String, String> paramEntry : paramsSet) {
            nvps.add(new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue()));
        }
        return nvps;
    }
    
    /**
     * fileConverter:填充文件参数.
     */
    private HttpEntity fileConverter(Map<String, File> params, Map<String, String> formParams) {
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
    
    
}
