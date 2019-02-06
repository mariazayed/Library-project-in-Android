package project.database.library.bibliothque;

import android.app.Activity;
import android.content.Intent;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Maria on 19-May-17.
 */
public class Login extends Activity{

    public static String user_id ;
  //  public static String user_password;
    private Button login ;
    private EditText id , password ;
    private Button back ;
    private DBHelper db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.login) ;
        db = new DBHelper(getApplicationContext()) ;
        back = (Button) findViewById(R.id.back) ;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Library.flag = 1 ;
                Toast.makeText(getApplicationContext(), "back", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login.this, Library.class);
                Login.this.startActivity(intent);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = (EditText) findViewById(R.id.id);
                password = (EditText) findViewById(R.id.password);
                String uid = id.getText().toString();
                String pass = password.getText().toString();
                if (uid.compareTo(DBHelper.ADMIN_ID) == 0 && pass.compareTo(DBHelper.ADMIN_PASSWORD) == 0) {
                    Toast.makeText(getApplicationContext(), "Welcome ...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, Manager.class);
                    Login.this.startActivity(intent);
                    finish();
                } else {
                    int result = db.check_user_validity(uid, pass);
                    if (result == 1) {
                        user_id = uid;
                        Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, User_Participant.class);
                        Login.this.startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Can not open you profile !", Toast.LENGTH_SHORT).show();
                        id.setText("");
                        password.setText("");
                    }
                }
            }
        });
    }
}
