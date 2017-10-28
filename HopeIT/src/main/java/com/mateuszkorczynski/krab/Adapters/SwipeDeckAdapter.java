package com.mateuszkorczynski.krab.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mateuszkorczynski.krab.MainActivity;
import com.mateuszkorczynski.krab.R;
import com.mateuszkorczynski.krab.RestUsers;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SwipeDeckAdapter extends BaseAdapter {

    private List<RestUsers> data;
    private Context context;

    public SwipeDeckAdapter(List<RestUsers> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            // normally use a viewholder
            v = inflater.inflate(R.layout.card_view, parent, false);
        }
        Log.i("Layer type: ", "Jajka");
        RestUsers item = (RestUsers)getItem(position);
        Log.i("Layer type: ", "Jajka2");
        ImageView imageView = (ImageView) v.findViewById(R.id.offer_image);
        Picasso.with(context).load(R.drawable.food).fit().centerCrop().into(imageView);
        Log.i("Layer type: ", "Jajka3");
        TextView textField = (TextView) v.findViewById(R.id.general_info);
        textField.setText(item.getName() + ", " + item.getAge());
        Log.i("Layer type: ", "Jajka4");
        TextView descField = (TextView) v.findViewById(R.id.description);
        descField.setText(item.getDesc());
        Log.i("Layer type: ", "Jajka5");
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Layer type: ", Integer.toString(v.getLayerType()));
                Log.i("Hardware Accel type:", Integer.toString(View.LAYER_TYPE_HARDWARE));
            }
        });
        return v;
    }
}