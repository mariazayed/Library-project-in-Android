package project.database.library.bibliothque;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Maria on 21-May-17.
 */
public class Change_Password extends Activity{

    private EditText old , new1 , new2 ;
    private Button change ;
    private Button back ;
    private DBHelper db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        db = new DBHelper(getApplicationContext()) ;
        old = (EditText) findViewById(R.id.old) ;
        new1 = (EditText) findViewById(R.id.new1) ;
        new2 = (EditText) findViewById(R.id.new2) ;
        change = (Button) findViewById(R.id.change) ;
        back = (Button) findViewById(R.id.back) ;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "back", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Change_Password.this, User_Participant.class);
                Change_Password.this.startActivity(intent);
                finish();
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_str = old.getText().toString() ;
                String new1_str = new1.getText().toString() ;
                String new2_str = new2.getText().toString() ;

                int result = db.check_password(old_str) ;
                if (result == 1 && new1_str.compareTo(new2_str) == 0 && old_str.compareTo(new1_str) != 0){
                    db.change_password(new1_str) ;
                    Toast.makeText(getApplicationContext(), "password changed successfully",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Wrong password ! , try again",Toast.LENGTH_SHORT).show();
                    old.setText("");
                    new1.setText("");
                    new2.setText("");
                }
            }
        });
    }
}
