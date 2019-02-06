package project.database.library.bibliothque;

import android.app.Activity;
import android.content.Intent;
import android.nfc.TagLostException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Maria on 5/29/2017.
 */
public class Project_Description extends Activity{

    private Button back , next ;
    private TextView txt ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        back = (Button) findViewById(R.id.back) ;
        next = (Button) findViewById(R.id.next) ;
        txt = (TextView) findViewById(R.id.txt) ;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "back", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Project_Description.this, Library.class);
                Project_Description.this.startActivity(intent);
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "IT squad", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Project_Description.this, IT_Squad.class);
                Project_Description.this.startActivity(intent);
                finish();
            }
        });

        String msg1 = "The tables implemented in our project satisfy the third normal form." ;
        String msg2 = "FirstNormal Form Test:\n" +
                "As shown each has a unique primary key, and the values in each column of each table are atomic," +
                "and we don't have any multi values or repeating groups which " +
                "indicates that the first normal form was successfully implemeted." ;
        String msg3 = "Second Normal Form Test:\n" +
                "And as the first normal form is satisfied and there is no candidate keys and no partial " +
                "functional dependency appears, the second normal form is satisfied." ;
        String msg4 = "Third Normal Form Test:\n" +
                "Lastly, as the transitive dependency is eliminated, we can state that the third normal " +
                "form was successfully satisfied in our tables.\n\n" ;

        txt.setText("\n" + msg1 + "\n\n" + msg2 + "\n\n" + msg3 + "\n\n" + msg4);

    }
}
