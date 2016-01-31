package com.vpspace.smartdiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Влад on 30.11.2015.
 */
public class MarksActivity extends MainActivity{

    TextView tvAlgebra, tvAlgebraA, tvAlgebraT;

    String algebra = "nothing";
    String algebraA = "nothing";
    String algebraT = "nothing";

    public SharedPreferences sPrefAlgebra, sPrefAlgebraA;

    private static final String SAVED_ALGEBRA = "saved_algebra";
    private static final String SAVED_ALGEBRAA = "saved_algebraa";
    private static final String SAVED_ALGEBRAT = "saved_algebrat";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marks);

        tvAlgebra = (TextView) findViewById(R.id.tvAlgebra);
        tvAlgebraA = (TextView) findViewById(R.id.tvAlgebraA);
        tvAlgebraT = (TextView) findViewById(R.id.tvAlgebraT);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intentMarks();
        loadTextAlgebra();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_marks, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        switch (id) {
            case R.id.action_delete:
                String str1 = tvAlgebra.getText().toString();
                if (str1 != "") {
                    deleteTextAlgebra();
                }
                return true;
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public void intentMarks(){
        Intent intent = getIntent();
        algebra = intent.getStringExtra("algebra");
        char [] chIntent = algebra.toCharArray();
        algebraA = intent.getStringExtra("algebraA");
        if (chIntent[0] != '1') {
            saveTextAlgebra(algebra, algebraA);
        }
        algebraT = intent.getStringExtra("algebraT");
        char [] chIntentT = algebraT.toCharArray();
        if (chIntentT[0] != '1') {
            saveTextAlgebraT(algebraT);
        }
    }

    void loadTextAlgebra(){
        sPrefAlgebra = getPreferences(MODE_PRIVATE);
        String savedText = sPrefAlgebra.getString(SAVED_ALGEBRA, "");
        tvAlgebra.setText(savedText);

        sPrefAlgebraA = getPreferences(MODE_PRIVATE);
        String savedTextA = sPrefAlgebraA.getString(SAVED_ALGEBRAA, "");
        tvAlgebraA.setText(savedTextA);

        sPrefAlgebraA = getPreferences(MODE_PRIVATE);
        String savedTextT = sPrefAlgebraA.getString(SAVED_ALGEBRAT, "");
        tvAlgebraT.setText(savedTextT);

    }

    void saveTextAlgebra(String str, String strA) {
        sPrefAlgebra = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPrefAlgebra.edit();
        ed.putString(SAVED_ALGEBRA, str);
        ed.commit();

        sPrefAlgebraA = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edA = sPrefAlgebraA.edit();
        edA.putString(SAVED_ALGEBRAA, strA);
        edA.commit();
        loadTextAlgebra();
    }

    void saveTextAlgebraT(String str) {
        sPrefAlgebra = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPrefAlgebra.edit();
        ed.putString(SAVED_ALGEBRAT, str);
        ed.commit();
    }

    void deleteTextAlgebra() {
        sPrefAlgebra = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPrefAlgebra.edit();
        ed.putString(SAVED_ALGEBRA, "");
        ed.commit();

        sPrefAlgebraA = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edA = sPrefAlgebraA.edit();
        edA.putString(SAVED_ALGEBRAA, "");
        edA.commit();


        sPrefAlgebra = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edT = sPrefAlgebra.edit();
        ed.putString(SAVED_ALGEBRAT, "");
        ed.commit();
        loadTextAlgebra();
    }

}
