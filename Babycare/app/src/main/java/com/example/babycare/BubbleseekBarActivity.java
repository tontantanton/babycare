package com.example.babycare;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.TextView;

import com.xw.repo.BubbleSeekBar;

public class BubbleseekBarActivity extends AppCompatActivity {

    private BubbleSeekBar bubbleSeekBar;
    private TextView persen;
    private int value =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubbleseek_bar);

        bubbleSeekBar = (BubbleSeekBar) findViewById(R.id.seekbar);
        persen = (TextView)findViewById(R.id.textview_persen);




        bubbleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                persen.setText(""+progress+"%");
                value = progress;

            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });



    }
}
