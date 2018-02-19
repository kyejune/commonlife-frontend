package com.kolonbenit.benitware.common.util.httpClient;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * HttpClient version 4.3 Standard
 * </pre>
 * @return
 */
public class HttpClientUril {

	private static final String ENCODING = "UTF-8";
	private static final String ENCODING2 = "euc-kr";

	private static HttpClient client;
	private static List<NameValuePair> listParam;
	private static String strRes = "";
	
	//private static HttpParams httpPrarm;

	public static String get(String url, Map<String, Object> param, Header[] headers) {
		client = HttpClientBuilder.create().build();
		listParam = convertParam(param);
		
		//String param_str = "";
		
		
		try { 
			
			HttpGet get = new HttpGet(url);
			System.out.println(url + "?" + URLEncodedUtils.format(listParam, ENCODING2));

			HttpResponse response = client.execute(get);
			strRes = EntityUtils.toString(response.getEntity() ,"UTF-8" ); //response.getEntity();
			
			//System.out.println(strRes);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strRes;
	}
	
	public static String get2(String url2) {
		HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("http://121.156.99.116:8080/kolon/morph.do");
        
        

        // Create some NameValuePair for HttpPost parameters
        /*List<NameValuePair> arguments = new ArrayList<>(3);
        arguments.add(new BasicNameValuePair("authId", "TEST_IOT"));
        arguments.add(new BasicNameValuePair("sendDate","20170220123030"));
        arguments.add(new BasicNameValuePair("content", "꺼불안방"));*/
        try {
        	
        	//System.out.println(arguments);
            //post.setEntity(new UrlEncodedFormEntity(arguments));
            HttpResponse response = client.execute(post);

            // Print out the response message
            strRes = EntityUtils.toString(response.getEntity());
            //System.out.println(response.getEntity().toString());            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		/*URL url = null;

        URLConnection urlConnection = null;    

        // URL 주소
        String sUrl = "http://121.156.99.116:8080/kolon/morph.do?sendDate=20170220123030&authId=TEST_IOT&content=꺼불안방";
		
		try {
			
			url = new URL(sUrl);
            urlConnection = url.openConnection();
         
            printByInputStream(urlConnection.getInputStream());
            System.out.println("출력완료");
	
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return strRes;
	}
	
	
	// 웹 서버로 부터 받은 웹 페이지 결과를 콘솔에 출력하는 메소드

    public static void printByInputStream(InputStream is) {

        byte[] buf = new byte[1024];
        int len = -1;      

        try {

            while((len = is.read(buf, 0, buf.length)) != -1) {
                System.out.write(buf, 0, len);
            }

        } catch(IOException e) {
            e.printStackTrace();
        }

    }




	public static String post(String url, Map<String, Object> param) {
		client = HttpClientBuilder.create().build();
		listParam = convertParam(param);
		//System.out.println(listParam);

		try {
			HttpPost post = new HttpPost(url);
			post.setEntity(new UrlEncodedFormEntity(listParam, ENCODING));

			HttpResponse response = client.execute(post);
			strRes = EntityUtils.toString(response.getEntity(), ENCODING);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return strRes;
	}
		
	

	/**
	 * <pre>
	 * cf. Header[] headers = {new BasicHeader("aa", "11"), new BasicHeader("bb", "22")};
	 * </pre>
	 * @param url
	 * @param param
	 * @param headers
	 * @return
	 */
	public static String post(String url, Map<String, Object> param, Header[] headers) {
		client = HttpClientBuilder.create().build();
		listParam = convertParam(param);
	
		try {
			HttpPost post = new HttpPost(url);
			post.setEntity(new UrlEncodedFormEntity(listParam, ENCODING));
			post.setHeaders(headers);
			HttpResponse response = client.execute(post);
			
			strRes = EntityUtils.toString(response.getEntity());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return strRes;
	}

	/**
	 * <pre>
	 * JSON 및 XML 요청
	 * 	- type : (1 : json / 2 : xml)
	 * </pre>
	 * @param url
	 * @param rawPayload
	 * @param type
	 * @return
	 */
	public static String post(String url, String rawPayload, int type) {
		client = HttpClientBuilder.create().build();

		boolean isProcess = true;
		StringBuilder sb = new StringBuilder();
		switch (type) {
		case 1:
			sb.append("application/json;");
			break;
		case 2:
			sb.append("application/xml;");
			break;
		default:
			isProcess = false;
			break;
		}

		if (isProcess) {
			try {
				StringEntity entity = new StringEntity(rawPayload, ENCODING);
				entity.setContentType(sb.toString());

				HttpPost post = new HttpPost(url);
				post.setEntity(entity);

				HttpResponse response = client.execute(post);
				strRes = EntityUtils.toString(response.getEntity());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return strRes;
	}

	private static List<NameValuePair> convertParam(Map<String, Object> param) {
        List<NameValuePair> listParam = new ArrayList<NameValuePair>();

        Iterator<String> it = param.keySet().iterator();
        while(it.hasNext()) {
            String key = it.next();
            listParam.add(new BasicNameValuePair(key, param.get(key).toString()));
        }
        return listParam;
    }
	
	
	private static String convertParam2(Map<String, Object> param) {
        String strParam="";
        Iterator<String> it = param.keySet().iterator();
        while(it.hasNext()) {
            String key = it.next();
            String value = param.get(key).toString();
            strParam = strParam + key + "=" + value + "&";            
        }
        return strParam;
    }

}