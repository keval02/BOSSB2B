package com.nivida.bossb2b;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nivida.bossb2b.Bean.Bean_Invoice_List;
import com.nivida.bossb2b.Bean.Bean_Set_Product_Categeory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dhruvil Bhosle on 28-11-2016.
 */
public class Custom_Invoice_Adapter extends BaseAdapter {


    List<Bean_Set_Product_Categeory> set_product_categeories = new ArrayList<>();

    Context context;

    Database db;

    public Custom_Invoice_Adapter(Context context, List<Bean_Set_Product_Categeory> set_product_categeories) {
        this.set_product_categeories = set_product_categeories;
        this.context = context;
        db = new Database(context);
    }

    @Override
    public int getCount() {
        return set_product_categeories.size();
    }

    @Override
    public Object getItem(int position) {
        return set_product_categeories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view;
        final Bean_Set_Product_Categeory set_product_categeory = set_product_categeories.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_invoice_listview, parent, false);

        TextView txt_cat_name, txt_product_name, txt_product_code, sr_no;
        final EditText edt_qut;
        ImageView delete;

        txt_cat_name = (TextView) view.findViewById(R.id.txt_categeory_name);
        txt_product_name = (TextView) view.findViewById(R.id.txt_product_name);
        txt_product_code = (TextView) view.findViewById(R.id.txt_product_code);
        edt_qut = (EditText) view.findViewById(R.id.edt_invoice_quantity);
        sr_no = (TextView) view.findViewById(R.id.txt_srno);
        sr_no.setText(String.valueOf(position + 1) + ".   ");
        txt_cat_name.setText(set_product_categeory.getCategeory_name());
        txt_product_name.setText(set_product_categeory.getCode() + " - " + set_product_categeory.getName());
        //txt_product_code.setText(set_product_categeory.getCode()+" -");
        edt_qut.setText(String.valueOf(set_product_categeory.getQuantity()));
        delete = (ImageView) view.findViewById(R.id.img_delete);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Internet.isConnectingToInternet(context)) {


                    AppPrefs prefs = new AppPrefs(context);
                prefs.setproduct_id(set_product_categeories.get(position).getId());
                Intent intent = new Intent(context, Product_Detail.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                } else {
                    Internet.noInternet(context);
                }

            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                db.removeFromCart(set_product_categeories.get(position).getId());
                notifyDataSetChanged();
                Toast.makeText(context, "Product is Deleted from cart", Toast.LENGTH_SHORT).show();

                if(db.getCartCount()<=0){


                    Toast.makeText(context,"There is No Product in Cart", Toast.LENGTH_LONG).show();



                }
                //set_product_categeories.clear();

                //db.delete_product_from_cart_list(product_code.getTag().toString(),bean_cart.get(position).getPro_Option_name(),bean_cart.get(position).getPro_Option_value_name());
                //bean_cart.clear();
                //bean_cart = db.Get_Product_Cart();


                    /*adapter.notifyDataSetChanged();
                   *//* db = new DatabaseHandler(BTL_Cart.this);
                    bean_cart =  db.Get_Product_Cart();*//*



                    grand_total = 0;

                    for (int i = 0; i < bean_cart.size(); i++) {
                        array_total_price.add(Float.parseFloat(bean_cart.get(i).getPro_total().replace("","")));

                        grand_total = grand_total + array_total_price.get(i);

                      Grandtotal = String.format("%.2f", grand_total);

                    }

                    tv_grand_total.setText("" + Grandtotal);*/
            }
        });

/*
        edt_qut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt_qut.isClickable()){

                    edt_qut.setText("1");
                }
                else{

                    edt_qut.setHint("0");
                    edt_qut.setHintTextColor(Color.parseColor("#396085"));


                }
            }
        });

       edt_qut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qty = edt_qut.getText().toString().trim();

                if(qty == "0"){

                    edt_qut.setText("1");
                    set_product_categeories.get(position).setQuantity(1);
                 edt_qut.setHintTextColor(Color.parseColor("#396085"));

                }
                else{


                    edt_qut.setText("1");

                }
            }
        });*/


        edt_qut.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String qty = edt_qut.getText().toString().trim();

                set_product_categeories.get(position).setQuantity(Integer.parseInt(qty.isEmpty() ? "0" : qty));

                if(qty.isEmpty()){

                    edt_qut.setHint("0");
                    edt_qut.setHintTextColor(Color.parseColor("#396085"));
                    edt_qut.setTextColor(Color.parseColor("#396085"));


                }


                if (!qty.isEmpty()) {

                    if (!(Integer.parseInt(qty) <= 0)) {
                        float totalPrice = Integer.parseInt(set_product_categeories.get(position).getProduct_selling_price()) *
                                set_product_categeories.get(position).getQuantity();

                        Log.e("Enter", set_product_categeories.get(position).getQuantity() + " - - " + totalPrice);
                        set_product_categeories.get(position).setTotal_price(String.valueOf(totalPrice));
                        db.updateToCart(set_product_categeories.get(position));
                    }
                }
            }


        });


        return view;
    }


    @Override
    public void notifyDataSetChanged() {
        set_product_categeories = db.getCartData();
        super.notifyDataSetChanged();
    }

    public List<Bean_Set_Product_Categeory> getProducts() {
        List<Bean_Set_Product_Categeory> selectedProducts = new ArrayList<>();
        for (int i = 0; i < set_product_categeories.size(); i++) {
            if (set_product_categeories.get(i).isChecked()) {
                selectedProducts.add(set_product_categeories.get(i));
            }
        }

        return selectedProducts;
    }

    public boolean isAnyBlankQty(){
        boolean isBlankQty=false;

        for(int i=0; i<set_product_categeories.size(); i++){
            if(set_product_categeories.get(i).getQuantity()<=0){
                isBlankQty=true;
                break;
            }
        }

        return isBlankQty;
    }
}
