package com.ismyself.common.core.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * package com.ismyself.testDmo.common.rsa;
 *
 * @auther txw
 * @create 2021-04-18  19:04
 * @description：
 */
@Slf4j
public class RSAUtils {


    // MAX_DECRYPT_BLOCK应等于密钥长度/8（1byte=8bit），所以当密钥位数为2048时，最大解密长度应为256.
    // 128 对应 1024，256对应2048
    private static final int KEYSIZE = 2048;

    // RSA最大加密明文大小
    private static final int MAX_ENCRYPT_BLOCK = 117;

    // RSA最大解密密文大小
    private static final int MAX_DECRYPT_BLOCK = 256;

    // 不仅可以使用DSA算法，同样也可以使用RSA算法做数字签名
    private static final String KEY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    public static final String DEFAULT_SEED = "$%^*%^()(ED47d784sde78"; // 默认种子

    public static final String PUBLIC_KEY = "PublicKey";
    public static final String PRIVATE_KEY = "PrivateKey";

    /**
     * 生成密钥
     *
     * @param seed 种子
     * @return 密钥对象
     * @throws Exception
     */

    public static Map<String, Key> initKey(String seed) throws Exception {
        log.info("生成密钥");
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        SecureRandom secureRandom = new SecureRandom();
        // 如果指定seed，那么secureRandom结果是一样的，所以生成的公私钥也永远不会变
//		secureRandom.setSeed(seed.getBytes());
        // Modulus size must range from 512 to 1024 and be a multiple of 64
        keygen.initialize(KEYSIZE, secureRandom);
        KeyPair keys = keygen.genKeyPair();
        PrivateKey privateKey = keys.getPrivate();
        PublicKey publicKey = keys.getPublic();
        Map<String, Key> map = new HashMap<>(2);
        map.put(PUBLIC_KEY, publicKey);
        map.put(PRIVATE_KEY, privateKey);
        return map;
    }

    /**
     * 生成默认密钥
     *
     * @return 密钥对象
     * @throws Exception
     */

    public static Map<String, Key> initKey() throws Exception {
        return initKey(DEFAULT_SEED);
    }

    /**
     * 取得私钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Key> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return encryptBASE64(key.getEncoded()); // base64加密私钥
    }

    /**
     * 取得公钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Key> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return encryptBASE64(key.getEncoded()); // base64加密公钥
    }

    /**
     * 用私钥对信息进行数字签名
     *
     * @param data       加密数据
     * @param privateKey 私钥-base64加密的
     * @return
     * @throws Exception
     */
    public static String signByPrivateKey(byte[] data, String privateKey) throws Exception {
        log.info("用私钥对信息进行数字签名");
        byte[] keyBytes = decryptBASE64(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey priKey = factory.generatePrivate(keySpec);// 生成私钥
        // 用私钥对信息进行数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);
        return encryptBASE64(signature.sign());

    }

    /**
     * BASE64Encoder 加密
     *
     * @param data 要加密的数据
     * @return 加密后的字符串
     */
    private static String encryptBASE64(byte[] data) {
//		BASE64Encoder encoder = new BASE64Encoder();
//		String encode = encoder.encode(data);
//		return encode;
        return new String(Base64.encode(data));
    }

    private static byte[] decryptBASE64(String data) {
        // BASE64Decoder 每76个字符换行
//		BASE64Decoder decoder = new BASE64Decoder();
//		byte[] buffer = decoder.decodeBuffer(data);
//		return buffer;
        // codec 的 Base64 不换行
        return Base64.encode(data).getBytes(StandardCharsets.UTF_8);
    }

    public static boolean verifyByPublicKey(byte[] data, String publicKey, String sign) throws Exception {
        byte[] keyBytes = decryptBASE64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);
        return signature.verify(decryptBASE64(sign)); // 验证签名
    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws Exception                    加密过程中的异常信息
     */
    public static String encryptByPublicKey(String str, String publicKey)
            throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        // base64编码的公钥
        byte[] keyBytes = decryptBASE64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(KEY_ALGORITHM)
                .generatePublic(new X509EncodedKeySpec(keyBytes));
        // RSA加密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);

        byte[] data = str.getBytes("UTF-8");
        // 加密时超过117字节就报错。为此采用分段加密的办法来加密
        byte[] enBytes = null;
        for (int i = 0; i < data.length; i += MAX_ENCRYPT_BLOCK) {
            // 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
            byte[] doFinal = cipher.doFinal(ArrayUtil.sub(data, i, i + MAX_ENCRYPT_BLOCK));
            enBytes = ArrayUtil.addAll(enBytes, doFinal);
        }
        String outStr = encryptBASE64(enBytes);
        return outStr;
    }

    /**
     * RSA私钥加密
     *
     * @param str        加密字符串
     * @param privateKey 公钥
     * @return 密文
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws Exception                    加密过程中的异常信息
     */
    public static String encryptByPrivateKey(String str, String privateKey)
            throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        // base64编码的公钥
        byte[] keyBytes = decryptBASE64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(KEY_ALGORITHM)
                .generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
        // RSA加密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, priKey);

        byte[] data = str.getBytes("UTF-8");
        // 加密时超过117字节就报错。为此采用分段加密的办法来加密
        byte[] enBytes = null;
        for (int i = 0; i < data.length; i += MAX_ENCRYPT_BLOCK) {
            // 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
            byte[] doFinal = cipher.doFinal(ArrayUtil.sub(data, i, i + MAX_ENCRYPT_BLOCK));
            enBytes = ArrayUtil.addAll(enBytes, doFinal);
        }
        String outStr = encryptBASE64(enBytes);
        return outStr;
    }

    /**
     * 读取公钥
     *
     * @param publicKeyPath
     * @return
     */
    public static PublicKey readPublic(String publicKeyPath) {
        if (publicKeyPath != null) {
            try (FileInputStream bais = new FileInputStream(publicKeyPath)) {
                CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
                X509Certificate cert = (X509Certificate) certificatefactory.generateCertificate(bais);
                return cert.getPublicKey();
            } catch (CertificateException e) {
                log.error(e.getMessage(), e);
            } catch (FileNotFoundException e) {
                log.error(e.getMessage(), e);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * 读取私钥
     *
     * @param
     * @return
     */
    public static PrivateKey readPrivate(String privateKeyPath, String privateKeyPwd) {
        if (privateKeyPath == null || privateKeyPwd == null) {
            return null;
        }
        try (InputStream stream = new FileInputStream(new File(privateKeyPath));) {
            // 获取JKS 服务器私有证书的私钥，取得标准的JKS的 KeyStore实例
            KeyStore store = KeyStore.getInstance("JKS");// JKS，二进制格式，同时包含证书和私钥，一般有密码保护；PKCS12，二进制格式，同时包含证书和私钥，一般有密码保护。
            // jks文件密码，根据实际情况修改
            store.load(stream, privateKeyPwd.toCharArray());
            // 获取jks证书别名
            Enumeration<String> en = store.aliases();
            String pName = null;
            while (en.hasMoreElements()) {
                String n = (String) en.nextElement();
                if (store.isKeyEntry(n)) {
                    pName = n;
                }
            }
            // 获取证书的私钥
            PrivateKey key = (PrivateKey) store.getKey(pName, privateKeyPwd.toCharArray());
            return key;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * RSA私钥解密
     *
     * @param encryStr   加密字符串
     * @param privateKey 私钥
     * @return 铭文
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     * @throws Exception                 解密过程中的异常信息
     */
    public static String decryptByPrivateKey(String encryStr, String privateKey)
            throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException, IOException {
        // base64编码的私钥
        byte[] decoded = decryptBASE64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(KEY_ALGORITHM)
                .generatePrivate(new PKCS8EncodedKeySpec(decoded));
        // RSA解密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, priKey);

        // 64位解码加密后的字符串
        byte[] data = decryptBASE64(encryStr);
        // 解密时超过128字节报错。为此采用分段解密的办法来解密
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i += MAX_DECRYPT_BLOCK) {
            byte[] doFinal = cipher.doFinal(ArrayUtil.sub(data, i, i + MAX_DECRYPT_BLOCK));
            sb.append(new String(doFinal));
        }

        return sb.toString();
    }

    /**
     * RSA公钥解密
     *
     * @param encryStr  加密字符串
     * @param publicKey 公钥
     * @return 铭文
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     * @throws Exception                 解密过程中的异常信息
     */
    public static String decryptByPublicKey(String encryStr, String publicKey)
            throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException {
        // base64编码的私钥
        byte[] decoded = decryptBASE64(publicKey);
        RSAPublicKey priKey = (RSAPublicKey) KeyFactory.getInstance(KEY_ALGORITHM)
                .generatePublic(new X509EncodedKeySpec(decoded));
        // RSA解密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, priKey);

        // 64位解码加密后的字符串
        byte[] data = decryptBASE64(encryStr);
        // 解密时超过128字节报错。为此采用分段解密的办法来解密
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i += MAX_DECRYPT_BLOCK) {
            byte[] doFinal = cipher.doFinal(ArrayUtil.sub(data, i, i + MAX_DECRYPT_BLOCK));
            sb.append(new String(doFinal));
        }

        return sb.toString();
    }

    /**
     * main方法测试 第一种用法：公钥加密，私钥解密。---用于加解密 第二种用法：私钥签名，公钥验签。---用于签名
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String ss = "hello";
        byte[] data = ss.getBytes();
        Map<String, Key> keyMap = initKey();// 构建密钥
        PublicKey publicKey = (PublicKey) keyMap.get(PUBLIC_KEY);
        PrivateKey privateKey = (PrivateKey) keyMap.get(PRIVATE_KEY);
        log.info("私钥format：{}", privateKey.getFormat());
        log.info("公钥format：{}", publicKey.getFormat());
        log.info("私钥string：{}", getPrivateKey(keyMap));
        log.info("公钥string：{}", getPublicKey(keyMap));
        // 产生签名
        String sign = signByPrivateKey(data, getPrivateKey(keyMap));
        log.info("签名sign={}", sign);
        // 验证签名
        boolean verify1 = verifyByPublicKey(ss.getBytes(), getPublicKey(keyMap), sign);
        log.info("经验证数据和签名匹配：{} ", verify1);
        boolean verify = verifyByPublicKey(data, getPublicKey(keyMap), sign);
        log.error("经验证数据和签名匹配：{} ", verify);
        // log.info("数字签名为"+sign);

        String s = "sdfadfasdfc";
        String encryStr = encryptByPublicKey(s, getPublicKey(keyMap));
        log.info("字符串 {} 的公钥加密结果为：{}", s, encryStr);
        String decryStr = decryptByPrivateKey(encryStr, getPrivateKey(keyMap));
        log.info("私钥解密结果为：{}", decryStr);
        //256
        String priKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCm6BqbMsB+YlzV9EEISBiLMy8FI1hUX54G25zVtCy7s6Ndf3XWFMZ/mmxN9R4+E0bznyWHjGjeV2TAF1TCQjyDQGv3NMnR5sKx0j4qxGvvIvnLG0eenRkfBm0MYc4m4f9hbT3T0ntMGM950lPnaE1O9JLQu4vNV7O+pGUocFmr9s7MwXqS8EG0Elx6hAPJt9NeZTvBf9eIUIlyiZmYIxNHITHpgsj2b/n5+W43bGjqli/YHu3UD7How1OMiR4XESQrKNbA4qnDtgiUrvw4WAeghOkEgAzUwnj8i5WWEvqbcGWuBzIfHOCyJ0KrKWJv7GUhKTIifS0/t35zW5iwYReRAgMBAAECggEBAJtY0Nq+xBdd6dZhvt28bMuy2gLIQQBW9rifUzw8tJ4HtvfhGIYnVBGUY1Wz3XAddR5vc/jToQ/A+88maAdeqv/myBaQlX++blwfuSkJJQSw4xo9y80/nxAXZQvvRHdARfgiOzeaYpQTS5ixQNLTa5EfZj7NMHPxOyUn0ddvqqa4TgYE6O2buoVbs/VJKi9Z82bhE1tFFEMc17xpnMMM3UT5CSC48TQ1e2QqOkAGvHXsZE/D2q0DO87DPlX9nM2rQ+kKOXEzCmZUXexOFrKQW7FXp531hO3DNGWPdtK/sH1gF0VKTZDFTqxFdH0wj2DfW6j+5deU/L2yiRD75vfH0KECgYEA76UPvUuWreg1PsNSpILm4bbZjvWM2rCfi4Z/baK0Hx0AIfOGZ378iJ5flJW7Xxf4gYWxeYtOIeVqKpTLazDOKLjtwgZWuJBPvcjP6F1Qh2y7OeKKLSDB/EV9eQNgcvI1OQZnF5Nz1SdT/Bvi0xsrVbqQtnPvtFeVo7eS/61yD6UCgYEAskwz+xAMoa7ySbPyu2mMf5jfEvA5mPtPRuzhwEO5Ite8MdJ4xH2oz84vaoGQjXm0wz8yZOljFnr/DqgaBJtfY2jWcDKMswXTrZRHnfrYb6axMhOG4UaY/fEH9z7KqBtQ7aEDUFhDDivZjzAT/EAV8Y9Cx54n/arWAW463rbXZH0CgYBoBS7KzOevvw51mtThgcx0BylyK1Wji20FZDElxq0GoFDxa8JYZD97kbsNRN0bDs2Ycw6xIm2cuvcVWpbdhUbHWFYjlRrJbCKgz2yngPG2htTR8Gpn4kMxnHw1LekMm+PMR+Ix0phB2ptS4EvZtH5f7OZpu/M9RZM3iDRvdLsA3QKBgQCZelssfM5Cx3Os2gfSJ7NXiuJLTRpng7zkUripZiKRyZ8oTALFMV4FM3lBdzJVdjSR9W6NyxEOouW3WiC7Ynuj+PTOM9VFrXhfbMqRRQLWaWkSsBhlBv5fagHvZM/SEWGCjB1sJe3i3wAx77Lkas3419cmOhA2nb4GETr/x+h1iQKBgCb88IePthrb6gkLZfTzt0XwRAjVwYqp/ynxA2pPBV7qPfOOUUgut+1rX0akG6LSzbnaVAy1M56hCEDg8gGo9Sydm8BHUT068ucPpNaV4A88RBgWM+e+AaB6XPWrHrAOxRSrjRa+K3DoxmRJq0TfOEPMHi2Fbu/XeeWgP082Lpsc";
        String pubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApugamzLAfmJc1fRBCEgYizMvBSNYVF+eBtuc1bQsu7OjXX911hTGf5psTfUePhNG858lh4xo3ldkwBdUwkI8g0Br9zTJ0ebCsdI+KsRr7yL5yxtHnp0ZHwZtDGHOJuH/YW0909J7TBjPedJT52hNTvSS0LuLzVezvqRlKHBZq/bOzMF6kvBBtBJceoQDybfTXmU7wX/XiFCJcomZmCMTRyEx6YLI9m/5+fluN2xo6pYv2B7t1A+x6MNTjIkeFxEkKyjWwOKpw7YIlK78OFgHoITpBIAM1MJ4/IuVlhL6m3BlrgcyHxzgsidCqylib+xlISkyIn0tP7d+c1uYsGEXkQIDAQAB";


        String encryStr1 = encryptByPublicKey(s, pubKey);
        log.info("字符串 {} 的公钥加密结果为：{}", s, encryStr1);
        String decryStr1 = decryptByPrivateKey(encryStr1, priKey);
        log.info("私钥解密结果为：{}", decryStr1);

    }

}

