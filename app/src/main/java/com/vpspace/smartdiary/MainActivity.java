package com.vpspace.smartdiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    public static final String SAVED_TEXT = "saved_text";

    SharedPreferences sPref;

    String saver = "1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_tabel:
                goToMarksActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public void goToMarksActivity (){
        Intent intent = new Intent(this, MarksActivity.class);
        intent.putExtra("algebra", saver);
        intent.putExtra("algebraT", saver);
        startActivity(intent);
    }

    public void goBack (){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void goAlgebraActivity (View v){
        Intent intent = new Intent(this, AlgebraActivity.class);
        startActivity(intent);
    }

    public void goGeometryActivity (View v){
        Intent intent = new Intent(this, GeometryActivity.class);
        startActivity(intent);
    }

    public void goPhysicsActivity (View v){
        Intent intent = new Intent(this, PhysicsActivity.class);
        startActivity(intent);
    }

}
