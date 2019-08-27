package com.aliang.aliangbok_until.aseutil;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Description:  进行数据加密
 * @Param: ${tags}
 * @author: 阿良
 * @CreateDate: ${date} ${time}</p>
 */
public class AESutil {
    //使用AES-128-CBC加密模式，key需要为16位,key和iv可以相同！
    private static String KEY = "1555553628857ivv";

    private static String IV =  "1555553628857ivv";


    /**
     * 加密方法
     * @param dataIn  要加密的数据
     * @param keyIn 加密key
     * @param ivIn 加密iv
     * @return 加密的结果
     * @throws Exception
     */
    public static String encrypt(String dataIn, String keyIn, String ivIn) throws Exception {
        String data =new String(dataIn.getBytes(),"UTF-8");
        String key =new String(keyIn.getBytes(),"UTF-8");
        String iv =new String(ivIn.getBytes(),"UTF-8");
        try {

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");//"算法/模式/补码方式"NoPadding PkcsPadding
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return new Base64().encodeToString(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密方法
     * @param dataIn 要解密的数据
     * @param keyIn  解密key
     * @param ivIn 解密iv
     * @return 解密的结果
     * @throws Exception
     */
    public static String desEncrypt(String dataIn, String keyIn, String ivIn) throws Exception {
        String data =new String(dataIn.getBytes(),"UTF-8");
        String key =new String(keyIn.getBytes(),"UTF-8");
        String iv =new String(ivIn.getBytes(),"UTF-8");
        try {
            byte[] encrypted1 = new Base64().decode(data);

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "UTF-8");
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用默认的key和iv加密
     * @param dataIn
     * @return
     * @throws Exception
     */
    public static String encrypt(String dataIn) throws Exception {
        String data =new String(dataIn.getBytes(),"UTF-8");
        return encrypt(data, KEY, IV);
    }

    /**
     * 使用默认的key和iv解密
     * @param dataIn
     * @return
     * @throws Exception
     */
    public static String desEncrypt(String dataIn) throws Exception {
        String data =new String(dataIn.getBytes(),"UTF-8");
        return desEncrypt(data, KEY, IV);
    }

    /**
     * 测试
     */
    public static void main(String args[]) throws Exception {

        String test1 = "sa";
        String test =new String(test1.getBytes(),"UTF-8");
        String data = null;
        String key =  KEY;
        String iv = IV;
//        data = encrypt(test, "1555515708066ali", "1555515708066bok");
        data = encrypt(test1);
        System.out.println("数据："+test);
        System.out.println("加密："+data);
//        String jiemi =desEncrypt(data,"1555515708066ali", "1555515708066bok").trim();
        String jiemi =desEncrypt(data);
        System.out.println("解密："+jiemi);
    }

}
