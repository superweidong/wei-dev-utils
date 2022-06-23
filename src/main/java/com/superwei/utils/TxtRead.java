package com.superwei.utils;

import com.alibaba.excel.EasyExcelFactory;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author weidongge
 * @date 2022-05-10 12:48
 */
public class TxtRead {


    public static void main(String[] args) {

        File file = new File("/Users/apple/Downloads/test-trans-csv.sql");
        List<ExportVo> exportVos = txt2String(file);
        EasyExcelFactory.write("/Users/apple/Downloads/" + "论文数据.xlsx", ExportVo.class).sheet("论文数据").doWrite(exportVos);
    }

    public static List<ExportVo> txt2String(File file) {
        List<ExportVo> result = new ArrayList<>();
        BufferedReader br = null;
        try {
            //构造一个BufferedReader类来读取文件
            br = new BufferedReader(new FileReader(file));
            String s;
            //使用readLine方法，一次读一行
            List<String> list = new ArrayList<>();
            while ((s = br.readLine()) != null) {
                list.add(s);
                if (s.equals("") || StringUtils.isAllBlank(s) || StringUtils.isAllEmpty(s)){
                    extracted(result, list);
                    list.clear();
                }
            }
            if (!list.isEmpty()) {
                extracted(result, list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static void extracted(List<ExportVo> result, List<String> list) {
        Optional<String> any = list.stream().filter(s1 -> s1.contains("DOI:")).findAny();
        int pidIdx = any.isPresent() ? 4 : 3;
        ExportVo exportVo = new ExportVo();
        exportVo.setTitle(list.get(0));
        String s1 = list.get(1);
        String replace = s1.replaceAll("[a-zA-Z]", "")
                .replace(" ", "")
                .replace("(", "")
                .replace(")", "")
                .replace("-", "");
        String substring = replace.substring(0, replace.length() - 4);
        exportVo.setEffectFactor(substring);
        exportVo.setAuthor(list.get(2));
        String s2 = list.get(pidIdx).replace("PMID: ", "");
        exportVo.setPMid(s2);
        result.add(exportVo);
    }

    @Data
    static class ExportVo {
        private String pMid;
        private String effectFactor;
        private String title;
        private String author;
    }


}
