package com.waben.stock.applayer.operation.util;

import com.deepoove.poi.XWPFTemplate;
import com.waben.stock.applayer.operation.warpper.mail.QuotoInquiry;
import com.waben.stock.interfaces.exception.ServiceException;
import net.sf.jett.transform.ExcelTransformer;
import org.apache.http.client.utils.DateUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by yuyidi on 2018/3/3.
 * @desc
 */
public class ExcelUtil {

    static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 渲染询价单模板
     */
    public static String renderInquiry(String contextPath, QuotoInquiry quotoInquiry) {
        String file = null;
        try (InputStream is = ExcelUtil.class.getResourceAsStream("/officetemplate/excel/inquiry.xlsx")) {
            if (is == null) {
                logger.error("模板文件找不到");
            }
            Date date = new Date();
            String path = contextPath + new SimpleDateFormat("yyyy/MM/dd/").format(date);
            File f = new File(path);
            if (!f.exists()) {
                f.mkdirs();
            }
            String fileName = System.currentTimeMillis() + ".xlsx";
            file = path + fileName;
            try (OutputStream os = new FileOutputStream(file)) {
                Map<String, Object> model = new HashMap<>();
                model.put("date", DateUtils.formatDate(quotoInquiry.getDate(), "yyyy/MM/dd"));
                model.put("tenor", quotoInquiry.getTenor());
                model.put("structure", "ATM Call");
                model.put("underlying", quotoInquiry.getUnderlying());
                model.put("code", quotoInquiry.getCode());
                model.put("strike", quotoInquiry.getStrike());
                model.put("amount", quotoInquiry.getAmount());
                model.put("price", quotoInquiry.getPrice());
                Workbook workbook = new ExcelTransformer().transform(is, model);
                workbook.write(os);
            } catch (IOException | InvalidFormatException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Deprecated
    public static void rendPurchase() {
        Map<String, Object> datas = new HashMap<String, Object>() {{
            put("underlying", "新湖瑞丰");
            put("code", "600208");
            put("begin", "2018/03/03");
            put("end", "2018/04/03");
            put("strike", "9.88");
            put("amount", "100W");
            put("rate", "7.5%");
        }};
        XWPFTemplate template = null;
        try (InputStream is = ClassLoader.getSystemResourceAsStream("officetemplate/world/purchase.docx");
             FileOutputStream out = new FileOutputStream("E:/out.docx")) {
            template = XWPFTemplate.compile(is).render(datas);
            template.write(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (template != null) {
                    template.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
