package com.superwei.utils.javassist;

import com.alibaba.excel.EasyExcelFactory;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author weidongge
 * @date 2022-02-24 17:44
 */
public class Test {

    public static void main(String[] args) throws Exception {
//        Class<?> aClass = GenerateClass.initMyClass("Temp1", SuperDto.class, Arrays.asList("name", "age"));
//        Class<?> bClass = GenerateClass.initMyClass("Temp2", SuperDto.class, Arrays.asList("name", "amount"));
//        HashMap<String, Class<?>> map = Maps.newHashMap();
//        map.put("Temp1", aClass);
//        map.put("Temp2", bClass);
//        for (int i = 1; i <= 2; i++) {
//            // 写法1
//            String fileName = TestFileUtil.getPath() + "noModelWrite" + System.currentTimeMillis() + ".xlsx";
//            // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
//            EasyExcelFactory.write(fileName, map.get("Temp" + i)).sheet("模板").doWrite(dataObj());
//        }

        JavassistClassGenerator generator  = new JavassistClassGenerator();
        generator.createClass("com.superwei.utils.javassist", "Tmp9",SuperDto.class);

    }

    private static List<SuperDto> dataObj() {
        List<SuperDto> list = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            SuperDto data = new SuperDto();
            data.setName("名称"+i);
            data.setAge(i + 100);
            data.setAmount(BigDecimal.valueOf(i));
            list.add(data);
        }
        return list;
    }
}
