package project.database.library.bibliothque;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Maria on 21-May-17.
 */
public class Show_User_Info extends Activity{
    private TextView id , name , password , address , email , gender , dob , s_date , e_date  ;
    private ImageView image ;
    private Button back ;
    private DBHelper db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        db = new DBHelper(getApplicationContext()) ;
        image = (ImageView) findViewById(R.id.image) ;
        id = (TextView) findViewById(R.id.id) ;
        name = (TextView) findViewById(R.id.name) ;
        password = (TextView) findViewById(R.id.password) ;
        address = (TextView) findViewById(R.id.address) ;
        email = (TextView) findViewById(R.id.email) ;
        gender = (TextView) findViewById(R.id.gender) ;
        dob = (TextView) findViewById(R.id.dob) ;
        s_date = (TextView) findViewById(R.id.start_date) ;
        e_date = (TextView) findViewById(R.id.e_date) ;
        back = (Button) findViewById(R.id.back) ;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "back", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Show_User_Info.this, User_Participant.class);
                Show_User_Info.this.startActivity(intent);
                finish();
            }
        });

        Cursor c = db.get_user_info() ;
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
            byte[] imagee = c.getBlob(c.getColumnIndex(db.getUSER_IMAGE()));

            name.setText(name.getText() + " " + namee);
            id.setText(id.getText() + " " + idd);
            password.setText(password.getText() + " " + passwordd);
            address.setText(address.getText() + " " + addresss);
            email.setText(email.getText() + " " + emaill);
            gender.setText(gender.getText() + " " + genderr);
            dob.setText(dob.getText() + " " + dobb);
            s_date.setText(s_date.getText() + " " + s_datee);
            e_date.setText(e_date.getText() + " " + e_datee);

            Bitmap bm = BitmapFactory.decodeByteArray(imagee, 0, imagee.length);
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            image.setMinimumHeight(dm.heightPixels);
            image.setMinimumWidth(dm.widthPixels);
            image.setImageBitmap(bm);
        }
        c.close();
    }
}
