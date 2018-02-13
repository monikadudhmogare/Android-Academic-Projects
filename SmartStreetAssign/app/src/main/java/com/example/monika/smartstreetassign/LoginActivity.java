package com.example.monika.smartstreetassign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by monika on 10/24/17.
 */

public class LoginActivity extends AppCompatActivity {

    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;
    Cursor cursor;
    Button btnLogin;
    EditText txtEmail, txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        openHelper = new DatabaseHelper(this);
        db = openHelper.getReadableDatabase();

        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPass = (EditText) findViewById(R.id.txtPass);

        /* Handling of the Login Button */

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String email = txtEmail.getText().toString();
                String pass = txtPass.getText().toString();

                cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COL_4 + "=? AND " + DatabaseHelper.COL_5 + "=?", new String[]{email, pass});
                if (cursor != null)
                {
                    if (cursor.getCount() > 0) {
                        cursor.moveToNext();
                        Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Login Error", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}
