package project.database.library.bibliothque;

import android.graphics.Bitmap;
import android.util.StringBuilderPrinter;

/**
 * Created by Maria on 24-May-17.
 */

public class Books {
    private String book_title ;
    private Bitmap book_image ;
    private String id ;

    public Books(String book_title, Bitmap book_image , String id) {
        this.book_title = book_title;
        this.book_image = book_image;
        this.id = id ;
    }

    public Books(String book_title, String id) {
        this.book_title = book_title;
        this.id = id ;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public Bitmap getBook_image() {
        return book_image;
    }

    public void setBook_image(Bitmap book_image) {
        this.book_image = book_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
