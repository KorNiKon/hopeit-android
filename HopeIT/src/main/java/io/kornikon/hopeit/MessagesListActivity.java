package io.kornikon.hopeit;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Do something when a list item is clicked
    }
}
