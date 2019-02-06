package project.database.library.bibliothque;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Maria on 5/28/2017.
 */
public class Info extends Activity{

    private TextView user_id , user_name , user_address , user_password , user_email , user_gender ,
            dob , sdate , edate ;
    private Button back ;
    private DBHelper db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        db = new DBHelper(getApplicationContext()) ;
        user_id = (TextView) findViewById(R.id.user_id) ;
        user_name = (TextView) findViewById(R.id.user_name) ;
        user_address= (TextView) findViewById(R.id.user_address) ;
        user_password = (TextView) findViewById(R.id.user_password) ;
        user_email = (TextView) findViewById(R.id.user_email) ;
        user_gender = (TextView) findViewById(R.id.user_gender) ;
        dob = (TextView) findViewById(R.id.dob) ;
        sdate = (TextView) findViewById(R.id.start_date) ;
        edate = (TextView) findViewById(R.id.end_date) ;
        back = (Button) findViewById(R.id.back) ;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "back", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Info.this, Show_Members.class);
                Info.this.startActivity(intent);
                finish();
            }
        });

        Cursor c = db.get_user_info2() ;
        c.moveToFirst();
        if(c.isLast()) {
            String namee = c.getString(c.getColumnIndex(db.getUSER_NAME()));
            String idd = c.getString(c.getColumnIndex(db.getUSER_ID()));
            String passwordd = c.getString(c.getColumnIndex(db.getUSER_PASSWORD()));
            String addresss = c.getString(c.getColumnIndex(db.getUSER_ADDRESS()));
            String emaill = c.getString(c.getColumnIndex(db.getUSER_EMAIL()));
            String genderr = c.getString(c.getColumnIndex(db.getUSER_GENDER()));
            String dobb = c.getString(c.getColumnIndex(db.getUSER_DOB()));
            String s_datee = c.getString(c.getColumnIndex(db.getSTART_P_DATE()));
            String e_datee = c.getString(c.getColumnIndex(db.getEND_P_DATE()));

            user_name.setText(user_name.getText() + " " + namee);
            user_id.setText(user_id.getText() + " " + idd);
            user_password.setText(user_password.getText() + " " + passwordd);
            user_address.setText(user_address.getText() + " " + addresss);
            user_email.setText(user_email.getText() + " " + emaill);
            user_gender.setText(user_gender.getText() + " " + genderr);
            dob.setText(dob.getText() + " " + dobb);
            sdate.setText(sdate.getText() + " " + s_datee);
            edate.setText(edate.getText() + " " + e_datee);
        }
        c.close();
    }
}
