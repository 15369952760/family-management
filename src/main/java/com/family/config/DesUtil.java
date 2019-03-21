package com.family.config;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
 /**
  * 
  * 项目名称：GMBTools
  * 类名称：DesUtil
  * 类描述：  TODO(des加密)
  * 开发单位: 北京天耀宏图科技有限公司
  * 创建人：HanMY
  * 创建时间：2014-12-16 上午9:50:44
  * 修改人：
  * 修改时间：
  * 修改备注：
  * @version v1.0
  */
public class DesUtil {
	
    private final static String DES = "DES";
    
    private final static String KEY = "greatmap_**";
    
     
    /**
     * Description 根据键值进行加密
     * @param data 
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encrypt(String data,String key) {
        byte[] bt=null;
		try {
			bt = encrypt(data.getBytes(), key.getBytes());
		} catch (Exception e) {
		}
		Encoder encoder = Base64.getEncoder();
        String strs = encoder.encodeToString(bt);
        return strs;
    }
    
    /**
     * Description 根据键值进行加密
     * @param data 
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) {
        return encrypt(data,KEY);
    }
    
    /**
     * 
     * @Title: getFileMD5
     * @Description: TODO(获取文件MD5)
     * @param @param file
     * @param @return    设定文件
     * @return String    返回类型
     * @throws
     */
    public static String getFileMD5(File file) {
        if (!file.exists() || !file.isFile()) {  
            return null;  
        }  
        MessageDigest digest = null;  
        FileInputStream in = null;  
        byte buffer[] = new byte[1024];  
        int len;  
        try {  
            digest = MessageDigest.getInstance("MD5");  
            in = new FileInputStream(file);  
            while ((len = in.read(buffer, 0, 1024)) != -1) {  
                digest.update(buffer, 0, len);  
            }  
            in.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
        BigInteger bigInt = new BigInteger(1, digest.digest());  
        return bigInt.toString(16);  
    }  
    
    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data,String key) throws IOException,
            Exception {
        if (data == null)
            return null;
        Decoder decoder = Base64.getDecoder();
        byte[] buf = decoder.decode(data);
        byte[] bt = decrypt(buf,KEY.getBytes());
        return new String(bt);
    }
    
    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data) throws IOException,
            Exception {
        return decrypt(data,KEY);
    }
 
    /**
     * Description 根据键值进行加密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
    	long startTime = System.currentTimeMillis();
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }
    
    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }
    
    public static void main(String[] args) throws IOException, Exception {
		String id = "mylog1.log";
		String eid = DesUtil.encrypt(id);
		String did = DesUtil.decrypt(eid);
		System.out.println(eid+":"+did);
    	
	}
}
