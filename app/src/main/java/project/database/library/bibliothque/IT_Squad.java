package project.database.library.bibliothque;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Maria on 5/29/2017.
 */
public class IT_Squad extends Activity{

    private Button back ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_it_squad);

        back = (Button) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "back", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(IT_Squad.this, Project_Description.class);
                IT_Squad.this.startActivity(intent);
                finish();
            }
        });
    }
}
