package project.database.library.bibliothque;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Maria on 21-May-17.
 */
public class Show_Members extends Activity{

    public static String user_id ;
    GridView gridView = null ;
    ArrayList<Members> list = null ;
    Members_List_Adapter adapter = null ;
    private Button back;
    private DBHelper db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_members);

        db = new DBHelper(getApplicationContext()) ;
        back = (Button) findViewById(R.id.back) ;
        gridView = (GridView) findViewById(R.id.gv) ;
        list = new ArrayList<>();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "back", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Show_Members.this, Manager.class);
                Show_Members.this.startActivity(intent);
                finish();
            }
        });

        Cursor cursor = db.get_members() ;
        list.clear();

        while (cursor.moveToNext()) {

            String name = cursor.getString(cursor.getColumnIndex(db.getUSER_NAME()));
            byte[] image = cursor.getBlob(cursor.getColumnIndex(db.getUSER_IMAGE()));
            String id = cursor.getString(cursor.getColumnIndex(db.getUSER_ID()));

            list.add(new Members(name , image , id));
        }

        adapter = new Members_List_Adapter(this, R.layout.activity_member_item, list);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                Toast.makeText(getApplicationContext(),list.get(position).getId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Show_Members.this, Info.class);
                Show_Members.this.startActivity(intent);
                finish();
                user_id = list.get(position).getId() ;
            }
        });
    }
}
