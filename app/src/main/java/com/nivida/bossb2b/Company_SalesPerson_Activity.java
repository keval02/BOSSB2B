package com.nivida.bossb2b;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Company_SalesPerson_Activity extends AppCompatActivity {


    RadioGroup rdo_select;

    RadioButton rdo_distributor , rdo_retailer;
    Button btn_next;
    AppPref prefs;

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company__sales_person_);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        prefs = new AppPref(getApplicationContext());
        rdo_select=(RadioGroup) findViewById(R.id.rdo_group_select);
        rdo_distributor = (RadioButton) findViewById(R.id.rdo_distributor);
        rdo_retailer = (RadioButton) findViewById(R.id.rdo_retailer);



        btn_next=(Button) findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checked_id=rdo_select.getCheckedRadioButtonId();

                if(checked_id==R.id.rdo_distributor){

                    prefs.setSelectedUserRole(C.DISTRIBUTOR_ROLE);
                    Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else if(checked_id==R.id.rdo_retailer){
                    prefs.setSelectedUserRole(C.DIS_RETAILER_ROLE);
                    Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }



    }

