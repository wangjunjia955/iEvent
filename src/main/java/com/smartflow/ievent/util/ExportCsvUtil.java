package com.smartflow.ievent.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smartflow.ievent.dto.EventForListOutputDTO;
import org.springframework.util.StringUtils;
import com.csvreader.CsvWriter;

public class ExportCsvUtil {

    /**
     * 判断是否是IE浏览器
     *
     * @param request
     * @return
     */
    public static boolean isMSBrowser(HttpServletRequest request) {
        String[] IEBrowserSignals = {"MSIE", "Trident", "Edge"};
        String userAgent = request.getHeader("User-Agent");
        for (String signal : IEBrowserSignals) {
            if (userAgent.contains(signal)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 导出csv文件
     *
     * @param request
     * @param response
     * @param dataList
     * @param filePath
     * @param fileName
     * @return boolean
     */
    public static String exportCsv(HttpServletRequest request, HttpServletResponse response, List<String[]> dataList, String[] headers, String filePath, String fileName) {
        try {
            //可以指定某个路径创建
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
            File csvFile = File.createTempFile(fileName, ".csv", file);
            //也可以直接创建在AppData\Local\Temp临时文件
//			File csvFile = File.createTempFile(fileName, ".csv");
            FileOutputStream out = new FileOutputStream(csvFile);
            CsvWriter csvWriter = new CsvWriter(csvFile.getCanonicalPath(), ',', Charset.forName("UTF-8"));
            //写入表头信息
            csvWriter.writeRecord(headers);
            for (String[] dataArray : dataList) {
                csvWriter.writeRecord(dataArray);
            }
            csvWriter.endRecord();
            //关闭写入的流
            csvWriter.close();
            System.out.println("csv文件导出成功");
			/*
			java.io.OutputStream out = response.getOutputStream();
            byte[] b = new byte[1024];
            java.io.File fileLoad = new java.io.File(csvFile.getCanonicalPath());
            response.reset();
            response.setContentType("application/csv");//设置文件ContentType类型，这样设置，下载时会自动判断文件类型
            String trueCSVName="我的任务清单.csv";
            //如果是IE浏览器，则用URFEncode解析
            if(isMSBrowser(request)){
            	trueCSVName = URLEncoder.encode(trueCSVName, "UTF-8");
            }else{//如果是谷歌、火狐则解析为ISO-8859-1
            	trueCSVName = new String(trueCSVName.getBytes("GBK"), "ISO-8859-1");//GBK UTF-8都可以
            }
            response.setHeader("Content-Disposition", "attachment;  filename="+ trueCSVName); 
            long fileLength = fileLoad.length();
            String length1 = String.valueOf(fileLength);
            response.setHeader("Content_Length", length1);
            java.io.FileInputStream in = new java.io.FileInputStream(fileLoad);

            out.write(new byte []{( byte ) 0xEF ,( byte ) 0xBB ,( byte ) 0xBF });//在写文件之前加上bom头，避免中文乱码
            int n;
            while ((n = in.read(b)) != -1) {
                out.write(b, 0, n); // 每次写入out1024字节
            }
            in.close();
            out.close();
            */
            //下载完成后删除临时文件
            //csvFile.delete();
            System.out.println(csvFile.getName());
//			System.out.println(csvFile.getCanonicalPath());
            return csvFile.getName();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     * 将数据导出为CSV文件
     * @param exportData 要导出的数据
     * @param map 头部
     * @param path 路径
     * @param fileName 文件名
     * @return
     */
    public static boolean createCSVFile(List exportData, LinkedHashMap<String, String> map, String path, String fileName) {
        File csvFile = null;
        BufferedWriter writer = null;
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            //定义文件名格式并创建
            csvFile = File.createTempFile(fileName, ".csv", file);
            System.out.println("csvFile:" + csvFile);
            //UTF-8使正确读取分隔符","
            //如果产生的文件乱码，windows下用GBK,linux下用UTF-8
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "UTF-8"), 1024);
            System.out.println("writer:" + writer);
            //写入前端字节流，防止乱码
            //			writer.write(getBOM());
            //写入文件头部
            for (Iterator it = map.entrySet().iterator(); it.hasNext(); ) {
                Entry header = (Entry) it.next();
                writer.write((String) header.getValue() != null ? (String) header.getValue() : "");
                if (it.hasNext()) {
                    writer.write(",");
                }
            }
            //writer.write("\r\n");
            writer.newLine();
            //写入文件内容
            //实体数据写入
            for (Iterator it = exportData.iterator(); it.hasNext(); ) {
                Object row = (Object) it.next();
                for (Iterator headerIt = map.entrySet().iterator(); headerIt.hasNext(); ) {
                    Entry header = (Entry) headerIt.next();
                    String str = row != null ? ((String) ((Map) row).get(header.getKey())) : "";
                    if (StringUtils.isEmpty(str)) {
                        str = "";
                    } else {
                        str = str.replaceAll("\"", "\"\"");
                        if (str.indexOf(",") >= 0) {
                            str = "\"" + str + "\"";
                        }
                    }
                    writer.write(str);
                    if (headerIt.hasNext()) {
                        writer.write(",");
                    }
                }
                if (it.hasNext()) {
                    writer.newLine();
                }
            }
            writer.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 导出并在浏览器端下载csv文件
     *
     * @param response
     * @param csvFilePath
     * @param fileName
     * @throws IOException
     */
    public static void exportCSVFile(HttpServletResponse response, String csvFilePath, String fileName) throws IOException {
        //		response.setHeader("Content-Type", "application-download");
        FileInputStream in = null;
        OutputStream out = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        response.setContentType("text/csv;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename="
                + URLEncoder.encode(fileName, "UTF-8"));
        response.setCharacterEncoding("UTF-8");
        try {
            in = new FileInputStream(csvFilePath);
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("获取文件错误");
        } finally {
            if (in != null) {
                try {
                    in.close();
                    out.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
