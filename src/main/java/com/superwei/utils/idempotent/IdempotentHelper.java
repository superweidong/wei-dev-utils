package com.superwei.utils.idempotent;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/**
 * @author weidongge
 * @date 2022-03-29 15:59
 */
@Slf4j
public class IdempotentHelper {

    /**
     * 获取md5串
     * @param json reqJSON
     * @param excludeKeys excludeKeys
     * @return java.lang.String
     * @author weidongge@51shebao.com
     * @date 2022/3/29
     */
    public String getParamMD5(final String json, String... excludeKeys) {
        TreeMap paramTreeMap = JSON.parseObject(json, TreeMap.class);
        if (excludeKeys!=null) {
            List<String> excludeKeyList = Arrays.asList(excludeKeys);
            if (!excludeKeyList.isEmpty()) {
                for (String excludeKey : excludeKeyList) {
                    paramTreeMap.remove(excludeKey);
                }
            }
        }
        String paramTreeMapJson = JSON.toJSONString(paramTreeMap);
        String md5deDupParam = jdkMD5(paramTreeMapJson);
        log.debug("md5 = {}, excludeKeys = {} paramTreeMapJSON = {}", md5deDupParam, Arrays.deepToString(excludeKeys), paramTreeMapJson);
        return md5deDupParam;
    }

    private static String jdkMD5(String src) {
        String res = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] mdBytes = messageDigest.digest(src.getBytes());
            res = DatatypeConverter.printHexBinary(mdBytes);
        } catch (Exception e) {
            log.error("MD5处理异常", e);
        }
        return res;
    }
}
