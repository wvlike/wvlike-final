package com.wvlike.msg.readFile;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ConverterUtils;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Date: 2024/10/29
 * @Author: tuxinwen
 * @Description:
 */
public class DemoMainExecute {

    public static void main(String[] args) {
//        excel();

        data();
    }

    public static void md5() {
        System.out.println(CipherMd5.encrypt("123456"));

    }

    public static void data() {
        FileReader fileReader = new FileReader("D:\\myspace\\工作相关\\其他配合\\数据批量解密以及Excel转换\\data.txt");
        for (String readLine : fileReader.readLines()) {
//            System.out.println("\"" + readLine + "\",");
            System.out.println("'" + readLine + "',");
        }
    }

    public static void excel() {
        String fileName = "D:\\myspace\\工作相关\\其他配合\\周洁仪\\原始数据.xlsx";
        // 这里默认读取第一个sheet
        DataListener dataListener = new DataListener();
        EasyExcel.read(fileName, EasyDemoDTO.class, dataListener).sheet().doRead();
        List<EasyDemoDTO> excelDataList = dataListener.getExcelDataList();
        SM4Crypto sm4Crypto = new SM4Crypto();
        for (EasyDemoDTO dto : excelDataList) {
            if (StrUtil.isNotBlank(dto.getSecond())) {
                String encrypt = sm4Crypto.decrypt(dto.getSecond());
                dto.setSecond(CipherMd5.encrypt(encrypt));
            } else {
                dto.setSecond("");
            }
            if (StrUtil.isNotBlank(dto.getThird())) {
                dto.setThird(CipherMd5.encrypt(dto.getThird()));
            } else {
                dto.setThird("");
            }
        }

        String path = "D:\\myspace\\工作相关\\其他配合\\周洁仪\\信也-进件渠道信息导出111.xlsx";

        EasyExcel.write(path, EasyDemoDTO.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .sheet()
                .doWrite(excelDataList);
    }

    @Data
    public static class EasyDemoDTO {

        @ExcelProperty("FID")
        private String first;

        @ExcelProperty("身份证")
        private String second;

        @ExcelProperty("实名手机号")
        private String third;

    }

    @Slf4j
    @Getter
    public static class DataListener implements ReadListener<EasyDemoDTO> {

        public DataListener() {
            this.excelDataList = Lists.newArrayList();
        }

        /**
         * excel每列数据集合
         */
        private final List<EasyDemoDTO> excelDataList;

        @Override
        public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
            Map<Integer, String> headNameMap = ConverterUtils.convertToStringMap(headMap, context);
            Collection<String> headNames = headNameMap.values();


        }

        @Override
        public void invoke(EasyDemoDTO data, AnalysisContext context) {
            this.excelDataList.add(data);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            log.info("excel读取-所有数据解析完成,总计：{}", CollUtil.isEmpty(excelDataList) ? 0 : excelDataList.size());
        }
    }

    public static class SM4Crypto {
        private static final String KEY_ALGORITHM = "SM4";
        private static final String DEFAULT_CIPHER_ALGORITHM = "SM4/ECB/PKCS5Padding";
        private static final String CHARSET_NAME = "UTF-8";

        private static final BouncyCastleProvider BOUNCY_CASTLE_PROVIDER = new BouncyCastleProvider();

        static {
            Security.addProvider(BOUNCY_CASTLE_PROVIDER);
        }

        /**
         * @Value("${finance.db.crypto.sm4.key:8oda9j/M+diuZtctnCceIw==}") dev：8oda9j/M+diuZtctnCceIw==
         * prd：ZEiym8qqVCD25t95Ao+xVw==
         */
        private final String key = "ZEiym8qqVCD25t95Ao+xVw==";

        public String encrypt(String content) {
            if (StringUtils.isBlank(content)) {
                return content;
            }
            return encrypt(content, key);
        }

        public String decrypt(String content) {
            if (StringUtils.isBlank(content)) {
                return content;
            }
            return decrypt(content, key);
        }

        /**
         * SM4 加密操作
         *
         * @param content 待加密内容
         * @param key     加密密钥
         * @return 返回Base64转码后的加密数据
         */
        public static String encrypt(String content, String key) {
            try {
                Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM, BOUNCY_CASTLE_PROVIDER);// NOSONAR
                cipher.init(Cipher.ENCRYPT_MODE, toKey(key));
                byte[] result = cipher.doFinal(content.getBytes(CHARSET_NAME));//NOSONAR
                return Base64.encodeBase64String(result);
            } catch (Exception ex) {
                throw new RuntimeException("当前服务繁忙，请稍后重试  ", ex); //NOSONAR
            }
        }

        /**
         * SM4 解密操作
         *
         * @param content base64编码的待解密数据
         * @param key
         * @return 返回明文
         */
        public static String decrypt(String content, String key) {
            try {
                Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM, BOUNCY_CASTLE_PROVIDER);// NOSONAR
                cipher.init(Cipher.DECRYPT_MODE, toKey(key));
                byte[] result = cipher.doFinal(Base64.decodeBase64(content));
                return new String(result, CHARSET_NAME);//NOSONAR
            } catch (Exception ex) {
                throw new RuntimeException("当前服务繁忙，请稍后重试 ", ex); //NOSONAR
            }
        }

        private static Key toKey(String base64Key) {
            byte[] bytes = Base64.decodeBase64(base64Key);
            return new SecretKeySpec(bytes, KEY_ALGORITHM);
        }

    }

    public static class CipherMd5 {

        private static final Logger logger = LoggerFactory.getLogger(CipherMd5.class);

        private static final String DEFAULT_CHARSET = "utf-8";

        private CipherMd5() {
            throw new IllegalStateException("Utility class");
        }

        /**
         * MD5加密
         *
         * @param text
         * @return md5密文
         */
        public static String encrypt(String text) {
            return encrypt(text, DEFAULT_CHARSET);
        }

        /**
         * MD5加密
         *
         * @param text
         * @param charset
         * @return md5密文
         */
        public static String encrypt(String text, String charset) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(text.getBytes(charset));
                byte[] cipherText = md.digest();
                StringBuilder buf = new StringBuilder("");
                int temp;
                for (int offset = 0; offset < cipherText.length; offset++) {
                    // 转十六进制
                    temp = cipherText[offset];
                    if (temp < 0) {
                        temp += 256;
                    }
                    if (temp < 16) {
                        buf.append("0");
                    }
                    buf.append(Integer.toHexString(temp));
                }
                return buf.toString();
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                logger.error(e.getMessage(), e);
                return null;
            }
        }
    }

}
