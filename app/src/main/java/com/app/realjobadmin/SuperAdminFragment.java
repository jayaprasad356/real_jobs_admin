package com.app.realjobadmin;

import static com.app.realjobadmin.constants.IConstants.LOGIN_TYPE;
import static com.app.realjobadmin.constants.IConstants.ROLE;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.app.realjobadmin.helper.Session;

public class SuperAdminFragment extends Fragment {
    Button btnSignIn;
    EditText edEmail, edPassword;
    Session session;
    boolean login = false;
    String role = "";

    public SuperAdminFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_super_admin, container, false);
        btnSignIn = root.findViewById(R.id.btnLogin);
        edEmail = root.findViewById(R.id.edEmail);
        edPassword = root.findViewById(R.id.edPassword);

        session = new Session(getActivity());
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edEmail.getText().toString().trim().equals("superadmin@gmail.com") && edPassword.getText().toString().trim().equals("123456")){
                    login = true;
                    role = "Super Admin";
                    signIn();
                }
            }
        });
        return root;
    }
    private void signIn() {
        session.setBoolean("is_logged_in", true);
        session.setData(ROLE, role);
        session.setData(LOGIN_TYPE, "Super Admin");
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra(ROLE, role);
        startActivity(intent);
    }
}