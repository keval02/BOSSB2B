package com.nivida.bossb2b.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NWSPL-17 on 2/2/2017.
 */
public class BeanShowHierarchy {


    boolean isOpen=false;

    String firm_shop_name = "";

    List<BeanReatailerHierarchy> reatailerHierarchies=new ArrayList<>();


    public BeanShowHierarchy() {
    }

    public String getFirm_shop_name() {
        return firm_shop_name;
    }

    public void setFirm_shop_name(String firm_shop_name) {
        this.firm_shop_name = firm_shop_name;
    }

    public List<BeanReatailerHierarchy> getReatailerHierarchies() {
        return reatailerHierarchies;
    }

    public void setReatailerHierarchies(List<BeanReatailerHierarchy> reatailerHierarchies) {
        this.reatailerHierarchies = reatailerHierarchies;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
