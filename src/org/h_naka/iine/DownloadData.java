package org.h_naka.iine;

import java.io.InputStream;
import java.lang.IllegalArgumentException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.IllegalStateException;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

public class DownloadData {
    private String m_url;

    public DownloadData(String url) {
        m_url = url;
    }
    
    public String getData() {
        InputStream is = null;
        String result = null;
        boolean isSuccess = true;
        
        // http get
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(m_url);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch(IllegalArgumentException e) {
        	e.printStackTrace();
            isSuccess = false;
        } catch (IOException e) {
            e.printStackTrace();
            isSuccess = false;
        } catch(IllegalStateException e) {
            e.printStackTrace();
            isSuccess = false;
        } catch(Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        if (isSuccess == false) {
            return null;
        }
        
        // convert response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
            result = builder.toString();
        } catch(IOException e) {
            e.printStackTrace();
            isSuccess = false;
        } catch(Exception e) {
            e.printStackTrace();
            isSuccess = false;
        } finally {
            try {
                is.close();
            } catch(IOException e) {
                e.printStackTrace();
                isSuccess = false;
            }
        }

        if (isSuccess == false) {
            return null;
        } else {
            return result;
        }
    }
}
