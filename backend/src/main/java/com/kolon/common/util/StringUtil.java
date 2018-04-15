package com.kolon.common.util;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by agcdDev on 2017-07-07.
 */
public class StringUtil {

    // 이미지 경로 추출
    public static String convertHtmlimg(String img) {
        Pattern p = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");
        Matcher m = p.matcher(img);
        String t = "";
        while (m.find()) {
            System.out.println(m.group(1));
            t = m.group(1);
        }
        return t;
    }

    public static String convertHtml(String value){
        String returnValue = value;
        if ((returnValue == null) || (returnValue.trim().equals(""))) {
            return "";
        }
        returnValue = returnValue.replaceAll("&lt;", "<");
        returnValue = returnValue.replaceAll("&gt;", ">");
        returnValue = returnValue.replaceAll("&quot;", "\"");
        returnValue = returnValue.replaceAll("&#39;", "\'");
        returnValue = returnValue.replaceAll("&#59;", ";");

        returnValue = returnValue.replaceAll("&#40;", "(");
        returnValue = returnValue.replaceAll("&#41;", ")");

        returnValue = returnValue.replaceAll("&amp;", "&");
        returnValue = returnValue.replaceAll("&#43;", "+");

        return returnValue;

    }
    /**
     * 예외를 HTML형식으로 출력한다
     * @param ex 예외
     * @return String 변환된 String
     */
    public static String stackTraceToString(Throwable ex) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        PrintStream p = new PrintStream(b);
        ex.printStackTrace(p);
        p.close();
        String stackTrace = b.toString();
        try {
            b.close();
        } catch (IOException e) {
            //logger.error("예외출력 처리에 사용된 스트림 관련 리소스를 해제처리에 예외가 발생했습니다. ");
            e.printStackTrace();
        }

        return convertHtmlBr(stackTrace);
    }

    /**
     * 일반 스트링을 HTML표시 형식으로 변경한다
     * @param comment 변환대상 문자열
     * @return String 변환된 문장열
     */
    public static String convertHtmlBr(String comment) {
        if (comment == null)
            return "";
        int length = comment.length();
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < length; i++) {
            String tmp = comment.substring(i, i + 1);
            if ("\r".compareTo(tmp) == 0) {
                tmp = comment.substring(++i, i + 1);
                if ("\n".compareTo(tmp) == 0) {
                    buffer.append("<br>\r");
                } else {
                    buffer.append("\r");
                }
            }
            buffer.append(tmp);
        }

        return buffer.toString();
    }

    /**
     * 일반 스트링을 FLEX내용표시 형식으로 변경한다(FLEX화면에서 \r,\n이 있을 경우 줄바꿈이 두번적용된다.)
     * @param comment 변환대상 문자열
     * @return String 변환된 문장열
     */
    public static String convertFlexStr(String comment) {
        String result = "";
        if (comment == null)
            return result;
        int length = comment.length();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            String tmp = comment.substring(i, i + 1);
            if ("\r".compareTo(tmp) == 0) {
                tmp = comment.substring(++i, i + 1);
                if ("\n".compareTo(tmp) == 0)
                    buffer.append("");
                else
                    buffer.append("");
            }
            buffer.append(tmp);
        }
        result = buffer.toString();
        return result;
    }


    /**
     * 널인경우 공백으로 바꾸어준다
     * @param  str 변경문자열
     * @return String 변경된 문자열
     */
    public static String nvl(String str) {
        return nvl(str, "");
    }

    /**
     *  널일경우 일정한 형식으로 바꾼다
     * @param str 변경전 문자열
     * @param NVLString 변경할 문자열
     * @return String 변경된 문자열
     */
    public static String nvl(String str, String NVLString) {
        if( (str == null) || (str.trim().equals(""))
                || (str.trim().equals("null")) ) {
            return NVLString;
        } else {
            return str;
        }
    }

    /**
     * 널인경우 공백으로 바꾸어준다
     * @param  str 변경문자열
     * @return int 변경된 숫자
     */
    public static int parseInt(String str) {
        return parseInt(str, 0);
    }

    /**
     *  널일경우 일정한 형식으로 바꾼다
     * @param str 변경전 문자열
     * @param basic 변경할 문자열
     * @return int 변경된 숫자
     */
    public static int parseInt(String str, int basic) {
        if( (str == null) || (str.trim().equals(""))
                || (str.trim().equals("null")) ) {
            return basic;
        } else {
            return Integer.parseInt(str);
        }
    }


    /**
     *  널일경우 일정한 형식으로 바꾼다, 텍스트 등의 숫자가 아닌 값이 들어오면 basic으로 반환함
     * @param str 변경전 문자열
     * @param basic 변경할 문자열
     * @return int 변경된 숫자
     */
    public static int parseIntSafe(String str, int basic) {
        if( (str == null) || (str.trim().equals(""))
                || (str.trim().equals("null")) ) {
            return basic;
        } else {
            try {
                return Integer.parseInt(str);
            } catch( NumberFormatException e ) {
                return basic;
            }
        }
    }


    /**
     * 대상 문자열의 strOld문자열을 strNew로 변환해 준다.(게시판등의 내용에 사용)
     * @param target 대상문자열
     * @param strOld 변환될문자
     * @param strNew 변환할문자
     * @return sb 변환된 문자열
     */
    public static String convertString (String target,String strOld,String strNew) {
        StringBuffer sb = new StringBuffer();

        if ((target == null) || target.equals("")) {
            return "";
        }
        int idx = 0;

        while((idx = target.indexOf(strOld)) > -1){
            sb.append(target.substring(0,idx));
            sb.append(strNew);
            target = target.substring(idx+1);
        }
        sb.append(target);
        return sb.toString();
    }

    /**
     * 텍스트 데이터의 내용을 화면에 보여주기 위해서 적절히 변환한다
     * @param target 대상문자열
     * @return result 변환된 결과 문자열
     */
    public static String convertText(String target){
        String result = "";
        //공백을 &nbsp;로 변환
        result = convertString(target," ","&nbsp;");
        //개행문자를 <BR>로변환
        result = convertString(result,"\n","<BR>");

        return result;
    }

    /**
     * 텍스트 데이터의 내용을 화면에 보여주기 위해서 적절히 변환한다
     * @param target 대상문자열
     * @return result 변환된 결과 문자열
     */
    public static String convertText3(String target){
        String result = "";
        //공백을 &nbsp;로 변환
        result = convertString(target," ","&nbsp;");
        //개행문자를 <BR>로변환
        result = convertString(result,"\n","");

        return result;
    }

    /**
     * 텍스트 데이터의 내용을 화면에 보여주기 위해서 적절히 변환한다 (이미지 있을경우)
     * @param target 대상문자열
     * @return result 변환된 결과 문자열
     */
    public static String convertText2(String target){
        String result = "";
        //공백을 &nbsp;로 변환
        result = convertString(target," ","&nbsp;");
        //개행문자를 <BR>로변환
        result = convertString(result,"\n","<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");

        return result;
    }

    /**
     * 텍스트 데이터의 내용을 화면에 보여주기 위해서 적절히 변환한다
     * @param target 대상문자열
     * @return result 변환된 결과 문자열
     */
    public static String convertText4(String target){
        String result = "";
        //공백을 &nbsp;로 변환
        result = convertString(target," ","&nbsp;");
        //개행문자를 <BR>로변환
        result = convertString(result,"\n","<BR>&nbsp;&nbsp;&nbsp;&nbsp;");

        return result;
    }

    /**
     * 텍스트 데이터의 내용을 textarea에 답변내용으로 보여주기 위해서 적절히 변환한다
     * @param target 대상문자열
     * @return result 변환된 결과 문자열
     */
    public static String convertAnswerText(String target){
        String result = "";
        //매 줄마다 >를 붙인다.
        result = convertString(target,"\n","\n > ");
        return result;
    }

    /**
     * 덧글의 내용을 스크립트로 넘겨주기 위해 특수문자 변환한다
     * @param target 대상문자열
     * @return result 변환된 결과 문자열
     */
    public static String convertLineText(String target){
        String result = "";
        //공백을 &nbsp;로 변환
        result = convertString(target,"\"","&quot;");
        //개행문자를 <BR>로변환
        result = convertString(result,"'","\\'");

        return result;
    }

    /**
     * 글을 HTML태그에 디폴트 표시할때 " 일경우 뒤의 문자가 짤리므로특수분자를 변경한다
     * @param target 대상문자열
     * @return result 변환된 결과 문자열
     */
    public static String convertTagDoubleQuot(String target){
        String result = "";
        //공백을 &nbsp;로 변환
        result = convertString(target,"\"","&quot;");

        return result;
    }

    /**
     * 일반 스트링을 javascript의 인수에 적합하도록 변경한다(개행문자를 모두 없앤다)
     * @param comment 변환대상 문자열
     * @return String 변환된 문장열
     */
    public static String convertJSArgs(String comment) {
        if (comment == null)
            return "";
        int length = comment.length();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            String tmp = comment.substring(i, i + 1);
            if ("\r".compareTo(tmp) == 0) {
                buffer.append("\\r");
            }else if ("\n".compareTo(tmp) == 0){
                buffer.append("\\n");
            }else if ("'".compareTo(tmp) == 0){
                buffer.append("\\'");
            }else if ("\"".compareTo(tmp) == 0){
                buffer.append("\\\"");
            }else{
                buffer.append(tmp);
            }
        }
        return buffer.toString();
    }

    /**
     * 제목등을 일정길이만 보여주고 뒤는 (...으로 변환한다)
     * @param str 대상문자열
     * @param size 보여줄 최대길이
     * @param end 잘라내고 뒤게 붙일 문자열
     * @return sb 변환된 문자열
     */
    public static String toTruncate(String str, int size, String end){
        StringBuffer sb = new StringBuffer();
        if ((str == null) || str.equals("")){
            return "";
        }
        int tempSize = str.length();
        if(tempSize > size){
            sb.append(str.substring(0, size));
            sb.append(end);
        } else {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * 제목등을 일정길이만 보여주고 뒤는 (...으로 변환한다)
     * @param str 대상문자열
     * @param size 보여줄 최대길이
     * @param end 잘라내고 뒤게 붙일 문자열
     * @return sb 변환된 문자열
     */
    public static String toFileTruncate(String str, int size, String end){
        StringBuffer sb = new StringBuffer();
        if ((str == null) || str.equals("")){
            return "";
        }
        String startStr = "";
        String endStr = "";
        if (str.indexOf(".") != -1) {
            Vector tempString = split(str, ".");
            startStr = (String)tempString.get(0);
            endStr = (String)tempString.get(1);
        } else {
            startStr = str;
            endStr = "";
        }
        int tempSize = startStr.length();
        if(tempSize > size){
            sb.append(startStr.substring(0, size));
            sb.append(end);
        } else {
            sb.append(startStr);
        }
        sb.append(".");
        sb.append(endStr);
        return sb.toString();
    }

    /**
     * 특정한 문자열을 다른 문자열로 치환
     * @param target 작업대상문자열
     * @param o 바뀔문자열
     * @param n 바꿀문자열
     * @return String 결과문자열
     */
    public static String replace(String target, String o, String n){
        StringBuffer result = new StringBuffer();
        int idx = 0;
        if ((target == null) || target.equals("")){
            return "";
        }
        //문장이 끝나지 않았으면 재귀호출한다
        while ((idx=target.indexOf(o)) != -1) {
            result.append(target.substring(0,idx));
            result.append(n);
            target = target.substring(idx+o.length());
        }
        result.append(target);

        return result.toString();

    }

    /**
     * 전화번호 형식 데이터를 순차적으로 취득
     * @param target 변환목표 문자열
     * @return String 전화번호의 자리수
     */
    public static Vector getTelToken(String target, String sep) {
        Vector vtTarget = new Vector();
        vtTarget.add("");
        vtTarget.add("");
        vtTarget.add("");
        if (target != null && !target.equals("")) {
            Vector vtTempTarget = split(target, sep);
            if (vtTempTarget.size() == 3 ) {
                vtTarget = vtTempTarget;
            }
        }
        return vtTarget;
    }

    /**
     * 메일 형식 데이터를 순차적으로 취득
     * @param target 변환목표 문자열
     * @return String 전화번호의 자리수
     */
    public static Vector getMailToken(String target, String sep) {
        Vector vtTarget = new Vector();
        vtTarget.add("");
        vtTarget.add("");
        if (target != null && !target.equals("")) {
            Vector vtTempTarget = split(target, sep);
            if (vtTempTarget.size() == 2 ) {
                vtTarget = vtTempTarget;
            }
        }
        return vtTarget;
    }

    /**
     * 문자열을 구분자에 의해 벡터로 리턴한다
     * @param target 변환대상 문자열
     * @param str 짤라낼 구분자
     * @return Vector 결과 벡터
     */
    public static Vector split(String target,String str){
        StringTokenizer token = new StringTokenizer(target,str,true); //읽을라인을 ","로 항목별로 토큰을 만든다

        Vector vtCsvTemp = new Vector();          // 토큰을 분리해낼 벡터

        //널이거나 문자가 없을경우는 size가 0인 벡터를 넘긴다
        if ((target == null) || (target.equals(""))){
            return vtCsvTemp ;
        }

        //토큰이 하나도 없을 경우는 해당문자열을 담은 size가 1인 벡터를 넘긴다
        if (token.hasMoreTokens()==false){
            vtCsvTemp.addElement(target);
            return vtCsvTemp;
        }

        //첫번째토큰값을 구해서 값이있는지 ","인지를 체크한다
        String oldToken = token.nextToken().trim();
        // 첫번째 토큰이","일경우 공백을 넣어준다
        if (oldToken.equals(str)){
            vtCsvTemp.addElement("");
            // 첫번째 토큰이 값이 있을경우 값을 넣어준다
        }else{
            vtCsvTemp.addElement(oldToken);
        }

        //모든토킅에 위의 처리를 반복한다
        while(token.hasMoreTokens()){
            String tempToken = token.nextToken().trim();
            // 현재토큰이 ","이면 앞의 값을 체크해서 앞의값이","이아니면 처리하지않고 앞의 값이 ","이면 널이므로 공백처리
            if (tempToken.equals(str)){
                //앞의 값도 ","이면 그값은 널이다
                if (oldToken.equals(str)){
                    vtCsvTemp.addElement("");
                }
                // 값이 있는경우는 값을 넣는다
            }else{
                vtCsvTemp.addElement(tempToken);
            }
            oldToken = tempToken;  // 이전데이터 비교를 위해 설정
        }
        return vtCsvTemp;  //구분된 항목의 벡터를 리턴
    }

    /**
     * 길이가 12자리 미만의 경우 공백으로 데이터를 체운
     * @param str 대상데이터
     * @return String 12자리가 체워진 데이
     */
    public static String rightPadding (String str) {
        StringBuffer sbData = new StringBuffer();
        int length = nvl(str).length();
        if (length >= 12) {
            sbData.append(str.substring(0, 12));
        } else {
            sbData.append(str);
            for (int i = length ; i < 12 ; i++) {
                sbData.append(" ");
            }
        }
        return sbData.toString();
    }


    /**
     * 메뉴에서 레벨당 들여쓰기를 설정한다
     * @param level 메뉴레벨
     * @return String 결과문자열
     */
    public static String printMenuTitle(String level) {
        StringBuffer sbLevel = new StringBuffer();
        int nlevel = 0;
        if ((level != null) && !"".equals(level)) {
            nlevel = Integer.parseInt(level);
        }
        for (int i = 1; i < nlevel; i++) {
            sbLevel.append("&nbsp;&nbsp;&nbsp;&nbsp;");
        }

        return sbLevel.toString();
    }


    public static String arrayToString(String[] tmp){
        StringBuffer buf = new StringBuffer();
        if(tmp!=null && tmp.length>0){
            for(int i=0;i<tmp.length;i++){
                buf.append(tmp[i]);
                buf.append(";");
            }
        }
        return buf.toString();
    }

    /**
     * 줄바꿈 처리 (파이어폭스 버그 대응)
     * @param sTarget
     * @param iChkByte
     * @return sRetStr String
     */
    public static String chkString(String sTarget, int iChkByte) {
        StringBuffer sRet = new StringBuffer();
        String sRetStr = "";
        String sTemp = "";
        int iLen = 0;
        int j = 0;
        int iSumByte = 0;

        if ((sTarget == null) || (sTarget.equals(""))) {
            return sRetStr;
        } else {
            iLen = sTarget.length();
        }
        for (int i = 0; i < iLen; i++) {
            j = i + 1;
            sTemp = sTarget.substring(i, j);

            try {
                iSumByte = iSumByte + sTemp.getBytes("UTF-8").length;
            } catch (java.io.UnsupportedEncodingException enc) {

            }

            if (iSumByte >= iChkByte) {
                sRet.append(sTarget.substring(i, j)).append("​&#x200B;");
                iSumByte = 0;
            } else {
                sRet.append(sTarget.substring(i, j));
            }
        }
        sRetStr = sRet.toString();
        return sRetStr;
    }

    public static String round(double target) {
        String pattern = "0.##";
        DecimalFormat df = new DecimalFormat(pattern);

        return df.format(target);

    }

    /**
     * 널인경우 공백으로 바꾸어준다
     * @param  str 변경문자열
     * @return int 변경된 숫자
     */
    public static double parseDouble(String str) {
        return parseDouble(str, 0);
    }

    /**
     *  널일경우 일정한 형식으로 바꾼다
     * @param str 변경전 문자열
     * @param basic 변경할 문자열
     * @return int 변경된 숫자
     */
    public static double parseDouble(String str, int basic) {
        try {
            if( (str == null) || (str.trim().equals(""))
                    || (str.trim().equals("null")) ) {
                return basic;
            } else {
                return Double.parseDouble(str);
            }
        }catch (Exception ex){
            return basic;
        }
    }

    /**
     * 글을 HTML태그에 디폴트 표시할때 " 일경우 뒤의 문자가 짤리므로특수분자를 변경한다
     * @param target 대상문자열
     * @return result 변환된 결과 문자열
     */
    public static String convertTag(String target){
        String result = "";
        //공백을 &nbsp;로 변환<
        result = convertString(target,"<","&lt;");
        result = convertString(result,">","&gt;");
        return result;
    }

    public static String[] lastString(String target) {
//		String[] divide = {"", "", "", "", ""};
        String[] divide = {"", "", "", ""};
        int idx = target.lastIndexOf(System.getProperty("file.separator"));
        String temp = "";
        String temp1 = "";
//		String temp2 = "";
        if (idx !=  -1){
            temp = target.substring(0, idx);
            divide[0] = target.substring(idx+1);
            int idx1 = temp.lastIndexOf(System.getProperty("file.separator"));
            if (idx1 !=  -1){
                temp1 = temp.substring(0, idx1);
                divide[1] = temp.substring(idx1+1);
                int idx2 = temp1.lastIndexOf(System.getProperty("file.separator"));
                if (idx2 !=  -1){
                    divide[3] = temp1.substring(0, idx2);
                    divide[2] = temp1.substring(idx2+1);

//					temp2 = temp1.substring(0, idx2);
//					divide[2] = temp1.substring(idx2+1);
//					int idx3 = temp2.lastIndexOf(System.getProperty("file.separator"));
//					if (idx3 !=  -1){
//						divide[4] = temp2.substring(0, idx3);
//						divide[3] = temp2.substring(idx3+1);
//
//					}
                }
            }
        }

        return divide;
    }

    /**
     * 문자열을 뒤에서부터 검색해 해당포맷(pattern)이 있으면 해당포맷뒤의 문자열을 삭제한다.
     * @param target 입력문자열
     * @param pattern (-,/)구분형식
     * @return String 취득한문자열
     */
    public static String lastString(String target, String pattern) {

        StringBuffer sbuf = new StringBuffer();

        int idx = target.lastIndexOf(pattern);

        if (idx !=  -1){
            sbuf.append(target.substring(0, idx));
        }else {
            sbuf.append(target);
        }

        return sbuf.toString();
    }

    /**
     * 전화변호 형식의 문자를 만든다. 일반 TEST를 전화번호 형식처럼 "-"이 붙여진 형식으로 바꾼다.
     * @param str 일반 숫자형 TEXT
     * @return 전화번호 형식 str
     */
    public static String toTel(String str){
		/*
		if(str == null || str.equals("")){
			return "";
		}
		str = str.replaceAll("-", "");
		try{
			String szTel1 ="";
			String szTel2 ="";
			String szTel3 ="";

			int len = str.length();

			if(str.substring(0,2).equals("02")){
				szTel1 = str.substring(0,2);
				szTel2 = str.substring(2,len-4);
				szTel3 = str.substring(len-4,len);
			}else{
				szTel1 = str.substring(0,3);
				szTel2 = str.substring(3,len-4);
				szTel3 = str.substring(len-4,len);

			}
			str = szTel1 + "-" + szTel2 + "-" + szTel3;
		}catch(Exception e){

		}

		 */
        return str;
    }

    /**
     * 주민등록번호 형식의 문자를 만든다. 일반 TEST를 주민등록번호 형식처럼 "-"이 붙여진 형식으로 바꾼다.
     * @param str 일반 숫자형 TEXT
     * @return 전화번호 형식 str
     */
    public static String toSsno(String str){
        if(str == null || str.equals("")){
            return "";
        }
        str = str.replaceAll("-", "");
        try{
            String szSsno1 ="";
            String szSsno2 ="";

            int len = str.length();

            szSsno1 = str.substring(0,6);
            szSsno2 = str.substring(6,len);

            str = szSsno1 + "-" + szSsno2;
        }catch(Exception e){

        }
        return str;
    }



    /**
     * 사업자 등록번호 형식의 문자를 만든다. 일반 TEST를 전화번호 형식처럼 "-"이 붙여진 형식으로 바꾼다.
     * @param str 일반 숫자형 TEXT
     * @return 전화번호 형식 str
     */
    public static String toVisNum(String str){
        if(str == null || str.equals("")){
            return "";
        }
        str = str.replaceAll("-", "");
        try{
            String szTel1 ="";
            String szTel2 ="";
            String szTel3 ="";

            int len = str.length();

            szTel1 = str.substring(0,3);
            szTel2 = str.substring(3,len-5);
            szTel3 = str.substring(len-5,len);

            str = szTel1 + "-" + szTel2 + "-" + szTel3;
        }catch(Exception e){

        }

        return str;
    }

    /**
     * 카드번호 형식의 문자를 만든다. 일반 TEXT를 카드번호 형식처럼 "-"이 붙여진 형식으로 바꾼다.
     * @param str 일반 숫자형 TEXT
     * @return 전화번호 형식 str
     */
    public static String toCardNum(String str){
        if(str == null || str.equals("")){
            return "";
        }
        str = str.replaceAll("-", "");
        try{
            String szTel1 ="";
            String szTel2 ="";
            String szTel3 ="";
            String szTel4 ="";

            int len = str.length();

            szTel1 = str.substring(0,4);
            szTel2 = str.substring(4,8);
            szTel3 = str.substring(8,12);
            szTel3 = str.substring(12,16);

            str = szTel1 + "-" + szTel2 + "-" + szTel3 + "-" + szTel4;
        }catch(Exception e){

        }

        return str;
    }


    /**
     * 모든 문자열의 "-"을 땐다.
     * @param str 일반 TEXT
     * @return 전화번호 형식 str
     */
    public static String toMinerhifun(String str){
        if(str == null || str.equals("")){
            return "";
        }
        str = str.replaceAll("-", "");

        return str;
    }

    /**
     * 구분자가 있는 문자열의 마지막 문자열의 값을 취득한다.
     * @param target 변환 대상의 문자열
     * @param sep 컷 하는 구분자
     * @return String 마지막 문자열
     */
    public static String parseFirstString(String target, String sep){
        String value = "";
        String tempTarget = nvl(target).trim();
        if (!"".equals(tempTarget)) {
            int idx = tempTarget.indexOf(sep);
            if (idx == -1) {
                value = tempTarget;

            } else {
                value = tempTarget.substring(0, idx);
            }
        }
        return value.trim();
    }
    /**
     * 구분자가 있는 문자열의 마지막 문자열값을 취득한다
     * @param target 변환대상 문자열
     * @param sep 짤라낼 구분자
     * @return String 마지막문자열
     */
    public static String parseLastString(String target, String sep){
        String value = "";
        String tempTarget = nvl(target).trim();
        if (!"".equals(tempTarget)) {
            int idx = tempTarget.lastIndexOf(sep);
            if (idx == -1) {
                value = tempTarget;

            } else {
                if (idx == (tempTarget.length()-1)){
                    value = "";
                } else {
                    value = tempTarget.substring(idx+1);
                }
            }
        }
        return value.trim();
    }
    /**
     * 문자열을 변환
     * @param target 변환대상 문자열
     * @param oldStr 대상 문자열
     * @param newStr 변환한 문자열
     * @return String 변환한 문자열
     */
    public static String replaceStr(String target, String oldStr, String newStr) {
        String result = target;

        if(target.equals(oldStr)){
            result = newStr;
        }

        return result;
    }

    /**
     * 문자열의 byte수 취득
     * @param str 문자열
     * @return String 바이트 수
     */
    public static int getByteLength(String str){
        int strLength = 0;
        char tempChar[] = new char[str.length()];

        for (int i = 0; i < tempChar.length; i++) {
            tempChar[i] = str.charAt(i) ;

            if (tempChar[i] < 128) {
                strLength++;
            } else {
                strLength += 2;
            }
        }

        return strLength;
    }

    /**
     * 숫자열을 고정된 자리수의 문자열로 변환
     * @param num 숫자
     * @param size 리턴되어야 할 문자열의 사이즈
     * @return String 문자열
     */
    public static String covertNumToStr(String num, int size) {
        String sRet = "";

        try{
            double pow_num = Math.pow(10, size);
            double dValue = pow_num + Double.parseDouble(num);
            String sValue = Double.toString(dValue);
            sRet = sValue.substring(1, size+1);
        }catch(Exception ex){
        }

        return sRet;
    }


    /**
     * 엔티티 문자열로 변환
     * @return String 문자열
     */
    public static String covertEntityCode(String url) {
        String sRet = url;

        try{
            sRet = sRet.replaceAll("&", "&amp;");
            sRet = sRet.replaceAll("<", "&lt;");
            sRet = sRet.replaceAll(">", "&gt;");
            sRet = sRet.replaceAll("\"", "&quot;");
            sRet = sRet.replaceAll("'", "&#x27;");
        }catch(Exception ex){
        }

        return sRet;
    }


    /**
     *
     * @param params
     * @return
     */
    public static String encodeString(Properties params) {
        StringBuffer sb = new StringBuffer(256);
        Enumeration names = params.propertyNames();

        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String value = params.getProperty(name);
            sb.append(URLEncoder.encode(name) + "=" + URLEncoder.encode(value) );

            if (names.hasMoreElements()) sb.append("&");
        }
        return sb.toString();
    }


    public static String jsonObjectHas(JSONObject obj, String key){
        String sRet = "";

        if(obj.has(key)){
            sRet = obj.getString(key);
        }

        return sRet;
    }
}
