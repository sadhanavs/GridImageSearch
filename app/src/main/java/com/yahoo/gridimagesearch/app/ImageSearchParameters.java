package com.yahoo.gridimagesearch.app;

import java.io.Serializable;

/**
 * Created by sadhanas on 6/16/14.
 */
public class ImageSearchParameters implements Serializable {

    private static final long serialVersionUID = 5177222050535318633L;

    private String size;
    private String type;
    private String color;
    private String query;
    private String domain;
    private String defaultParams;

    public String getSize() {
        return size;
    }

    public ImageSearchParameters() {
        defaultParams="rsz=8&v=1.0";
        color="black";
        size="large";
        domain="";
        type="photo";
        query="";

    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }


    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDomain() {
        return this.domain;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(defaultParams+"&imgc="+color+"&imgcolor="+color+"&imgsz="+size+"&imgtype="+type+"&as_sitesearch="+domain+"&q="+query);
        return builder.toString();
    }
}
