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
public class Borrow extends Activity{

    public static String bid ;
    GridView gridView = null ;
    ArrayList<Borrow_Object> gridArray = new ArrayList<Borrow_Object>();
    Borrow_List_Adapter adapter = null ;
    private Button back;
    private DBHelper db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow);

        db = new DBHelper(getApplicationContext()) ;
        back = (Button) findViewById(R.id.back) ;
        gridView = (GridView) findViewById(R.id.gv) ;

        Cursor cursor = db.get_bbooks() ;
        gridArray.clear();

        while (cursor.moveToNext()) {

            String title = cursor.getString(cursor.getColumnIndex(db.getBOOK_TITLE()));
            String id = cursor.getString(cursor.getColumnIndex(db.getBOOK_ID()));
            gridArray.add(new Borrow_Object(title,id));
        }

        adapter = new Borrow_List_Adapter(this, R.layout.activity_borrow_item, gridArray);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                bid = gridArray.get(position).getBid() ;
                Toast.makeText(getApplicationContext(),gridArray.get(position).getBid(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Borrow.this, Borrow_Page.class);
                Borrow.this.startActivity(intent);
                finish();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "back", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Borrow.this, User_Participant.class);
                Borrow.this.startActivity(intent);
                finish();
            }
        });



    }
}
