package com.nivida.bossb2b.Bean;

import com.google.gson.annotations.SerializedName;

public class BeanVendor {


    public BeanVendor() {

    }

    @SerializedName("User")
    User user;

    @SerializedName("Distributor")
    Distributor distributor;

    @SerializedName("Address")
    Address address;


    String vendor_id = "";
    String user_id = "";
    String company_name = "";
    String contact_person_name = "";
    String designation = "";
    String email_id = "";
    String mobile_no = "";
    String first_name = "";
    String Phone_no = "";
    String alternate_no = "";
    String pincode = "";
    String city = "";
    String area = "";
    String addressString = "";


    String id = "";
    String role_id = "";

    String user_name = "";


    public String getFirst_name() {
        return user.getFirstName();
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole_id() {
        return user.getRoleID();
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getUser_name() {
        return user.getUserName();
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone_no() {
        return user.getPhoneNo();
    }

    public void setPhone_no(String phone_no) {
        Phone_no = phone_no;
    }


    public String getVendor_id() {
        return user.getUserid();
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCompany_name() {
        return distributor.getFirmShopName();
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getContact_person_name() {
        return contact_person_name;
    }

    public void setContact_person_name(String contact_person_name) {
        this.contact_person_name = contact_person_name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail_id() {
        return user.getEmailID();
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAlternate_no() {
        return alternate_no;
    }

    public void setAlternate_no(String alternate_no) {
        this.alternate_no = alternate_no;
    }

    public String getAddress() {
        return address.getCity().getName() + " , " + address.getAddress();
    }

    public void setAddress(String address) {
        this.addressString = address;
    }

    public String getPincode() {
        return address.getPincode();
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }


    public static class User {

        @SerializedName("id")
        String userid = "";

        @SerializedName("role_id")
        String roleID = "";

        @SerializedName("user_name")
        String userName = "";

        @SerializedName("email_id")
        String emailID = "";

        @SerializedName("phone_no")
        String phoneNo = "";

        @SerializedName("mobile_no")
        String alternateNo = "";

        @SerializedName("first_name")
        String firstName = "";

        @SerializedName("last_name")
        String lastName = "";

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getRoleID() {
            return roleID;
        }

        public void setRoleID(String roleID) {
            this.roleID = roleID;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getEmailID() {
            return emailID;
        }

        public void setEmailID(String emailID) {
            this.emailID = emailID;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public String getAlternateNo() {
            return alternateNo;
        }

        public void setAlternateNo(String alternateNo) {
            this.alternateNo = alternateNo;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }

    public static class Distributor {
        @SerializedName("firm_shop_name")
        String firmShopName = "";

        public String getFirmShopName() {
            return firmShopName;
        }

        public void setFirmShopName(String firmShopName) {
            this.firmShopName = firmShopName;
        }
    }

    public static class Address {
        @SerializedName("id")
        String addressID = "";

        @SerializedName("address_1")
        String address1 = "";

        @SerializedName("address_2")
        String address2 = "";

        @SerializedName("address_3")
        String address3 = "";

        @SerializedName("pincode")
        String pincode = "";

        @SerializedName("city_id")
        String city_id = "";


        @SerializedName("City")
        City city;


        public String getAddressID() {
            return addressID;
        }

        public void setAddressID(String addressID) {
            this.addressID = addressID;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getAddress() {
            return address1 + "" /*+ address2 + ""*/ /*+ address3*/;
        }

        public void setAddress(String address) {
            this.address3 = address;
        }


        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public City getCity() {
            return city;
        }

        public void setCity(City city) {
            this.city = city;
        }


    }

    public static class City {


        String name = "";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDistributor(Distributor distributor) {
        this.distributor = distributor;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
