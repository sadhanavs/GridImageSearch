package com.yahoo.gridimagesearch.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;


public class SettingActivity extends Activity {

    Spinner spColor;
    Spinner spSize;
    Spinner spType;
    EditText etSite;
    ArrayAdapter colorDataAdapter;
    ArrayAdapter imageTypeDataAdapter;
    ArrayAdapter imageSizeDataAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        spColor = (Spinner) findViewById(R.id.spColor);
        spSize  = (Spinner) findViewById(R.id.spSize);
        spType  = (Spinner) findViewById(R.id.spImageType);
        etSite  = (EditText) findViewById(R.id.etSite);

        ImageSearchParameters parameters = (ImageSearchParameters)getIntent().getSerializableExtra("parameters");
        populateSpinners();
        if(parameters != null) {

            if (!parameters.getColor().isEmpty()) {
                int index = colorDataAdapter.getPosition(parameters.getColor());
                spColor.setSelection(index);
            }

            if (!parameters.getSize().isEmpty()) {
                int index = imageSizeDataAdapter.getPosition(parameters.getSize());
                spSize.setSelection(index);
            }

            if (!parameters.getType().isEmpty()) {
                int index = imageTypeDataAdapter.getPosition(parameters.getType());
                spType.setSelection(index);
            }

            if (!parameters.getDomain().isEmpty()) {
                etSite.setText(parameters.getDomain());
            }
        }

    }

    public void onSave(View v) {
        String color = spColor.getSelectedItem().toString();
        String size  = spSize.getSelectedItem().toString();
        String type  = spType.getSelectedItem().toString();
        String domain = etSite.getText().toString();


        Intent data = new Intent();
        data.putExtra("color",color);
        data.putExtra("size",size);
        data.putExtra("type",type);
        data.putExtra("domain",domain);
        setResult(RESULT_OK, data);
        finish();


    }

    private void populateSpinners() {
        List<String> spColorData = new ArrayList<String>();
        spColorData.add("red");
        spColorData.add("blue");
        spColorData.add("green");
        spColorData.add("pink");
        spColorData.add("black");
        spColorData.add("white");
        colorDataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,spColorData);
        spColor.setAdapter(colorDataAdapter);
        spColor.setPrompt("Choose Image Color");



        List<String> spImageSizeData = new ArrayList<String>();
        spImageSizeData.add("icon");
        spImageSizeData.add("small");
        spImageSizeData.add("large");
        spImageSizeData.add("xLarge");
        imageSizeDataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,spImageSizeData);
        spSize.setAdapter(imageSizeDataAdapter);
        spSize.setPrompt("Choose Image Size");

        List<String> spImageTypeData = new ArrayList<String>();
        spImageTypeData.add("face");
        spImageTypeData.add("photo");
        spImageTypeData.add("clipart");
        spImageTypeData.add("lineart");
        imageTypeDataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,spImageTypeData);
        spType.setAdapter(imageTypeDataAdapter);
        spType.setPrompt("Choose Image Type");




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.setting, menu);
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
