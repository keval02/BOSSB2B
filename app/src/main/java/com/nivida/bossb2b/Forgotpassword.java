package com.nivida.bossb2b;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Forgotpassword extends AppCompatActivity {
    EditText etreset_mobile_number;
    String email;
    Button submit , cancel;
    ProgressActivity loadingView;
    String json = new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        fetchId();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

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




    }
    private void fetchId()
    {
        submit= (Button)findViewById(R.id.btn_resetpassword);
        cancel= (Button)findViewById(R.id.btn_cancel);
        etreset_mobile_number= (EditText)findViewById(R.id.etreset_mobile_number);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Forgotpassword.this,LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(etreset_mobile_number.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(Forgotpassword.this, "Mobile/Email is required", Toast.LENGTH_LONG).show();

                }else {
                    email = etreset_mobile_number.getText().toString().trim();
                    new Send_forget_data().execute();
                }



            }
        });
    }



    public void onBackPressed() {
        // TODO Auto-generated method stub


        Intent i = new Intent(Forgotpassword.this,LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    };


    public class Send_forget_data extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(
                        Forgotpassword.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {



                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("email_id",email));



                Log.e("2",""+email);

                //   Log.e("4", "" + parameters);
                // http://app.nivida.in/agraeta/User/App_Forgotpassword
                // json = new ServiceHandler().makeServiceCall(GlobalVariable.server_link + "User/App_Forgotpassword", ServiceHandler.POST, parameters);
                json = new ServiceHandler().makeServiceCall(GlobalVariable.link+"User/App_forgot_password", ServiceHandler.POST, parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                System.out.println("array: " + json);

                return json;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error1: " + e.toString());

                return json;

            }
//            Log.e("result",result);


            //    return null;
        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {

                //db = new DatabaseHandler(());
                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                   /* Toast.makeText(LogInPage.this, "SERVER ERROR",
                            Toast.LENGTH_SHORT).show();*/
                    Toast.makeText(Forgotpassword.this, "SERVER ERROR", Toast.LENGTH_LONG).show();
                    loadingView.dismiss();

                }
                else
                {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Toast.makeText(Forgotpassword.this, "" + Message, Toast.LENGTH_LONG).show();
                        //Toast.makeText(LogInPage.this,""+Message,Toast.LENGTH_LONG).show();
                        loadingView.dismiss();

                    } else {

                        String Message = jObj.getString("message");
                        Toast.makeText(Forgotpassword.this, "" + Message, Toast.LENGTH_LONG).show();
                        // Toast.makeText(LogInPage.this,""+Message,Toast.LENGTH_LONG).show();

                        loadingView.dismiss();
                        Intent i = new Intent(Forgotpassword.this, LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }
}
