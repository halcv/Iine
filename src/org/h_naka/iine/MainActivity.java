package org.h_naka.iine;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Gravity;
import android.app.ProgressDialog;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

public class MainActivity extends Activity {

    private Button m_getButton;
    private EditText m_urlEditText;
    private TextView m_infoTextView;
    private IineDataProcess m_process;
    private ProgressDialog m_dialog;
    private Spinner m_intervalSpinner;
    private ArrayAdapter<String> m_intervalAdapter;
    private String [] m_interval;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initInstance();
        setInterface();
    }

    @Override
    protected void onDestroy() {
        m_process.stopTimer();
        super.onDestroy();
    }
    private void initInstance() {
        m_getButton    = (Button)findViewById(R.id.getButton);
        m_urlEditText  = (EditText)findViewById(R.id.urlEditText);
        m_infoTextView = (TextView)findViewById(R.id.infoTextView);
        m_process = new IineDataProcess(this);
        createProgressDialog();
        m_interval = getResources().getStringArray(R.array.interval);
        m_intervalSpinner = (Spinner)findViewById(R.id.intervalSpinner);
        m_intervalAdapter = new ArrayAdapter<String>(this,R.layout.interval,m_interval);
        m_intervalSpinner.setAdapter(m_intervalAdapter);
        m_intervalSpinner.setOnItemSelectedListener(m_process);
        m_intervalSpinner.setSelection(1);
    }

    private void setInterface() {
        m_getButton.setOnClickListener(m_process);
    }

    public EditText getUrlEditText() {
        return m_urlEditText;
    }
    public TextView getInfoTextView() {
        return m_infoTextView;
    }

    public Spinner getIntervalSpinner() {
        return m_intervalSpinner;
    }
    
    public void showInfoData() {
        StringBuilder builder = new StringBuilder();
        builder.append(getResources().getString(R.string.likeLabel));
        builder.append(Integer.toString(m_process.getIineInfoData().getLike()));
        builder.append("\n");

        builder.append(getResources().getString(R.string.commentLabel));
        builder.append(Integer.toString(m_process.getIineInfoData().getComment()));
        builder.append("\n");
        
        builder.append(getResources().getString(R.string.shareLabel));
        builder.append(Integer.toString(m_process.getIineInfoData().getShare()));
        builder.append("\n");
        final String out = builder.toString();

        runOnUiThread(new Runnable() {
			public void run() {
				m_infoTextView.setText(out);
                m_dialog.dismiss();
            }
		});
    }

    public void showDataError() {
		runOnUiThread(new Runnable() {
			public void run() {
                createToast(getResources().getString(R.string.getIineError));
                m_dialog.dismiss();
            }
		});
    }

    private void createToast(String message) {
        Toast toast = Toast.makeText(this,message,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public void showProgressDialog() {
		runOnUiThread(new Runnable() {
			public void run() {
                m_dialog.show();
            }
		});
    }
    
    private void createProgressDialog() {
        m_dialog = new ProgressDialog(this);
        m_dialog.setIndeterminate(true);
        m_dialog.setCancelable(false);
        m_dialog.setMessage(getResources().getString(R.string.getIine));
    }
}
