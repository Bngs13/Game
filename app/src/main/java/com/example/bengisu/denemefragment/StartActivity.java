package com.example.bengisu.denemefragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StartActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE="";
    EditText nameText;
    Button start;
    AlertDialog alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);
        start=findViewById(R.id.start);
        nameText=findViewById(R.id.name);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nameText.getText().toString().isEmpty()){
                    alert=new AlertDialog.Builder(StartActivity.this).create();
                    alert.setTitle("Caution");
                    alert.setMessage("Please enter your name!");
                    alert.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                     alert.show();
                }
                else SendMassage();
            }
        });


    }

    public void SendMassage(){
        Intent intent=new Intent(this,MainActivity.class);
        nameText=findViewById(R.id.name);
        String message=nameText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

}
