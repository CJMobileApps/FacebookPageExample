
package com.cjfreelancing.facebookexample.models;

import java.util.List;

public class Images {


    private List<Image> images = null;

    private String id;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
