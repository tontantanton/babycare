package com.example.babycare;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babycare.Content.ImagesActivity;
import com.example.babycare.Lexitron.LongLexTo;
import com.example.babycare.Lexitron.LongParseTree;
import com.example.babycare.Lexitron.Trie;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;


public class RecordVoiceActivity extends AppCompatActivity {
    private Button recordVoice ;
    private Vector indexList;
    private Vector typeList;
    private String speech;
    private String[] arrB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_voice);
        recordVoice = (Button)findViewById(R.id.recordVoice);

            recordVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSpeech();
            }
        });


    }

    public void getSpeech() {
        Intent voiceIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        voiceIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        startActivityForResult(voiceIntent, 1234);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1234: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    arrB = result.get(0).toString().split("และ");

                    GridLayout r = (GridLayout) findViewById(R.id.mainlayout);
                    for(int i = 0; i<arrB.length; i++) {
                        View list = getLayoutInflater().inflate( R.layout.activity_record_list,null);
                        TextView txt = (TextView) list.findViewById(R.id.txt_name);
                        txt.setText(arrB[i]);
                        r.addView(list);

                        //Button
                        Button gotoNext1 = (Button) findViewById(R.id.btncal);
                        gotoNext1.setOnClickListener (new View.OnClickListener() {

                            public void onClick(View  V) {

                                for(int i = 0; i<arrB.length; i++) {

                                    //Intent intent = new Intent(V.getContext(), Page_new.class);
                                    // Intent intent = new Intent(V.getContext(), Page_search_auto_1.class);
                                    // Intent intent = new Intent(V.getContext(), Pesquisa.class);
                                    Intent intent = new Intent(V.getContext(), Page_check.class);
                                intent.putExtra("s_text_1", "" + arrB[i]);
                                startActivityForResult(intent, 0);
                            }
                            }

                        });//Button


                    }
                }
                break;
            }
        }
    }

    public void wordInstance(String text) {

        indexList.clear();
        typeList.clear();
        int pos, index;
        String word;
        boolean found;
        char ch;

        pos=0;
        while(pos<text.length()) {

            //Check for special characters and English words/numbers
            ch=text.charAt(pos);

            //English
            if(((ch>='A')&&(ch<='Z'))||((ch>='a')&&(ch<='z'))) {
                while((pos<text.length())&&(((ch>='A')&&(ch<='Z'))||((ch>='a')&&(ch<='z'))))
                    ch=text.charAt(pos++);
                if(pos<text.length())
                    pos--;
                indexList.addElement(new Integer(pos));
                typeList.addElement(new Integer(3));
            }
            //Digits
            else if(((ch>='0')&&(ch<='9'))||((ch>='๐')&&(ch<='๙'))) {
                while((pos<text.length())&&(((ch>='0')&&(ch<='9'))||((ch>='๐')&&(ch<='๙'))||(ch==',')||(ch=='.')))
                    ch=text.charAt(pos++);
                if(pos<text.length())
                    pos--;
                indexList.addElement(new Integer(pos));
                typeList.addElement(new Integer(3));
            }
            //Special characters
            else if((ch<='~')||(ch=='ๆ')||(ch=='ฯ')||(ch=='“')||(ch=='”')||(ch==',')) {
                pos++;
                indexList.addElement(new Integer(pos));
                typeList.addElement(new Integer(4));
            }
            //Thai word (known/unknown/ambiguous)
            else{

            }
//                pos=ptree.parseWordInstance(pos, text);
        } //While all text length
//        iter=indexList.iterator();
    } //wordInstance

}

