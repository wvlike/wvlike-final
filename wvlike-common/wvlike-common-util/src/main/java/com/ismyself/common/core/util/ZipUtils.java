package com.ismyself.common.core.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Description 文件压缩工具包
 * @Author young_chen
 * @Date 2022/10/25 9:26
 */

public class ZipUtils {
    private static final Logger logger = LoggerFactory.getLogger(ZipUtils.class);

    private ZipUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void setZipForStream(Map<String, String> fileMap, ZipOutputStream zipOutputStream) {
        BufferedInputStream urlInputStream = null;
        try {
            for (Map.Entry<String, String> entry : fileMap.entrySet()) {
                String path = entry.getValue();
                String type = "";
                if (path.lastIndexOf(".") != -1) {
                    int i = path.lastIndexOf(".");
                    type = path.substring(i);
                }

                //创建压缩文件内的文件
                zipOutputStream.putNextEntry(new ZipEntry(entry.getKey() + type));
                //根据文件访问地址获取输入流
                URL url = new URL(path);
                urlInputStream = new BufferedInputStream(url.openStream());
                readInputStream(urlInputStream, zipOutputStream);
                zipOutputStream.closeEntry();
            }
        } catch (IOException e) {
            throw new RuntimeException("文件获取失败" + e.getMessage());
        }
    }

    public static void setImgForStream(String fileUrl, OutputStream outputStream) {
        BufferedInputStream urlInputStream = null;
        try {
            //根据文件访问地址获取输入流
            URL url = new URL(fileUrl);
            urlInputStream = new BufferedInputStream(url.openStream());
            readInputStream(urlInputStream, outputStream);
        } catch (IOException e) {
            throw new RuntimeException("文件获取失败" + e.getMessage());
        }
    }


    private static void readInputStream(InputStream is, OutputStream outputStream) {
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = is.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException" + e.getMessage());
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                logger.error("IOException:{}", e.getMessage());
            }
        }
    }


}
