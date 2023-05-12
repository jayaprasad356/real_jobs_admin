package com.app.realjobadmin;

import static com.app.realjobadmin.constants.IConstants.EMAIL;
import static com.app.realjobadmin.constants.IConstants.EMPLOYEE_LOGIN;
import static com.app.realjobadmin.constants.IConstants.LOGIN_TYPE;
import static com.app.realjobadmin.constants.IConstants.MOBILE;
import static com.app.realjobadmin.constants.IConstants.NAME;
import static com.app.realjobadmin.constants.IConstants.PASSWORD;
import static com.app.realjobadmin.constants.IConstants.ROLE;
import static com.app.realjobadmin.constants.IConstants.SUCCESS;
import static com.app.realjobadmin.constants.IConstants.USER_ID;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.realjobadmin.helper.ApiConfig;
import com.app.realjobadmin.helper.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginForJoiningsFragment extends Fragment {
    Button btnSignIn;
    EditText edEmail, edPassword;
    Session session;

    public LoginForJoiningsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_admin_login, container, false);
        btnSignIn = root.findViewById(R.id.btnLogin);
        edEmail = root.findViewById(R.id.edEmail);
        edPassword = root.findViewById(R.id.edPassword);

        session = new Session(getActivity());
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                employeeLogin(edEmail.getText().toString().trim(), edPassword.getText().toString().trim());

            }
        });
        return root;
    }

    private void employeeLogin(String email, String password) {
        if (email.equals("admin@gmail.com") && password.equals("123456")) {

            String id = "1";
            String name = "Admin";
            //   String mobile = data.getString("mobile");
            session.setData(ROLE, "Admin");
            session.setData(NAME,name);
            session.setData(USER_ID,id);
             session.setData(MOBILE,"1234567890");
            session.setData(LOGIN_TYPE, "employee");
            Intent intent = new Intent(getActivity(), JoiningActivity.class);
            startActivity(intent);
            getActivity().finish();

        }else {
            Toast.makeText(getActivity(), "Invalid Credential", Toast.LENGTH_SHORT).show();
        }


    }
}