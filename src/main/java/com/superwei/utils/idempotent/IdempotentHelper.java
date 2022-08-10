package com.superwei.utils.idempotent;

import com.alibaba.fastjson.JSON;
import com.shebao.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.TreeMap;

/**
 * @author weidongge
 * @date 2022-03-29 15:59
 */
@Slf4j
public class IdempotentHelper {

    /**
     * 获取md5串
     *
     * @param json        reqJSON
     * @param excludeKeys excludeKeys
     * @return java.lang.String
     * @author weidongge
     * @date 2022/3/29
     */
    public static String getMd5(final String json, String... excludeKeys) {
        TreeMap paramTreeMap = JSON.parseObject(json, TreeMap.class);
        if (excludeKeys.length > 0) {
            for (String excludeKey : excludeKeys) {
                paramTreeMap.remove(excludeKey);
            }
        }
        return jdkMd5(JSON.toJSONString(paramTreeMap));
    }

    /**
     * MD5
     *
     * @param src src
     * @return java.lang.String
     * @author weidongge
     * @date 2022/8/5 15:35
     **/
    private static String jdkMd5(String src) {
        String res;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] mdBytes = messageDigest.digest(src.getBytes());
            res = DatatypeConverter.printHexBinary(mdBytes);
        } catch (Exception e) {
            log.error("MD5处理异常", e);
            throw new BusinessException("MD5处理异常", e);
        }
        return res;
    }
}
