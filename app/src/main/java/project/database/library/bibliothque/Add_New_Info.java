package project.database.library.bibliothque;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Maria on 22-May-17.
 */
public class Add_New_Info extends Activity{

    private EditText aid , cid , pid , paddress , pemail ;
    private Button add_new  ;
    private DBHelper db ;

    private int counter = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        db = new DBHelper(getApplicationContext()) ;
        aid = (EditText) findViewById(R.id.aid) ;
        cid = (EditText) findViewById(R.id.cid) ;
        pid = (EditText) findViewById(R.id.pid) ;
        paddress = (EditText) findViewById(R.id.paddress) ;
        pemail = (EditText) findViewById(R.id.pemail) ;
        add_new = (Button) findViewById(R.id.add_new) ;

        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 1 ;

                String author_id = aid.getText().toString() ;
                String category_id = cid.getText().toString() ;
                String publisher_id = pid.getText().toString() ;
                String publisher_address = paddress.getText().toString() ;
                String publisher_email = pemail.getText().toString() ;

                if (Add_Book.flag2 == 1)
                    counter ++ ;
                if (Add_Book.flag3 == 1)
                    counter ++ ;
                if (Add_Book.flag4 == 1)
                    counter ++ ;

                if (author_id.compareTo("") != 0 && category_id.compareTo("") != 0 && publisher_id.compareTo("") != 0){
                    if (Add_Book.flag2 == 1) {
                        db.add_author(author_id);
                        counter -- ;
                        Toast.makeText(getApplicationContext(), "New author added successfully", Toast.LENGTH_SHORT).show();
                    }
                    if (Add_Book.flag3 == 1) {
                        db.add_publisher(publisher_id, publisher_email, publisher_address);
                        counter --  ;
                        Toast.makeText(getApplicationContext(), "New publisher added successfully", Toast.LENGTH_SHORT).show();
                    }
                    if (Add_Book.flag4 == 1) {
                        db.add_category(category_id);
                        counter -- ;
                        Toast.makeText(getApplicationContext(), "New category added successfully", Toast.LENGTH_SHORT).show();
                    }
                    if (flag == 1) {
                        flag = 0;
                        if (Add_Book.PAGES.compareTo("") == 0)
                            Add_Book.PAGES = "0";
                        counter -- ;
                        db.add_new_book();
                        Toast.makeText(getApplicationContext(), "The book added successfully !", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(Add_New_Info.this, Add_Book.class);
                    Add_New_Info.this.startActivity(intent);
                    finish();
             /*       if(counter == -1) {
                        Intent intent = new Intent(Add_New_Info.this, Add_Book.class);
                        Add_New_Info.this.startActivity(intent);
                        finish();
                    } */
                }else{
                    Toast.makeText(getApplicationContext(), "Important fields are empty !", Toast.LENGTH_SHORT).show();
                    aid.setText("");
                    cid.setText("");
                    pid.setText("");
                    pemail.setText("");
                    paddress.setText("");
                }
            }
        });
    }
}
