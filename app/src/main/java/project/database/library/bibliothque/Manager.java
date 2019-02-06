package project.database.library.bibliothque;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Maria on 21-May-17.
 */
public class Manager extends Activity {

    Button add_member , show_members , add_book , delete_member ;
    private Button back ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        add_member = (Button) findViewById(R.id.add_member) ;
        show_members =(Button) findViewById(R.id.show_members) ;
        add_book = (Button) findViewById(R.id.add_book) ;
        delete_member = (Button) findViewById(R.id.delete_member) ;
        back = (Button) findViewById(R.id.back) ;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "back", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Manager.this, Login.class);
                Manager.this.startActivity(intent);
                finish();
            }
        });

        add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Manager.this, Add_Member.class);
            Manager.this.startActivity(intent);
            finish();
        }
        });

        show_members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Manager.this, Show_Members.class);
                Manager.this.startActivity(intent);
                finish();
            }
        });

        add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Manager.this, Add_Book.class);
                Manager.this.startActivity(intent);
                finish();
            }
        });

        delete_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Manager.this, Delete_Member.class);
                Manager.this.startActivity(intent);
                finish();
            }
        });
    }
}
