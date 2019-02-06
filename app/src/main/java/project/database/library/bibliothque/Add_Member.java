package project.database.library.bibliothque;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by Maria on 21-May-17.
 */
public class Add_Member extends Activity{

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask ;
    private TextView id , name , password , address , email , gender , dob , s_date , e_date ;
    private ImageView image ;
    Button add , choose ;
    private DBHelper db ;
    private Button back ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        db = new DBHelper(getApplicationContext()) ;
        image = (ImageView) findViewById(R.id.image) ;
        id = (TextView) findViewById(R.id.id) ;
        name = (TextView) findViewById(R.id.name) ;
        password = (TextView) findViewById(R.id.password) ;
        address = (TextView) findViewById(R.id.address) ;
        email = (TextView) findViewById(R.id.email) ;
        gender = (TextView) findViewById(R.id.gender) ;
        dob = (TextView) findViewById(R.id.dob) ;
        s_date = (TextView) findViewById(R.id.start_date) ;
        e_date = (TextView) findViewById(R.id.e_date) ;
        add = (Button) findViewById(R.id.add) ;
        choose = (Button) findViewById(R.id.select_image) ;
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage() ;
            }
        });
        back = (Button) findViewById(R.id.back) ;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "back", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Add_Member.this, Manager.class);
                Add_Member.this.startActivity(intent);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String idd = id.getText().toString() ;
                    String namee = name.getText().toString() ;
                    String passwordd = password.getText().toString() ;
                    String addresss = address.getText().toString() ;
                    String emaill = email.getText().toString() ;
                    String genderr = gender.getText().toString() ;
                    String b_date = null ;
                    String st_date = null ;
                    String en_date = null ;
                    byte [] imagee = imageViewToByte(image) ;

                    int a = check_date_validity(dob.getText().toString()) ;
                    int b = check_date_validity(s_date.getText().toString()) ;
                    int c = check_date_validity(e_date.getText().toString()) ;

                    if (a == 0){
                        Toast.makeText(getApplicationContext(), "Invalid DOB !", Toast.LENGTH_SHORT).show();
                        dob.setText("");
                    }else{
                        int result = check_age_validity(dob.getText().toString()) ;
                        if (result == 0){
                            Toast.makeText(getApplicationContext(), "Illegal age !", Toast.LENGTH_SHORT).show();
                            dob.setText("");
                        }else
                        if (result == 1)
                            b_date =  dob.getText().toString() ;
                    }


                    if (b == 0 || c == 0){
                        Toast.makeText(getApplicationContext(), "Invalid date !", Toast.LENGTH_SHORT).show();
                        s_date.setText("");
                        e_date.setText("");
                    }else
                    if (b == 1 && c == 1) {
                        int result = check_one_year_validity(s_date.getText().toString(),e_date.getText().toString()) ;
                        if (result == 0){
                            Toast.makeText(getApplicationContext(), "Illegal period !", Toast.LENGTH_SHORT).show();
                            s_date.setText("");
                            e_date.setText("");
                        }else
                        if (result == 1){
                            st_date = s_date.getText().toString() ;
                            en_date = e_date.getText().toString() ;
                        }
                    }

                    if (b_date.compareTo("") != 0 && st_date.compareTo("") != 0 && en_date.compareTo("") != 0
                            && idd.compareTo("") != 0 && namee.compareTo("") != 0
                            && passwordd.compareTo("") != 0 && emaill.compareTo("") != 0
                            && check_gender(genderr) == 1){
                        db.insert_member(idd,namee,passwordd,emaill,addresss,b_date,genderr,st_date,en_date,imagee) ;
                        Toast.makeText(getApplicationContext(), "New member added", Toast.LENGTH_SHORT).show();
                        name.setText("");
                        id.setText("");
                        password.setText("");
                        email.setText("");
                        address.setText("");
                        dob.setText("");
                        gender.setText("");
                        s_date.setText("");
                        e_date.setText("");
                        image.setImageResource(R.drawable.profile);
                    }else
                    {
                        Toast.makeText(getApplicationContext(), "Error Occurred !", Toast.LENGTH_SHORT).show();
                        name.setText("");
                        id.setText("");
                        password.setText("");
                        email.setText("");
                        address.setText("");
                        dob.setText("");
                        gender.setText("");
                        s_date.setText("");
                        e_date.setText("");
                        image.setImageResource(R.drawable.profile);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error occurred !", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    id.setText("");
                    password.setText("");
                    email.setText("");
                    address.setText("");
                    dob.setText("");
                    gender.setText("");
                    s_date.setText("");
                    e_date.setText("");
                    image.setImageResource(R.drawable.profile);
                }
            }
        });

    }

    private int check_gender(String genderr) {
        if (genderr.compareToIgnoreCase("female") == 0 || genderr.compareToIgnoreCase("male") == 0)
            return 1 ;
        else
            return 0 ;
    }

    private int check_one_year_validity(String start, String end) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = new java.util.Date();
        sdf.format(date).toString(); // current date
        String[] now = sdf.format(date).toString().split("-");

        String [] st = start.split("-") ;
        String [] en = end.split("-") ;
        if (Integer.parseInt(en[2]) - Integer.parseInt(st[2]) == 1
                && Integer.parseInt(now[2]) == Integer.parseInt(st[2])
                && Integer.parseInt(now[1]) == Integer.parseInt(st[1])
                && Integer.parseInt(now[0]) == Integer.parseInt(st[0]))
            return 1 ;
        else
            return 0 ;
    }

    private int check_age_validity(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = new java.util.Date();
        sdf.format(date).toString(); // current date
        String[] now = sdf.format(date).toString().split("-");

        String[] birth = str.split("-");

        if (Integer.parseInt(now[2]) - Integer.parseInt(birth[2]) >= 15
                && Integer.parseInt(now[2]) - Integer.parseInt(birth[2]) <= 85)
            return 1 ;
        else
            return 0 ;
    }

    private int check_date_validity(String date) {
        String [] new_date = date.split("-") ;
        if (new_date.length != 3)
            return 0 ;
        if (new_date.length == 3){
            String str_year = new_date[2] ;
            String str_month = new_date[1] ;
            String str_day = new_date[0] ;

            int year = Integer.parseInt(str_year) ;
            int month = Integer.parseInt(str_month) ;
            int day = Integer.parseInt(str_day) ;

            int result = check_day_validity(year,month,day) ;

            return result ;
        }
        return 0 ;
    }

    private int check_day_validity(int year, int month, int day) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
            if (day >= 1 && day <= 31)
                return 1 ;
            else
                return 0 ;
        }else
        if (month == 4 || month == 6 || month == 9 || month == 11){
            if (day >= 1 && day <= 30)
                return 1 ;
            else
                return 0 ;
        }else
        if (month == 2) {
            if((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
                if (day >= 1 && day <= 29)
                    return 1;
                else
                    return 0;
            } else if (year % 4 != 0) {
                if (day >= 1 && day <= 28)
                    return 1;
                else
                    return 0;
            }
        }
        return 0 ;
    }

    private byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Add_Member.this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(Add_Member.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Gallery")) {
                    userChoosenTask ="Choose from Gallery";
                    if(result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error occurred !", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error occurred !", Toast.LENGTH_SHORT).show();
        }
        image.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error occurred !", Toast.LENGTH_SHORT).show();
            }
        }
        image.setImageBitmap(bm);
    }

}
