package io.kornikon.hopeit.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.kornikon.hopeit.R;
import io.kornikon.hopeit.model.Kid;

public class SwipeDeckAdapter extends BaseAdapter {

    private List<Kid> data;
    private Context context;

    public SwipeDeckAdapter(List<Kid> data, Context context) {
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
        Kid item = (Kid) getItem(position);
        byte[] byteArray = item.getPhoto();
        ImageView imageView = (ImageView) v.findViewById(R.id.offer_image);
        if(byteArray != null){
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            imageView.setImageBitmap(bmp);
        }
        else{
            Picasso.with(context).load("@drawable/food.png").fit().centerCrop().into(imageView);
        }
        TextView textField = (TextView) v.findViewById(R.id.general_info);
        textField.setText(item.getName() + ", " + item.getAge());
        TextView descField = (TextView) v.findViewById(R.id.description);
        descField.setText(item.getDesc());
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