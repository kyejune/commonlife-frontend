/**
 * COPYRIGHT ⓒ Kolon Benit CO. LTD. All rights reserved.
 * 코오롱베니트(주)의 사전 승인 없이 본 내용의 전부 또는 일부에 대한 복사, 배포,사용을 금합니다.
 */
package com.kolonbenit.benitware.common.util.file;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;


/**
 * @description 파일처리를 위한 Util 클래스
 * @author 장승호 
 * @version 1.0
 * @since 2014.01.19 
 * @date 2014.01.19 
 */
public class FileUtil {

    // 파일사이즈 1K
    static final long BUFFER_SIZE = 1024L;
    // 파일구분자
    static final char FILE_SEPARATOR = File.separatorChar;
    // 윈도우시스템 파일 접근권한
    static final char ACCESS_READ = 'R'; // 읽기전용
    static final char ACCESS_SYS = 'S'; // 시스템
    static final char ACCESS_HIDE = 'H'; // 숨김
    // 최대 문자길이
    static final int MAX_STR_LEN = 1024;

    // Log
    protected static Log log = LogFactory.getLog(FileUtil.class);

    /**
     * <pre>
     * Comment : 파일 타입 변환 
     * </pre>
     *
     * @param String fullPath    디렉토리의 절대경로
     * @return String typeArg  파일 타입 확장자 ex)png,jpg
     */
    public static byte[]  fileToArray(String fullPath,String typeArg) throws Exception {
    		
    	byte[] byteBuf;
		BufferedImage originalImage = ImageIO.read(new File(fullPath));

		// convert BufferedImage to byte array
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(originalImage, typeArg, baos);
		baos.flush();
		byteBuf = baos.toByteArray();
		
		baos.close();
		
		return byteBuf;
    }
    
    
    /**
     * <pre>
     * Comment : 파일 타입 변환 
     * </pre>
     *
     * @param String fullPath    디렉토리의 절대경로
     * @return String typeArg  파일 타입 확장자 ex)png,jpg
     */
    public static void  fileMake(byte[] pType,String makeFullPath,String type) throws Exception {
    		
    	InputStream in = new ByteArrayInputStream(pType);
    	BufferedImage bImageFromConvert = ImageIO.read(in);
    	 
    	ImageIO.write(bImageFromConvert,type, new File(makeFullPath)); 
		 
    }
    
    
    
    
    
    
 
}
