package project.database.library.bibliothque;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Maria on 5/29/2017.
 */
public class Return_List_Adapter extends ArrayAdapter<Return_Object> {

    Context context;
    int layoutResourceId;
    ArrayList<Return_Object> data = new ArrayList<Return_Object>();

    public Return_List_Adapter(Context context, int layoutResourceId, ArrayList<Return_Object> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        Return_List_Adapter.RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new Return_List_Adapter.RecordHolder();
            holder.bname = (TextView) row.findViewById(R.id.book_name);
            row.setTag(holder);
        } else {
            holder = (Return_List_Adapter.RecordHolder) row.getTag();
        }
        Return_Object item = data.get(position);
        holder.bname.setText(item.getBname());
        return row;
    }

    static class RecordHolder {
        TextView bname;
    }
}
