package project.database.library.bibliothque;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Maria on 5/29/2017.
 */

public class Borrow_List_Adapter extends ArrayAdapter<Borrow_Object> {

    Context context;
    int layoutResourceId;
    ArrayList<Borrow_Object> data = new ArrayList<Borrow_Object>();

    public Borrow_List_Adapter(Context context, int layoutResourceId, ArrayList<Borrow_Object> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context; this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        Borrow_List_Adapter.RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new Borrow_List_Adapter.RecordHolder();
            holder.bname = (TextView) row.findViewById(R.id.book_name);
            row.setTag(holder);
        } else {
            holder = (Borrow_List_Adapter.RecordHolder) row.getTag();
        }
        Borrow_Object item = data.get(position);
        holder.bname.setText(item.getBname());
        return row;
    }

    static class RecordHolder {
        TextView bname ;
    }
}
