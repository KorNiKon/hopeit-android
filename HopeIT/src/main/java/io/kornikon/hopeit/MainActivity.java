package io.kornikon.hopeit;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.daprlabs.aaron.swipedeck.SwipeDeck;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

import io.kornikon.hopeit.adapter.SwipeDeckAdapter;
import io.kornikon.hopeit.model.Kid;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SwipeDeck cardStack;
    private Context context = this;
    private SwipeDeckAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);
        //new HttpRequestTask().execute();

        cardStack.setCallback(new SwipeDeck.SwipeDeckCallback() {
            @Override
            public void cardSwipedLeft(long stableId) {
                Log.i("MainActivity", "card was swiped left, position in adapter: " + stableId);
            }

            @Override
            public void cardSwipedRight(long stableId) {
                Log.i("MainActivity", "card was swiped right, position in adapter: " + stableId);
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_history:
                                Log.e("x", "History");
                                return true;
                            case R.id.action_donate:
                                Log.e("x", "Donate");
                                return true;
                            case R.id.action_messages:
                                Log.e("x", "Messages");
                                return true;

                        }
                        return true;
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        new HttpRequestTask().execute();
    }

    public void setCardContent(ArrayList<Kid> list) {
        SwipeDeckAdapter deckAdapter = new SwipeDeckAdapter(list, this);
        if (cardStack != null) {
            cardStack.setAdapter(deckAdapter);
        }
        deckAdapter.notifyDataSetChanged();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, ArrayList<Kid>> {
        @Override
        protected ArrayList<Kid> doInBackground(Void... params) {
            try {
                final String url = "https://hopeit-server.herokuapp.com/kids";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<Kid[]> responseEntity = restTemplate.getForEntity(url, Kid[].class);
                Kid[] objects = responseEntity.getBody();
                //Kid[] greeting = restTemplate.getForObject(url, Kid.class);
                Log.i("MainActivity", "Returning " + Arrays.asList((objects)));
                return new ArrayList<Kid>(Arrays.asList(objects));
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return new ArrayList<Kid>();
        }

        @Override
        protected void onPostExecute(ArrayList<Kid> greeting) {
            ArrayList<Kid> list = new ArrayList<Kid>(greeting);
            setCardContent(list);
        }

    }
}
