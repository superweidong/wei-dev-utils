package com.superwei.utils.http2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class Http2Test {



    private static final String mUrl = "";
    private static final Logger logger = LoggerFactory.getLogger(Http2Test.class);



    public void testRequest() throws IOException, URISyntaxException {
        logger.info("{}","dsadasd");
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("params", "{     \"channel\":1,         \"to\":[             \"18513318418\",             \"18513318418\"         ],         \"msg\":\"SMS_60825523\",         \"template\":{             \"thirdPartyKey\":\"SMS_60825523\",             \"params\":[             {                 \"code\":\"132431\",                 \"product\":\"搜房网\"               },             {                 \"code\":\"132431\",                 \"product\":\"搜房网\"             }             ]         },         \"priority\":2 }");
        //String postForm = Http2Utils.postForm(mUrl, queryParams);
        Http2Utils http2Utils = Http2Utils.getInstance();
        String postForm = http2Utils.postForm(mUrl, queryParams);
        System.out.println(postForm);
    }

    /*class TestInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String url = request.url().toString();
            System.out.println("url=" + url);
            Response response = null;
            if (url.equals(mUrl)) {
                String responseString = "{\"message\":\"我是模拟的数据\"}";//模拟的错误的返回值
                response = new Response.Builder()
                        .code(400)
                        .request(request)
                        .protocol(Protocol.HTTP_1_0)
                        .body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()))
                        .addHeader("content-type", "application/json")
                        .build();
            } else {
                response = chain.proceed(request);
            }
            return response;
        }
    }*/

}
