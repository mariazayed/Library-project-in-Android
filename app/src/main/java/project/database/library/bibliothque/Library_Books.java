package project.database.library.bibliothque;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Maria on 24-May-17.
 */
public class Library_Books extends Activity{

    public static String book_id ;
    public static Bitmap book_image ;
    private GridView gridView = null ;
    private ArrayList<Books> gridArray = new ArrayList<Books>();
    private Books_List_Adapter adapter = null ;
    private Button back;
    private DBHelper db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_books);

        db = new DBHelper(getApplicationContext()) ;
        back = (Button) findViewById(R.id.back) ;
        gridView = (GridView) findViewById(R.id.gv) ;

        //set grid view images
        Bitmap the_da_vinci_code = BitmapFactory.decodeResource(this.getResources(), R.drawable.the_da_vinci_code);
        Bitmap the_economy_today = BitmapFactory.decodeResource(this.getResources(), R.drawable.the_economy_today);
        Bitmap archaeology_under_dictatorship = BitmapFactory.decodeResource(this.getResources(), R.drawable.archaeology_under_dictatorship);
        Bitmap art_and_archaeology_technical_a = BitmapFactory.decodeResource(this.getResources(), R.drawable.art_and_archaeology_technical_a);
        Bitmap war_power_police_power = BitmapFactory.decodeResource(this.getResources(), R.drawable.war_power_police_power);
        Bitmap jane_eyre = BitmapFactory.decodeResource(this.getResources(), R.drawable.jane_eyre);
        Bitmap murder_on_the_orient_express = BitmapFactory.decodeResource(this.getResources(), R.drawable.murder_on_the_orient_express);
        Bitmap the_power_of_now = BitmapFactory.decodeResource(this.getResources(), R.drawable.the_power_of_now);
        Bitmap the_language_lnstinct_how_the = BitmapFactory.decodeResource(this.getResources(), R.drawable.the_language_lnstinct_how_the);
        Bitmap in_the_footsteps_of_crazy_horse = BitmapFactory.decodeResource(this.getResources(), R.drawable.in_the_footsteps_of_crazy_horse);

        gridArray.add(new Books("The Da vinci code" , the_da_vinci_code , "0000"));
        gridArray.add(new Books("The economy today" , the_economy_today , "0001"));
        gridArray.add(new Books("Archaeology Under Dictatorship" , archaeology_under_dictatorship , "0002"));
        gridArray.add(new Books("Art and archaeology technical a" , art_and_archaeology_technical_a , "0003"));
        gridArray.add(new Books("War power, police power" , war_power_police_power , "0004"));
        gridArray.add(new Books("Jane Eyre" , jane_eyre , "0005"));
        gridArray.add(new Books("Murder on the Orient Express" , murder_on_the_orient_express , "0006"));
        gridArray.add(new Books("The Power of Now" , the_power_of_now , "0007"));
        gridArray.add(new Books("The Language Instinct: How the" , the_language_lnstinct_how_the , "0008"));
        gridArray.add(new Books("In the Footsteps of Crazy Horse" , in_the_footsteps_of_crazy_horse , "0009"));

        Cursor cursor = db.get_all_books() ;
        while (cursor.moveToNext()) {

            String name = cursor.getString(cursor.getColumnIndex(db.getBOOK_TITLE()));
            String id = cursor.getString(cursor.getColumnIndex(db.getBOOK_ID()));

            if (id.compareTo("0000") != 0 && id.compareTo("0001") != 0 && id.compareTo("0002") != 0 &&
                    id.compareTo("0003") != 0 && id.compareTo("0004") != 0 && id.compareTo("0005") != 0 &&
                    id.compareTo("0006") != 0 && id.compareTo("0007") != 0 && id.compareTo("0008") != 0 &&
                    id.compareTo("0009") != 0)
                gridArray.add(new Books(name , id));
        }

        adapter = new Books_List_Adapter(this, R.layout.activity_book_item, gridArray);
        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                Toast.makeText(getApplicationContext(),gridArray.get(position).getId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Library_Books.this, Book_Info.class);
                Library_Books.this.startActivity(intent);
                finish();
                book_id = gridArray.get(position).getId() ;
                book_image = gridArray.get(position).getBook_image() ;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Library.flag = 1 ;
                Toast.makeText(getApplicationContext(), "back", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Library_Books.this, Library.class);
                Library_Books.this.startActivity(intent);
                finish();
            }
        });

    }
}
