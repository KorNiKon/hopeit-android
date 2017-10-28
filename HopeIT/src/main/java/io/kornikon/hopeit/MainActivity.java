package io.kornikon.hopeit;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.daprlabs.aaron.swipedeck.SwipeDeck;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import io.kornikon.hopeit.adapter.SwipeDeckAdapter;
import io.kornikon.hopeit.model.Kid;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

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
        bottomNavigationView.getMenu().getItem(1).setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_history:
                                goToHistory();
                            case R.id.action_donate:
                                return true;
                            case R.id.action_messages:
                                goToMessages();
                        }
                        return true;
                    }
                });
    }

    protected void goToMessages(){
        Intent intent = new Intent(this, MessagesListActivity.class);
        startActivity(intent);
    }

    protected void goToHistory(){
        Log.e("x", "History");
    }

    @Override
    protected void onResume() {
        super.onResume();
        new HttpRequestTask("/kids", Kid[].class, new MyConsumer<Kid[]>() {
            @Override
            public void accept(Kid[] kids) {
                setCardContent(Arrays.asList(kids));
            }
        }).execute();
    }

    public void setCardContent(List<Kid> list) {
        SwipeDeckAdapter deckAdapter = new SwipeDeckAdapter(list, this);
        if (cardStack != null) {
            cardStack.setAdapter(deckAdapter);
        }
        deckAdapter.notifyDataSetChanged();
    }

    private class HttpRequestTask<T> extends AsyncTask<Void, Void, T[]> {

        private static final String SERVER_URL = "https://hopeit-server.herokuapp.com";

        private final String subpath;
        private final Class<T[]> returnType;
        private final MyConsumer<T[]> consumer;

        public HttpRequestTask(String subpath, Class<T[]> returnType, MyConsumer<T[]> consumer) {
            this.subpath = subpath;
            this.returnType = returnType;
            this.consumer = consumer;
        }

        @Override
        protected T[] doInBackground(Void... params) {
            try {
                final String url = SERVER_URL + subpath;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<T[]> responseEntity = restTemplate.getForEntity(url, returnType);
                T[] objects = responseEntity.getBody();
                Log.i("MainActivity", "Returning " + Arrays.asList((objects)));
                return objects;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(T[] greeting) {
            if (!isCancelled() && greeting != null) {
                consumer.accept(greeting);
            }
        }

    }
}
