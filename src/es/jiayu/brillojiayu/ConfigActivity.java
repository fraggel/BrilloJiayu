package es.jiayu.brillojiayu;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by U028952 on 17/09/13.
 */
public class ConfigActivity extends Activity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    TextView textMin=null;
    TextView textMax=null;
    Button setBtn=null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_activity);
        SeekBar minB=(SeekBar)findViewById(R.id.seekBar);
        textMin=(TextView)findViewById(R.id.textView2);
        textMax=(TextView)findViewById(R.id.textView4);
        setBtn=(Button)findViewById(R.id.button);

        SeekBar maxB=(SeekBar)findViewById(R.id.seekBar2);
        minB.setOnSeekBarChangeListener(this);
        maxB.setOnSeekBarChangeListener(this);
        setBtn.setOnClickListener(this);
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);
        textMin.setText(prefs.getString("minBrillo","25"));
        textMax.setText(prefs.getString("maxBrillo","100"));
        minB.setProgress(Integer.parseInt(String.valueOf(textMin.getText())));
        maxB.setProgress(Integer.parseInt(String.valueOf(textMax.getText())));
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        int seek_label_pos = seekBar.getProgress();

        if(R.id.seekBar==seekBar.getId()){
            textMin.setText(String.valueOf(seek_label_pos));
        }else if(R.id.seekBar2==seekBar.getId()){
            textMax.setText(String.valueOf(seek_label_pos));
        }


    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View view) {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();

        edit.putString("minBrillo",String.valueOf(textMin.getText()));
        edit.putString("maxBrillo",String.valueOf(textMax.getText()));
        edit.commit();
        finish();
    }
}