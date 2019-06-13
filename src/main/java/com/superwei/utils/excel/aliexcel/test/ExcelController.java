package com.superwei.utils.excel.aliexcel.test;


import com.superwei.utils.excel.aliexcel.EasyExcelUtil;
import com.superwei.utils.http.HttpClientUtil;
import org.apache.commons.io.FileUtils;
import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther: weidongge
 * @date: 2019/6/13 11:38
 */
@RestController
public class ExcelController {
    /**
     * 读取 Excel（允许多个 sheet）
     */
    @RequestMapping(value = "readExcelWithSheets", method = RequestMethod.POST)
    public Object readExcelWithSheets(MultipartFile excel) {
        return EasyExcelUtil.readExcel(excel, new OldBillSocialImportInfoVo());
    }



    public static Object readExcelWithSheetsTest(MultipartFile excel) {
        return EasyExcelUtil.readExcel(excel, new OldBillSocialImportInfoVo(),"五险一金大库代理业务明细表", 4);
    }

    public static void main(String[] args) {

        HttpClientUtil httpUtil = HttpClientUtil.getInstance();
        try {
            Map<String, String> params = new HashMap<>();
            params.put("bill_date", "2019-04");
            params.put("company_id", "2020236");
            params.put("category", "1");
            params.put("hidden", "1");
            InputStream inputStream = httpUtil.doGetForStream("", params);

            FileUtils.copyInputStreamToFile(inputStream, new File("D:\\2020236.xlsx"));


            File pdfFile = new File("D:\\2020236.xlsx");
            FileInputStream fileInputStream = new FileInputStream(pdfFile);
            MultipartFile multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);


            Object o = readExcelWithSheetsTest(multipartFile);


            List<OldBillSocialImportInfoVo> oldBillSocialImportInfoVos = (List<OldBillSocialImportInfoVo>) o;


            System.out.println(oldBillSocialImportInfoVos);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 读取 Excel（指定某个 sheet）
     */
    @RequestMapping(value = "readExcel", method = RequestMethod.POST)
    public Object readExcel(MultipartFile excel, int sheetNo,
                            @RequestParam(defaultValue = "1") int headLineNum) {
        return EasyExcelUtil.readExcel(excel, new ImportInfo(), sheetNo, headLineNum);
    }

    /**
     * 导出 Excel（一个 sheet）
     */
    @RequestMapping(value = "writeExcel", method = RequestMethod.GET)
    public void writeExcel(HttpServletResponse response) throws IOException {
        List<ExportInfo> list = getList();
        String fileName = "一个 Excel 文件";
        String sheetName = "第一个 sheet";

        EasyExcelUtil.writeExcel(response, list, fileName, sheetName, new ExportInfo());
    }

    /**
     * 导出 Excel（多个 sheet）
     */
    @RequestMapping(value = "writeExcelWithSheets", method = RequestMethod.GET)
    public void writeExcelWithSheets(HttpServletResponse response) throws IOException {
        List<ExportInfo> list = getList();
        String fileName = "一个 Excel 文件";
        String sheetName1 = "第一个 sheet";
        String sheetName2 = "第二个 sheet";
        String sheetName3 = "第三个 sheet";

        EasyExcelUtil.writeExcelWithSheets(response, list, fileName, sheetName1, new ExportInfo())
                .write(list, sheetName2, new ExportInfo())
                .write(list, sheetName3, new ExportInfo())
                .finish();
    }

    private List<ExportInfo> getList() {
        List<ExportInfo> list = new ArrayList<>();
        ExportInfo model1 = new ExportInfo();
        model1.setName("howie");
        model1.setAge("19");
        model1.setAddress("123456789");
        model1.setEmail("123456789@gmail.com");
        list.add(model1);
        ExportInfo model2 = new ExportInfo();
        model2.setName("harry");
        model2.setAge("20");
        model2.setAddress("198752233");
        model2.setEmail("198752233@gmail.com");
        list.add(model2);
        return list;
    }
}
