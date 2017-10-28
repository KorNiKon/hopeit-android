package io.kornikon.hopeit;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

//        cardStack.setLeftImage(R.id.left_image);
//        cardStack.setRightImage(R.id.right_image);

        Button btn = (Button) findViewById(R.id.button_left);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopCardLeft(500);

            }
        });
        Button btn2 = (Button) findViewById(R.id.button_right);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopCardRight(180);
            }
        });

        Button btn3 = (Button) findViewById(R.id.button_center);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HttpRequestTask().execute();
            }
        });

    }

    public void setCardContent(ArrayList<Kid> list) {
        SwipeDeckAdapter deckAdapter = new SwipeDeckAdapter(list, this);
        if (cardStack != null) {
            cardStack.setAdapter(deckAdapter);
        }
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
