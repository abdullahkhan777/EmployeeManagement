package com.example.android.employeeadapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EmployeeDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        Button backButton = (Button)findViewById(R.id.back);

        TextView fNameTxt = (TextView) findViewById(R.id.fName);
        TextView lNameTxt = (TextView) findViewById(R.id.lName);

        Intent intent = getIntent();

        String fName = intent.getStringExtra("fName");
        String lName = intent.getStringExtra("lName");

        fNameTxt.setText(fName);
        lNameTxt.setText(" "+lName);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(EmployeeDetails.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }
}