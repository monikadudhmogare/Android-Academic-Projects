package com.example.monika.smartstreetassign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static String TAG = "MainActivity";

    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    Button btnreg, btnlogin;
    EditText txtfname, txtpass, txtemail, txtphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openHelper = new DatabaseHelper(this);
        txtfname = (EditText) findViewById(R.id.txtfname);
        txtphone = (EditText) findViewById(R.id.txtphone);
        txtemail = (EditText) findViewById(R.id.txtemail);
        txtpass = (EditText) findViewById(R.id.txtpass);
        btnreg = (Button) findViewById(R.id.btnreg);
        btnlogin = (Button) findViewById(R.id.btnlogin);

        /* Handling of the Registration Button */

        btnreg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                db = openHelper.getWritableDatabase();
                String fname = txtfname.getText().toString();
                String phone = txtphone.getText().toString();
                String email = txtemail.getText().toString();
                String pass = txtpass.getText().toString();

                insertdata(fname, phone, email, pass);
                Toast.makeText(getApplicationContext(), "Registeration successful", Toast.LENGTH_LONG).show();

            }
        });

        /* Handling of the Login Button to Navigate to Login Page */

        btnlogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    /* Insertion of data values */
    public void insertdata(String fname, String phone, String email, String pass)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_2, fname);
        contentValues.put(DatabaseHelper.COL_3, phone);
        contentValues.put(DatabaseHelper.COL_4, email);
        contentValues.put(DatabaseHelper.COL_5, pass);
        long id = db.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
    }
}
