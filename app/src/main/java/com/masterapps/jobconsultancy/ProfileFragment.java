package com.masterapps.jobconsultancy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.masterapps.jobconsultancy.models.UserDetails;

import java.util.List;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    FloatingActionButton fabEdit;
    TextView tvName, tvEmail, tvDescription, tvSalary, tvExperience, tvContaact, tvWhatsapp, tvGender, tvLanguages;

    Boolean load;

    FirebaseUser firebaseUser;
    FirebaseFirestore firestore;
    UserDetails userDetails;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firestore = FirebaseFirestore.getInstance();

        userDetails = new UserDetails();

        if(firebaseUser!= null) {
            firestore.collection("Users").document(firebaseUser.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isComplete()){
                                userDetails = task.getResult().toObject(UserDetails.class);
                                Log.d("signup log","userDetails name "+ userDetails.getName());
                                loadUser();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(container.getContext(),"failed to retrieve profile data",Toast.LENGTH_SHORT).show();
                        }
                    });


            return inflater.inflate(R.layout.fragment_profile, container, false);
        }else if(userDetails.isSharedPref(container.getContext())){
            return inflater.inflate(R.layout.fragment_profile, container, false);
        }
        else {
            return inflater.inflate(R.layout.fragment_profile_guest, container, false);
        }

    }



    private Boolean loadUser() {

        tvName.setText(userDetails.getName());
        tvGender.setText(userDetails.getGender());
        tvWhatsapp.setText(userDetails.getWhatsappNo());
        //tvExperience.setText(userDetails.getsExp());
        tvContaact.setText(userDetails.getContact());
        tvLanguages.setText(listToStrong(userDetails.getLanguages()));
        tvEmail.setText(userDetails.getEmailId());
        tvDescription.setText(userDetails.getDescription());
      //  tvSalary.setText(userDetails.getCurrentSalary()+"");


        return true;
    }

    private String listToStrong(List<String> languages) {
        String s = "";
        if(languages!=null){
            for(String i : languages){
                if(languages.indexOf(i)==(languages.size()-1)){
                    s = s + " "+ i;
                }else {
                    s = s + " "+ i+ ",";
                }
            }
            return s;
        }else
            return null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {


            Log.d("signup log","userDetails name "+ userDetails.getName());
            InitializeUI(view);
            //loadUser();
            fabEdit.setOnClickListener(this);
        }else {
            Button btLogin = view.findViewById(R.id.btLogin);
            btLogin.setOnClickListener(this);
        }


    }

    private void InitializeUI(View view) {

        fabEdit = view.findViewById(R.id.fabEdit);

        tvName = view.findViewById(R.id.tvUserWhatsApp);
        tvDescription = view.findViewById(R.id.tvUserDescription);
        tvLanguages= view.findViewById(R.id.tvLanguages);
        tvEmail = view.findViewById(R.id.tvUserEmail);
        tvContaact = view.findViewById(R.id.tvUserContact);
        //tvExperience = view.findViewById(R.id.tvUserExp);

        tvWhatsapp = view.findViewById(R.id.tvUserWhatsApp);
        tvGender = view.findViewById(R.id.tvUserGender);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabEdit:{
                startActivity(new Intent(v.getContext(),EditProfileActivity.class));
                break;
            }
            case R.id.btLogin:{

                startActivity(new Intent(v.getContext(),LogInActivity.class));
                onDestroy();
                break;
            }
        }
    }
}