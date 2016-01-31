package com.vpspace.smartdiary;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;

public class GeometryActivity extends MainActivity {
    private ViewFlipper flipper = null;
    private float fromPosition;

    TextView tvText;
    TextView tvText2;
    TextView tvTextC;


    TextView tvTextT;
    TextView tvTextT2;

    String algebra, algebraA, algebraT;

    String str;
    String strT;
    String strC;

    Integer con = 0;

    private final int IDD_MARCS_SAVE = 1;
    private final int IDD_MARCS_SAVET = 0;
    private final int IDD_MARCS_SAVEC = 2;


    public static final String SAVED_TEXTT = "saved_textt";
    public static final String SAVED_TEXTC = "saved_textc";


    ImageButton btnSaveC, btnLoadC, btnSave,  btnLoad, btnSaveT, btnLoadT;


    ToggleButton swMenu;

    SharedPreferences sPrefT;
    SharedPreferences sPrefC;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geometry);

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        //mainLayout.setOnTouchListener(this);

        flipper = (ViewFlipper) findViewById(R.id.flipper);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int layouts[] = new int[]{ R.layout.first, R.layout.second};
        for (int layout : layouts)
            flipper.addView(inflater.inflate(layout, null));

        swMenu = (ToggleButton) findViewById(R.id.sw_menu);

        swMenu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){


            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                if (isChecked){
                    swGo();
                }else{
                    swGoBack();
                }
            }

        });

        tvText = (TextView) findViewById(R.id.tvText);
        tvText2 = (TextView) findViewById(R.id.tvText2);
        tvTextC = (TextView) findViewById(R.id.tvTextC);
        tvTextT = (TextView) findViewById(R.id.tvTextT);
        tvTextT2 = (TextView) findViewById(R.id.tvTextT2);

        btnSave = (ImageButton) findViewById(R.id.btnSave);
        btnSaveC = (ImageButton) findViewById(R.id.btnSaveC);
        btnLoad = (ImageButton) findViewById(R.id.btnLoad);
        btnLoadC = (ImageButton) findViewById(R.id.btnLoadC);
        btnSaveT = (ImageButton) findViewById(R.id.btnSaveT);
        btnLoadT = (ImageButton) findViewById(R.id.btnLoadT);



        View.OnClickListener OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnSave:

                        showDialog(IDD_MARCS_SAVE);
                        break;
                    case R.id.btnSaveC:

                        showDialog(IDD_MARCS_SAVEC);
                        break;
                    case R.id.btnLoad:
                        String str1 = tvText.getText().toString();
                        if (str1 != ""){
                            cleanText();}
                        break;
                    case R.id.btnLoadC:

                            cleanAllTextC();
                        break;
                    case R.id.btnSaveT:
                        showDialog(IDD_MARCS_SAVET);
                        break;
                    case R.id.btnLoadT:
                        String str2 = tvTextT.getText().toString();
                        if (str2 != ""){
                            cleanTextT();}
                        break;
                    default:
                        break;
                }
            }
        };

        btnSave.setOnClickListener(OnClickListener);
        btnSaveC.setOnClickListener(OnClickListener);
        btnLoad.setOnClickListener(OnClickListener);
        btnLoadC.setOnClickListener(OnClickListener);
        btnSaveT.setOnClickListener(OnClickListener);
        btnLoadT.setOnClickListener(OnClickListener);

        mass();
        massT();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        switch (id) {
            case R.id.action_in_marks:
                String str1 = tvText2.getText().toString();
                if (str1 != "") {
                    loadTextToTable();
                }
                return true;
            case R.id.action_in_marks_t:
                String str2 = tvTextT2.getText().toString();
                if (str2 != "") {
                    loadTextToTableT();
                }
                return true;
            case R.id.action_delete_all:
                cleanAllText();
                Toast.makeText(this, "Оценки очищены", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_delete_all_t:
                cleanAllTextT();
                Toast.makeText(this, "Оценки очищены", Toast.LENGTH_SHORT).show();
                return true;
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public void swGo(){
        flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.go_next_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.go_next_out));
        flipper.showNext();

    }

    public void swGoBack(){
        flipper.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.go_prev_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.go_prev_out));
        flipper.showPrevious();
    }

    /*public boolean onTouch(View view, MotionEvent event) {

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                fromPosition = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                float toPosition = event.getX();
                if (fromPosition > toPosition)
                {
                    flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.go_next_in));
                    flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.go_next_out));
                    flipper.showNext();

                }
                else if (fromPosition < toPosition)
                {
                    flipper.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.go_prev_in));
                    flipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.go_prev_out));
                    flipper.showPrevious();
                }
            default:
                break;
        }
        return true;
    }*/

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case IDD_MARCS_SAVE:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Выберете оценку");
                final String[] marcks ={"12", "11", "10", "9", "8", "7", "6", "5", "4", "3", "2", "1"};

                builder = new AlertDialog.Builder(this);
                builder.setTitle("Выберете оценку");

                builder.setItems(marcks, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        // TODO Auto-generated method stub
                        str = marcks[item];
                        saveText(str);
                    }

                });

                builder.setNeutralButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });


                builder.setCancelable(true);
                return builder.create();

            case IDD_MARCS_SAVEC:
                AlertDialog.Builder builderC = new AlertDialog.Builder(this);
                builderC.setMessage("Выберете оценку");
                final String[] marcksC ={"12", "11", "10", "9", "8", "7", "6", "5", "4", "3", "2", "1"};

                builder = new AlertDialog.Builder(this);
                builder.setTitle("Выберете оценку");

                builder.setItems(marcksC, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        // TODO Auto-generated method stub
                        strC = marcksC[item];
                        saveTextC(strC);
                    }

                });

                builder.setNeutralButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });


                builder.setCancelable(true);
                return builder.create();


            case IDD_MARCS_SAVET:
                AlertDialog.Builder builderT = new AlertDialog.Builder(this);
                builderT.setMessage("Выберете оценку");
                final String[] marcksT ={"12", "11", "10", "9", "8", "7", "6", "5", "4", "3", "2", "1"};

                builder = new AlertDialog.Builder(this);
                builder.setTitle("Выберете оценку"); // заголовок для диалога

                builder.setItems(marcksT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        // TODO Auto-generated method stub
                        strT = marcksT[item];
                        saveTextT();
                    }

                });

                builder.setNeutralButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });


                builder.setCancelable(true);
                return builder.create();

            default:
                return null;
        }
    }



    void loadText(){
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, "");
        tvText.setText(savedText);
    }

    void saveText(String str) {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TEXT, sPref.getString(SAVED_TEXT, "") + " " + str + " ");
        ed.commit();
        loadText();
        Toast.makeText(this, "Оценка добавлена", Toast.LENGTH_SHORT).show();
        mass();
    }

    void loadTextC(){
        sPrefC = getPreferences(MODE_PRIVATE);
        String savedTextC = sPrefC.getString(SAVED_TEXTC, "");
        tvTextC.setText(savedTextC);
    }

    void saveTextC(String str) {
        sPrefC = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edC = sPref.edit();
        edC.putString(SAVED_TEXTC, str);
        edC.commit();
        loadTextC();
        Toast.makeText(this, "Контрольная добавлена", Toast.LENGTH_SHORT).show();
        mass();
    }
    void cleanAllTextC() {
        sPrefC = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edC = sPref.edit();
        edC.putString(SAVED_TEXTC, "");
        edC.commit();
        sPrefC = getPreferences(MODE_PRIVATE);
        String savedText = sPrefC.getString(SAVED_TEXTC, "");
        tvTextC.setText(savedText);
        Toast.makeText(this, "Оценки очищены", Toast.LENGTH_SHORT).show();
    }



    void mass(){

        loadText();
        loadTextC();
        strC = tvTextC.getText().toString();


        if (strC != "") {
            con = Integer.valueOf(strC);
        }

        String str = tvText.getText().toString();
        if (str != ""){
            int a[] = new int[100];
            int i = 0;
            int x = 0;
            char[] chArray = str.toCharArray();
            if (chArray[i] == ' ') {
                while (i < str.length()) {
                    if (chArray[i + 2] == ' ') {
                        String st = Character.toString(chArray[i + 1]);
                        Integer q = Integer.valueOf(st);

                        a[x] = q;
                        i = i + 3;
                    } else {
                        String st1 = Character.toString(chArray[i + 1]);
                        String st2 = Character.toString(chArray[i + 2]);
                        String st = st1 + st2;
                        Integer q = Integer.valueOf(st);

                        a[x] = q;
                        i = i + 4;
                    }
                    x++;
                }
            }

            int w = 0;
            double average = 0;
            double sum = 0;
            while (a[w] > 0) {
                sum = sum + a[w];
                w++;
            }

            average = (sum / w);

            if (con != 0){
                average = (average + con)/2;
            }

            if(average >= 10){
                tvText2.setTextColor(Color.rgb(81, 255, 0));
            }else if ((average<10)&(average>=8)){
                tvText2.setTextColor(Color.rgb(39, 161, 51));
            }else if ((average<8)&(average>=7)){
                tvText2.setTextColor(Color.rgb(32, 124, 1));
            }else if ((average<7)&(average>=5)){
                tvText2.setTextColor(Color.rgb(241, 210, 37));
            }else if ((average<5)&(average>=3)){
                tvText2.setTextColor(Color.rgb(241, 170, 37));
            }else if ((average<3)&(average>=1)){
                tvText2.setTextColor(Color.rgb(241, 40, 37));
            }

            average = average * 100;
            if((average % 10 == 0)&(average % 100 == 0)){
                average = average / 100;
                int intaverage = (int) average;
                String faverage = Integer.toString(intaverage);
                tvText2.setText(faverage);

            }else {
                average = average / 100;
                average = Math.round(average * 100.0) / 100.0;

                String faverage = Double.toString(average);
                tvText2.setText(faverage);
            }
        }
    }

    public void loadTextToTable(){
        algebra = tvText.getText().toString();
        algebraA = tvText2.getText().toString();
        Intent intent = new Intent(this, MarksActivity.class);
        if (tvText.getText().toString().length() != 0) {
            intent.putExtra("algebra", algebra);
            intent.putExtra("algebraA", algebraA);
            intent.putExtra("algebraT", saver);
        }
        startActivity(intent);
    }

    public void loadTextToTableT(){
        Intent intent = new Intent(this, MarksActivity.class);
        intent.putExtra("algebraT", tvTextT2.getText().toString());
        intent.putExtra("algebra", saver);
        intent.putExtra("algebraA", saver);
        startActivity(intent);
    }

    void cleanText() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, "");

        char[] chMarkArray = savedText.toCharArray();
        SharedPreferences.Editor ed = sPref.edit();

        if (chMarkArray[chMarkArray.length - 3] == ' '){
            if(chMarkArray.length > 3) {
                savedText = new String(chMarkArray, 0, chMarkArray.length - 3);
                ed.putString(SAVED_TEXT, savedText);
            }else{ed.putString(SAVED_TEXT, ""); tvText2.setText("");}
        }else if (chMarkArray[chMarkArray.length - 4] == ' ') {
            if (chMarkArray.length > 4) {
                savedText = new String(chMarkArray, 0, chMarkArray.length - 4);
                ed.putString(SAVED_TEXT, savedText);
            }else{ed.putString(SAVED_TEXT, ""); tvText2.setText("");}
        }
        ed.commit();
        sPref = getPreferences(MODE_PRIVATE);
        tvText.setText(savedText);
        mass();
        Toast.makeText(this, "Оценка очищена", Toast.LENGTH_SHORT).show();}

    void cleanAllText() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TEXT, "");
        ed.commit();
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, "");
        tvText.setText(savedText);
        tvText2.setText(savedText);
        Toast.makeText(this, "Оценки очищены", Toast.LENGTH_SHORT).show();
    }




    void loadTextT(){
        sPrefT = getPreferences(MODE_PRIVATE);
        String savedTextT = sPrefT.getString(SAVED_TEXTT, "");
        tvTextT.setText(savedTextT);
    }

    void saveTextT() {
        sPrefT = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edT = sPrefT.edit();
        edT.putString(SAVED_TEXTT, sPrefT.getString(SAVED_TEXTT, "") + " " + strT + " ");
        edT.commit();
        loadTextT();
        Toast.makeText(this, "Тематическая добавлена", Toast.LENGTH_SHORT).show();
        massT();
    }

    void massT(){
        loadTextT();

        String strT = tvTextT.getText().toString();
        if (strT != ""){
            int aT[] = new int[100];
            int iT = 0;
            int xT = 0;
            char[] chArray = strT.toCharArray();
            if (chArray[iT] == ' ') {
                while (iT < strT.length()) {
                    if (chArray[iT + 2] == ' ') {
                        String stT = Character.toString(chArray[iT + 1]);
                        Integer qT = Integer.valueOf(stT);

                        aT[xT] = qT;
                        iT = iT + 3;
                    } else {
                        String st1T = Character.toString(chArray[iT + 1]);
                        String st2T = Character.toString(chArray[iT + 2]);
                        String stT = st1T + st2T;
                        Integer q = Integer.valueOf(stT);

                        aT[xT] = q;
                        iT = iT + 4;
                    }
                    xT++;
                }
            }

            int wT = 0;
            double averageT = 0;
            double sumT = 0;
            while (aT[wT] > 0) {
                sumT = sumT + aT[wT];
                wT++;
            }

            averageT = (sumT / wT);

            if(averageT >= 10){
                tvTextT2.setTextColor(Color.rgb(81, 255, 0));
            }else if ((averageT<10)&(averageT>=8)){
                tvTextT2.setTextColor(Color.rgb(39, 161, 51));
            }else if ((averageT<8)&(averageT>=7)){
                tvTextT2.setTextColor(Color.rgb(32, 124, 1));
            }else if ((averageT<7)&(averageT>=5)){
                tvTextT2.setTextColor(Color.rgb(241, 210, 37));
            }else if ((averageT<5)&(averageT>=3)){
                tvTextT2.setTextColor(Color.rgb(241, 170, 37));
            }else if ((averageT<3)&(averageT>=1)){
                tvTextT2.setTextColor(Color.rgb(241, 40, 37));
            }

            averageT = averageT * 100;
            if((averageT % 10 == 0)&(averageT % 100 == 0)){
                averageT = averageT / 100;
                int intaverageT = (int) averageT;
                String faverageT = Integer.toString(intaverageT);
                tvTextT2.setText(faverageT);

            }else {
                averageT = averageT / 100;
                averageT = Math.round(averageT * 100.0) / 100.0;

                String faverageT = Double.toString(averageT);
                tvTextT2.setText(faverageT);
            }
        }
    }


    void cleanAllTextT() {
        sPrefT = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edT = sPrefT.edit();
        edT.putString(SAVED_TEXTT, "");
        edT.commit();
        sPrefT = getPreferences(MODE_PRIVATE);
        String savedTextT = sPrefT.getString(SAVED_TEXTT, "");
        tvTextT.setText(savedTextT);
        tvTextT2.setText(savedTextT);
        Toast.makeText(this, "Тематические оценки очищены", Toast.LENGTH_SHORT).show();}

    void cleanTextT() {
        sPrefT = getPreferences(MODE_PRIVATE);
        String savedTextT = sPrefT.getString(SAVED_TEXTT, "");

        char[] chMarkArray = savedTextT.toCharArray();
        SharedPreferences.Editor edT = sPrefT.edit();

        if (chMarkArray[chMarkArray.length - 3] == ' '){
            if(chMarkArray.length > 3) {
                savedTextT = new String(chMarkArray, 0, chMarkArray.length - 3);
                edT.putString(SAVED_TEXTT, savedTextT);
            }else{edT.putString(SAVED_TEXTT, ""); tvTextT2.setText(""); savedTextT = "";}
        }else if (chMarkArray[chMarkArray.length - 4] == ' ') {
            if (chMarkArray.length > 4) {
                savedTextT = new String(chMarkArray, 0, chMarkArray.length - 4);
                edT.putString(SAVED_TEXTT, savedTextT);
            }else{edT.putString(SAVED_TEXTT, ""); tvTextT2.setText(""); savedTextT = "";}
        }

        edT.commit();
        tvTextT.setText(savedTextT);
        massT();
        Toast.makeText(this, "Тематическая оценка очищена", Toast.LENGTH_SHORT).show();}

}



