package io.kornikon.hopeit;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

import io.kornikon.hopeit.model.Kid;
import io.kornikon.hopeit.model.myMessage;

/**
 * Created by Esroh on 28-Oct-17.
 */

public class MessagesListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_list);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation_msgs);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_history:
                                goToHistory();
                            case R.id.action_donate:
                                goToDonations();
                            case R.id.action_messages:
                                return true;
                        }
                        return true;
                    }
                });
    }

    protected void goToDonations(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    protected void goToHistory(){
        Log.e("x", "History");
    }

    public void setListContent(ArrayList<myMessage> list){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.message_list, list);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Do something when a list item is clicked
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, ArrayList<myMessage>> {
        @Override
        protected ArrayList<myMessage> doInBackground(Void... params) {
            try {
                final String url = "https://hopeit-server.herokuapp.com/kids";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<myMessage[]> responseEntity = restTemplate.getForEntity(url, myMessage[].class);
                myMessage[] objects = responseEntity.getBody();
                //Kid[] greeting = restTemplate.getForObject(url, Kid.class);
                Log.i("MainActivity", "Returning " + Arrays.asList((objects)));
                return new ArrayList<myMessage>(Arrays.asList(objects));
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return new ArrayList<myMessage>();
        }

        @Override
        protected void onPostExecute(ArrayList<myMessage> greeting) {
            ArrayList<myMessage> list = new ArrayList<myMessage>(greeting);
            setListContent(list);
        }

    }
}
