package com.yahoo.gridimagesearch.app;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;

import java.util.ArrayList;


public class GridImageSearchActivity extends Activity {

    EditText    etQuery;
    Button      btSearch;
    GridView    gvResults;
    StringBuilder queryString;
    ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
    ImageResultsArrayAdapter imageAdapter;

    static final int RESULT_CODE=25;
    static final int MAX_PAGE_COUNT=8;

    ImageSearchParameters parameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_image_search);
        parameters = new ImageSearchParameters();
        setupViews();
        imageAdapter = new ImageResultsArrayAdapter(this, imageResults);
        gvResults.setAdapter(imageAdapter);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
                ImageResult imageResult = imageResults.get(position);
                i.putExtra("result", imageResult);
                startActivity(i);
            }
        });

        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == RESULT_CODE && resultCode == RESULT_OK) {
            String size = data.getExtras().getString("size");
            String type = data.getExtras().getString("type");
            String domain = data.getExtras().getString("domain");
            String color = data.getExtras().getString("color");
            parameters.setColor(color);
            parameters.setDomain(domain);
            parameters.setSize(size);
            parameters.setType(type);

            //Toast.makeText(getApplicationContext(),parameters.toString(),Toast.LENGTH_SHORT).show();
            customLoadMoreDataFromApi(0);
        }
    }

    //This is for menu item.
    public void onClickAction(MenuItem m) {
        Intent i = new Intent(this,SettingActivity.class);
        startActivityForResult(i,RESULT_CODE);
    }
    public void customLoadMoreDataFromApi(int page) {
        AsyncHttpClient client = new AsyncHttpClient();

        if (page >= MAX_PAGE_COUNT) { page=0; imageResults.clear();}
        if (page ==0) imageResults.clear();

        //Toast.makeText(getApplicationContext(),"Are we scrolling?"+page+"Setting "+parameters.toString(),Toast.LENGTH_SHORT).show();

        String googleUri = "https://ajax.googleapis.com/ajax/services/search/images?" +
                "start=" + page+"&"+parameters.toString();


        //Toast.makeText(this,googleUri,Toast.LENGTH_SHORT).show();
        client.get(googleUri, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                super.onSuccess(response);
                JSONArray imageJsonResults = null;
                try {
                    imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
                    //imageResults.clear();
                    imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
                    Log.d("DEBUG",imageResults.toString());


                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });


    }

    public void setupViews() {
        etQuery     = (EditText) findViewById(R.id.etQuery);
        btSearch    = (Button) findViewById(R.id.btnSearch);
        gvResults   = (GridView)findViewById(R.id.gvResults);

        //ArrayList<ImageResult> imageResult = new ArrayList<ImageResult>();
    }

    // This is on Search button.
    public  void onImageSearch(View v) {
        String query = etQuery.getText().toString();
        parameters.setQuery(query);
        customLoadMoreDataFromApi(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.grid_image_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
