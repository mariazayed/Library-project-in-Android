package project.database.library.bibliothque;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Maria on 28-Apr-17.
 */

public class DBHelper{

    Library library = new Library() ;
    DB db ;
    public static final String ADMIN_ID = "ADMIN00000" ;
    public static final String ADMIN_PASSWORD = "admin" ;

    public DBHelper(Context context) {
        db = new DB(context) ;
    }

    public int check_user_validity(String id , String pass){
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  * "
                        + "FROM " + db.USER_TABLE + " u "
                        + " WHERE u." + db.USER_ID + " = '" + id + "' "
                        +  "AND " + db.USER_PASSWORD + " = '" + pass + "' " ;

        Cursor c = database.rawQuery(selectQuery, null);

        if (c.getCount() != 0) {
            int result = check_participation_validity(get_start_date(id), get_end_date(id));
            if (result == 1)
                return 1;
            else
                return 0 ;
        }
        else
            return 0;
    }

    private String get_start_date(String id){
        SQLiteDatabase database = db.getReadableDatabase();
        String str = null ;
        String selectQuery =
                "SELECT u." + db.START_P_DATE
                        + " FROM " + db.USER_TABLE + " u "
                        + " WHERE u." + db.USER_ID + " = '" + id + "' " ;

        Cursor c = database.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
           str = c.getString(c.getColumnIndex(db.START_P_DATE));
        }
        return str ;
    }

    private String get_end_date(String id){
        SQLiteDatabase database = db.getReadableDatabase();
        String str = null ;
        String selectQuery =
                "SELECT u." + db.END_P_DATE
                        + " FROM " + db.USER_TABLE + " u "
                        + " WHERE u." + db.USER_ID + " = '" + id + "' " ;

        Cursor c = database.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            str = c.getString(c.getColumnIndex(db.END_P_DATE));
        }
        return str ;
    }

    public Cursor get_user_info2(){
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  * "
                        + "FROM " + db.USER_TABLE + " u "
                        + " WHERE u." + db.USER_ID + " = '" + Show_Members.user_id + "' " ;

        Cursor c = database.rawQuery(selectQuery, null);
        return c ;
    }

    public Cursor get_user_info(){
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  * "
                        + "FROM " + db.USER_TABLE + " u "
                        + " WHERE u." + db.USER_ID + " = '" + Login.user_id + "' " ;

        Cursor c = database.rawQuery(selectQuery, null);
        return c ;
    }

    public int check_password(String pass){
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  * "
                        + "FROM " + db.USER_TABLE + " u "
                        + " WHERE u." + db.USER_ID + " = '" + Login.user_id + "' "
                        +  "AND " + db.USER_PASSWORD + " = '" + pass + "' " ;

        Cursor c = database.rawQuery(selectQuery, null);
        if (c.getCount() == 1)
            return 1 ;
        else
            return 0 ;
    }

    public void change_password(String pass){
        SQLiteDatabase database = db.getWritableDatabase();
        String strSQL = "UPDATE " + db.USER_TABLE
                +" SET " + db.USER_PASSWORD + " = '" + pass + "' WHERE "
                + db.USER_ID + " = '" + Login.user_id + "'" ;
        database.execSQL(strSQL) ;
    }

    public Cursor has_book(){
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  bw.* , b.* "
                        + "FROM " + db.USER_TABLE + " u , " + db.BOOK_TABLE + " b , " + db.BORROW_TABLE + " bw "
                        + " WHERE u." + db.USER_ID + " = '" + Login.user_id + "' "
                        +  " AND u." + db.USER_ID + " = bw." + db.B_UID
                        +  " AND b." + db.BOOK_ID + " = bw." + db.B_BID ;

        Cursor c = database.rawQuery(selectQuery, null);
        return c ;
    }

    private int check_participation_validity(String start, String end) {
        if(start.compareTo("") != 0 && start.compareTo("") != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date date = new java.util.Date();
            sdf.format(date).toString(); // current date
            String[] now = sdf.format(date).toString().split("-");
            String[] startt = start.split("-");
            String[] endd = end.split("-");
            if (Integer.parseInt(startt[2]) == Integer.parseInt(now[2])) {
                if (Integer.parseInt(startt[1]) < Integer.parseInt(now[1])) {
                    return 1;
                } else if (Integer.parseInt(startt[1]) == Integer.parseInt(now[1])) {
                    if (Integer.parseInt(startt[0]) <= Integer.parseInt(now[0])) {
                        return 1;
                    }
                }
            } else if (Integer.parseInt(endd[2]) == Integer.parseInt(now[2])) {
                if (Integer.parseInt(endd[1]) > Integer.parseInt(now[1])) {
                    return 1;
                } else if (Integer.parseInt(endd[1]) == Integer.parseInt(now[1])) {
                    if (Integer.parseInt(endd[0]) >= Integer.parseInt(now[0])) {
                        return 1;
                    }
                }
            }
            return 0;
        }else
            return 0 ;
    }

    public String getBOOK_TABLE() {
        return db.BOOK_TABLE;
    }

    public String getAUTHOR_TABLE() {
        return db. AUTHOR_TABLE;
    }

    public String getPUBLISHER_TABLE() {
        return db.PUBLISHER_TABLE;
    }

    public String getUSER_TABLE() {
        return db.USER_TABLE;
    }

    public String getCATEGORY_TABLE() {
        return db.CATEGORY_TABLE;
    }

    public String getWRITE_TABLE() {
        return db.WRITE_TABLE;
    }

    public String getBELONGS_TO_TABLE() {
        return db.BELONGS_TO_TABLE;
    }

    public String getBORROW_TABLE() {
        return db.BORROW_TABLE;
    }

    public String getPUBLISHED_TABLE() {
        return db.PUBLISHED_TABLE;
    }

    public String getBOOK_ID() {
        return db.BOOK_ID;
    }

    public String getBOOK_TITLE() {
        return db.BOOK_TITLE;
    }

    public String getNUMBER_OF_PAGES() {
        return db.NUMBER_OF_PAGES;
    }

    public String getIS_AVAILABLE() {
        return db.IS_AVAILABLE;
    }

    public String getAUTHOR_ID() {
        return db.AUTHOR_ID;
    }

    public String getAUTHOR_NAME() {
        return db.AUTHOR_NAME;
    }

    public String getPUBLISHER_ID() {
        return db.PUBLISHER_ID;
    }

    public String getPUBLISHER_NAME() {
        return db.PUBLISHER_NAME;
    }

    public String getPUBLISHER_EMAIL() {
        return db.PUBLISHER_EMAIL;
    }

    public String getPUBLISHER_ADDRESS() {
        return db.PUBLISHER_ADDRESS;
    }

    public String getUSER_ID() {
        return db.USER_ID;
    }

    public String getUSER_NAME() {
        return db.USER_NAME;
    }

    public String getUSER_ADDRESS() {
        return db.USER_ADDRESS;
    }

    public String getUSER_PASSWORD() {
        return db.USER_PASSWORD;
    }

    public String getUSER_EMAIL() {
        return db.USER_EMAIL;
    }

    public String getUSER_GENDER() {
        return db.USER_GENDER;
    }

    public String getUSER_DOB() {
        return db.USER_DOB;
    }

    public String getSTART_P_DATE() {
        return db.START_P_DATE;
    }

    public String getEND_P_DATE() {
        return db.END_P_DATE;
    }

    public String getUSER_IMAGE() {
        return db.USER_IMAGE;
    }

    public String getCATEGORY_ID() {
        return db.CATEGORY_ID;
    }

    public String getCATEGORY_NAME() {
        return db.CATEGORY_NAME;
    }

    public String getW_AID() {
        return db.W_AID;
    }

    public String getW_BID() {
        return db.W_BID;
    }

    public String getBT_BID() {
        return db.BT_BID;
    }

    public String getBT_CID() {
        return db.BT_CID;
    }

    public String getB_UID() {
        return db.B_UID;
    }

    public String getB_BID() {
        return db.B_BID;
    }

    public String getIS_LATE() {
        return db.IS_LATE;
    }

    public String getIS_RETURNED() {
        return db.IS_RETURNED;
    }

    public String getSTART_B_DATE() {
        return db.START_B_DATE;
    }

    public String getEND_B_DATE() {
        return db.END_B_DATE;
    }

    public String getP_PID() {
        return db.P_PID;
    }

    public String getP_BID() {
        return db.P_BID;
    }

    public String getPUBLISHED_YEAR() {
        return db.PUBLISHED_YEAR;
    }

    public void insert_member(String id, String name, String password, String email, String address,
                               String b_date, String gender, String st_date, String en_date, byte[] im) {
        SQLiteDatabase database = db.getWritableDatabase();

        ContentValues contentValues1 = new  ContentValues();
        contentValues1.put(db.USER_ID , id);
        contentValues1.put(db.USER_NAME , name);
        contentValues1.put(db.USER_PASSWORD , password);
        contentValues1.put(db.USER_EMAIL , email);
        contentValues1.put(db.USER_ADDRESS , address);
        contentValues1.put(db.USER_DOB , b_date);
        contentValues1.put(db.USER_GENDER , gender);
        contentValues1.put(db.START_P_DATE , st_date);
        contentValues1.put(db.END_P_DATE , en_date);
        contentValues1.put(db.USER_IMAGE , im);
        database.insert(db.USER_TABLE, null, contentValues1 );
    }

    public int check_id_validity(String id) {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  * "
                        + "FROM " + db.USER_TABLE + " u "
                        + "WHERE u." + db.USER_ID + " = '" + id + "'" ;

        Cursor c = database.rawQuery(selectQuery, null);

        if (c.getCount() == 1)
            return 1;
        else
            return 0;
    }

    public void delete_member(String id) {
        SQLiteDatabase database = db.getWritableDatabase();
        database.delete(db.USER_TABLE , db.USER_ID + " = ? ", new String[] { id });
        database.delete(db.BORROW_TABLE , db.B_UID + " = ? " , new String[]{ id }) ;
    }

    public void check_b_books(String id) {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT b.*"
                        + " FROM " + db.USER_TABLE + " u , " + db.BOOK_TABLE + " b , " + db.BORROW_TABLE + " bw "
                        + " WHERE u." + db.USER_ID + " = '" + id + "' "
                        + " AND u." + db.USER_ID + " = bw." + db.B_UID
                        + " AND b." + db.BOOK_ID + " = bw." + db.B_BID
                        + " AND bw." + db.IS_RETURNED + " = 0"
                        + " AND b." + db.IS_AVAILABLE + " = 0";

        Cursor c = database.rawQuery(selectQuery, null);

        c.moveToFirst() ;
        while (c.moveToNext()){
            ContentValues args = new ContentValues();
            args.put(db.IS_AVAILABLE, 1);
            database.update(db.BOOK_TABLE , args , db.IS_AVAILABLE + " = 0" , null);
        }
    }

    public int return_books(String id) {
        SQLiteDatabase database = db.getReadableDatabase();
  /*      SQLiteDatabase database = db.getWritableDatabase();

        String strSQL = "UPDATE " + db.BOOK_TABLE
                +" SET " + db.IS_AVAILABLE + " = 1"
                + " WHERE (" +  "SELECT  b." + db.IS_AVAILABLE
                + " FROM " + db.USER_TABLE + " u , " + db.BOOK_TABLE + " b , " + db.BORROW_TABLE + " bw "
                + " WHERE u." + db.USER_ID + " = '" + id+ "' "
                +  " AND u." + db.USER_ID + " = bw." + db.B_UID
                +  " AND b." + db.BOOK_ID + " = bw." + db.B_BID
                +  " AND bw." + db.IS_RETURNED + " = 0 )" ;
        database.execSQL(strSQL) ; */
                String selectQuery =
                "SELECT  bw." + db.B_BID
                        + " FROM " + db.USER_TABLE + " u , " + db.BOOK_TABLE + " b , " + db.BORROW_TABLE + " bw "
                        + " WHERE u." + db.USER_ID + " = '" + id+ "' "
                        +  " AND u." + db.USER_ID + " = bw." + db.B_UID
                        +  " AND b." + db.BOOK_ID + " = bw." + db.B_BID
                        +  " AND bw." + db.IS_RETURNED + " = 0" ;

        Cursor c = database.rawQuery(selectQuery, null);
        c.moveToFirst() ;

        SQLiteDatabase database2 = db.getWritableDatabase();
        while (c.moveToNext()){
            String strSQL = "UPDATE " + db.BOOK_TABLE
                    +" SET " + db.IS_AVAILABLE + " = 0" ;
            database2.execSQL(strSQL) ;
        }

        return 1 ;
    }

    public int check_author_name(String aname) {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  a." + db.AUTHOR_ID
                        + " FROM " + db.AUTHOR_TABLE + " a "
                        + " WHERE a." + db.AUTHOR_NAME + " = '" + aname + "' " ;

        Cursor c = database.rawQuery(selectQuery, null);

        if (c.getCount() > 0)
            return 1 ; // author exists
        else
            return 0 ; // author NOT exists
    }

    public int check_publisher_name(String pname) {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  p." + db.PUBLISHER_ID
                        + " FROM " + db.PUBLISHER_TABLE + " p "
                        + " WHERE p." + db.PUBLISHER_NAME + " = '" + pname + "' " ;

        Cursor c = database.rawQuery(selectQuery, null);

        if (c.getCount() > 0)
            return 1 ; // publisher exists
        else
            return 0 ; // publisher NOT exists
    }

    public int check_category_name(String cname) {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  c." + db.CATEGORY_ID
                        + " FROM " + db.CATEGORY_TABLE + " c "
                        + " WHERE c." + db.CATEGORY_NAME + " = '" + cname + "' " ;

        Cursor c = database.rawQuery(selectQuery, null);

        if (c.getCount() > 0)
            return 1 ; // category exists
        else
            return 0 ; // category NOT exists
    }

    public void add_author(String author_id) {
        SQLiteDatabase database = db.getWritableDatabase();
        database.execSQL("INSERT INTO " + db.AUTHOR_TABLE + "(" + db.AUTHOR_ID + "," + db.AUTHOR_NAME + ") " +
                "VALUES" + "( '" + author_id +"' , '" + Add_Book.AUTHOR_NAME + "' )");
       /* SQLiteDatabase database = db.getWritableDatabase();

        ContentValues contentValues1 = new  ContentValues();
        contentValues1.put(db.AUTHOR_ID , author_id);
        contentValues1.put(db.AUTHOR_NAME , Add_Book.AUTHOR_NAME);
        database.insert(db.AUTHOR_TABLE, null, contentValues1 ); */
    }

    public void add_publisher(String publisher_id, String publisher_email, String publisher_address) {
        SQLiteDatabase database = db.getWritableDatabase();

        database.execSQL("INSERT INTO " + db.PUBLISHER_TABLE + "(" + db.PUBLISHER_ID + "," + db.PUBLISHER_NAME + " , "
                + db.PUBLISHER_ADDRESS + " , " + db.PUBLISHER_EMAIL +") "
                + "VALUES" + "('" + publisher_id + "', '" + Add_Book.PUBLISHER_NAME + "', '" + publisher_address + "', " +
                "'" + publisher_email + "')");
    /*    SQLiteDatabase database = db.getWritableDatabase();

        ContentValues contentValues1 = new  ContentValues();
        contentValues1.put(db.PUBLISHER_ID , publisher_id);
        contentValues1.put(db.PUBLISHER_NAME , Add_Book.PUBLISHER_NAME);
        contentValues1.put(db.PUBLISHER_EMAIL , publisher_email);
        contentValues1.put(db.PUBLISHER_ADDRESS , publisher_address);
        database.insert(db.PUBLISHER_TABLE, null, contentValues1 ); */
    }

    public void add_category(String category_id) {
        SQLiteDatabase database = db.getWritableDatabase();

        database.execSQL("INSERT INTO " + db.CATEGORY_TABLE + "(" + db.CATEGORY_ID + "," + db.CATEGORY_NAME + ") " +
                "VALUES" + "('" + category_id + "' , '" + Add_Book.CATEGORY_NAME +"')");
     /*   SQLiteDatabase database = db.getWritableDatabase();

        ContentValues contentValues1 = new  ContentValues();
        contentValues1.put(db.CATEGORY_ID , category_id);
        contentValues1.put(db.CATEGORY_NAME , Add_Book.CATEGORY_NAME);
        database.insert(db.CATEGORY_TABLE, null, contentValues1 ); */
    }

    public void add_new_book() {
        SQLiteDatabase database = db.getWritableDatabase();

        String pid = find_pid() ;
        String aid = find_aid() ;
        String cid = find_cid() ;

        database.execSQL("INSERT INTO " + db.BOOK_TABLE + "(" + db.BOOK_ID + "," + db.BOOK_TITLE + " , " + db.NUMBER_OF_PAGES
                + " , " + db.IS_AVAILABLE + ") " + "VALUES" +
                "( '" + Add_Book.BOOK_ID + "' , '" + Add_Book.BOOK_TITLE + "' , " + Integer.parseInt(Add_Book.PAGES) + " ,1)");

        database.execSQL("INSERT INTO " + db.WRITE_TABLE + "(" + db.W_BID + "," + db.W_AID + ") VALUES" +
                "('" + Add_Book.BOOK_ID + "' , '"+ aid +"')");

        database.execSQL("INSERT INTO " + db.BELONGS_TO_TABLE + "(" + db.BT_BID + "," + db.BT_CID + ") " +
                "VALUES" + "('" + Add_Book.BOOK_ID  +"' , '" + cid + "')");

        database.execSQL("INSERT INTO " + db.PUBLISHED_TABLE + "(" + db.P_BID + "," + db.P_PID + " , " + db.PUBLISHED_YEAR  +")" +
                " VALUES" + "('" + Add_Book.BOOK_ID + "' , '" + pid + "' , " + Add_Book.YEAR + ")");

  /*      ContentValues contentValues1 = new  ContentValues();
        contentValues1.put(db.BOOK_TITLE , btitle);
        contentValues1.put(db.BOOK_ID , bid);
        contentValues1.put(db.NUMBER_OF_PAGES , pages);
        contentValues1.put(db.IS_AVAILABLE , 1);
        database.insert(db.BOOK_TABLE, null, contentValues1 );

        ContentValues contentValues2 = new  ContentValues();
        contentValues2.put(db.W_BID , bid);
        contentValues2.put(db.W_AID , aid);
        database.insert(db.WRITE_TABLE, null, contentValues2 );

        ContentValues contentValues3 = new  ContentValues();
        contentValues3.put(db.BT_CID , cid);
        contentValues3.put(db.BT_BID , bid);
        database.insert(db.BELONGS_TO_TABLE, null, contentValues3 );

        ContentValues contentValues4 = new  ContentValues();
        contentValues4.put(db.P_PID , pid);
        contentValues4.put(db.P_BID , bid);
        contentValues4.put(db.PUBLISHED_YEAR , year);
        database.insert(db.PUBLISHED_TABLE, null, contentValues4 ); */
    }

    private String find_cid() {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  c." + db.CATEGORY_ID
                        + " FROM " + db.CATEGORY_TABLE + " c "
                        + " WHERE c." + db.CATEGORY_NAME + " = '" + Add_Book.CATEGORY_NAME + "' " ;

        Cursor c = database.rawQuery(selectQuery, null);

        c.moveToFirst() ;
        return c.getString(c.getColumnIndex(db.CATEGORY_ID));
    }

    private String find_aid() {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  a." + db.AUTHOR_ID
                        + " FROM " + db.AUTHOR_TABLE + " a "
                        + " WHERE a." + db.AUTHOR_NAME + " = '" + Add_Book.AUTHOR_NAME + "' " ;

        Cursor c = database.rawQuery(selectQuery, null);

        c.moveToFirst() ;
        return c.getString(c.getColumnIndex(db.AUTHOR_ID));
    }

    private String find_pid() {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  p." + db.PUBLISHER_ID
                        + " FROM " + db.PUBLISHER_TABLE + " p "
                        + " WHERE p." + db.PUBLISHER_NAME + " = '" + Add_Book.PUBLISHER_NAME + "' " ;

        Cursor c = database.rawQuery(selectQuery, null);

        c.moveToFirst() ;
        return c.getString(c.getColumnIndex(db.PUBLISHER_ID));
    }

    public int check_book_id(String bid) {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  *"
                        + " FROM " + db.BOOK_TABLE + " b "
                        + "WHERE b." + db.BOOK_ID + " = '" + bid + "'" ;

        Cursor c = database.rawQuery(selectQuery, null);

        if (c.getCount() == 0)
            return 1; // book is not exists
        else
            return 0; // book is already exists
    }

    public int check_book_title(String btitle) {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  *"
                        + " FROM " + db.BOOK_TABLE + " b "
                        + "WHERE b." + db.BOOK_TITLE + " = '" + btitle + "'" ;

        Cursor c = database.rawQuery(selectQuery, null);

        if (c.getCount() == 0)
            return 1; // book is not exists
        else
            return 0; // book is already exists
    }

    public Cursor get_members() {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  u.*"
                        + " FROM " + db.USER_TABLE + " u " ;

        Cursor c = database.rawQuery(selectQuery, null);
        return c ;
    }

    public Cursor get_book_info() {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  b.*"
                        + " FROM " + db.BOOK_TABLE + " b "
                        + " WHERE b." + db.BOOK_ID + " = '" + Library_Books.book_id + "' ";

        Cursor c = database.rawQuery(selectQuery, null);
        return c ;
    }

    public Cursor get_author_info() {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  a.*"
                        + " FROM " + db.BOOK_TABLE + " b , " + db.AUTHOR_TABLE  + " a , " + db.WRITE_TABLE + " w "
                        + " WHERE a." + db.AUTHOR_ID + " = w." + db.W_AID
                        + " AND b." + db.BOOK_ID + " = w." + db.W_BID
                        + " AND b." + db.BOOK_ID + " = '" + Library_Books.book_id + "' ";

        Cursor c = database.rawQuery(selectQuery, null);
        return c ;
    }

    public Cursor get_publisher_info() {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  p.* , pb." + db.PUBLISHED_YEAR
                        + " FROM " + db.BOOK_TABLE + " b , " + db.PUBLISHER_TABLE  + " p , " + db.PUBLISHED_TABLE + " pb "
                        + " WHERE p." + db.PUBLISHER_ID + " = pb." + db.P_PID
                        + " AND b." + db.BOOK_ID + " = pb." + db.P_BID
                        + " AND b." + db.BOOK_ID + " = '" + Library_Books.book_id + "' ";

        Cursor c = database.rawQuery(selectQuery, null);
        return c ;
    }

    public Cursor get_category_info() {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  c.*"
                        + " FROM " + db.BOOK_TABLE + " b , " + db.CATEGORY_TABLE  + " c , " + db.BELONGS_TO_TABLE + " bt "
                        + " WHERE c." + db.CATEGORY_ID + " = bt." + db.BT_CID
                        + " AND b." + db.BOOK_ID + " = bt." + db.BT_BID
                        + " AND b." + db.BOOK_ID + " = '" + Library_Books.book_id + "' ";

        Cursor c = database.rawQuery(selectQuery, null);
        return c ;
    }

    public Cursor get_returned_books2() {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  b." + db.BOOK_ID
                        + " FROM " + db.BOOK_TABLE + " b , " + db.USER_TABLE  + " u , " + db.BORROW_TABLE + " bw "
                        + " WHERE u." + db.USER_ID + " = bw." + db.B_UID
                        + " AND b." + db.BOOK_ID + " = bw." + db.B_BID
                        + " AND u." + db.USER_ID + " = '" + Show_Members.user_id + "' "
                        + " AND bw." + db.IS_RETURNED + " = 1" ;

        Cursor c = database.rawQuery(selectQuery, null);
        return c ;
    }

    public Cursor get_bbooks() {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  b.*"
                        + " FROM " + db.BOOK_TABLE + " b"
                        + " WHERE b." + db.IS_AVAILABLE + " = 1" ;

        Cursor c = database.rawQuery(selectQuery, null);
        return c ;
    }

    public boolean borrow() {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(db.IS_AVAILABLE, 0);
        int i =  database.update(db.BOOK_TABLE, args, db.BOOK_ID + " = '" + Borrow.bid + "' " , null);
        return i > 0;

     /*   SQLiteDatabase database = db.getWritableDatabase();
        String strSQL = "UPDATE " + db.BOOK_TABLE
                +" SET " + db.IS_AVAILABLE + " = 0"
                + " WHERE " + db.BOOK_ID + " = '" + Borrow.bid + "' " ;
        database.execSQL(strSQL) ; */
    }

    public Cursor get_book_info2() {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  b.*"
                        + " FROM " + db.BOOK_TABLE + " b "
                        + " WHERE b." + db.BOOK_ID + " = '" + Borrow.bid + "' ";

        Cursor c = database.rawQuery(selectQuery, null);
        return c ;
    }

    public Cursor get_author_info2() {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  a.*"
                        + " FROM " + db.BOOK_TABLE + " b , " + db.AUTHOR_TABLE  + " a , " + db.WRITE_TABLE + " w "
                        + " WHERE a." + db.AUTHOR_ID + " = w." + db.W_AID
                        + " AND b." + db.BOOK_ID + " = w." + db.W_BID
                        + " AND b." + db.BOOK_ID + " = '" + Borrow.bid + "' ";

        Cursor c = database.rawQuery(selectQuery, null);
        return c ;
    }

    public Cursor get_publisher_info2() {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  p.* , pb." + db.PUBLISHED_YEAR
                        + " FROM " + db.BOOK_TABLE + " b , " + db.PUBLISHER_TABLE  + " p , " + db.PUBLISHED_TABLE + " pb "
                        + " WHERE p." + db.PUBLISHER_ID + " = pb." + db.P_PID
                        + " AND b." + db.BOOK_ID + " = pb." + db.P_BID
                        + " AND b." + db.BOOK_ID + " = '" + Borrow.bid + "' ";

        Cursor c = database.rawQuery(selectQuery, null);
        return c ;
    }

    public Cursor get_category_info2() {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  c.*"
                        + " FROM " + db.BOOK_TABLE + " b , " + db.CATEGORY_TABLE  + " c , " + db.BELONGS_TO_TABLE + " bt "
                        + " WHERE c." + db.CATEGORY_ID + " = bt." + db.BT_CID
                        + " AND b." + db.BOOK_ID + " = bt." + db.BT_BID
                        + " AND b." + db.BOOK_ID + " = '" + Borrow.bid + "' ";

        Cursor c = database.rawQuery(selectQuery, null);
        return c ;
    }

    public void add_borrow_book() {
        SQLiteDatabase database = db.getWritableDatabase();

        ContentValues contentValues1 = new  ContentValues();
        contentValues1.put(db.B_UID , Login.user_id);
        contentValues1.put(db.B_BID , Borrow.bid);
        contentValues1.put(db.START_B_DATE , current_date());
        contentValues1.put(db.END_B_DATE , get_end_b_date());
        contentValues1.put(db.IS_LATE , 0);
        contentValues1.put(db.IS_RETURNED , 0);
        database.insert(db.BORROW_TABLE, null, contentValues1 );
    }

    @NonNull
    private String get_end_b_date() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = new java.util.Date();
        String[] now =  sdf.format(date).toString().split("-"); // current date
        int day , month , year ;
        String end_date = null ;

        if (Integer.parseInt(now[1]) == 1 || Integer.parseInt(now[1]) == 3
                || Integer.parseInt(now[1]) == 5 || Integer.parseInt(now[1]) == 7
                || Integer.parseInt(now[1]) == 8 || Integer.parseInt(now[1]) == 10){
            if (Integer.parseInt(now[0]) >= 1 && Integer.parseInt(now[0]) <= 16) {
                day = Integer.parseInt(now[0]) + 15;
                end_date = Integer.toString(day) + "-" + now[1] + "-" + now[2] ;
            }
            else{
                day = Integer.parseInt(now[0]) - 15 ;
                month = Integer.parseInt(now[1]) + 1 ;
                end_date = Integer.toString(day) + "-" + Integer.toString(month) + "-" + now[2] ;
            }
        }
        else
            if (Integer.parseInt(now[1]) == 4 || Integer.parseInt(now[1]) == 6
                    || Integer.parseInt(now[1]) == 9 || Integer.parseInt(now[1]) == 11){
                if (Integer.parseInt(now[0]) >= 1 && Integer.parseInt(now[0]) <= 15 ) {
                    day = Integer.parseInt(now[0]) + 15;
                    end_date = Integer.toString(day) + "-" + now[1] + "-" + now[2] ;
                }
                else{
                    day = Integer.parseInt(now[0]) - 15 ;
                    month =  Integer.parseInt(now[1]) + 1 ;
                    end_date = Integer.toString(day) + "-" + Integer.toString(month) + "-" + now[2] ;
                }
            }
        else
                if ( Integer.parseInt(now[1]) == 2){
                    if ( Integer.parseInt(now[0]) >= 1 &&  Integer.parseInt(now[0]) <= 13) {
                        day = Integer.parseInt(now[0]) + 13;
                        end_date = Integer.toString(day) + "-" + now[1] + "-" + now[2] ;
                    }
                    else {
                        day =  Integer.parseInt(now[0]) - 13 ;
                        month =  Integer.parseInt(now[1]) + 1 ;
                        end_date = Integer.toString(day) + "-" + Integer.toString(month) + "-" + now[2] ;
                    }
                }
        else
                    if ( Integer.parseInt(now[1]) == 12){
                        if ( Integer.parseInt(now[0]) >= 1 &&  Integer.parseInt(now[0]) <= 16) {
                            day = Integer.parseInt(now[0]) + 15;
                            end_date = Integer.toString(day) + "-" + now[1] + "-" + now[2] ;
                        }
                        else{
                            day =  Integer.parseInt(now[0]) - 15 ;
                            month = 1 ;
                            year =  Integer.parseInt(now[2]) + 1 ;
                            end_date = Integer.toString(day) + "-" + Integer.toString(month) + "-" + Integer.toString(year) ;
                        }
                    }
        return end_date ;
    }

    @NonNull
    private String current_date() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = new java.util.Date();
        return sdf.format(date).toString(); // current date
    }

    public Cursor get_borrow_books() {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  b.*"
                        + " FROM " + db.BOOK_TABLE + " b , " + db.USER_TABLE  + " u , " + db.BORROW_TABLE + " bw "
                        + " WHERE u." + db.USER_ID + " = bw." + db.B_UID
                        + " AND b." + db.BOOK_ID + " = bw." + db.B_BID
                        + " AND u." + db.USER_ID + " = '" + Login.user_id + "' "
                        + " AND bw." + db.IS_RETURNED + " = 0";

        Cursor c = database.rawQuery(selectQuery, null);
        return c ;
    }

    public Cursor get_borrow_info() {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  bw.*"
                        + " FROM " + db.BOOK_TABLE + " b , " + db.USER_TABLE  + " u , " + db.BORROW_TABLE + " bw "
                        + " WHERE u." + db.USER_ID + " = bw." + db.B_UID
                        + " AND b." + db.BOOK_ID + " = bw." + db.B_BID
                        + " AND b." + db.BOOK_ID + " = '" + Return.bid + "' "
                        + " AND u." + db.USER_ID + " = '" + Login.user_id + "' ";

        Cursor c = database.rawQuery(selectQuery, null);
        return c ;
    }

    public int check_late(String edate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = new java.util.Date();
        String [] now = sdf.format(date).toString().split("-"); // current date
        String [] end = edate.split("-") ;

        if (Integer.parseInt(now[2]) > Integer.parseInt(end[2]))
            return 1 ;
        else if (Integer.parseInt(now[2]) == Integer.parseInt(end[2])){
            if (Integer.parseInt(now[1]) > Integer.parseInt(end[1]))
                return 1 ;
            else
                if (Integer.parseInt(now[1]) == Integer.parseInt(end[1]))
                    if (Integer.parseInt(now[0]) > Integer.parseInt(end[0]))
                        return 1 ;
        }
        return 0 ;
    }

    public boolean return_book() {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(db.IS_AVAILABLE, 1);
        int i =  database.update(db.BOOK_TABLE, args, db.BOOK_ID + " = '" + Return.bid + "' " , null);
        return i > 0;
    }

    public boolean update_borrow_table() {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(db.IS_RETURNED, 1);
        int i =  database.update(db.BORROW_TABLE ,
                args, db.B_BID + " = '" + Return.bid + "' " + " AND " + db.B_UID + " = '" + Login.user_id + "'" , null);
        return i > 0;
    }

    public Cursor get_book_info3() {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  b.*"
                        + " FROM " + db.BOOK_TABLE + " b "
                        + " WHERE b." + db.BOOK_ID + " = '" + Return.bid + "' ";

        Cursor c = database.rawQuery(selectQuery, null);
        return c ;
    }

    public Cursor get_author_info3() {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  a.*"
                        + " FROM " + db.BOOK_TABLE + " b , " + db.AUTHOR_TABLE  + " a , " + db.WRITE_TABLE + " w "
                        + " WHERE a." + db.AUTHOR_ID + " = w." + db.W_AID
                        + " AND b." + db.BOOK_ID + " = w." + db.W_BID
                        + " AND b." + db.BOOK_ID + " = '" + Return.bid + "' ";

        Cursor c = database.rawQuery(selectQuery, null);
        return c ;
    }

    public Cursor get_publisher_info3() {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  p.* , pb." + db.PUBLISHED_YEAR
                        + " FROM " + db.BOOK_TABLE + " b , " + db.PUBLISHER_TABLE  + " p , " + db.PUBLISHED_TABLE + " pb "
                        + " WHERE p." + db.PUBLISHER_ID + " = pb." + db.P_PID
                        + " AND b." + db.BOOK_ID + " = pb." + db.P_BID
                        + " AND b." + db.BOOK_ID + " = '" + Return.bid + "' ";

        Cursor c = database.rawQuery(selectQuery, null);
        return c ;
    }

    public Cursor get_category_info3() {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  c.*"
                        + " FROM " + db.BOOK_TABLE + " b , " + db.CATEGORY_TABLE  + " c , " + db.BELONGS_TO_TABLE + " bt "
                        + " WHERE c." + db.CATEGORY_ID + " = bt." + db.BT_CID
                        + " AND b." + db.BOOK_ID + " = bt." + db.BT_BID
                        + " AND b." + db.BOOK_ID + " = '" + Return.bid + "' ";

        Cursor c = database.rawQuery(selectQuery, null);
        return c ;
    }

    public Cursor get_all_books() {
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery =
                "SELECT  b.*"
                        + " FROM " + db.BOOK_TABLE + " b " ;

        Cursor c = database.rawQuery(selectQuery, null);
        return c ;
    }

    // this inner class implements the encapsulation concept
    class DB extends SQLiteOpenHelper {

        // DataBase name
        private static final String DATABASE_NAME = "Library.db";

        // Data Base version
        private static final int DATABASE_VERSION = 1;

        // Table names
        final String BOOK_TABLE = "book";
        final String AUTHOR_TABLE = "author";
        final String PUBLISHER_TABLE = "publisher";
        final String USER_TABLE = "user";
        final String CATEGORY_TABLE = "category";
        final String WRITE_TABLE = "write";
        final String BELONGS_TO_TABLE = "belongs_to";
        final String BORROW_TABLE = "borrow";
        final String PUBLISHED_TABLE = "published";

        // Columns names
        // --> Book table
        final String BOOK_ID = "book_id";
        final String BOOK_TITLE = "book_title";
        final String NUMBER_OF_PAGES = "number_of_pages";
        final String IS_AVAILABLE = "is_available";
        // --> Author table
        final String AUTHOR_ID = "author_id";
        final String AUTHOR_NAME = "author_name";
        // --> Publisher table
        final String PUBLISHER_ID = "publisher_id";
        final String PUBLISHER_NAME = "publisher_name";
        final String PUBLISHER_EMAIL = "publisher_email";
        final String PUBLISHER_ADDRESS = "publisher_address";
        // --> User table
        final String USER_ID = "user_id";
        final String USER_NAME = "user_name";
        final String USER_ADDRESS = "user_address";
        final String USER_PASSWORD = "user_password";
        final String USER_EMAIL = "user_email";
        final String USER_GENDER = "user_gender";
        final String USER_DOB = "user_dob"; // DOB = Data Of Birth
        final String START_P_DATE = "start_p_date";
        final String END_P_DATE = "end_p_date";
        final String USER_IMAGE = "user_image";
        // --> Category table
        final String CATEGORY_ID = "category_id";
        final String CATEGORY_NAME = "category_name";
        // --> Write table
        final String W_AID = "w_aid";
        final String W_BID = "w_bid";
        // --> Belongs_to table
        final String BT_BID = "bt_bid";
        final String BT_CID = "bt_cid";
        // --> Borrow table
        final String B_UID = "b_uid";
        final String B_BID = "b_bid";
        final String IS_LATE = "is_late";
        final String IS_RETURNED = "is_returned";
        final String START_B_DATE = "start_b_date";
        final String END_B_DATE = "end_b_date";
        // --> Published table
        final String P_PID = "p_pid";
        final String P_BID = "p_bid";
        final String PUBLISHED_YEAR = "published_year";

        // Create table statements
        String CREATE_TABLE_BOOK = create_book();
        String CREATE_TABLE_AUTHOR = create_author();
        String CREATE_TABLE_PUBLISHER = create_publisher();
        String CREATE_TABLE_USER = create_user();
        String CREATE_TABLE_CATEGORY = create_category();
        String CREATE_TABLE_WRITE = create_write();
        String CREATE_TABLE_BELONGS_TO = create_belongs_to();
        String CREATE_TABLE_BORROW = create_borrow();
        String CREATE_TABLE_PUBLISHED = create_published();

        public DB(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // Create all tables
            db.execSQL(CREATE_TABLE_BOOK);
            db.execSQL(CREATE_TABLE_AUTHOR);
            db.execSQL(CREATE_TABLE_PUBLISHER);
            db.execSQL(CREATE_TABLE_USER);
            db.execSQL(CREATE_TABLE_CATEGORY);
            db.execSQL(CREATE_TABLE_WRITE);
            db.execSQL(CREATE_TABLE_BELONGS_TO);
            db.execSQL(CREATE_TABLE_BORROW);
            db.execSQL(CREATE_TABLE_PUBLISHED);

            // Inserting data into the tables
            add_book(db) ;
            add_author(db) ;
            add_publisher(db) ;
            add_user(db) ;
            add_category(db) ;
            add_write(db) ;
            add_belongs_to(db) ;
            add_borrow(db) ;
            add_published(db) ;
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // onUpgrade drop older tables
            db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_BOOK);
            db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_AUTHOR);
            db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_PUBLISHER);
            db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_USER);
            db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_CATEGORY);
            db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_WRITE);
            db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_BELONGS_TO);
            db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_BORROW);
            db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_PUBLISHED);

            // Create new tables
            onCreate(db);
        }

        public String create_book() {
            return "CREATE TABLE " + BOOK_TABLE + "("
                    + BOOK_ID + " TEXT PRIMARY KEY NOT NULL , "
                    + BOOK_TITLE + " TEXT UNIQUE NOT NULL , "
                    + NUMBER_OF_PAGES + " INTEGER , "
                    + IS_AVAILABLE + " INTEGER )";
        }

        public String create_author() {
            return "CREATE TABLE " + AUTHOR_TABLE + "("
                    + AUTHOR_ID + " TEXT PRIMARY KEY NOT NULL , "
                    + AUTHOR_NAME + " TEXT NOT NULL " + ")";
        }

        public String create_publisher() {
            return "CREATE TABLE " + PUBLISHER_TABLE + "("
                    + PUBLISHER_ID + " TEXT PRIMARY KEY NOT NULL , "
                    + PUBLISHER_NAME + " TEXT NOT NULL , "
                    + PUBLISHER_ADDRESS + " TEXT , "
                    + PUBLISHER_EMAIL + " TEXT " + ")";
        }

        public String create_user() {
            return "CREATE TABLE " + USER_TABLE + "("
                    + USER_ID + " TEXT PRIMARY KEY NOT NULL , "
                    + USER_NAME + " TEXT NOT NULL , "
                    + USER_PASSWORD + " TEXT NOT NULL , "
                    + USER_ADDRESS + " TEXT , "
                    + USER_EMAIL + " TEXT NOT NULL ,"
                    + USER_GENDER + " TEXT , "
                    + USER_DOB + " DATE , "
                    + START_P_DATE + " DATE NOT NULL , "
                    + END_P_DATE + " DATE NOT NULL , "
                    + USER_IMAGE + " BLOB " + " )";
        }

        public String create_category() {
            return "CREATE TABLE " + CATEGORY_TABLE + "("
                    + CATEGORY_ID + " TEXT PRIMARY KEY NOT NULL , "
                    + CATEGORY_NAME + " TEXT NOT NULL " + ")";
        }

        public String create_write() {
            return "CREATE TABLE " + WRITE_TABLE + "("
                    + W_AID + " TEXT REFERENCES " + AUTHOR_TABLE + " , "
                    + W_BID + " TEXT REFERENCES " + BOOK_TABLE + " , "
                    + "CONSTRAINT P_S_ID PRIMARY KEY ( " + W_AID + " , " + W_BID + " ) " + ")";
        }

        public String create_belongs_to() {
            return "CREATE TABLE " + BELONGS_TO_TABLE + "("
                    + BT_BID + " TEXT REFERENCES " + BOOK_TABLE + " , "
                    + BT_CID + " TEXT REFERENCES " + CATEGORY_TABLE + " , "
                    + "CONSTRAINT P_S_ID PRIMARY KEY ( " + BT_BID + " , " + BT_CID + " ) " + ")";
        }

        public String create_borrow() {
            return "CREATE TABLE " + BORROW_TABLE + "("
                    + B_BID + " TEXT REFERENCES " + BOOK_TABLE + " , "
                    + B_UID + " TEXT REFERENCES " + USER_TABLE + " , "
                    + IS_LATE + " INTEGER , "
                    + IS_RETURNED + " INTEGER , "
                    + START_B_DATE + " DATE , "
                    + END_B_DATE + " DATE , "
                    + "CONSTRAINT P_S_ID PRIMARY KEY ( " + B_BID + " , " + B_UID + " ) " + ")";
        }

        public String create_published() {
            return "CREATE TABLE " + PUBLISHED_TABLE + "("
                    + P_BID + " TEXT PRIMARY KEY NOT NULL, "
                    + P_PID + " TEXT ,"
                    + PUBLISHED_YEAR + " INTEGER , "
                    + " FOREIGN KEY ( " + P_BID + " ) REFERENCES " +
                    BOOK_TABLE + "( " + BOOK_ID + " ) ,"
                    + " FOREIGN KEY ( " + P_PID + " ) REFERENCES " +
                    PUBLISHER_TABLE + "( " + P_PID + " )" + ")";
        }

        public void add_book(SQLiteDatabase db){
            db.execSQL("INSERT INTO " + BOOK_TABLE + "(" + BOOK_ID + "," + BOOK_TITLE + " , " + NUMBER_OF_PAGES
                    + " , " + IS_AVAILABLE +") " +
                    "VALUES" + "( '0000' , 'The Da vinci code' , 489 , 0 )");
            db.execSQL("INSERT INTO " + BOOK_TABLE + "(" + BOOK_ID + "," + BOOK_TITLE + " , " + NUMBER_OF_PAGES
                    + " , " + IS_AVAILABLE +") " +
                    "VALUES" + "( '0001' , 'The economy today', 506 ,1 )");
            db.execSQL("INSERT INTO " + BOOK_TABLE + "(" + BOOK_ID + "," + BOOK_TITLE + " , " + NUMBER_OF_PAGES
                    + " , " + IS_AVAILABLE +") " +
                    "VALUES" + "( '0002' , 'Archaeology Under Dictatorship', 204 ,0 )");
            db.execSQL("INSERT INTO " + BOOK_TABLE + "(" + BOOK_ID + "," + BOOK_TITLE + " , " + NUMBER_OF_PAGES
                    + " , " + IS_AVAILABLE +") " +
                    "VALUES" + "( '0003' , 'Art and archaeology technical a', 512 ,0 )");
            db.execSQL("INSERT INTO " + BOOK_TABLE + "(" + BOOK_ID + "," + BOOK_TITLE + " , " + NUMBER_OF_PAGES
                    + " , " + IS_AVAILABLE +") " +
                    "VALUES" + "( '0004' , 'War power, police power', 315 ,1)");
            db.execSQL("INSERT INTO " + BOOK_TABLE + "(" + BOOK_ID + "," + BOOK_TITLE + " , " + NUMBER_OF_PAGES
                    +" , " + IS_AVAILABLE + ") " +
                    "VALUES" + "( '0005' , 'Jane Eyre', 500 ,1)");
            db.execSQL("INSERT INTO " + BOOK_TABLE + "(" + BOOK_ID + "," + BOOK_TITLE + " , " + NUMBER_OF_PAGES
                    +" , " + IS_AVAILABLE + ") " +
                    "VALUES" + "( '0006' , 'Murder on the Orient Express', 200 ,1 )");
            db.execSQL("INSERT INTO " + BOOK_TABLE + "(" + BOOK_ID + "," + BOOK_TITLE + " , " + NUMBER_OF_PAGES
                    +" , " + IS_AVAILABLE +") " +
                    "VALUES" + "( '0007' , 'The Power of Now', 130 ,1)");
            db.execSQL("INSERT INTO " + BOOK_TABLE + "(" + BOOK_ID + "," + BOOK_TITLE + " , " + NUMBER_OF_PAGES
                    +" , " + IS_AVAILABLE+") " +
                    "VALUES" + "( '0008' ,'The Language Instinct: How the', 250 , 1)");
            db.execSQL("INSERT INTO " + BOOK_TABLE + "(" + BOOK_ID + "," + BOOK_TITLE + " , " + NUMBER_OF_PAGES
                    +" , " + IS_AVAILABLE+ ") " +
                    "VALUES" + "( '0009' , 'In the Footsteps of Crazy Horse', 320 ,1 )");
        /*    db.execSQL("INSERT INTO " + BOOK_TABLE + "(" + BOOK_ID + "," + BOOK_TITLE + " , " + NUMBER_OF_PAGES
                    +" , " + IS_AVAILABLE+") " +
                    "VALUES" + "( '0010' ,'The Mysterious Affair at Style', 100 ,1)");
            db.execSQL("INSERT INTO " + BOOK_TABLE + "(" + BOOK_ID + "," + BOOK_TITLE + " , " + NUMBER_OF_PAGES
                    +" , " + IS_AVAILABLE+") " +
                    "VALUES" + "( '0011' ,'The Secret Adversary', 150 ,1 )");
            db.execSQL("INSERT INTO " + BOOK_TABLE + "(" + BOOK_ID + "," + BOOK_TITLE + " , " + NUMBER_OF_PAGES
                    +" , " + IS_AVAILABLE+") " +
                    "VALUES" + "( '0012' , 'The Big Four', 200 ,1 )");
            db.execSQL("INSERT INTO " + BOOK_TABLE + "(" + BOOK_ID + "," + BOOK_TITLE + " , " + NUMBER_OF_PAGES
                    +" , " + IS_AVAILABLE+") " +
                    "VALUES" + "( '0013' ,'The Floating Admiral', 322 ,1 )");
            db.execSQL("INSERT INTO " + BOOK_TABLE + "(" + BOOK_ID + "," + BOOK_TITLE + " , " + NUMBER_OF_PAGES
                    +" , " + IS_AVAILABLE+") " +
                    "VALUES" + "( '0014' ,'Peril at End House', 430 ,1 )");
            db.execSQL("INSERT INTO " + BOOK_TABLE + "(" + BOOK_ID + "," + BOOK_TITLE + " , " + NUMBER_OF_PAGES
                    +" , " + IS_AVAILABLE+") " +
                    "VALUES" + "( '0015' ,'Unfinished Portrait', 360 ,1 )");
            db.execSQL("INSERT INTO " + BOOK_TABLE + "(" + BOOK_ID + "," + BOOK_TITLE + " , " + NUMBER_OF_PAGES
                    +" , " + IS_AVAILABLE+ ") " +
                    "VALUES" + "( '0016' ,'Organic Synthesis:The Disconne', 900 ,1 )");
            db.execSQL("INSERT INTO " + BOOK_TABLE + "(" + BOOK_ID + "," + BOOK_TITLE + " , " + NUMBER_OF_PAGES
                    +" , " + IS_AVAILABLE+ ") " +
                    "VALUES" + "( '0017' ,'Workbook for Organic Synthesis', 800 ,1 )");
            db.execSQL("INSERT INTO " + BOOK_TABLE + "(" + BOOK_ID + "," + BOOK_TITLE + " , " + NUMBER_OF_PAGES
                    +" , " + IS_AVAILABLE +") " +
                    "VALUES" + "( '0018' ,'Harry Potter and the Philosophy', 200 ,1 )");
            db.execSQL("INSERT INTO " + BOOK_TABLE + "(" + BOOK_ID + "," + BOOK_TITLE + " , " + NUMBER_OF_PAGES
                    +" , " + IS_AVAILABLE+") " +
                    "VALUES" + "( '0019' ,'Harry Potter and the Chamber of', 400 ,1 )");
            db.execSQL("INSERT INTO " + BOOK_TABLE + "(" + BOOK_ID + "," + BOOK_TITLE + " , " + NUMBER_OF_PAGES
                    +" , " + IS_AVAILABLE+") " +
                    "VALUES" + "( '0020' ,'Harry Potter and the Prisoner ', 500 ,0 )");
            db.execSQL("INSERT INTO " + BOOK_TABLE + "(" + BOOK_ID + "," + BOOK_TITLE + " , " + NUMBER_OF_PAGES
                    +" , " + IS_AVAILABLE  +") " +
                    "VALUES" + "( '0021' ,'Harry Potter and the Goblet of', 573 ,1 )");
            db.execSQL("INSERT INTO " + BOOK_TABLE + "(" + BOOK_ID + "," + BOOK_TITLE + " , " + NUMBER_OF_PAGES
                    +" , " + IS_AVAILABLE + ") " +
                    "VALUES" + "( '0022' ,'Harry Potter and the Order of', 600 ,1 )");
            db.execSQL("INSERT INTO " + BOOK_TABLE + "(" + BOOK_ID + "," + BOOK_TITLE + " , " + NUMBER_OF_PAGES
                    +" , " + IS_AVAILABLE + ") " +
                    "VALUES" + "( '0023' ,'Harry Potter and the Half-Bloo', 732 , 0 )");
            db.execSQL("INSERT INTO " + BOOK_TABLE + "(" + BOOK_ID + "," + BOOK_TITLE + " , " + NUMBER_OF_PAGES
                    +" , " + IS_AVAILABLE + " ) " +
                    "VALUES" + "( '0024' , 'Harry Potter and the Deathly H', 765 ,1 )"); */
        }

        public void add_author(SQLiteDatabase db){
            db.execSQL("INSERT INTO " + AUTHOR_TABLE + "(" + AUTHOR_ID + "," + AUTHOR_NAME + ") VALUES" + "('00001' , 'Dan Brown')");
            db.execSQL("INSERT INTO " + AUTHOR_TABLE + "(" + AUTHOR_ID + "," + AUTHOR_NAME + ") VALUES" + "('00002' , 'Charlotte Bronte')");
            db.execSQL("INSERT INTO " + AUTHOR_TABLE + "(" + AUTHOR_ID + "," + AUTHOR_NAME + ") VALUES" + "('00003' , 'Brandly schiller')");
            db.execSQL("INSERT INTO " + AUTHOR_TABLE + "(" + AUTHOR_ID + "," + AUTHOR_NAME + ") VALUES" + "('00004' , 'Frederick Driscoll')");
            db.execSQL("INSERT INTO " + AUTHOR_TABLE + "(" + AUTHOR_ID + "," + AUTHOR_NAME + ") VALUES" + "('00005' , 'Mark Neocleous')");
            db.execSQL("INSERT INTO " + AUTHOR_TABLE + "(" + AUTHOR_ID + "," + AUTHOR_NAME + ") VALUES" + "('00006' , 'Mahmoud Darwish')");
            db.execSQL("INSERT INTO " + AUTHOR_TABLE + "(" + AUTHOR_ID + "," + AUTHOR_NAME + ") VALUES" + "('00007' , 'agatha christie')");
            db.execSQL("INSERT INTO " + AUTHOR_TABLE + "(" + AUTHOR_ID + "," + AUTHOR_NAME + ") VALUES" + "('00008' , 'J. K. Rowling')");
            db.execSQL("INSERT INTO " + AUTHOR_TABLE + "(" + AUTHOR_ID + "," + AUTHOR_NAME + ") VALUES" + "('00009' , 'Eckhart Tolle')");
            db.execSQL("INSERT INTO " + AUTHOR_TABLE + "(" + AUTHOR_ID + "," + AUTHOR_NAME + ") VALUES" + "('00010' , 'Steven Pinker')");
            db.execSQL("INSERT INTO " + AUTHOR_TABLE + "(" + AUTHOR_ID + "," + AUTHOR_NAME + ") VALUES" + "('00011' , 'Joseph Marshall')");
            db.execSQL("INSERT INTO " + AUTHOR_TABLE + "(" + AUTHOR_ID + "," + AUTHOR_NAME + ") VALUES" + "('00012' , 'James Mark Yellowhawk')");
            db.execSQL("INSERT INTO " + AUTHOR_TABLE + "(" + AUTHOR_ID + "," + AUTHOR_NAME + ") VALUES" + "('00013' , 'Stuart Warren')");
            db.execSQL("INSERT INTO " + AUTHOR_TABLE + "(" + AUTHOR_ID + "," + AUTHOR_NAME + ") VALUES" + "('00014' , 'Paul Wyatt')");
        }

        public void add_publisher(SQLiteDatabase db){
            db.execSQL("INSERT INTO " + PUBLISHER_TABLE + "(" + PUBLISHER_ID + "," + PUBLISHER_NAME + " , "
                    + PUBLISHER_ADDRESS + " , " + PUBLISHER_EMAIL +") "
                    + "VALUES" + "('001', 'Anchor Book ', 'Newyork ', 'Anchor Book @yahoo.com ')");
            db.execSQL("INSERT INTO " + PUBLISHER_TABLE + "(" + PUBLISHER_ID + "," + PUBLISHER_NAME + " , "
                    + PUBLISHER_ADDRESS + " , " + PUBLISHER_EMAIL +") "
                    + "VALUES" + "('002', 'Oxford  university Press', 'Britain UK', 'onlinequeries.uk@oup.com')");
            db.execSQL("INSERT INTO " + PUBLISHER_TABLE + "(" + PUBLISHER_ID + "," + PUBLISHER_NAME + " , "
                    + PUBLISHER_ADDRESS + " , " + PUBLISHER_EMAIL +") "
                    + "VALUES" + "('003', 'Academic Press', ' UK', 'academic_Press@p.com')");
            db.execSQL("INSERT INTO " + PUBLISHER_TABLE + "(" + PUBLISHER_ID + "," + PUBLISHER_NAME + " , "
                    + PUBLISHER_ADDRESS + " , " + PUBLISHER_EMAIL +") "
                    + "VALUES" + "('004', 'Andrews McMeel Publishing, LLC', 'United States', 'andrewsmcmeel@mc.com')");
            db.execSQL("INSERT INTO " + PUBLISHER_TABLE + "(" + PUBLISHER_ID + "," + PUBLISHER_NAME + " , "
                    + PUBLISHER_ADDRESS + " , " + PUBLISHER_EMAIL +") "
                    + "VALUES" + "('005', 'Edinburgh University Press', 'Edinburgh', 'Edinburgh University.press@pre')");
            db.execSQL("INSERT INTO " + PUBLISHER_TABLE + "(" + PUBLISHER_ID + "," + PUBLISHER_NAME + " , "
                    + PUBLISHER_ADDRESS + " , " + PUBLISHER_EMAIL +") "
                    + "VALUES" + "('006', 'Austin Macauley Publishers Lim', 'United Kingdom', 'austinmacauley@59.com')");
            db.execSQL("INSERT INTO " + PUBLISHER_TABLE + "(" + PUBLISHER_ID + "," + PUBLISHER_NAME + " , "
                    + PUBLISHER_ADDRESS + " , " + PUBLISHER_EMAIL +") "
                    + "VALUES" + "('007', 'Arcadia Publishing', 'Mount Pleasant South Carolina', 'Arcadia.Publishing@hotmail.com')");
            db.execSQL("INSERT INTO " + PUBLISHER_TABLE + "(" + PUBLISHER_ID + "," + PUBLISHER_NAME + " , "
                    + PUBLISHER_ADDRESS + " , " + PUBLISHER_EMAIL +") "
                    + "VALUES" + "('008', 'Aladdin Paperbacks', 'America carolina', 'Aladdin.Paperbacks@yahoo.com')");
            db.execSQL("INSERT INTO " + PUBLISHER_TABLE + "(" + PUBLISHER_ID + "," + PUBLISHER_NAME + " , "
                    + PUBLISHER_ADDRESS + " , " + PUBLISHER_EMAIL +") "
                    + "VALUES" + "('009', 'Pavilion Books Holdings Ltd', 'London WC', 'Pavilion Books@hotmail.com')");
        }

        public void add_user(SQLiteDatabase db){
            db.execSQL("INSERT INTO " + USER_TABLE + "(" + USER_ID + "," + USER_NAME + " , " + USER_ADDRESS + " , "
                    + USER_PASSWORD + " , " + USER_EMAIL + " , " + USER_GENDER + " , " + USER_DOB + " , "
                    + START_P_DATE + " , " + END_P_DATE + " , " + USER_IMAGE +") " +
                    "VALUES" + "('Maria12345' , 'Maria' , 'Ramallah' , '12345' , 'mariazayed96@gmail.com' , 'female' , " +
                    "'21-4-1996'  , '20-06-2016' , '20-06-2017' , 'null' )");
            db.execSQL("INSERT INTO " + USER_TABLE + "(" + USER_ID + "," + USER_NAME + " , " + USER_ADDRESS + " , "
                    + USER_PASSWORD + " , " + USER_EMAIL + " , " + USER_GENDER + " , " + USER_DOB + " , "
                    + START_P_DATE + " , " + END_P_DATE + " , " + USER_IMAGE + ") " +
                    "VALUES" + "('Massa12345' , 'Massa' , 'Ramallah' , '12345' , 'massa.salah18@gmail.com' , 'female' , " +
                    "'1-1-1996'  , '18-07-2016' , '18-07-2017' , 'null' )");
            db.execSQL("INSERT INTO " + USER_TABLE + "(" + USER_ID + "," + USER_NAME + " , " + USER_ADDRESS + " , "
                    + USER_PASSWORD + " , " + USER_EMAIL + " , " + USER_GENDER + " , " + USER_DOB + " , " +
                    START_P_DATE + " , " + END_P_DATE + " , " + USER_IMAGE + ") " +
                    "VALUES" + "('User12345' , 'User' , 'Ramallah' , '12345' , '--------------' , 'female' , " +
                    "'20-5-1996' , '18-07-2016' , '18-02-2017' , 'null' )");
        }

        public void add_category(SQLiteDatabase db){
            db.execSQL("INSERT INTO " + CATEGORY_TABLE + "(" + CATEGORY_ID + "," + CATEGORY_NAME + ") VALUES" + "('01' , 'Novels')");
            db.execSQL("INSERT INTO " + CATEGORY_TABLE + "(" + CATEGORY_ID + "," + CATEGORY_NAME + ") VALUES" + "('02' , 'Classics')");
            db.execSQL("INSERT INTO " + CATEGORY_TABLE + "(" + CATEGORY_ID + "," + CATEGORY_NAME + ") VALUES" + "('03' , 'Archaeology')");
            db.execSQL("INSERT INTO " + CATEGORY_TABLE + "(" + CATEGORY_ID + "," + CATEGORY_NAME + ") VALUES" + "('04' , 'Art')");
            db.execSQL("INSERT INTO " + CATEGORY_TABLE + "(" + CATEGORY_ID + "," + CATEGORY_NAME + ") VALUES" + "('05' , 'History')");
            db.execSQL("INSERT INTO " + CATEGORY_TABLE + "(" + CATEGORY_ID + "," + CATEGORY_NAME + ") VALUES" + "('06' , 'Literature')");
            db.execSQL("INSERT INTO " + CATEGORY_TABLE + "(" + CATEGORY_ID + "," + CATEGORY_NAME + ") VALUES" + "('07' , 'Music')");
            db.execSQL("INSERT INTO " + CATEGORY_TABLE + "(" + CATEGORY_ID + "," + CATEGORY_NAME + ") VALUES" + "('08' , 'Philosophy')");
            db.execSQL("INSERT INTO " + CATEGORY_TABLE + "(" + CATEGORY_ID + "," + CATEGORY_NAME + ") VALUES" + "('09' , 'Linguistic')");
            db.execSQL("INSERT INTO " + CATEGORY_TABLE + "(" + CATEGORY_ID + "," + CATEGORY_NAME + ") VALUES" + "('10' , 'Society')");
            db.execSQL("INSERT INTO " + CATEGORY_TABLE + "(" + CATEGORY_ID + "," + CATEGORY_NAME + ") VALUES" + "('11' , 'Culture')");
            db.execSQL("INSERT INTO " + CATEGORY_TABLE + "(" + CATEGORY_ID + "," + CATEGORY_NAME + ") VALUES" + "('12' , 'Economy')");
            db.execSQL("INSERT INTO " + CATEGORY_TABLE + "(" + CATEGORY_ID + "," + CATEGORY_NAME + ") VALUES" + "('13' , 'Fiction')");
            db.execSQL("INSERT INTO " + CATEGORY_TABLE + "(" + CATEGORY_ID + "," + CATEGORY_NAME + ") VALUES" + "('14' , 'Chemistry')");
        }

        public void add_write(SQLiteDatabase db){
            db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0000' , '00001')");
            db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0003' , '00002')");
            db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0005' , '00002')");
            db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0001' , '00003')");
            db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0002' , '00004')");
            db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0004' , '00005')");
            db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0006' , '00007')");
     //       db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0010' , '00007')");
     //       db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0011' , '00007')");
     //       db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0012' , '00007')");
     //       db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0013' , '00007')");
     //       db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0014' , '00007')");
     //       db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0015' , '00007')");
     //       db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0018' , '00008')");
     //       db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0019' , '00008')");
     //       db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0020' , '00008')");
     //       db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0021' , '00008')");
     //       db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0022' , '00008')");
     //       db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0023' , '00008')");
     //       db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0024' , '00008')");
            db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0007' , '00009')");
            db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0008' , '00010')");
            db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0009' , '00011')");
            db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0009' , '00012')");
     //       db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0016' , '0013')");
     //       db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0017' , '0013')");
     //       db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0016' , '0014')");
     //       db.execSQL("INSERT INTO " + WRITE_TABLE + "(" + W_BID + "," + W_AID + ") VALUES" + "('0017' , '0014')");
        }

        public void add_belongs_to(SQLiteDatabase db){
            db.execSQL("INSERT INTO " + BELONGS_TO_TABLE + "(" + BT_BID + "," + BT_CID + ") VALUES" + "('0000' , '01')");
            db.execSQL("INSERT INTO " + BELONGS_TO_TABLE + "(" + BT_BID + "," + BT_CID + ") VALUES" + "('0001' , '12')");
            db.execSQL("INSERT INTO " + BELONGS_TO_TABLE + "(" + BT_BID + "," + BT_CID + ") VALUES" + "('0002' , '03')");
            db.execSQL("INSERT INTO " + BELONGS_TO_TABLE + "(" + BT_BID + "," + BT_CID + ") VALUES" + "('0003' , '03')");
            db.execSQL("INSERT INTO " + BELONGS_TO_TABLE + "(" + BT_BID + "," + BT_CID + ") VALUES" + "('0004' , '10')");
            db.execSQL("INSERT INTO " + BELONGS_TO_TABLE + "(" + BT_BID + "," + BT_CID + ") VALUES" + "('0005' , '02')");
            db.execSQL("INSERT INTO " + BELONGS_TO_TABLE + "(" + BT_BID + "," + BT_CID + ") VALUES" + "('0006' , '01')");
            db.execSQL("INSERT INTO " + BELONGS_TO_TABLE + "(" + BT_BID + "," + BT_CID + ") VALUES" + "('0007' , '08')");
            db.execSQL("INSERT INTO " + BELONGS_TO_TABLE + "(" + BT_BID + "," + BT_CID + ") VALUES" + "('0008' , '09')");
            db.execSQL("INSERT INTO " + BELONGS_TO_TABLE + "(" + BT_BID + "," + BT_CID + ") VALUES" + "('0009' , '11')");
        /*    db.execSQL("INSERT INTO " + BELONGS_TO_TABLE + "(" + BT_BID + "," + BT_CID + ") VALUES" + "('0010' , '13')");
            db.execSQL("INSERT INTO " + BELONGS_TO_TABLE + "(" + BT_BID + "," + BT_CID + ") VALUES" + "('0011' , '13')");
            db.execSQL("INSERT INTO " + BELONGS_TO_TABLE + "(" + BT_BID + "," + BT_CID + ") VALUES" + "('0012' , '13')");
            db.execSQL("INSERT INTO " + BELONGS_TO_TABLE + "(" + BT_BID + "," + BT_CID + ") VALUES" + "('0013' , '13')");
            db.execSQL("INSERT INTO " + BELONGS_TO_TABLE + "(" + BT_BID + "," + BT_CID + ") VALUES" + "('0014' , '13')");
            db.execSQL("INSERT INTO " + BELONGS_TO_TABLE + "(" + BT_BID + "," + BT_CID + ") VALUES" + "('0015' , '13')");
            db.execSQL("INSERT INTO " + BELONGS_TO_TABLE + "(" + BT_BID + "," + BT_CID + ") VALUES" + "('0016' , '14')");
            db.execSQL("INSERT INTO " + BELONGS_TO_TABLE + "(" + BT_BID + "," + BT_CID + ") VALUES" + "('0017' , '14')");
            db.execSQL("INSERT INTO " + BELONGS_TO_TABLE + "(" + BT_BID + "," + BT_CID + ") VALUES" + "('0018' , '13')");
            db.execSQL("INSERT INTO " + BELONGS_TO_TABLE + "(" + BT_BID + "," + BT_CID + ") VALUES" + "('0019' , '13')");
            db.execSQL("INSERT INTO " + BELONGS_TO_TABLE + "(" + BT_BID + "," + BT_CID + ") VALUES" + "('0020' , '13')");
            db.execSQL("INSERT INTO " + BELONGS_TO_TABLE + "(" + BT_BID + "," + BT_CID + ") VALUES" + "('0021' , '13')");
            db.execSQL("INSERT INTO " + BELONGS_TO_TABLE + "(" + BT_BID + "," + BT_CID + ") VALUES" + "('0022' , '13')");
            db.execSQL("INSERT INTO " + BELONGS_TO_TABLE + "(" + BT_BID + "," + BT_CID + ") VALUES" + "('0023' , '13')");
            db.execSQL("INSERT INTO " + BELONGS_TO_TABLE + "(" + BT_BID + "," + BT_CID + ") VALUES" + "('0024' , '13')"); */
        }

        public void add_borrow(SQLiteDatabase db){
            db.execSQL("INSERT INTO " + BORROW_TABLE + "(" + B_UID + "," + B_BID + " , " + IS_LATE + " , "
                    + IS_RETURNED + " , " + START_B_DATE + " , " + END_B_DATE + ") " +
                    "VALUES" + "('Maria12345' , '0000' , 0 , 0 , '18-05-2017' , '01-06-2017' )");
            db.execSQL("INSERT INTO " + BORROW_TABLE + "(" + B_UID + "," + B_BID + " , " + IS_LATE + " , "
                    + IS_RETURNED + " , " + START_B_DATE + " , " + END_B_DATE + ") " +
                    "VALUES" + "('Maria12345' , '0001' , 1 , 1  , '01-07-2016' , '15-07-2016' )");
            db.execSQL("INSERT INTO " + BORROW_TABLE + "(" + B_UID + "," + B_BID + " , " + IS_LATE + " , "
                    + IS_RETURNED + " , " + START_B_DATE + " , " + END_B_DATE + ") " +
                    "VALUES" + "('Maria12345' , '0002' , 0 , 1 , '08-08-2016' , '22-08-2016' )");
            db.execSQL("INSERT INTO " + BORROW_TABLE + "(" + B_UID + "," + B_BID + " , " + IS_LATE + " , "
                    + IS_RETURNED + " , " + START_B_DATE + " , " + END_B_DATE + ") " +
                    "VALUES" + "('Massa12345' , '0003' , 0 , 0 , '20-05-2017' , '3-06-2017' )");
            db.execSQL("INSERT INTO " + BORROW_TABLE + "(" + B_UID + "," + B_BID + " , " + IS_LATE + " , "
                    + IS_RETURNED  + " , " + START_B_DATE + " , " + END_B_DATE + ") " +
                    "VALUES" + "('Massa12345' , '0004' , 1 , 1 , '01-09-2016' , '15-09-2016' )");
        }

        public void add_published(SQLiteDatabase db){
            db.execSQL("INSERT INTO " + PUBLISHED_TABLE + "(" + P_BID + "," + P_PID + " , " + PUBLISHED_YEAR  +")" +
                    " VALUES" + "('0000' , '001' , 2006)");
            db.execSQL("INSERT INTO " + PUBLISHED_TABLE + "(" + P_BID + "," + P_PID + " , " + PUBLISHED_YEAR  +")" +
                    " VALUES" + "('0001' , '002' , 2010)");
            db.execSQL("INSERT INTO " + PUBLISHED_TABLE + "(" + P_BID + "," + P_PID + " , " + PUBLISHED_YEAR  +")" +
                    " VALUES" + "('0002' , '003' , 2006)");
            db.execSQL("INSERT INTO " + PUBLISHED_TABLE + "(" + P_BID + "," + P_PID + " , " + PUBLISHED_YEAR  +")" +
                    " VALUES" + "('0003' , '004' , 2006)");
            db.execSQL("INSERT INTO " + PUBLISHED_TABLE + "(" + P_BID + "," + P_PID + " , " + PUBLISHED_YEAR  +")" +
                    " VALUES" + "('0004' , '005' , 2014)");
            db.execSQL("INSERT INTO " + PUBLISHED_TABLE + "(" + P_BID + "," + P_PID + " , " + PUBLISHED_YEAR  +")" +
                    " VALUES" + "('0005' , '004' , 1986 )");
            db.execSQL("INSERT INTO " + PUBLISHED_TABLE + "(" + P_BID + "," + P_PID + " , " + PUBLISHED_YEAR  +")" +
                    " VALUES" + "('0006' , '007' , 1934)");
            db.execSQL("INSERT INTO " + PUBLISHED_TABLE + "(" + P_BID + "," + P_PID + " , " + PUBLISHED_YEAR  +")" +
                    " VALUES" + "('0007' , '007' , 2013)");
            db.execSQL("INSERT INTO " + PUBLISHED_TABLE + "(" + P_BID + "," + P_PID + " , " + PUBLISHED_YEAR  +")" +
                    " VALUES" + "('0008' , '003' , 2003 )");
            db.execSQL("INSERT INTO " + PUBLISHED_TABLE + "(" + P_BID + "," + P_PID + " , " + PUBLISHED_YEAR  +")" +
                    " VALUES" + "('0009' , '004' , 2014 )");
      /*      db.execSQL("INSERT INTO " + PUBLISHED_TABLE + "(" + P_BID + "," + P_PID + " , " + PUBLISHED_YEAR  +")" +
                    " VALUES" + "('0010' , '004' , 1984)");
            db.execSQL("INSERT INTO " + PUBLISHED_TABLE + "(" + P_BID + "," + P_PID + " , " + PUBLISHED_YEAR  +")" +
                    " VALUES" + "('0011' , '004' , 1985 )");
            db.execSQL("INSERT INTO " + PUBLISHED_TABLE + "(" + P_BID + "," + P_PID + " , " + PUBLISHED_YEAR  +")" +
                    " VALUES" + "('0012' , '004' , 1956)");
            db.execSQL("INSERT INTO " + PUBLISHED_TABLE + "(" + P_BID + "," + P_PID + " , " + PUBLISHED_YEAR  +")" +
                    " VALUES" + "('0013' , '004' ,1985 )");
            db.execSQL("INSERT INTO " + PUBLISHED_TABLE + "(" + P_BID + "," + P_PID + " , " + PUBLISHED_YEAR  +")" +
                    " VALUES" + "('0014' , '003' , 1983)");
            db.execSQL("INSERT INTO " + PUBLISHED_TABLE + "(" + P_BID + "," + P_PID + " , " + PUBLISHED_YEAR  +")" +
                    " VALUES" + "('0015' , '003' , 1987 )");
            db.execSQL("INSERT INTO " + PUBLISHED_TABLE + "(" + P_BID + "," + P_PID + " , " + PUBLISHED_YEAR  +")" +
                    " VALUES" + "('00016' , '005' ,2011 )");
            db.execSQL("INSERT INTO " + PUBLISHED_TABLE + "(" + P_BID + "," + P_PID + " , " + PUBLISHED_YEAR  +")" +
                    " VALUES" + "('0017' , '001' , 2013 )");
            db.execSQL("INSERT INTO " + PUBLISHED_TABLE + "(" + P_BID + "," + P_PID + " , " + PUBLISHED_YEAR  +")" +
                    " VALUES" + "('0018' , '002' , 2003)");
            db.execSQL("INSERT INTO " + PUBLISHED_TABLE + "(" + P_BID + "," + P_PID + " , " + PUBLISHED_YEAR  +")" +
                    " VALUES" + "('0019' , '002' , 2004 )");
            db.execSQL("INSERT INTO " + PUBLISHED_TABLE + "(" + P_BID + "," + P_PID + " , " + PUBLISHED_YEAR  +")" +
                    " VALUES" + "('0020' , '002' , 2005 )");
            db.execSQL("INSERT INTO " + PUBLISHED_TABLE + "(" + P_BID + "," + P_PID + " , " + PUBLISHED_YEAR  +")" +
                    " VALUES" + "('0021' , '002' , 2006 )");
            db.execSQL("INSERT INTO " + PUBLISHED_TABLE + "(" + P_BID + "," + P_PID + " , " + PUBLISHED_YEAR  +")" +
                    " VALUES" + "('0022' , '002' , 2007)");
            db.execSQL("INSERT INTO " + PUBLISHED_TABLE + "(" + P_BID + "," + P_PID + " , " + PUBLISHED_YEAR  +")" +
                    " VALUES" + "('0023' , '002' , 2008)");
            db.execSQL("INSERT INTO " + PUBLISHED_TABLE + "(" + P_BID + "," + P_PID + " , " + PUBLISHED_YEAR  +")" +
                    " VALUES" + "('0024' , '006' , 2009 )"); */
        }
    }
}