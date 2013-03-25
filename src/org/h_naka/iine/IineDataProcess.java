package org.h_naka.iine;

import java.util.Timer;
import android.view.View;
import android.view.View.OnClickListener;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import android.widget.Toast;
import android.view.Gravity;
import android.content.res.Resources;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.AdapterView;

public class IineDataProcess
    implements OnClickListener, OnIineJsonDataDownloadListener, OnItemSelectedListener {

    private MainActivity m_activity;
    private IineInfoData m_infoData;
    private Resources m_resources;
    private Timer m_timer;
    private IineJsonDataDownloadTimerTask m_timerTask;
    private int m_interval;
    
    public IineDataProcess(MainActivity activity) {
        m_activity = activity;
        m_infoData = new IineInfoData();
        m_resources = m_activity.getResources();
        m_timer = null;
        m_timerTask = null;
        m_interval = 5000;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.getButton) {
            if (getUrl().equals("")) {
                createToast(m_resources.getString(R.string.noUrl));
                return;
            }
            getJsonData();
        }
    }

    @Override
    public void onStartIineJsonDataDownload() {
        m_activity.showProgressDialog();
    }
    
    @Override
    public void onFinishIineJsonDataDownload(JSONArray jArray) {
        if (jArray == null) {
            m_activity.showDataError();
            return;
        }

        try {
            JSONObject json = jArray.getJSONObject(0);
            m_infoData.setLike(json.getInt(m_resources.getString(R.string.likeTag)));
            m_infoData.setComment(json.getInt(m_resources.getString(R.string.commentTag)));
            m_infoData.setShare(json.getInt(m_resources.getString(R.string.shareTag)));
            m_activity.showInfoData();
        } catch(JSONException e) {
            e.printStackTrace();
            m_activity.showDataError();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent,View v,int pos,long id) {
        if (m_activity.getIntervalSpinner() == (Spinner)parent) {
            m_interval = Integer.parseInt((String)(parent.getSelectedItem())) * 1000;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    
    private void getJsonData() {
        m_activity.getInfoTextView().setText("");
        StringBuilder builder = new StringBuilder();
        builder.append(m_resources.getString(R.string.fqlURL1));
        builder.append(getUrl());
        builder.append(m_resources.getString(R.string.fqlURL2));

        startTimer(builder.toString());
    }

    private String getUrl() {
        return (m_activity.getUrlEditText().getText().toString());
    }
    
    private void createToast(String message) {
        Toast toast = Toast.makeText(m_activity,message,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public IineInfoData getIineInfoData() {
        return m_infoData;
    }

    private void startTimer(String url) {
        if (m_timer != null) {
            stopTimer();
        }

        m_timer = new Timer(true);
        m_timerTask = new IineJsonDataDownloadTimerTask(url);
        m_timerTask.setOnIineJsonDataDownloadListener(this);
        m_timer.schedule(m_timerTask,0,m_interval);
    }

    public void stopTimer() {
        if (m_timer == null) {
            return;
        }
        m_timerTask.cancel();
        m_timerTask = null;
        m_timer.cancel();
        m_timer = null;
    }
}

