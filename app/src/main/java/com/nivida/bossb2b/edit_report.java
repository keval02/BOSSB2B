package com.nivida.bossb2b;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nivida.bossb2b.Bean.Bean_EditTadaReport;
import com.nivida.bossb2b.Bean.Bean_ExtraHqMaster;
import com.nivida.bossb2b.Bean.Bean_HQMaster;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class edit_report extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    public static final int CAMERA_DIRECT = 2;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private String imgPath = "";


    String reportID = "0";
    String reportDate = "";


    ProgressActivity loadingView;
    AppPref prefs;

    String imagepath1 = "";
    String imagepath2 = "";
    ImageView im_cemara_direct_icon, im_direct_cemara1, im_direct_cemara2, im_direct_cemara3;
    LinearLayout layout_direct_cemara, layout_uploaded_cemara;
    EditText ed_fromdate, edt_to, edt_hq, edt_from, edt_mobilenet, edt_miscexpenses;

    TextView txt_hq, txt_lodge, txt_exhq;

    EditText edt_spnhq, edt_spnlodge, edt_spnexhq, edt_travel, edt_comment;

    Button btn_submit, btn_submittada, btn_editreport;


    String topDate;

    Bean_EditTadaReport bean_editTadaReport = new Bean_EditTadaReport();
    Bean_HQMaster bean_hqMaster = new Bean_HQMaster();
    ArrayAdapter<String> allCityAdapter;
    ArrayAdapter<String> onlyCityAdapter;
    ArrayAdapter<String> onlyhqAdaptet;

    String fromDate = "", toDate = "";

    Spinner spinner, spinnerafterselect, spinner_from;

    ArrayList<String> allCityIDs = new ArrayList<>();
    ArrayList<String> allCityNames = new ArrayList<>();

    ArrayList<String> onlyCityIDs = new ArrayList<>();
    ArrayList<String> onlyCityNames = new ArrayList<>();

    ArrayList<String> onlyhqCityIDs = new ArrayList<>();
    ArrayList<String> onlyhqCityNames = new ArrayList<>();


    DatePickerDialog datePickerDialog;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    List<Bean_EditTadaReport> editTadaReports = new ArrayList<>();
    List<Bean_ExtraHqMaster> extraHqMasterList = new ArrayList<>();


    int mYear, mMonth, mDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_report);
        prefs = new AppPref(getApplicationContext());

        Intent intent = getIntent();
        reportID = intent.getStringExtra("id");
        reportDate = intent.getStringExtra("reportDate");

        im_cemara_direct_icon = (ImageView) findViewById(R.id.im_cemara_direct_icon);
        layout_direct_cemara = (LinearLayout) findViewById(R.id.layout_direct_cemara);
        layout_uploaded_cemara = (LinearLayout) findViewById(R.id.layout_uploaded_cemara);
        ed_fromdate = (EditText) findViewById(R.id.ed_fromdate);


        edt_to = (EditText) findViewById(R.id.edt_toooo);

        edt_from = (EditText) findViewById(R.id.edt_fromm);

        edt_hq = (EditText) findViewById(R.id.edt_hqq);

        edt_mobilenet = (EditText) findViewById(R.id.edt_mobilenet);


        edt_miscexpenses = (EditText) findViewById(R.id.edt_miscexpenses);


        edt_travel = (EditText) findViewById(R.id.edt_travel);


        edt_comment = (EditText) findViewById(R.id.edt_cmt);
        edt_comment.setText(bean_hqMaster.getComment());


        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submittada = (Button) findViewById(R.id.btn_tadareport);
        btn_editreport = (Button) findViewById(R.id.btn_tadaeditreport);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        txt_hq = (TextView) findViewById(R.id.txt_hqq);
        txt_lodge = (TextView) findViewById(R.id.txt_lodge);
        txt_exhq = (TextView) findViewById(R.id.txt_exhq);

        edt_spnhq = (EditText) findViewById(R.id.edt_hqprice);
        edt_spnhq.setText(bean_hqMaster.getHq_price());
        edt_spnhq.setEnabled(false);


        edt_spnlodge = (EditText) findViewById(R.id.edt_lodg);


        edt_spnexhq = (EditText) findViewById(R.id.edt_edtexhq);

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

        spinner = (Spinner) findViewById(R.id.spinner_too);
        spinner_from = (Spinner) findViewById(R.id.spinner_from);
        spinnerafterselect = (Spinner) findViewById(R.id.spinner_afterselect);

        allCityAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_spinner_item, allCityNames);
        onlyhqAdaptet = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_spinner_item, onlyhqCityNames);
        onlyCityAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_spinner_item, onlyCityNames);

        spinner.setAdapter(allCityAdapter);
        spinner_from.setAdapter(onlyhqAdaptet);
        spinnerafterselect.setAdapter(onlyCityAdapter);


        spinner_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position == 0) {


                } else {


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    spinnerafterselect.setVisibility(View.GONE);
                    txt_lodge.setVisibility(View.GONE);
                    edt_spnlodge.setVisibility(View.GONE);
                    txt_exhq.setVisibility(View.GONE);
                    txt_hq.setVisibility(View.VISIBLE);
                    edt_spnhq.setVisibility(View.VISIBLE);
                    edt_spnexhq.setVisibility(View.GONE);
                } else if (position == 1) {
                    spinnerafterselect.setVisibility(View.VISIBLE);
                    txt_hq.setVisibility(View.GONE);
                    edt_spnhq.setVisibility(View.GONE);
                    txt_lodge.setVisibility(View.GONE);
                    edt_spnlodge.setVisibility(View.GONE);
                    txt_exhq.setVisibility(View.VISIBLE);
                    edt_spnexhq.setVisibility(View.VISIBLE);

                } else {
                    spinnerafterselect.setVisibility(View.GONE);
                    txt_hq.setVisibility(View.GONE);
                    edt_spnhq.setVisibility(View.GONE);
                    txt_lodge.setVisibility(View.VISIBLE);
                    edt_spnlodge.setVisibility(View.VISIBLE);
                    txt_exhq.setVisibility(View.GONE);
                    edt_spnexhq.setVisibility(View.GONE);

                    int cityPosition = position - 2;
                    edt_spnlodge.setText(extraHqMasterList.get(cityPosition).getLoading_boarding_price());
                    edt_spnlodge.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerafterselect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int cityPosition = position;
                edt_spnexhq.setText(extraHqMasterList.get(cityPosition).getExtra_hq_price());
                edt_spnexhq.setEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        im_cemara_direct_icon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int count = layout_direct_cemara.getChildCount();

                if (count <= 10) {
                    takeimage(CAMERA_REQUEST);
                } else {
                    Toast.makeText(edit_report.this, "You Can Upload Max. 10 Images.", Toast.LENGTH_SHORT).show();
                }


            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date = ed_fromdate.getText().toString();

                if (date.isEmpty()) {
                    Toast.makeText(edit_report.this, "Please Select the Date first", Toast.LENGTH_SHORT).show();
                } else {

                    List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                    parameters.add(new BasicNameValuePair("com_sales_person_id", prefs.getUser_id()));
                    // parameters.add(new BasicNameValuePair("from_location", bean_hqMaster.getCity_name()));

                    if (spinner_from.getSelectedItemPosition() == 0) {
                        parameters.add(new BasicNameValuePair("from_location", bean_editTadaReport.getCity_name()));
                    } else {
                        parameters.add(new BasicNameValuePair("from_location", spinner_from.getSelectedItem().toString()));
                    }


                    if (spinner.getSelectedItemPosition() == 0) {
                        parameters.add(new BasicNameValuePair("to_location", bean_editTadaReport.getCity_name()));
                    } else if (spinner.getSelectedItemPosition() == 1) {
                        parameters.add(new BasicNameValuePair("to_location", spinnerafterselect.getSelectedItem().toString()));
                    } else {
                        parameters.add(new BasicNameValuePair("to_location", spinner.getSelectedItem().toString()));
                    }

                    parameters.add(new BasicNameValuePair("travelling_cost", edt_travel.getText().toString()));
                    parameters.add(new BasicNameValuePair("loading_boarding", edt_spnlodge.getText().toString()));
                    parameters.add(new BasicNameValuePair("misc_expenses", edt_miscexpenses.getText().toString()));
                    parameters.add(new BasicNameValuePair("report_date", ed_fromdate.getText().toString()));
                    parameters.add(new BasicNameValuePair("comment", edt_comment.getText().toString()));
                    parameters.add(new BasicNameValuePair("report_id", bean_editTadaReport.getId()));

                    int count = layout_direct_cemara.getChildCount();

                    for (int i = 0; i < count; i++) {
                        View view = layout_direct_cemara.getChildAt(i);

                        String path = ((TextView) view.findViewById(R.id.txt_imagePath)).getText().toString();
                        if (!path.isEmpty() && !path.startsWith("http"))
                            parameters.add(new BasicNameValuePair("attachment_" + (i + 1), path));
                    }
                    new SubmitTADA(parameters).execute();

                    Log.e("Parameters" ,"-->" +parameters);

                }
            }
        });

        new tadaeditrepotfinal().execute();


    }


    private void takeimage(int requestCode) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
        startActivityForResult(cameraIntent, requestCode);
    }

    public Uri setImageUri() {
        // Store image in dcim
        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".png");
        Uri imgUri = Uri.fromFile(file);
        this.imgPath = file.getAbsolutePath();
        return imgUri;
    }

    public String getImagePath() {

        return imgPath;
    }

    public Bitmap decodeFile(String path) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, o);
            // The new size we want to scale to
            final int REQUIRED_SIZE = 400;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeFile(path, o2);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            String selectedImagePath = getImagePath();

            Bitmap photo = decodeFile(selectedImagePath);

            /*im_direct_cemara2.setImageBitmap(photo);
            im_direct_cemara3.setImageBitmap(photo);*/
            //layout_imageview_1.setVisibility(View.GONE);
            // im_cemara_icon.setVisibility(View.VISIBLE);
            imagepath1 = "";
            //Uri tempUri = getImageUri(getApplicationContext(), photo);

            imagepath1 = selectedImagePath;
            resizeImage(selectedImagePath);
            Log.e("image path", imagepath1);
        } else if (requestCode == CAMERA_DIRECT && resultCode == RESULT_OK) {
            String selectedImagePath = getImagePath();

            Bitmap photo = decodeFile(selectedImagePath);


            //Bitmap photo = (Bitmap) data.getExtras().get("data");
            im_direct_cemara1.setVisibility(View.VISIBLE);
            im_direct_cemara1.setImageBitmap(photo);
            //layout_imageview_1.setVisibility(View.GONE);
            // im_cemara_icon.setVisibility(View.VISIBLE);
            imagepath2 = "";
            //Uri tempUri = getImageUri(getApplicationContext(), photo);

            imagepath2 = selectedImagePath;
            resizeImage(selectedImagePath);
            Log.e("image path2", imagepath2);
        }

    }

    public void resizeImage(String path) {
        Bitmap image2 = decodeFile(path);
        File file = new File(path);
        try {
            double xFactor = 0;
            double width = Double.valueOf(image2.getWidth());
            Log.v("WIDTH", String.valueOf(width));
            double height = Double.valueOf(image2.getHeight());
            Log.v("height", String.valueOf(height));
            if (width > height) {
                xFactor = 841 / width;
            } else {
                xFactor = 595 / width;
            }


            Log.v("Nheight", String.valueOf(width * xFactor));
            Log.v("Nweight", String.valueOf(height * xFactor));
            int Nheight = (int) ((xFactor * height));
            int NWidth = (int) (xFactor * width);

            Bitmap bm = Bitmap.createScaledBitmap(image2, NWidth, Nheight, true);
            file.createNewFile();
            FileOutputStream ostream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, ostream);

            final View view = getLayoutInflater().inflate(R.layout.layout_subimage_add, null);
            ImageView img_attachment = (ImageView) view.findViewById(R.id.img_attachment);
            ImageView img_remove = (ImageView) view.findViewById(R.id.img_remove);
            TextView txt_imagePath = (TextView) view.findViewById(R.id.txt_imagePath);

            img_attachment.setImageBitmap(bm);
            txt_imagePath.setText(path);


            img_attachment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showFullImageView();
                }
            });

            img_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layout_direct_cemara.removeView(view);
                }
            });

            layout_direct_cemara.addView(view);

            ostream.close();
        } catch (Exception e) {

        }
    }

    private void showFullImageView() {

        ArrayList<String> imagePaths = new ArrayList<>();

        for (int i = 0; i < layout_direct_cemara.getChildCount(); i++) {
            View view = layout_direct_cemara.getChildAt(i);
            String path = ((TextView) view.findViewById(R.id.txt_imagePath)).getText().toString();
            imagePaths.add(path);
        }

        Intent intent = new Intent(getApplicationContext(), FullZoomViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putStringArrayListExtra("imagePaths", imagePaths);
        startActivity(intent);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    public class tadaeditrepotfinal extends AsyncTask<Void, Void, String> {

        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new ProgressActivity(edit_report.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {

                String date;
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
                date = df.format(c.getTime());

                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("report_id", reportID));
                Log.e("report_id", reportID);

                parameters.add(new BasicNameValuePair("com_sales_person_id",prefs.getUser_id()));
                parameters.add(new BasicNameValuePair("report_date" , reportDate));
                Log.e("params", parameters.toString());

                // parameters.add(new BasicNameValuePair("password",password));


                // Log.e("","" + login_arry);

                String json = new ServiceHandler().makeServiceCall(Web.LINK + Web.EDIT_TADA_REPORT, ServiceHandler.POST, parameters);

                Log.e("json", "->" + json);

                return json;


            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error: " + e.toString());

                Log.e("Exception", e.getMessage());

                return null;
            }


        }


        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            loadingView.dismiss();
            Log.e("myJSON", "->" + json);

            if (json == null || json.isEmpty()) {
                //new meeting_check().execute();
                Toast.makeText(edit_report.this, "Server Error Occured\nPlease Try Again Later!", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("json", json);

                try {

                    JSONObject object = new JSONObject(json);

                    boolean status = object.getBoolean("status");


                    if (status) {
                        String Message = object.getString("message");

                        Toast.makeText(edit_report.this, "" + Message, Toast.LENGTH_LONG).show();
                        JSONObject dataobject = object.getJSONObject("data");
                        JSONObject Hqmasterobject = dataobject.getJSONObject("TADAReport");

                        bean_editTadaReport.setId(Hqmasterobject.getString("id"));
                        bean_editTadaReport.setCom_sales_person_id(Hqmasterobject.getString("com_sales_person_id"));
                        bean_editTadaReport.setReport_date(Hqmasterobject.getString("report_date"));


                        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy", Locale.getDefault());
                        SimpleDateFormat sdfServer = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault());

                        ed_fromdate.setText(sdf.format(sdfServer.parse(bean_editTadaReport.getReport_date())));


                        //ed_fromdate.setText(bean_editTadaReport.getReport_date());
                        ed_fromdate.setEnabled(false);
                        bean_editTadaReport.setFrom_location(Hqmasterobject.getString("from_location"));
                        bean_editTadaReport.setTo_location(Hqmasterobject.getString("to_location"));
                        bean_editTadaReport.setTravelling_cost(Hqmasterobject.getString("travelling_cost"));
                        if (bean_editTadaReport.getTravelling_cost() == null || bean_editTadaReport.getTravelling_cost().equalsIgnoreCase("null") || bean_editTadaReport.getTravelling_cost().isEmpty()) {

                            edt_travel.setText("");
                        } else {

                            edt_travel.setText(bean_editTadaReport.getTravelling_cost());
                        }


                        bean_editTadaReport.setLoading_boarding(Hqmasterobject.getString("loading_boarding"));


                        bean_editTadaReport.setMisc_expenses(Hqmasterobject.getString("misc_expenses"));
                        if (bean_editTadaReport.getMisc_expenses() == null || bean_editTadaReport.getMisc_expenses().equalsIgnoreCase("null") || bean_editTadaReport.getMisc_expenses().isEmpty()) {
                            edt_miscexpenses.setText("");

                        } else {
                            edt_miscexpenses.setText(bean_editTadaReport.getMisc_expenses());
                        }
                        bean_editTadaReport.setIs_extra_hq(Hqmasterobject.getString("is_extra_hq"));
                        bean_editTadaReport.setComment(Hqmasterobject.getString("comment"));
                        edt_comment.setText(bean_editTadaReport.getComment());


                        JSONArray TADAAttachmentReport = dataobject.getJSONArray("TADAAttachmentReport");

                        ArrayList<String> attachments = new ArrayList<>();

                        for (int i = 0; i < TADAAttachmentReport.length(); i++) {

                            JSONObject attachment = TADAAttachmentReport.getJSONObject(i);

                            attachments.add(Web.IMAGELINK + attachment.getString("attachment"));
                            bean_editTadaReport.setAttachment(Web.IMAGELINK + attachment.getString("attachment"));

                        }

                        bean_editTadaReport.setAttachmentpath(attachments);

                        JSONObject masterobject = dataobject.getJSONObject("HQMaster");

                        bean_editTadaReport.setHqmasterid(masterobject.getString("id"));
                        bean_editTadaReport.setCom_sales_person_id(masterobject.getString("com_sales_person_id"));
                        bean_editTadaReport.setState_id(masterobject.getString("state_id"));
                        bean_editTadaReport.setCity_id(masterobject.getString("city_id"));
                        bean_editTadaReport.setHq_price(masterobject.getString("hq_price"));

                        edt_spnhq.setText(bean_editTadaReport.getHq_price());
                        edt_spnhq.setEnabled(false);


                        bean_editTadaReport.setMonthly_allowance(masterobject.getString("monthly_allowance"));
                        edt_mobilenet.setText(bean_editTadaReport.getMonthly_allowance());
                        edt_mobilenet.setEnabled(false);


                        JSONObject cityobject = dataobject.getJSONObject("City");
                        bean_editTadaReport.setCity_name(cityobject.getString("name"));
                        Log.e("cityname", bean_editTadaReport.getCity_name());
                        edt_hq.setText(bean_editTadaReport.getCity_name());
                        edt_hq.setEnabled(false);

                        JSONObject stateobject = dataobject.getJSONObject("State");
                        bean_editTadaReport.setState_name(stateobject.getString("name"));

                        JSONObject userobject = dataobject.getJSONObject("User");
                        bean_editTadaReport.setFirst_name(userobject.getString("first_name"));

                        bean_editTadaReport.setLast_name(userobject.getString("last_name"));


                        edt_to.setText(bean_editTadaReport.getFirst_name() + " " + bean_editTadaReport.getLast_name());
                        edt_to.setEnabled(false);
                        Log.e("name", bean_editTadaReport.getFirst_name());

                        JSONArray hqassign = dataobject.getJSONArray("ExtraHqAssign");

                        onlyhqCityIDs.add("0");
                        onlyhqCityNames.add("HQ");


                        allCityIDs.add("0");
                        allCityNames.add("HQ");

                        allCityIDs.add("1");
                        allCityNames.add("Ex. HQ");

                        for (int i = 0; i < hqassign.length(); i++) {


                            JSONObject main = hqassign.getJSONObject(i);

                            Bean_ExtraHqMaster extraHqMaster = new Bean_ExtraHqMaster();


                            extraHqMaster.setId(main.getString("id"));
                            extraHqMaster.setHq_id(main.getString("hq_id"));
                            extraHqMaster.setCity_id(main.getString("city_id"));
                            if (main.getString("extra_hq_price").equalsIgnoreCase("null") || main.getString("extra_hq_price").isEmpty()) {

                                extraHqMaster.setExtra_hq_price("0");
                            } else {

                                extraHqMaster.setExtra_hq_price(main.getString("extra_hq_price"));
                            }
                            if (main.getString("loading_boarding_price").equalsIgnoreCase("null") || main.getString("loading_boarding_price").isEmpty()) {

                                extraHqMaster.setLoading_boarding_price("0");

                            } else {

                                extraHqMaster.setLoading_boarding_price(main.getString("loading_boarding_price"));
                            }


                            /*extraHqMaster.setCreated(Hqmasterobject.getString("created"));
                            extraHqMaster.setModified(Hqmasterobject.getString("modified"));
*/

                            JSONObject cityobjectt = main.getJSONObject("City");

                            allCityIDs.add(cityobjectt.getString("id"));
                            allCityNames.add(cityobjectt.getString("name"));
                            onlyCityIDs.add(cityobjectt.getString("id"));
                            onlyCityNames.add(cityobjectt.getString("name"));
                            onlyhqCityIDs.add(cityobjectt.getString("id"));
                            onlyhqCityNames.add(cityobjectt.getString("name"));

                            extraHqMaster.setCity_name(cityobjectt.getString("name"));

                            extraHqMasterList.add(extraHqMaster);


                        }

                        allCityAdapter.notifyDataSetChanged();
                        onlyCityAdapter.notifyDataSetChanged();
                        onlyhqAdaptet.notifyDataSetChanged();


                        Log.e("is_extra_hq", bean_editTadaReport.getIs_extra_hq());

                        if (bean_editTadaReport.getIs_extra_hq().equalsIgnoreCase("1")) {
                            spinner.setSelection(1, true);

                            int index = onlyCityNames.indexOf(bean_editTadaReport.getTo_location());
                            spinnerafterselect.setSelection(index, true);

                        } else {
                            if (bean_editTadaReport.getCity_name().equalsIgnoreCase(bean_editTadaReport.getTo_location())) {
                                spinner.setSelection(0, true);
                            } else {
                                int index = onlyCityNames.indexOf(bean_editTadaReport.getTo_location());
                                spinner.setSelection(index + 2, true);
                            }
                        }

                        if (bean_editTadaReport.getCity_name().equalsIgnoreCase(bean_editTadaReport.getFrom_location())) {
                            spinner_from.setSelection(0, true);
                        } else {
                            int index = onlyCityNames.indexOf(bean_editTadaReport.getFrom_location());
                            spinner_from.setSelection(index + 1, true);
                        }

                        for (int i = 0; i < bean_editTadaReport.getAttachmentpath().size(); i++) {
                            View view = getLayoutInflater().inflate(R.layout.layout_subimage_add, null);
                            ImageView img_attachment = (ImageView) view.findViewById(R.id.img_attachment);
                            ImageView img_remove = (ImageView) view.findViewById(R.id.img_remove);
                            TextView txt_imagePath = (TextView) view.findViewById(R.id.txt_imagePath);

                            img_remove.setVisibility(View.GONE);

                            img_attachment.setMaxWidth(50);
                            img_attachment.setMaxHeight(50);

                            Picasso.with(getApplicationContext())
                                    .load(bean_editTadaReport.getAttachmentpath().get(i))
                                    .into(img_attachment);

                            txt_imagePath.setText(bean_editTadaReport.getAttachmentpath().get(i));

                            img_attachment.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ArrayList<String> imagePaths = new ArrayList<>();

                                    for (int i = 0; i < layout_direct_cemara.getChildCount(); i++) {
                                        View view = layout_direct_cemara.getChildAt(i);
                                        String path = ((TextView) view.findViewById(R.id.txt_imagePath)).getText().toString();
                                        imagePaths.add(path);
                                    }

                                    Intent intent = new Intent(getApplicationContext(), FullZoomViewActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putStringArrayListExtra("imagePaths", imagePaths);
                                    startActivity(intent);
                                }
                            });
                            layout_direct_cemara.addView(view);
                        }


                    } else {


                        String Message = object.getString("message");
                        Toast.makeText(edit_report.this, "" + Message, Toast.LENGTH_LONG).show();
                        Intent i = new Intent(edit_report.this, HomeActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();

                    }
                } catch (JSONException j) {
                    j.printStackTrace();
                    Log.e("excePtioN", j.getMessage());
                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.e("excePtioN Dt", e.getMessage());
                }

            }

        }


    }

    private class SubmitTADA extends AsyncTask<Void, Void, String> {

        List<NameValuePair> params = new ArrayList<>();




        public SubmitTADA(List<NameValuePair> params) {
            this.params = params;
            loadingView = new ProgressActivity(edit_report.this, "");
            loadingView.setCancelable(false);
            loadingView.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            String json = new ServiceHandler().makeServiceCall(Web.LINK + "TADAReport/App_Get_TADA_Report_Update", ServiceHandler.POST, this.params, 1);

            return json;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            loadingView.dismiss();


            Log.e("JSON", "" + json);

            Toast.makeText(edit_report.this, "TADA Report Updated Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(edit_report.this, EditTADAReport.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        }
    }


}
