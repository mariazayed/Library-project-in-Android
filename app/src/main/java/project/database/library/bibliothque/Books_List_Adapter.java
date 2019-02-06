package project.database.library.bibliothque;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Maria on 24-May-17.
 */
public class Books_List_Adapter extends ArrayAdapter<Books> {
    Context context; int layoutResourceId; ArrayList<Books> data = new ArrayList<Books>();
    public Books_List_Adapter(Context context, int layoutResourceId, ArrayList<Books> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context; this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.book_title);
            holder.imageItem = (ImageView) row.findViewById(R.id.book_item);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }
        Books item = data.get(position);
        holder.txtTitle.setText(item.getBook_title());
        if (item.getBook_image() == null)
            holder.imageItem.setImageResource(R.drawable.bookss);
        else
            holder.imageItem.setImageBitmap(item.getBook_image());
        return row;
    }

    static class RecordHolder {
        TextView txtTitle;
        ImageView imageItem;
    }
}

