package project.database.library.bibliothque;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Maria on 25-May-17.
 */
public class Members_List_Adapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<Members> membersList;

    public Members_List_Adapter(Context context, int layout, ArrayList<Members> membersList) {
        this.context = context;
        this.layout = layout;
        this.membersList = membersList;
    }
    @Override
    public int getCount() {
        return membersList.size() ;
    }

    @Override
    public Object getItem(int position) {
        return membersList.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    private class ViewHolder{
        ImageView image ;
        TextView name ;
    }

    public View getView(int position, View view, ViewGroup viewGroup) {

        Members_List_Adapter.ViewHolder holder = new Members_List_Adapter.ViewHolder();
        View row = view;

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.name = (TextView) row.findViewById(R.id.name);
            holder.image = (ImageView) row.findViewById(R.id.image) ;
            row.setTag(holder);
        }
        else {
            holder = (Members_List_Adapter.ViewHolder) row.getTag();
        }

        Members member = membersList.get(position);

        holder.name.setText(member.getMember_name());

        byte[] im = member.getMember_image();

        if (im != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(im, 0, im.length);
            holder.image.setImageBitmap(bitmap);
        }
        else
        holder.image.setBackgroundResource(R.drawable.profile);

        return row;
    }


}
