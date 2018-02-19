package com.kolonbenit.benitware.common.util.httpClient;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * HttpClient version 4.5 Standard
 * </pre>
 * @return
 */
public class SSLHttpClientUril {

	private static final String ENCODING = "UTF-8";

	private static CloseableHttpClient client;
	private static List<NameValuePair> listParam;
	private static String strRes = "";

	private static CloseableHttpClient sslClient() {
		SSLContextBuilder builder = new SSLContextBuilder();

		try {
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
			client = HttpClients.custom()
					.setSSLSocketFactory(sslsf)
					.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
					.build();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return client;
	}

	public static String get(String url, Map<String, Object> param, Header[] headers) {
		client = sslClient();
		listParam = convertParam(param);

		try {
			HttpGet get = new HttpGet(url + "?" + URLEncodedUtils.format(listParam, ENCODING));
			get.setHeaders(headers);
			
			HttpResponse response = client.execute(get);
			strRes = EntityUtils.toString(response.getEntity());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return strRes;
	}
	
	public static String get(String url, Map<String, Object> param) {
		client = sslClient();
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
		client = sslClient();
		listParam = convertParam(param);

		String strResult = "";
		
		try {
			HttpPost post = new HttpPost(url);
			post.setEntity(new UrlEncodedFormEntity(listParam, ENCODING));

			HttpResponse response = client.execute(post);
			strResult = EntityUtils.toString(response.getEntity(),ENCODING);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return strResult;
	}

	private static List<NameValuePair> convertParam(Map<String, Object> param) {
        List<NameValuePair> listParam = new ArrayList<NameValuePair>();

        if (param != null) {
	        Iterator<String> it = param.keySet().iterator();
	        while(it.hasNext()) {
	            String key = it.next();
	            listParam.add(new BasicNameValuePair(key, param.get(key).toString()));
	        }
        }
        return listParam;
    }

	public static String postJson(String url, String sJson) {
		client = sslClient();

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

	public static String putJson(String url, String sJson) {
		client = sslClient();

		try {
			HttpPut put = new HttpPut(url);
			put.setHeader("Content-Type", "application/json");

			StringEntity params = new StringEntity(sJson, ENCODING);
			put.setEntity(params);

			HttpResponse response = client.execute(put);
			strRes = EntityUtils.toString(response.getEntity());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return strRes;
	}

	public static String postJson(String url, Header header, String sJson) {
		client = sslClient();

		try {
			HttpPost post = new HttpPost(url);
			post.setHeader("Content-Type", "application/json");
			post.addHeader(header);

			StringEntity params = new StringEntity(sJson, ENCODING);
			post.setEntity(params);

			HttpResponse response = client.execute(post);
			strRes = EntityUtils.toString(response.getEntity());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return strRes;
	}
	
	public static String postJson(String url, Header[] headers, String sJson) {
		client = sslClient();
		
		try {
			HttpPost post = new HttpPost(url);
			post.setHeaders(headers);
			
			StringEntity params = new StringEntity(sJson, ENCODING);
			post.setEntity(params);
			
			HttpResponse response = client.execute(post);
			strRes = EntityUtils.toString(response.getEntity());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return strRes;
	}

	public static String deleteJson(String url, ObjectNode onJson) {

		RestTemplate restTemplate = new RestTemplate();
		
		HttpEntity httpEntity = new HttpEntity(onJson);
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, httpEntity, String.class);
		
		return responseEntity.getBody();
	}
	
	public static String deleteJson(String url) {
		client = sslClient();

		try {
			HttpDelete delete = new HttpDelete(url);
			delete.setHeader("Content-Type", "application/json");

			HttpResponse response = client.execute(delete);
			strRes = EntityUtils.toString(response.getEntity());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return strRes;
	}
	
	public static String deleteJson(String url, String sJson) {
		client = sslClient();

		try {
			HttpDeleteWithBody del = new HttpDeleteWithBody(url);
			del.setHeader("Content-Type", "application/json");

			StringEntity params = new StringEntity(sJson, ENCODING);
			del.setEntity(params);

			HttpResponse response = client.execute(del);
			strRes = EntityUtils.toString(response.getEntity());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return strRes;
	}
	
	public static String deleteJson(String url, Header[] headers, String sJson) {
		
		client = sslClient();
		
		try {
			HttpDeleteWithBody del = new HttpDeleteWithBody(url);
			del.setHeaders(headers);
			
			StringEntity params = new StringEntity(sJson, ENCODING);
			del.setEntity(params);
			
			HttpResponse response = client.execute(del);
			strRes = EntityUtils.toString(response.getEntity());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return strRes;
	}
}
