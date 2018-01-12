package com.nivida.bossb2b.Bean;

import com.nivida.bossb2b.Model.AppModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dhruvil Bhosle on 22-11-2016.
 */
public class Bean_Categeory extends AppModel {



        String id = "";
        String name = "";
        String app_image = "";
        int is_child = 0 ;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getApp_image() {
            return app_image;
        }

        public void setApp_image(String app_image) {
            this.app_image = app_image;
        }


        public int getIs_child() {
            return is_child;
        }

        public void setIs_child(int is_child) {
            this.is_child = is_child;
        }




}
