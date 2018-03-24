package com.kolon.common.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class HttpPostRequester {
    final static private Logger logger = LoggerFactory.getLogger( HttpPostRequester.class );

    final static private String CONTENT_TYPE = "Content-Type";
    final static private String ACCEPT = "Accept";

        private CloseableHttpClient httpClient;
        private URIBuilder          uriBuilder;
        private TimerTask           timedOutTask;
        private String              jsonBody;

    public HttpPostRequester(CloseableHttpClient httpClient, String host, String path)
                throws URISyntaxException {
            this.httpClient = httpClient;
            this.uriBuilder = new URIBuilder(host);
            this.uriBuilder.setPath(path);
        }

        public HttpPostRequester setParameter(String param , String value ) {
            this.uriBuilder.setParameter(param, value);
            return this;
    }

    public HttpPostRequester setParameter(Map<String,String> paramMap) {
        Iterator<String> e = paramMap.keySet().iterator();
        while(e.hasNext()) {
            String key = e.next();
            this.uriBuilder.setParameter( key, paramMap.get(key));
        }
        return this;
    }

    public void setBody(String jsonBody) {
        this.jsonBody = jsonBody;
    }

    public Map execute() throws URISyntaxException, IOException, HttpRequestFailedException {
        Map result = null;
        ObjectMapper mapper = null;
        HttpPost httpPost = new HttpPost(this.uriBuilder.build());
        httpPost.addHeader( CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE );

        if( this.jsonBody != null ) {
            logger.debug(">>jsonBody: " + this.jsonBody);
            httpPost.setEntity(new StringEntity(this.jsonBody, ContentType.APPLICATION_JSON));
        }

        // Execute
        HttpResponse response = httpClient.execute( httpPost );
        httpPost.releaseConnection();
        if( response.getStatusLine().getStatusCode() >= 400 ) {
            throw new HttpRequestFailedException(
                    response.getStatusLine().getStatusCode(),
                    response.getStatusLine().getReasonPhrase());
        }

        // Mapping result
        mapper = new ObjectMapper();
        result = mapper.readValue( response.getEntity().getContent(),  Map.class );

        return result;
    }

    private class timedOutHttpPutAbortTimerTask extends TimerTask {
        private HttpPut  timedOutHttpPut;

        public timedOutHttpPutAbortTimerTask(HttpPut timedOutHttpPut) {
            this.timedOutHttpPut = timedOutHttpPut;
        }

        public void run() {
            if (timedOutHttpPut != null) {
                timedOutHttpPut.abort();
            }
        }
    };

    public Map executeWithTimeout(int hardTimeout) throws URISyntaxException, IOException, HttpRequestFailedException {
        Map result = null;
        ObjectMapper mapper = null;
        HttpPut timedOutHttpPut = new HttpPut(this.uriBuilder.build());
        timedOutHttpPut.addHeader( CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE );

        if( this.jsonBody != null ) {
            logger.debug(">>jsonBody: " + this.jsonBody);
            timedOutHttpPut.setEntity(new StringEntity(this.jsonBody, ContentType.APPLICATION_JSON));
        }

        timedOutHttpPutAbortTimerTask task = new timedOutHttpPutAbortTimerTask( timedOutHttpPut );
        new Timer(true).schedule(task, hardTimeout * 1000);

        // Execute
        HttpResponse response = httpClient.execute( timedOutHttpPut );
        timedOutHttpPut.releaseConnection();
        if( response.getStatusLine().getStatusCode() >= 400 ) {
            throw new HttpRequestFailedException(
                    response.getStatusLine().getStatusCode(),
                    response.getStatusLine().getReasonPhrase());
        }

        // Mapping result
        mapper = new ObjectMapper();
        result = mapper.readValue( response.getEntity().getContent(),  Map.class );

        return result;
    }
}
