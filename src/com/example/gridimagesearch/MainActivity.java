package com.example.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MainActivity extends Activity {
	private final int SETTINGS_CODE = 10;
	private EditText etQuery;
	private GridView gvResults;
	private Button btnSearch;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupViews();
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
			}
			
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	} 
	
	public void onSettings(MenuItem mi){
		Intent iSettings = new Intent(MainActivity.this, SettingsActivity.class);
		startActivityForResult(iSettings, SETTINGS_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // REQUEST_CODE is defined above
	  if (resultCode == RESULT_OK && requestCode == SETTINGS_CODE) {
	     Toast.makeText(this, "something", Toast.LENGTH_SHORT).show();
	  }
	} 
	
	public void setupViews(){
		etQuery = (EditText)findViewById(R.id.etQuery);
		gvResults = (GridView)findViewById(R.id.gvResults);
		btnSearch = (Button)findViewById(R.id.btnSearch);
	}
	
	public void onImageSearch(View v){
		String query = etQuery.getText().toString();
		Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
		AsyncHttpClient client = new AsyncHttpClient();
		// https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=android
		client.get("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + Uri.encode(query) + "&rsz=8&start=" + 0, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject response){				
				try{
					JSONArray imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
					imageResults.clear();
					imageAdapter.addAll(ImageResult.fromJsonArray(imageJsonResults));					
					Log.d("DEBUG", imageResults.toString());
				} catch(JSONException e){
					e.printStackTrace();
				}
			}			
		});
	}
}
