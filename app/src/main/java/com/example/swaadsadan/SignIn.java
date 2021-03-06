package com.example.swaadsadan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Common.Common;
import Model.User;

public class SignIn extends AppCompatActivity {

    EditText editPhone,editPassword;
    Button SignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editPhone = (EditText)findViewById(R.id.editText_Phone);
        editPassword = (EditText)findViewById(R.id.editText_Password);
        SignIn = (Button)findViewById(R.id.signin);

        final FirebaseDatabase database = FirebaseDatabase.getInstance("https://swaad-sadan-367ce-default-rtdb.firebaseio.com/");
        final DatabaseReference table_user = database.getReference("User");

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog dialog = new ProgressDialog(SignIn.this);
                dialog.setMessage("PLease wait....");
                dialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Check if user exists in database
                        if(dataSnapshot.child(editPhone.getText().toString()).exists()){
                            //get user information
                            dialog.dismiss();
                            User user = dataSnapshot.child(editPhone.getText().toString()).getValue(User.class);
                            //user.setPhone(editPhone.getText().toString());
                            if(user.getPassword().equals(editPassword.getText().toString())){
                                Toast.makeText(SignIn.this, "SignIn Succesfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignIn.this,Home.class);
                                Common.currentUser = user;
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(SignIn.this, "Wrong Password!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(SignIn.this, "User Doesnot Exist in Database", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
