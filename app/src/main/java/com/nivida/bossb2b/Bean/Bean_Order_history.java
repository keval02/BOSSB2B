package com.nivida.bossb2b.Bean;

import java.io.Serializable;

/**
 * Created by prince on 5/2/2016.
 */
public class Bean_Order_history implements Serializable {

    String id;
    String pro_category_id="";
    String order_id;
    String invoice_no;
    String invoice_prefix;
    String user_id;
    String role_id;
    String billing_address_id;
    String shipping_address_id;
    String first_name;
    String last_name;
    String email;
    String mobile;
    String payment_name;
    String payment_address;
    String payment_method;
    String payment_code;
    String shipping_name;
    String shipping_address;
    String shipping_cost;
    String comment;
    String total;
    String order_status_is;
    String order_pdf;
    String product_id;
    String product_name;
    String product_code;
    String product_qty;
    String product_selling_price;
    String product_total;
    String product_option_name;
    String product_option_id;
    String product_value_name;
    String product_value_id;
    String order_date;
    String owner_id="";
    String attachment="";
    String scheme_info="";
    boolean hasCouponApplied=false;
    String coupon_code="";
    String coupon_name="";
    String firm_shop_name="";

    public String getFirm_shop_name() {
        return firm_shop_name;
    }

    public void setFirm_shop_name(String firm_shop_name) {
        this.firm_shop_name = firm_shop_name;
    }


    int Quantity = 0;

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getOwnerFirstName() {
        return OwnerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        OwnerFirstName = ownerFirstName;
    }

    public String getOwnerLastName() {
        return OwnerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        OwnerLastName = ownerLastName;
    }

    String discount_amount="";
    String OwnerFirstName="";
    String OwnerLastName="";


    public Bean_Order_history() {
    }

    public Bean_Order_history(String id, String product_name, String product_code, String product_qty, String product_selling_price, String product_total, String product_option_name, String product_value_name) {
        this.id = id;
        this.product_name = product_name;
        this.product_code = product_code;
        this.product_qty = product_qty;
        this.product_selling_price = product_selling_price;
        this.product_total = product_total;
        this.product_option_name = product_option_name;
        this.product_value_name = product_value_name;
    }

    public Bean_Order_history(String id, String order_id, String invoice_no, String invoice_prefix, String user_id, String role_id, String billing_address_id, String shipping_address_id, String first_name, String last_name, String email, String mobile, String payment_name, String payment_address, String payment_method, String payment_code, String shipping_name, String shipping_address, String shipping_cost, String comment, String total, String order_status_is, String order_pdf, String product_name, String product_code, String product_qty, String product_selling_price, String product_total, String product_option_name, String product_value_name) {
        this.id = id;
        this.order_id = order_id;
        this.invoice_no = invoice_no;
        this.invoice_prefix = invoice_prefix;
        this.user_id = user_id;
        this.role_id = role_id;
        this.billing_address_id = billing_address_id;
        this.shipping_address_id = shipping_address_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.mobile = mobile;
        this.payment_name = payment_name;
        this.payment_address = payment_address;
        this.payment_method = payment_method;
        this.payment_code = payment_code;
        this.shipping_name = shipping_name;
        this.shipping_address = shipping_address;
        this.shipping_cost = shipping_cost;
        this.comment = comment;
        this.total = total;
        this.order_status_is = order_status_is;
        this.order_pdf = order_pdf;
        this.product_name = product_name;
        this.product_code = product_code;
        this.product_qty = product_qty;
        this.product_selling_price = product_selling_price;
        this.product_total = product_total;
        this.product_option_name = product_option_name;
        this.product_value_name = product_value_name;
    }

    public Bean_Order_history(String id, String order_id, String invoice_no, String invoice_prefix, String user_id, String role_id, String billing_address_id, String shipping_address_id, String first_name, String last_name, String email, String mobile, String payment_name, String payment_address, String payment_method, String payment_code, String shipping_name, String shipping_address, String shipping_cost, String comment, String total, String order_status_is, String order_pdf, String product_id, String product_name, String product_code, String product_qty, String product_selling_price, String product_total, String product_option_name, String product_value_name) {
        this.id = id;
        this.order_id = order_id;
        this.invoice_no = invoice_no;
        this.invoice_prefix = invoice_prefix;
        this.user_id = user_id;
        this.role_id = role_id;
        this.billing_address_id = billing_address_id;
        this.shipping_address_id = shipping_address_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.mobile = mobile;
        this.payment_name = payment_name;
        this.payment_address = payment_address;
        this.payment_method = payment_method;
        this.payment_code = payment_code;
        this.shipping_name = shipping_name;
        this.shipping_address = shipping_address;
        this.shipping_cost = shipping_cost;
        this.comment = comment;
        this.total = total;
        this.order_status_is = order_status_is;
        this.order_pdf = order_pdf;
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_code = product_code;
        this.product_qty = product_qty;
        this.product_selling_price = product_selling_price;
        this.product_total = product_total;
        this.product_option_name = product_option_name;
        this.product_value_name = product_value_name;
    }

    public Bean_Order_history(String id, String order_id, String invoice_no, String invoice_prefix, String user_id, String role_id, String billing_address_id, String shipping_address_id, String first_name, String last_name, String email, String mobile, String payment_name, String payment_address, String payment_method, String payment_code, String shipping_name, String shipping_address, String shipping_cost, String comment, String total, String order_status_is, String order_pdf, String product_id, String product_name, String product_code, String product_qty, String product_selling_price, String product_total, String product_option_name, String product_value_name, String order_date) {
        this.id = id;
        this.order_id = order_id;
        this.invoice_no = invoice_no;
        this.invoice_prefix = invoice_prefix;
        this.user_id = user_id;
        this.role_id = role_id;
        this.billing_address_id = billing_address_id;
        this.shipping_address_id = shipping_address_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.mobile = mobile;
        this.payment_name = payment_name;
        this.payment_address = payment_address;
        this.payment_method = payment_method;
        this.payment_code = payment_code;
        this.shipping_name = shipping_name;
        this.shipping_address = shipping_address;
        this.shipping_cost = shipping_cost;
        this.comment = comment;
        this.total = total;
        this.order_status_is = order_status_is;
        this.order_pdf = order_pdf;
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_code = product_code;
        this.product_qty = product_qty;
        this.product_selling_price = product_selling_price;
        this.product_total = product_total;
        this.product_option_name = product_option_name;
        this.product_value_name = product_value_name;
        this.order_date = order_date;
    }

    public String getPro_category_id() {
        return pro_category_id;
    }

    public void setPro_category_id(String pro_category_id) {
        this.pro_category_id = pro_category_id;
    }

    public String getProduct_option_id() {
        return product_option_id;
    }

    public void setProduct_option_id(String product_option_id) {
        this.product_option_id = product_option_id;
    }

    public String getProduct_value_id() {
        return product_value_id;
    }

    public void setProduct_value_id(String product_value_id) {
        this.product_value_id = product_value_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getProduct_qty() {
        return product_qty;
    }

    public void setProduct_qty(String product_qty) {
        this.product_qty = product_qty;
    }

    public String getProduct_selling_price() {
        return product_selling_price;
    }

    public void setProduct_selling_price(String product_selling_price) {
        this.product_selling_price = product_selling_price;
    }

    public String getProduct_total() {
        return product_total;
    }

    public void setProduct_total(String product_total) {
        this.product_total = product_total;
    }

    public String getProduct_option_name() {
        return product_option_name;
    }

    public void setProduct_option_name(String product_option_name) {
        this.product_option_name = product_option_name;
    }

    public String getProduct_value_name() {
        return product_value_name;
    }

    public void setProduct_value_name(String product_value_name) {
        this.product_value_name = product_value_name;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getInvoice_no() {
        return invoice_no;
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    public String getInvoice_prefix() {
        return invoice_prefix;
    }

    public void setInvoice_prefix(String invoice_prefix) {
        this.invoice_prefix = invoice_prefix;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getBilling_address_id() {
        return billing_address_id;
    }

    public void setBilling_address_id(String billing_address_id) {
        this.billing_address_id = billing_address_id;
    }

    public String getShipping_address_id() {
        return shipping_address_id;
    }

    public void setShipping_address_id(String shipping_address_id) {
        this.shipping_address_id = shipping_address_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPayment_name() {
        return payment_name;
    }

    public void setPayment_name(String payment_name) {
        this.payment_name = payment_name;
    }

    public String getPayment_address() {
        return payment_address;
    }

    public void setPayment_address(String payment_address) {
        this.payment_address = payment_address;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getPayment_code() {
        return payment_code;
    }

    public void setPayment_code(String payment_code) {
        this.payment_code = payment_code;
    }

    public String getShipping_name() {
        return shipping_name;
    }

    public void setShipping_name(String shipping_name) {
        this.shipping_name = shipping_name;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public String getShipping_cost() {
        return shipping_cost;
    }

    public void setShipping_cost(String shipping_cost) {
        this.shipping_cost = shipping_cost;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getOrder_status_is() {
        return order_status_is;
    }

    public void setOrder_status_is(String order_status_is) {
        this.order_status_is = order_status_is;
    }

    public String getOrder_pdf() {
        return order_pdf;
    }

    public void setOrder_pdf(String order_pdf) {
        this.order_pdf = order_pdf;
    }


    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getScheme_info() {
        return scheme_info;
    }

    public void setScheme_info(String scheme_info) {
        this.scheme_info = scheme_info;
    }

    public boolean isHasCouponApplied() {
        return hasCouponApplied;
    }

    public void setHasCouponApplied(boolean hasCouponApplied) {
        this.hasCouponApplied = hasCouponApplied;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public String getCoupon_name() {
        return coupon_name;
    }

    public void setCoupon_name(String coupon_name) {
        this.coupon_name = coupon_name;
    }

    public String getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(String discount_amount) {
        this.discount_amount = discount_amount;
    }

    public void setQuantity(String quantity) {
    }
}
