package com.kolon.common.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.regex.Pattern;

public class XSSRequestWrapper extends HttpServletRequestWrapper {

    private static Pattern[] patterns = new Pattern[] {
            Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
            Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
                            | Pattern.DOTALL),
            Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
                            | Pattern.DOTALL),
            Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
            Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE
                    | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE
                    | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE
                    | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
            Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
            Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE
                    | Pattern.MULTILINE | Pattern.DOTALL)

    };

    public XSSRequestWrapper(HttpServletRequest servletRequest) {

        super(servletRequest);

    }

    @Override
    public String[] getParameterValues(String parameter) {
        //System.out.println("getParameterValues");
        String[] values = super.getParameterValues(parameter);

        if (values == null) {
            return null;
        }

        int count = values.length;

        String[] encodedValues = new String[count];

        for (int i = 0; i < count; i++) {

            encodedValues[i] = stripXSS(values[i]);

        }

        return encodedValues;

    }

    @Override
    public String getParameter(String parameter) {
        //System.out.println("getParameter");
        String value = super.getParameter(parameter);

        return stripXSS(value);

    }

    @Override
    public String getHeader(String name) {
        //System.out.println("getHeader");
        String value = super.getHeader(name);

        return stripXSS(value);

    }

    private String stripXSS(String value) {
        if (value == null) {
            return null;
        }

        StringBuffer result = new StringBuffer(value.length());


        for (int i=0; i<value.length(); ++i) {
            switch (value.charAt(i)) {
                case '<':
                    result.append("&lt;");
                    break;
                case '>':
                    result.append("&gt;");
                    break;
                case '"':
                    result.append("&quot;");
                    break;
                case '\'':
                    result.append("&#39;");
                    break;
                case '%':
                    result.append("&#37;");
                    break;
                case ';':
                    result.append("&#59;");
                    break;
                case '(':
                    result.append("&#40;");
                    break;
                case ')':
                    result.append("&#41;");
                    break;
                case '&':
                    result.append("&amp;");
                    break;
                case '+':
                    result.append("&#43;");
                    break;
                default:
                    result.append(value.charAt(i));
                    break;
            }
        }
        value = result.toString();
        //System.out.println("value : "+ value);
        return value;

        /*
        if (value != null) {

                // NOTE: It's highly recommended to use the ESAPI library and
                // uncomment the following line to
                // avoid encoded attacks.
                // value = ESAPI.encoder().canonicalize(value);
                // Avoid null characters

                value = value.replaceAll("\0", "");

                // Remove all sections that match a pattern
                for (Pattern scriptPattern : patterns) {
                    if ( scriptPattern.matcher(value).find() ) {
                        //System.out.println("match.....");
                        value=value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
                    }
                }
            }
            //System.out.print("result: "+value);
            return value;
	*/
    }

}