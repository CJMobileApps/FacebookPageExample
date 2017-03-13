package com.cjfreelancing.facebookexample.models;

import java.util.List;


public class Photos {


    private List<Data> data = null;

    private Paging paging;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

}

