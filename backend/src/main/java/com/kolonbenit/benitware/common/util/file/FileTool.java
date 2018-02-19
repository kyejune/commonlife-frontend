package com.kolonbenit.benitware.common.util.file;
import java.io.File;
import java.util.ArrayList;


public class FileTool {
    
    // 파일사이즈 1K
    static final long BUFFER_SIZE     = 1024L;
    // 파일구분자
    static final char FILE_SEPARATOR     = File.separatorChar;
    // 윈도우시스템 파일 접근권한
    static final char ACCESS_READ     = 'R';    // 읽기전용
    static final char ACCESS_SYS     = 'S';    // 시스템
    static final char ACCESS_HIDE     = 'H';    // 숨김
    // 최대 문자길이
    static final int MAX_STR_LEN = 1024;
    
    /**
     * <pre>
     * Comment : 파일을 생성한다. 
     * </pre>
     * @param String fileName 파일의 절대경로 +파일명
     * @param String content 저장할 문자열입니다. c:/test/test1/test44.txt
     *
     */
    public  static String createNewFile(String filePath){
      
        // 인자값 유효하지 않은 경우 블랭크 리턴
        if (filePath==null || filePath.equals("")){
            return "";
        }
        
        File file = new File(filePath);
        String result = "";
        try{
            if(file.exists()){
                   result = filePath;
            }else{
                // 존재하지 않으면 생성함
                new File(file.getParent()).mkdirs();
                if(file.createNewFile()){
                    result = file.getAbsolutePath();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
       
        return result;
    }
    
    
    /**
     * 디렉토리 내부 하위목록들 중에서 파일을 찾는 기능(모든 목록 조회)
     * @param File[] fileArray 파일목록
     * @return ArrayList list 파일목록(절대경로)
     * @exception Exception
    */
    public static ArrayList getSubFilesByAll(File[] fileArray) throws Exception {
        
        ArrayList list = new ArrayList();
        
        for (int i = 0; i < fileArray.length; i++) {
            // 디렉토리 안에 디렉토리면 그 안의 파일목록에서 찾도록 재귀호출한다.
            if (fileArray[i].isDirectory()) {
                File [] tmpArray = fileArray[i].listFiles();
                list.addAll(getSubFilesByAll(tmpArray));
            // 파일이면 담는다.
            } else {
                list.add(fileArray[i].getAbsolutePath());
            }
        }
        
        return list;
    }
    /**
     * <pre>
     * Comment : 디렉토리를 생성한다. 
     * </pre>
     * @param dirPath 생성하고자 하는 절대경로
     * @return 성공하면   새성된 절대경로, 아니면 블랭크
     */

    public  static String createNewDirectory(String dirPath){
       
        // 인자값 유효하지 않은 경우 블랭크 리턴
        if (dirPath==null || dirPath.equals("")){
            return "";
        }
        
        File file = new File(dirPath);
        String result = "";
        try{
            // 없으면 생성
            if(file.exists()){
                // 혹시 존재해도 파일이면 생성 - 생성되지 않는다.(아래는 실질적으로는 진행되지 않음)
                if(file.isFile()){
                    //new File(file.getParent()).mkdirs();
                    if (file.mkdirs()){
                        result = file.getAbsolutePath();    
                    }
                }else{
                    result = file.getAbsolutePath(); 
                }
            }else{
                // 존해하지 않으면 생성 
                if (file.mkdirs()){
                    result = file.getAbsolutePath();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
       
        return result;
    }
  
 
}
