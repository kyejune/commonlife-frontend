package com.kolon.common.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
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

public class HttpGetRequester {
    private static final Logger logger = LoggerFactory.getLogger(HttpGetRequester.class);
    private static final String CONTENT_TYPE = "Content-Type";

    private CloseableHttpClient httpClient;
    private URIBuilder          uriBuilder;

    public HttpGetRequester(CloseableHttpClient httpClient, String host, String path)
                throws URISyntaxException {
        this.httpClient = httpClient;
        this.uriBuilder = new URIBuilder(host);
        this.uriBuilder.setPath(path);
    }

    public HttpGetRequester setParameter(String param , String value ) {
        this.uriBuilder.setParameter(param, value);
        return this;
    }

    public HttpGetRequester setParameter(Map<String,String> paramMap) {
        Iterator<String> e = paramMap.keySet().iterator();
        while(e.hasNext()) {
            String key = e.next();
            this.uriBuilder.setParameter( key, paramMap.get(key));
        }
        return this;
    }

    public Map execute() throws URISyntaxException, IOException, HttpRequestFailedException {
        Map result = null;
        ObjectMapper mapper = null;
        HttpGet httpGet = new HttpGet(this.uriBuilder.build());
        httpGet.addHeader( CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE );

        // Execute
        try
        {
            HttpResponse response = httpClient.execute( httpGet );
            if( response.getStatusLine().getStatusCode() >= 400 ) {
                throw new HttpRequestFailedException(
                        response.getStatusLine().getStatusCode(),
                        response.getStatusLine().getReasonPhrase());
            }

            // Mapping result
            mapper = new ObjectMapper();
            result = mapper.readValue( response.getEntity().getContent(),  Map.class );
        } finally {
            httpGet.releaseConnection();
        }

        return result;
    }

    private class TimedOutHttpGetAbortTimerTask extends TimerTask {
        private HttpGet  timedOutHttpGet;
        private boolean  stopFlag;

        public TimedOutHttpGetAbortTimerTask(HttpGet timedOutHttpGet) {
            this.stopFlag = false;
            this.timedOutHttpGet = timedOutHttpGet;
        }

        public void stop() {
            this.stopFlag = true;
        }

        public void run() {
            if (timedOutHttpGet != null && !stopFlag ) {
                timedOutHttpGet.abort();
            }
        }
    };

    public Map executeWithTimeout(int hardTimeout) throws URISyntaxException, IOException, HttpRequestFailedException {
        Map result = null;
        ObjectMapper mapper = null;
        HttpGet timedOutHttpGet = new HttpGet(this.uriBuilder.build());
        timedOutHttpGet.addHeader( CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE );
        Timer timer;

        TimedOutHttpGetAbortTimerTask task = new TimedOutHttpGetAbortTimerTask( timedOutHttpGet );
        timer = new Timer(true);
        timer.schedule(task, hardTimeout * 1000);

        try {
            // Execute
            HttpResponse response = httpClient.execute(timedOutHttpGet);
            task.stop();
            timer.cancel();

            if (response.getStatusLine().getStatusCode() >= 400) {
                throw new HttpRequestFailedException(
                        response.getStatusLine().getStatusCode(),
                        response.getStatusLine().getReasonPhrase());
            }

            // Mapping result
            mapper = new ObjectMapper();
            result = mapper.readValue(response.getEntity().getContent(), Map.class);
        } finally {
            timedOutHttpGet.releaseConnection();
        }

        return result;
    }
}
