package com.xht.android.managerhelp;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;

import com.xht.android.managerhelp.view.RoundImageView;

/**
 *
 * Created by Administrator on 2016/12/19.
 *
 */
public class LoadPersonImageView extends Activity{
    private RoundImageView personImg;
    private Button loadImg;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        setContentView(R.layout.activity_loadmg);

        initialize();
    }

    private void initialize() {

        personImg = (RoundImageView) findViewById(R.id.personImg);
        loadImg = (Button) findViewById(R.id.loadImg);
    }
}
