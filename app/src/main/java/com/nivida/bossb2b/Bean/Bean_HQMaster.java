package com.nivida.bossb2b.Bean;

import java.util.ArrayList;

/**
 * Created by Ajay on 12/30/2016.
 */

public class Bean_HQMaster {

    String id ="";
    String com_sales_person_id ="";
    String state_id ="";
    String city_id="";
    String hq_price="";
    String  monthly_allowance="";
    String sort_order="";
    String status ="";
    String created="";
    String modified="";
    String city_name="";
    String state_name="";
    String first_name="";
    String last_name="";
    String comment="";
    String report_date="";
    String from_location="";
    String to_location="";
    String travelling_cost="";
    String loading_boarding="";
    String misc_expenses="";
    String is_extra_hq="";
    String attachment="";
    ArrayList<String> attachmentpath = new ArrayList<>();

    public ArrayList<String> getAttachmentpath() {
        return attachmentpath;
    }

    public void setAttachmentpath(ArrayList<String> attachmentpath) {
        this.attachmentpath = attachmentpath;
    }


    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }



    public String getReport_date() {
        return report_date;
    }

    public void setReport_date(String report_date) {
        this.report_date = report_date;
    }

    public String getFrom_location() {
        return from_location;
    }

    public void setFrom_location(String from_location) {
        this.from_location = from_location;
    }

    public String getTo_location() {
        return to_location;
    }

    public void setTo_location(String to_location) {
        this.to_location = to_location;
    }

    public String getTravelling_cost() {
        return travelling_cost;
    }

    public void setTravelling_cost(String travelling_cost) {
        this.travelling_cost = travelling_cost;
    }

    public String getLoading_boarding() {
        return loading_boarding;
    }

    public void setLoading_boarding(String loading_boarding) {
        this.loading_boarding = loading_boarding;
    }

    public String getMisc_expenses() {
        return misc_expenses;
    }

    public void setMisc_expenses(String misc_expenses) {
        this.misc_expenses = misc_expenses;
    }

    public String getIs_extra_hq() {
        return is_extra_hq;
    }

    public void setIs_extra_hq(String is_extra_hq) {
        this.is_extra_hq = is_extra_hq;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCom_sales_person_id() {
        return com_sales_person_id;
    }

    public void setCom_sales_person_id(String com_sales_person_id) {
        this.com_sales_person_id = com_sales_person_id;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getHq_price() {
        return hq_price;
    }

    public void setHq_price(String hq_price) {
        this.hq_price = hq_price;
    }

    public String getMonthly_allowance() {
        return monthly_allowance;
    }

    public void setMonthly_allowance(String monthly_allowance) {
        this.monthly_allowance = monthly_allowance;
    }

    public String getSort_order() {
        return sort_order;
    }

    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}
