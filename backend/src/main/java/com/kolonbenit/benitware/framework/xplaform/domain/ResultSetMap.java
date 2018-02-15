package com.kolonbenit.benitware.framework.xplaform.domain;


import java.sql.Clob;

import com.kolonbenit.benitware.framework.util.ClobUtil;
import org.apache.commons.collections.FastHashMap;

/**
 * @description resultset 결과를 Map에 처리하기 위한 클래스
 * @author 장승호
 * @version 1.0
 * @since 2014.01.19
 * @date 2014.01.19
 * @classKorName ResultSetMap
 */
public class ResultSetMap extends FastHashMap {

    private static final long serialVersionUID = 8081730361426656101L;

    /**
     * @description
     * @param  Object key, Object value 쿼리 row별 결과
     * @return Object map
     * @throws Exception
     * @methodKorName resultset 결과를 Map에 camelcase형태로 저장
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    public Object put( Object key, Object value ) {

        // Clob..
        if (value instanceof Clob) {
            value = ClobUtil.read((Clob) value);
        }
        return super.put(convertToCamelCase(key.toString()), value );
    }


    /**
     * @description
     * @param  Object key, Object value 쿼리 row별 결과
     * @return Object map
     * @throws Exception
     * @methodKorName resultset 결과를 Map에 저장
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    public Object putNoLowerCase( Object key, Object value ) {
        // Clob..
        if (value instanceof Clob) {
            value = ClobUtil.read((Clob) value);
        }
        return super.put( key.toString(), value );
    }

    /**
     * @description
     * @param  String str 컬럼명 정보
     * @return Object map
     * @throws Exception
     * @methodKorName 컬럼명을 camelCase 형태로 저장
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    public static String convertToCamelCase(String str) {
        char delimiter='_';
        StringBuilder result = new StringBuilder();
        boolean nextUpper = false;
        String allLower = str.toLowerCase();

        for (int i = 0; i < allLower.length(); i++) {
            char currentChar = allLower.charAt(i);
            if (currentChar == delimiter) {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    currentChar = Character.toUpperCase(currentChar);
                    nextUpper = false;
                }
                result.append(currentChar);
            }
        }
        return result.toString();
    }

}