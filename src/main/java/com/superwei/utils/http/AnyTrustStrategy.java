package com.superwei.utils.http;

import org.apache.http.conn.ssl.TrustStrategy;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 策略类.
 * 2.2.5-release 版本时会彻底删除，请注意启用新方法RestApiHttpClient
 */
public class AnyTrustStrategy implements TrustStrategy {

    @Override
    public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        return true;
    }
    
}