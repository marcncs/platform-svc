package com.winsafe.sap.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*******************************************************************************************  
 * 用于生成文件或字符串的MD5值
 * @author: ryan.xi	  
 * @date：2014-11-05  
 * @version 1.0  
 *  
 *  
 * Version    Date       ModifiedBy                 Content  
 * -------- ---------    ----------         ------------------------  
 * 1.0      2014-11-05   ryan.xi                
 *******************************************************************************************  
 */ 
public class MD5BigFileUtil {
    private final static Logger logger = Logger.getLogger(MD5BigFileUtil.class);
    static MessageDigest md = null;

    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ne) {
            logger.error("NoSuchAlgorithmException: md5", ne);
        }
    }

    /**
     * 对一个文件求他的md5值
     * @param f 要求md5值的文件
     * @return md5串
     */
    public static String md5(InputStream in) {
        try {
            //100KB each time
            byte[] buffer = new byte[102400];
            int length;
            long loopCount = 0;
            while ((length = in.read(buffer)) != -1) {
                md.update(buffer, 0, length);
                loopCount++;
            }
            
            logger.debug("read file to buffer loopCount:"+loopCount);

            return new String(Hex.encodeHex(md.digest()));
        } catch (FileNotFoundException e) {
            logger.error("md5 file failed:" + e.getMessage());
            return null;
        } catch (IOException e) {
            logger.error("md5 file failed:" + e.getMessage());
            return null;
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 对一个文件求他的md5值
     * @param f 要求md5值的文件
     * @return md5串
     */
    public static String md5(File f) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            //100KB each time
            byte[] buffer = new byte[102400];
            int length;
            long loopCount = 0;
            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
                loopCount++;
            }
            
            logger.debug("read file to buffer loopCount:"+loopCount);

            return new String(Hex.encodeHex(md.digest()));
        } catch (FileNotFoundException e) {
            logger.error("md5 file " + f.getAbsolutePath() + " failed:" + e.getMessage());
            return null;
        } catch (IOException e) {
            logger.error("md5 file " + f.getAbsolutePath() + " failed:" + e.getMessage());
            return null;
        } finally {
            try {
                if (fis != null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 求一个字符串的md5值
     * @param target 字符串
     * @return md5 value
     */
    public static String md5(String target) {
        return DigestUtils.md5Hex(target);
    }
    
    public static void main(String[] args) {
        long begin =System.currentTimeMillis();
        System.out.println(md5(new File("D:/sinochem_20120301.bak")));
        long end =System.currentTimeMillis();
        System.out.println("time:"+((end-begin)/1000)+"s");
        
        System.out.println(md5("hello world"));
    }

}