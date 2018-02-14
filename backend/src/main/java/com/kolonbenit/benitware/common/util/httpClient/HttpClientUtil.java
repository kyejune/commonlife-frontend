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
public class HttpClientUtil {

	private static final String ENCODING = "UTF-8";

	private static HttpClient client;
	private static List<NameValuePair> listParam;
	private static String strRes = "";

	public static String get(String url, Map<String, Object> param) {
		client = HttpClientBuilder.create().build();
		listParam = convertParam(param);

		try {
			HttpGet get = new HttpGet(url + "?" + URLEncodedUtils.format(listParam, ENCODING));

			HttpResponse response = client.execute(get);
			strRes = EntityUtils.toString(response.getEntity());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return strRes;
	}

	public static String post(String url, Map<String, Object> param) {
		client = HttpClientBuilder.create().build();
		listParam = convertParam(param);

		try {
			HttpPost post = new HttpPost(url);
			post.setEntity(new UrlEncodedFormEntity(listParam, ENCODING));

			HttpResponse response = client.execute(post);
			strRes = EntityUtils.toString(response.getEntity());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return strRes;
	}
	
	
	public static String voice_post(String url, Map<String, Object> param) {
		client = HttpClientBuilder.create().build();
		listParam = convertParam(param);
	

		try {
			HttpPost post = new HttpPost(url);
			post.setEntity(new UrlEncodedFormEntity(listParam,ENCODING));

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

	public static String postJson(String url, String sJson) {
		
		client = HttpClientBuilder.create().build();
		
		try {
			
			HttpPost post = new HttpPost(url);
			post.setHeader("Content-Type", "application/json");

			StringEntity params = new StringEntity(sJson, ENCODING);
			post.setEntity(params);

			HttpResponse response = client.execute(post);
			strRes = EntityUtils.toString(response.getEntity());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return strRes;
	}
	
}