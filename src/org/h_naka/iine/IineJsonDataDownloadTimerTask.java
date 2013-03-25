package org.h_naka.iine;

import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONException;

public class IineJsonDataDownloadTimerTask extends TimerTask {

    private String m_url;
    private OnIineJsonDataDownloadListener m_listener;
    
    public IineJsonDataDownloadTimerTask(String url) {
        m_url = url;
    }
    
	@Override
	public void run() {
        m_listener.onStartIineJsonDataDownload();
        JSONArray jArray = getJsonDataFromUrl(m_url);
        m_listener.onFinishIineJsonDataDownload(jArray);
    }

    public void setOnIineJsonDataDownloadListener(OnIineJsonDataDownloadListener listener) {
        m_listener = listener;
    }
    
    private JSONArray getJsonDataFromUrl(String url) {
        JSONArray m_jArray;
        String data = new DownloadData(url).getData();

        if (data == null) {
        	return null;
        }
        
        // try parse the string to a JSON object
        try {
            m_jArray = new JSONArray(data);
        } catch(JSONException e) {
            e.printStackTrace();
            m_jArray = null;;
        } 

        return m_jArray;
    }
}

interface OnIineJsonDataDownloadListener {
    public void onFinishIineJsonDataDownload(JSONArray jArray);
    public void onStartIineJsonDataDownload();
}