package com.a96group.appdup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {

   // CheckBox Diabetic,Nonveg,Vegetarian;
    //Button generate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //for back button
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("bmi") + "( " + bundle.getString("message") +" )";

        TextView t = (TextView) findViewById(R.id.textView3);
        t.setText(message);
      //  addListenerOnButtonClick();
    }

    public boolean onOptionsItemSelected(MenuItem item) {

    Intent myintent=new Intent(getApplicationContext(),MainActivity2.class);
        startActivityForResult(myintent,0);
        return true;
    }

    //public void addListenerOnButtonClick() {
      //  Diabetic = (CheckBox) findViewById(R.id.checkBox);
        //Nonveg = (CheckBox) findViewById(R.id.checkBox2);
        //Vegetarian = (CheckBox) findViewById(R.id.checkBox3);
       // generate = (Button) findViewById(R.id.button3);

        //generate.setOnClickListener(new View.OnClickListener() {
          //  @Override
           // public void onClick(View view) {
             //   StringBuffer result = new StringBuffer();
               // result.append("Diabetic:").append(Diabetic.isChecked());
                //result.append("\nNonveg:").append(Nonveg.isChecked());
                //result.append("\nVegetarian:").append(Vegetarian.isChecked());

            //}
        //});
    //}
}
