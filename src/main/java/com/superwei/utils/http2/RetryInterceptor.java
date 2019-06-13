package com.superwei.utils.http2;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;


/**
 * httpUtil2.0工具请求重试拦截器.
 * Title:RetryInterceptor
 * @Description:httpUtil2.0工具请求重试拦截器.
 */
public class RetryInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(RetryInterceptor.class);
    //最大重试次数
    private int maxRetry = 3;
    //延迟
    private long delay = 3000;
    //叠加延迟
    private long increaseDelay = 5000;

    /**
     * 无参构造.
     */
    public RetryInterceptor() {
    }

    /**
     * 带惨构造.
     * @param maxRetry 最大重试次数
     */
    public RetryInterceptor(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    /**
     * 带惨构造.
     * @param maxRetry 最大重试次数
     * @param delay  延迟时间
     */
    public RetryInterceptor(int maxRetry, long delay) {
        this.maxRetry = maxRetry;
        this.delay = delay;
    }

    /**
     * 带惨构造.
     * @param maxRetry 最大重试次数
     * @param delay  延迟时间
     * @param increaseDelay  增加延迟时间
     */
    public RetryInterceptor(int maxRetry, long delay, long increaseDelay) {
        this.maxRetry = maxRetry;
        this.delay = delay;
        this.increaseDelay = increaseDelay;
    }

    /**
     * 拦截器.
     */
    @Override
    public Response intercept(Chain chain) throws IOException {

        RetryWrapper retryWrapper = proceed(chain);

        while (retryWrapper.isNeedReTry()) {
            retryWrapper.retryNum++;
            logger.error(String.format("request failed  the url  is ->%s", retryWrapper.request.url().toString()));
            logger.error(String.format("request retry   the time is ->%s", retryWrapper.retryNum));
            try {
                Thread.sleep(delay + (retryWrapper.retryNum - 1) * increaseDelay);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            proceed(chain, retryWrapper.request, retryWrapper);
        }
        return retryWrapper.response == null ? chain.proceed(chain.request()) : retryWrapper.response;
    }

    private RetryWrapper proceed(Chain chain) throws IOException {
        Request request = chain.request();
        RetryWrapper retryWrapper = new RetryWrapper(request, maxRetry);

        proceed(chain, request, retryWrapper);

        return retryWrapper;
    }

    private void proceed(Chain chain, Request request, RetryWrapper retryWrapper) throws IOException {
        try {
            Response response = chain.proceed(request);
            retryWrapper.setResponse(response);
        } catch (SocketException | SocketTimeoutException socketException) {
            socketException.printStackTrace();
        }
    }

    /**
     * 静态类.
     * Title:RetryWrapper
     * @Description:封装请求重试、请求次数等参数. 
     */
    static class RetryWrapper {
        //假如设置为3次重试的话，则最大可能请求5次（默认1次+3次重试 + 最后一次默认）
        volatile int retryNum = 0; 
        Request request;
        Response response;
        private int maxRetry;

        public RetryWrapper(Request request, int maxRetry) {
            this.request = request;
            this.maxRetry = maxRetry;
        }

        public void setResponse(Response response) {
            this.response = response;
        }

        Response response() {
            return this.response;
        }

        Request request() {
            return this.request;
        }

        public boolean isSuccessful() {
            return response != null && response.isSuccessful();
        }

        public boolean isNeedReTry() {
            return !isSuccessful() && retryNum < maxRetry;
        }

        public void setRetryNum(int retryNum) {
            this.retryNum = retryNum;
        }

        public void setMaxRetry(int maxRetry) {
            this.maxRetry = maxRetry;
        }
    }
}
