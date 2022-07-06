package com.example.rakthdonar;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


    public class Signuptab extends Fragment {
        TextView name,semail,apass,cpass;
        Button signup;
        String userid;
        String mail;
        FirebaseFirestore fr;
        private FirebaseAuth mauth;
        FirebaseUser user;

        @Override
        public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
            ViewGroup root=(ViewGroup) inflater.inflate(R.layout.signuptab,container,false);
            mauth = FirebaseAuth.getInstance();
            fr=FirebaseFirestore.getInstance();
            name=root.findViewById(R.id.name);
            semail=root.findViewById(R.id.mailids);

            apass=root.findViewById(R.id.passwords);

            cpass=root.findViewById(R.id.passwordscon);

            signup=root.findViewById(R.id.signin);
           signup.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   registeruser();
               }
           });
            return root;
        }




        @Override
        public void onStart() {
            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mauth.getCurrentUser();
            if(currentUser != null){
                currentUser.reload();
            }
        }
        private void registeruser() {
            int flag=0;
            String name1 = name.getText().toString();
            if (name1.isEmpty()) {
                name.setError("Name is required");
                name.requestFocus();
                flag=1;

            }
            mail = semail.getText().toString();
            if (mail.isEmpty()) {
                semail.setError("mail is required");
                semail.requestFocus();
                flag=1;

            }
            if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                semail.setError("please provide vaild adress");
                semail.requestFocus();
                flag=1;
            }
            String pa = apass.getText().toString();
            if (pa.isEmpty()) {
                apass.setError("password is required");
                apass.requestFocus();
                flag=1;

            }
            String cpa = cpass.getText().toString();

            if (cpa.isEmpty()) {
                cpass.setError("password is required");
                cpass.requestFocus();
                flag=1;

            }
            if (!(cpa.equals(pa))) {
                cpass.setError("Password not matched");
                cpass.requestFocus();
                flag=1;
            }

            if (pa.length() < 6) {
                apass.setError("atleast 6 characters");
                apass.requestFocus();
                flag=1;
            }
            if (!name1.isEmpty() && !mail.isEmpty() && flag==0) {


                mauth.createUserWithEmailAndPassword(mail, pa)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mauth.getCurrentUser();
                                    updateUI(user);
                                    Log.w(TAG, "createUserWithEmail");
                                    reload();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }

                            private void updateUI(FirebaseUser user) {
                                if (user != null) {
                                    Toast.makeText(getActivity().getApplicationContext(), "user", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(getActivity().getApplicationContext(), "nouser", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });



            }
            else{
                Toast.makeText(getActivity(), "Fill the details to Correctly", Toast.LENGTH_SHORT).show();
            }
        }

        public void reload(){
            Intent in=new Intent(getActivity(),Profileactivity.class);
            in.putExtra("email",mail);
            startActivity(in);
            getActivity().finish();
        }

    }


