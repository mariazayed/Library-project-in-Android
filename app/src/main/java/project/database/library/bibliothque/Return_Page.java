package project.database.library.bibliothque;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Maria on 5/29/2017.
 */
public class Return_Page extends Activity{
    private DBHelper db ;
    private TextView bid , btitle , npages , aname  , pname , pemail , paddress , cname , pyear , sdate , edate , late ;
    private Button back , returnn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_page);

        db = new DBHelper(getApplicationContext());
        back = (Button) findViewById(R.id.back);

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
        sdate = (TextView) findViewById(R.id.sdate) ;
        edate = (TextView) findViewById(R.id.edate) ;
        late = (TextView) findViewById(R.id.late) ;
        back = (Button) findViewById(R.id.back) ;
        returnn = (Button) findViewById(R.id.returnn) ;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "back", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Return_Page.this, Return.class);
                Return_Page.this.startActivity(intent);
                finish();
            }
        });

        Cursor c1 = db.get_book_info3() ;
        Cursor c2 = db.get_author_info3() ;
        Cursor c3 = db.get_publisher_info3() ;
        Cursor c4 = db.get_category_info3() ;
        Cursor c5 = db.get_borrow_info() ;

        c1.moveToFirst();
        if(c1.isLast()) {
            String book_title = c1.getString(c1.getColumnIndex(db.getBOOK_TITLE()));
            String idd = c1.getString(c1.getColumnIndex(db.getBOOK_ID()));
            String pages = c1.getString(c1.getColumnIndex(db.getNUMBER_OF_PAGES()));

            btitle.setText(btitle.getText() + " " + book_title);
            bid.setText(bid.getText() + " " + idd);
            npages.setText(npages.getText() + " " + pages);
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

        c5.moveToFirst();
        if(c5.isLast()) {
            String sdatee = c5.getString(c5.getColumnIndex(db.getSTART_B_DATE()));
            String edatee = c5.getString(c5.getColumnIndex(db.getEND_B_DATE()));
            int latee = db.check_late(edatee);
            if (latee == 1)
                late.setText("Late !");
            if (latee == 0)
                late.setText("Not Late");
            sdate.setText(sdate.getText() + " " + sdatee);
            edate.setText(edate.getText() + " " + edatee);
        }
        c5.close();

        returnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    db.return_book();
                    db.update_borrow_table() ;
                    Toast.makeText(getApplicationContext(),"Returned Successfully", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
