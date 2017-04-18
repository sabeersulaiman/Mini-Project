package com.a96group.appdup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    private EditText Name;
    private EditText Age;
    private TextView Gender;


    private RadioGroup RadioSexGroup;
    private RadioButton radioSexButton;


    private EditText Weight;
    private EditText Height;
    private Button clickButton;
    private TextView result;

    public int selectedId;

    private String bmiValue;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Name = (EditText) findViewById(R.id.editText);
        Age = (EditText) findViewById(R.id.editText2);
        Gender = (TextView) findViewById(R.id.textView4);
        Height = (EditText) findViewById(R.id.editText3);
        Weight = (EditText) findViewById(R.id.editText4);

     //   Male=(RadioButton) findViewById(R.id.radioMale);
       // Female=(RadioButton) findViewById(R.id.radiofemale);
        RadioSexGroup=(RadioGroup) findViewById(R.id.radiosex);



        final TextView textView = (TextView) findViewById(R.id.textView3);
        clickButton = (Button) findViewById(R.id.button);
        clickButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);

                String heightStr = Height.getText().toString();
                String weightStr = Weight.getText().toString();

                selectedId=RadioSexGroup.getCheckedRadioButtonId();
                radioSexButton=(RadioButton)findViewById(selectedId);
                if (heightStr != null && !"".equals(heightStr)
                        && weightStr != null && !"".equals(weightStr)) {
                    float heightvalue = Float.parseFloat(heightStr) / 100;
                    float weightvalue = Float.parseFloat(weightStr);
                    float bmi = weightvalue / (heightvalue * heightvalue);
                    bmiValue = String.valueOf(bmi);
                    message = displayBMI(bmi);
                }

                if (Name.getText().toString().length()==0) {

                    Toast.makeText(getApplicationContext(), "Name cannot be Blank", Toast.LENGTH_SHORT).show();
                    Name.setError("name cannot be Blank");
                    return;
                }
                if (Age.getText().toString().length()==0){

                    Toast.makeText(getApplicationContext(), "Age cannot be Blank", Toast.LENGTH_SHORT).show();
                    Age.setError("Age cannot be Blank");
                    return;
                }
                if(Height.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(),"Enter your Height",Toast.LENGTH_SHORT).show();
                    Height.setError("Height cannot be Blank");
                    return;
                }
                if(Weight.getText().toString().length()==0) {
                    Toast.makeText(getApplicationContext(), "Enter your Weight", Toast.LENGTH_SHORT).show();
                    Height.setError("Weight cannot be Blank");
                    return;
                }


                intent.putExtra("bmi", bmiValue);
                intent.putExtra("message", message);

                startActivity(intent);
                finish();
            }
        });

    }
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity2.this);
        builder.setCancelable(false);
        builder.setMessage("DO you want to exit?");

        builder.setPositiveButton("Yes",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog,int which){
                finish();
            }
        });
        builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }







    private String displayBMI(float bmi) {
        String bmilabel = "";
        if (Float.compare(bmi, 15f) > 0 && Float.compare(bmi, 18.5f) <= 0) {
            bmilabel = getString(R.string.underweight);
        } else if (Float.compare(bmi, 18.5f) > 0 && Float.compare(bmi, 25f) <= 0) {
            bmilabel = getString(R.string.normal);
        } else if (Float.compare(bmi, 25f) > 0 && Float.compare(bmi, 30f) <= 0) {
            bmilabel = getString(R.string.overweight);
        } else if (Float.compare(bmi, 30f) > 0 && Float.compare(bmi, 35f) <= 0) {
            bmilabel = getString(R.string.obese_class_i);
        } else if (Float.compare(bmi, 35f) > 0 && Float.compare(bmi, 40f) <= 0) {
            bmilabel = getString(R.string.obese_class_ii);
        } else
            bmilabel = getString(R.string.obese_class_iii);

        return bmilabel;
    }

  // public void addListenerOnButton(View view) {
    // RadioButton    button = (RadioButton) view;
      //  boolean Male=true;
        //boolean Female = false;
       // if (button.getId() == R.id.radioMale) {
         //   Male = radioSexButton.isChecked();
           // if (Female) {
                //radioSexButton.setChecked(false);
            //}
        //} else if (button.getId() == R.id.radiofemale) {
          //  Female = radioSexButton.isChecked();
            //{
              //  if (Male) {
                //    Male = false;
                  //  radioSexButton.setChecked(false);
                //}

            //}
        //}
    //}
//}


//public void addListenerOnclick(View view){
  //      radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
   //   btnDisplay = (Button) findViewById(R.id.radioSex);
     //   btnDisplay.setOnClickListener(new View.OnClickListener() {
      //@Override
        //   public void onClick(View view) {
          //      int selectedId = radioSexGroup.getCheckedRadioButtonId();
            //   radioSexButton = (RadioButton) findViewById(selectedId);

        //    }
      //  });
    //}
}















