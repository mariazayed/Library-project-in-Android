package project.database.library.bibliothque;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Maria on 5/28/2017.
 */

public class Book_Info extends Activity {

    private DBHelper db ;
    private TextView bid , btitle , npages , aname  , pname , pemail , paddress , cname , pyear , available ;
    private Button back ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        db = new DBHelper(getApplicationContext()) ;
        bid = (TextView) findViewById(R.id.bid) ;
        btitle = (TextView) findViewById(R.id.btitle) ;
        npages = (TextView) findViewById(R.id.npages) ;
        aname = (TextView) findViewById(R.id.aname) ;
        pname = (TextView) findViewById(R.id.pname) ;
        pemail = (TextView) findViewById(R.id.pemail) ;
        paddress = (TextView) findViewById(R.id.paddress) ;
        cname = (TextView) findViewById(R.id.cname) ;
        pyear = (TextView) findViewById(R.id.pyear) ;
        available = (TextView) findViewById(R.id.available) ;
        back = (Button) findViewById(R.id.back) ;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "back", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Book_Info.this, Library_Books.class);
                Book_Info.this.startActivity(intent);
                finish();
            }
        });

        Cursor c1 = db.get_book_info() ;
        Cursor c2 = db.get_author_info() ;
        Cursor c3 = db.get_publisher_info() ;
        Cursor c4 = db.get_category_info() ;

        c1.moveToFirst();
        if(c1.isLast()) {
            String book_title = c1.getString(c1.getColumnIndex(db.getBOOK_TITLE()));
            String idd = c1.getString(c1.getColumnIndex(db.getBOOK_ID()));
            String pages = c1.getString(c1.getColumnIndex(db.getNUMBER_OF_PAGES()));
            int av = c1.getInt(c1.getColumnIndex(db.getIS_AVAILABLE()));

            btitle.setText(btitle.getText() + " " + book_title);
            bid.setText(bid.getText() + " " + idd);
            npages.setText(npages.getText() + " " + pages);
            if (av == 1)
                available.setText("Available");
            if (av == 0)
                available.setText("Not Availble");

        }
        c1.close();

        c2.moveToFirst();
        if(c2.isLast()) {
            String author_name = c2.getString(c2.getColumnIndex(db.getAUTHOR_NAME()));
            aname.setText(aname.getText() + " " + author_name);
        }
        c2.close();

        c3.moveToFirst();
        if(c3.isLast()) {
            String name = c3.getString(c3.getColumnIndex(db.getPUBLISHER_NAME()));
            String email = c3.getString(c3.getColumnIndex(db.getPUBLISHER_EMAIL()));
            String address = c3.getString(c3.getColumnIndex(db.getPUBLISHER_ADDRESS()));
            String yearr = c3.getString(c3.getColumnIndex(db.getPUBLISHED_YEAR()));

            pname.setText(pname.getText() + " " + name);
            pemail.setText(pemail.getText() + " " + email);
            paddress.setText(paddress.getText() + " " + address);
            pyear.setText(pyear.getText() + " " + yearr);
        }
        c3.close();

        c4.moveToFirst();
        if(c2.isLast()) {
            String category_name = c4.getString(c4.getColumnIndex(db.getCATEGORY_NAME()));
            cname.setText(cname.getText() + " " + category_name);
        }
        c4.close();
    }
}
