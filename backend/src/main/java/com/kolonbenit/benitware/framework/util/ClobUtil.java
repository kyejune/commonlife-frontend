package com.kolonbenit.benitware.framework.util;

import java.io.Reader;
import java.io.Writer;
import java.sql.Clob;

public class ClobUtil {
    public ClobUtil() {
    }

    public static String read(Clob clob) {
        char[] result = null;

        try {
            Reader reader = clob.getCharacterStream();
            result = new char[0];

            int size;
            char[] new_result;
            for(char[] buf = new char[4096]; (size = reader.read(buf)) != -1; result = new_result) {
                new_result = new char[result.length + size];
                System.arraycopy(result, 0, new_result, 0, result.length);
                System.arraycopy(buf, 0, new_result, result.length, size);
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return new String(result);
    }

    public static void write(Clob clob, String text) {
        try {
            Writer out = clob.setCharacterStream(1L);
            out.write(text);
            out.flush();
            out.close();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }
}
