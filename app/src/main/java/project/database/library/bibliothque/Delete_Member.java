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
public class Delete_Member extends Activity{

    private Button delete ;
    private EditText user_id ;
    private Button back ;
    private DBHelper db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_member);

        db = new DBHelper(getApplicationContext()) ;
        user_id = (EditText) findViewById(R.id.user_id) ;
        delete = (Button) findViewById(R.id.delete) ;
        back = (Button) findViewById(R.id.back) ;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "back", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Delete_Member.this, Manager.class);
                Delete_Member.this.startActivity(intent);
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result1 = db.check_id_validity(user_id.getText().toString()) ;
                if(result1 == 1) {
                    db.check_b_books(user_id.getText().toString()) ; // check if the user has borrowed books
                    db.delete_member(user_id.getText().toString());
                    Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    user_id.setText("");
                }else{
                    Toast.makeText(getApplicationContext(), "Wrong ID ! , Try again ...", Toast.LENGTH_SHORT).show();
                    user_id.setText("");
                }
            }
        });

    }
}
