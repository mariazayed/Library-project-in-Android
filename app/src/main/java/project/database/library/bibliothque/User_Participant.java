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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Maria on 19-May-17.
 */
public class User_Participant extends Activity{

    private Button borrow , return_book , show_info , change_password ;
    private Button back ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_participant);

        borrow = (Button) findViewById(R.id.borrow) ;
        return_book = (Button) findViewById(R.id.return_book) ;
        show_info = (Button) findViewById(R.id.show_info) ;
        change_password = (Button) findViewById(R.id.change_password) ;
        back = (Button) findViewById(R.id.back) ;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "back", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(User_Participant.this, Login.class);
                User_Participant.this.startActivity(intent);
                finish();
            }
        });

        borrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Redirecting...",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(User_Participant.this , Borrow.class) ;
                User_Participant.this.startActivity(intent);
                finish();
            }
        });

        return_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Redirecting...",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(User_Participant.this , Return.class) ;
                User_Participant.this.startActivity(intent);
                finish();
            }
        });

        show_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Redirecting...",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(User_Participant.this , Show_User_Info.class) ;
                User_Participant.this.startActivity(intent);
                finish();
            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Redirecting...",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(User_Participant.this , Change_Password.class) ;
                User_Participant.this.startActivity(intent);
                finish();
            }
        });
    }
}
