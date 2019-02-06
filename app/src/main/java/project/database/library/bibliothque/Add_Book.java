package project.database.library.bibliothque;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;

/**
 * Created by Maria on 21-May-17.
 */
public class Add_Book extends Activity{

    public static int flag = 0 , flag2 = 0 , flag3 = 0 , flag4 = 0 ;
    public static String BOOK_ID , BOOK_TITLE , AUTHOR_NAME , PUBLISHER_NAME , CATEGORY_NAME , PAGES , YEAR ;
    private EditText book_id , book_title , number_of_pages , author_name , publisher_name , category_name , published_year;
    private Button add_book ;
    private Button back ;
    private DBHelper db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        db = new DBHelper(getApplicationContext()) ;
        book_id = (EditText) findViewById(R.id.book_id) ;
        book_title = (EditText) findViewById(R.id.book_title) ;
        number_of_pages = (EditText) findViewById(R.id.n_of_pages) ;
        author_name = (EditText) findViewById(R.id.author_name) ;
        publisher_name = (EditText) findViewById(R.id.publisher_name) ;
        category_name = (EditText) findViewById(R.id.category_name) ;
        published_year = (EditText) findViewById(R.id.pyear) ;
        add_book = (Button) findViewById(R.id.add_book) ;
        back = (Button) findViewById(R.id.back) ;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "back", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Add_Book.this, Manager.class);
                Add_Book.this.startActivity(intent);
                finish();
            }
        });

        add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BOOK_ID = book_id.getText().toString();
                BOOK_TITLE = book_title.getText().toString();
                PAGES = number_of_pages.getText().toString();
                AUTHOR_NAME = author_name.getText().toString();
                PUBLISHER_NAME = publisher_name.getText().toString();
                CATEGORY_NAME = category_name.getText().toString();
                YEAR = published_year.getText().toString();

                int result2 = db.check_author_name(AUTHOR_NAME); // 1 --> author exists , 0 --> author not exists
                int result3 = db.check_publisher_name(PUBLISHER_NAME); // 1 --> publisher exists , 0 --> publisher not exists
                int result4 = db.check_category_name(CATEGORY_NAME); // 1 --> category exists , 0 --> category not exists
                int result7 = check_pages(PAGES);
                int result8 = check_year(YEAR);

                if (BOOK_ID.compareTo("") != 0 || BOOK_TITLE.compareTo("") != 0) {
                    int result1 = check_if_empty(AUTHOR_NAME, PUBLISHER_NAME, CATEGORY_NAME);
                    int result5 = db.check_book_id(BOOK_ID); // 1 --> book_id is not exist , 0 --> book_id exist
                    int result6 = db.check_book_title(BOOK_TITLE); // 1 --> book is not exist , 0 --> book exist
                    if (result1 == 1 && result5 == 1 && result6 == 1 && result7 == 1 && result8 == 1) {
                        if (result2 == 0)
                            flag2 = 1 ;
                        if (result3 == 0)
                            flag3 = 1;
                        if (result4 == 0)
                            flag4 = 1;
                        if (result2 == 0 || result3 == 0 || result4 == 0) {
                            Toast.makeText(getApplicationContext(), "data not found! add to the tables", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Add_Book.this, Add_New_Info.class);
                            Add_Book.this.startActivity(intent);
                            finish();
                        }
                    } else {
                        book_id.setText("");
                        book_title.setText("");
                        number_of_pages.setText("");
                        author_name.setText("");
                        publisher_name.setText("");
                        category_name.setText("");
                        published_year.setText("");
                        Toast.makeText(getApplicationContext(), "Error Occurred !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    book_id.setText("");
                    book_title.setText("");
                    number_of_pages.setText("");
                    author_name.setText("");
                    publisher_name.setText("");
                    category_name.setText("");
                    published_year.setText("");
                    Toast.makeText(getApplicationContext(), "The book must NOT be empty !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private int check_year(String year) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = new java.util.Date();
        sdf.format(date).toString(); // current date
        String[] now = sdf.format(date).toString().split("-");

        char[] c1 = year.toCharArray() ;
        if (year.compareTo("") != 0) {
            for (int i = 0; i < c1.length; i++)
                if (!Character.isDigit(c1[i]))
                    return 0;

            if (Integer.parseInt(year) <= Integer.parseInt(now[2]) && Integer.parseInt(year) >= 1500)
                return 1;
            else
                return 0;
        }else
            return 1;
    }

    private int check_pages(String pages) {
        char[] c1 = pages.toCharArray() ;

        if (pages.compareTo("") != 0) {
            for (int i = 0; i < c1.length; i++)
                if (!Character.isDigit(c1[i]))
                    return 0;

            if (Integer.parseInt(pages) >= 50 && Integer.parseInt(pages) <= 3000)
                return 1;
            else
                return 0;
        }else
            return 1 ;
    }

    private int check_if_empty(String aname, String pname, String cname) {
        if (aname.compareTo("") == 0 || pname.compareTo("") == 0 || cname.compareTo("") == 0){
            Toast.makeText(getApplicationContext(), "Important field are empty !", Toast.LENGTH_SHORT).show();
            book_id.setText("");
            book_title.setText("");
            number_of_pages.setText("");
            author_name.setText("");
            publisher_name.setText("");
            category_name.setText("");
            published_year.setText("");
            return 0 ;
        }
        else
            return 1 ;
    }
}
