package com.yahoo.gridimagesearch.app;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.jar.JarException;

/**
 * Created by sadhanas on 6/14/14.
 */
public class ImageResult implements Serializable{
    private static final long serialVersionId=3423545667123455L;
    private String fullUrl;
    private  String thumbUrl;

    public ImageResult(JSONObject json) {
        try {
            this.fullUrl = json.getString("url");
            this.thumbUrl = json.getString("tbUrl");


        } catch (JSONException e){
            this.fullUrl = null;
            this.thumbUrl = null;
        }
    }

    public String toString() {
        return this.thumbUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }



    public String getFullUrl() {
        return fullUrl;
    }



    public static ArrayList<ImageResult > fromJSONArray( JSONArray array){
        ArrayList<ImageResult> results = new ArrayList<ImageResult>();
        for( int x =0; x < array.length();x++) {
            try {
                results.add(new ImageResult(array.getJSONObject(x)));
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return results;
    }

}
