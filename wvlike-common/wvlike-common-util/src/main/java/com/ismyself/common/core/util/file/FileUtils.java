package com.ismyself.common.core.util.file;

import cn.hutool.core.io.IoUtil;
import com.ismyself.common.base.exception.CommonException;
import com.ismyself.common.base.result.ResultCode;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


/**
 * @author
 * @version 1.0
 * @date 2023/8/26 12:47
 */
@Slf4j
public class FileUtils {

    /**
     * 通过文件地址获取文件流
     *
     * @param filePath
     * @return
     */
    public static InputStream getFileStreamByUrl(String filePath) {
        // 服务器返回的状态
        int HttpResult;
        //设置数组大小
        byte[] bytes = new byte[204800];
        try {
            // 创建URL
            URL url = new URL(filePath);
            // 试图连接并取得返回状态码
            URLConnection urlconn = url.openConnection();
            urlconn.connect();
            HttpURLConnection httpconn = (HttpURLConnection) urlconn;
            HttpResult = httpconn.getResponseCode();
            if (HttpResult != HttpURLConnection.HTTP_OK) {
                log.error("获取文件失败，无法连接到文件资源");
            } else {
                // 取数据长度
                int filesize = urlconn.getContentLength();
                log.info("取数据长度:{}", filesize);
                urlconn.getInputStream();
                InputStream inputStream = urlconn.getInputStream();
                //如果这里只需要返回stream，则直接返回 不需要转byte[]
                return inputStream;
            }
            log.info("文件大小，length:{}", bytes.length);
        } catch (Exception e) {
            log.error("获取文件异常，e: ", e);
        }
        throw new CommonException(ResultCode.FAIL);
    }

    /**
     * 通过文件地址获取文件byte
     *
     * @param filePath
     * @return
     */
    public static byte[] getFileByteByUrl(String filePath) {
        // 服务器返回的状态
        int HttpResult;
        //设置数组大小
        byte[] bytes = new byte[204800];
        try {
            // 创建URL
            URL url = new URL(filePath);
            // 试图连接并取得返回状态码
            URLConnection urlconn = url.openConnection();
            urlconn.connect();
            HttpURLConnection httpconn = (HttpURLConnection) urlconn;
            HttpResult = httpconn.getResponseCode();
            if (HttpResult != HttpURLConnection.HTTP_OK) {
                log.error("获取文件失败，无法连接到文件资源");
            } else {
                // 取数据长度
                int filesize = urlconn.getContentLength();
                log.info("取数据长度:{}", filesize);
                urlconn.getInputStream();
                InputStream inputStream = urlconn.getInputStream();
                //如果这里只需要返回stream，则直接返回 不需要转byte[]
                ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
                int ch;
                while ((ch = inputStream.read()) != -1) {
                    swapStream.write(ch);
                }
                bytes = swapStream.toByteArray();
            }
            log.info("文件大小，length:{}", bytes.length);
        } catch (Exception e) {
            log.error("获取文件异常，e: ", e);
        }
        return bytes;
    }


    /**
     * 获取url文件后缀
     *
     * @param fileUrl
     * @return
     */
    public static String getFileSuffixByUrl(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            String path = new File(url.getPath()).getName();
            int lastDotIndex = path.lastIndexOf('.');
            String suffix = path.substring(lastDotIndex + 1);

            return suffix;
        } catch (Exception e) {
            log.error("获取文件后缀异常，e:{}", e);
        }
        return "";
    }

    /**
     * //前缀字符串定义文件名；必须至少三个字符
     */
    static final String PREFIX = "stream2file";
    /**
     * //后缀字符串定义文件的扩展名；如果为null，则将使用后缀".tmp"
     */
    static final String SUFFIX = ".tmp";

    /**
     * 通过输入流转File
     *
     * @param in
     * @return
     * @throws IOException
     */
    public static File stream2file(InputStream in) throws IOException {
        final File tempFile = File.createTempFile(PREFIX, SUFFIX);
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IoUtil.copy(in, out);
        }
        return tempFile;
    }


    /**
     * 获取文件大小 kB
     *
     * @param file
     * @return
     */
    public static double getFileKbSize(File file) {
        long sizeInBytes = file.length();
        double sizeInKB = (double) sizeInBytes / 1024;
        return sizeInKB;
    }


    public static File convertFile(InputStream inputStream) throws IOException {
        // 创建临时文件
        File tempFile = File.createTempFile("prefix", "suffix");

        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;

            // 从输入流读取数据并写入临时文件
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        return tempFile;
    }

    public static byte[] convertByte(File file) {

        byte[] fileBytes = null;

        try (FileInputStream fileInputStream = new FileInputStream(file);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            fileBytes = byteArrayOutputStream.toByteArray();
            return fileBytes;
        } catch (IOException e) {
            log.error("处理异常", e);
        }
        return fileBytes;
        // 使用 fileBytes 进行后续操作
        // ...
    }

    /**
     * 下载文件到本地路径
     *
     * @param fileUrl         文件URL
     * @param destinationPath 要保存的文件路径
     * @throws IOException
     */
    public static void downloadFile(String fileUrl, String destinationPath) {
        try {
            InputStream inputStream = new URL(fileUrl).openStream();
            Path destination = Paths.get(destinationPath);
            Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("下载文件失败: {}", e.getMessage(), e);

            throw new CommonException(ResultCode.FAIL);
        }
    }
}
