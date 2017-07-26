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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nivida.bossb2b.Bean.Bean_Set_Product_Categeory;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dhruvil Bhosle on 25-11-2016.
 */
public class SetSubCategeoryAdapter extends BaseAdapter {

    List<Bean_Set_Product_Categeory> product_categeories = new ArrayList<>();

    Context context;

    Database db;

    OnItemChecked onItemChecked;

    AppPref pref;

    public SetSubCategeoryAdapter(Context context, List<Bean_Set_Product_Categeory> product_categeories) {
        this.product_categeories = product_categeories;
        this.context = context;
    }

    public SetSubCategeoryAdapter(Context context, List<Bean_Set_Product_Categeory> product_categeories, OnItemChecked onItemChecked) {
        this.product_categeories = product_categeories;
        this.context = context;
        this.onItemChecked = onItemChecked;
        pref = new AppPref(context);
    }

    @Override
    public int getCount() {
        return product_categeories.size();
    }

    @Override
    public Object getItem(int position) {
        return product_categeories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final View view;

        final ImageView img_plus, img_minus;
        final EditText edt_qut;
        TextView txt_outofstock, categeory_name;

        final Bean_Set_Product_Categeory product_categeory = product_categeories.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_item_details_list, parent, false);

        categeory_name = (TextView) view.findViewById(R.id.categeory_name);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.categeory_checkbox);


        categeory_name.setText(product_categeory.getCode() + " - " + product_categeory.getName());

        db = new Database(context);

        img_plus = (ImageView) view.findViewById(R.id.img_plus);
        img_minus = (ImageView) view.findViewById(R.id.img_minus);
        edt_qut = (EditText) view.findViewById(R.id.edt_quantity);
        txt_outofstock = (TextView) view.findViewById(R.id.txt_outofstock);


        if (pref.getSelectedUserRole().equalsIgnoreCase(C.DISTRIBUTOR_ROLE) && product_categeory.getB2b_stock().equalsIgnoreCase("0")) {
            checkBox.setEnabled(false);
            txt_outofstock.setVisibility(View.VISIBLE);
            img_plus.setVisibility(View.GONE);
            img_minus.setVisibility(View.GONE);
            edt_qut.setVisibility(View.GONE);
        }

        if (db.isInCart(product_categeory)) {
            checkBox.setChecked(true);
            product_categeories.get(position).setChecked(true);
            Bean_Set_Product_Categeory currentProductInCart = db.getCurrentProduct(product_categeory);
            edt_qut.setText(String.valueOf(currentProductInCart.getQuantity()));
            product_categeories.get(position).setQuantity(currentProductInCart.getQuantity());
            product_categeories.get(position).setTotal_price(currentProductInCart.getTotal_price());
        }

        if (checkBox.isChecked()) {
            edt_qut.setEnabled(true);
            img_minus.setClickable(true);
            img_plus.setClickable(true);
            img_plus.setEnabled(true);
            img_minus.setEnabled(true);

        } else {
            edt_qut.setEnabled(false);
            img_plus.setEnabled(false);
            img_minus.setEnabled(false);
        }

        edt_qut.setText(String.valueOf(product_categeories.get(position).getQuantity()));

        //Log.e("quantity",position+"->"+edt_qut.getText().toString());

        checkBox.setChecked(product_categeories.get(position).isChecked());

        Log.e("isChecked", position + "->" + checkBox.isChecked());

        categeory_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppPrefs prefs = new AppPrefs(context);
                prefs.setproduct_id(product_categeories.get(position).getId());
                Intent intent = new Intent(context, Product_Detail.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {
                    img_plus.setEnabled(true);
                    img_minus.setEnabled(true);
                    edt_qut.setText("1");
                    product_categeories.get(position).setQuantity(1);
                    edt_qut.setEnabled(true);
                    img_minus.setClickable(true);
                    img_plus.setClickable(true);
                    db.addToCart(product_categeories.get(position));


                } else {
                    edt_qut.setText("");
                    edt_qut.setHint("0");
                    product_categeories.get(position).setQuantity(0);
                    edt_qut.setEnabled(false);
                    img_plus.setClickable(false);
                    img_minus.setClickable(false);

                    db.removeFromCart(product_categeory.getId());
                }

                product_categeories.get(position).setChecked(isChecked);
                onItemChecked.onItemCheckedListener();

            }
        });

        edt_qut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_qut.setCursorVisible(true);
            }
        });






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

                if(!qty.isEmpty()){
                    int quantity=Integer.parseInt(qty);

                    if(quantity>0){
                        product_categeories.get(position).setQuantity(Integer.parseInt(qty));
                        float totalPrice = Integer.parseInt(product_categeories.get(position).getProduct_selling_price()) *
                                product_categeories.get(position).getQuantity();

                        product_categeories.get(position).setTotal_price(String.valueOf(totalPrice));

                        db.addToCart(product_categeories.get(position));
                    }
                    else {
                        db.removeFromCart(product_categeories.get(position).getId());
                    }
                }
                else {
                    db.removeFromCart(product_categeories.get(position).getId());
                }



                /*if (qty.isEmpty()) {

                    edt_qut.setHint("0");
                    edt_qut.setHintTextColor(Color.parseColor("#396085"));
                    product_categeories.get(position).setQuantity(0);

                } else if (Integer.parseInt(qty) == 0) {
                    edt_qut.setText("1");
                    product_categeories.get(position).setQuantity(1);

                } else {
                    product_categeories.get(position).setQuantity(Integer.parseInt(qty));

                }*/


                //notifyDataSetChanged();


            }


        });

        img_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String currentQty=edt_qut.getText().toString().trim();
                edt_qut.setCursorVisible(false);

                currentQty = currentQty.isEmpty() ? "0" : currentQty;

                int Quantity = Integer.parseInt(currentQty);

                Quantity++;

                product_categeories.get(position).setQuantity(Quantity);
                edt_qut.setText(String.valueOf(Quantity));

                //db.addToCart(product_categeories.get(position));
            }
        });
        img_minus.setOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String currentQty= edt_qut.getText().toString().trim();
                        currentQty = currentQty.isEmpty() ? "0" : currentQty;
                        edt_qut.setCursorVisible(false);
                        int quantity = Integer.parseInt(currentQty);

                        if(quantity>=1){
                            quantity = quantity - 1;
                            product_categeories.get(position).setQuantity(quantity);
                            edt_qut.setText(String.valueOf(quantity));
                        }

                    }
                });





        return view;
    }


    public List<Bean_Set_Product_Categeory> getProducts() {
        List<Bean_Set_Product_Categeory> selectedProducts = new ArrayList<>();
        for (int i = 0; i < product_categeories.size(); i++) {
            if (product_categeories.get(i).isChecked()) {
                selectedProducts.add(product_categeories.get(i));
            }
        }

        return selectedProducts;
    }

    public interface OnItemChecked {
        void onItemCheckedListener();
    }
}
