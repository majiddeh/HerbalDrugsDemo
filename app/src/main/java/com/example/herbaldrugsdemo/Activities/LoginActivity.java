package com.example.herbaldrugsdemo.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.herbaldrugsdemo.ApiServices;
import com.example.herbaldrugsdemo.Assets.Links;
import com.example.herbaldrugsdemo.Assets.MySingleton;
import com.example.herbaldrugsdemo.Assets.UserSharedPrefrences;
import com.example.herbaldrugsdemo.R;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    ImageView imgBackToolbarLogin;
    TextView txtRegister,txtPhone,txtPass;
    CardView login;
    CheckBox chkpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inits();
        onclicks();

    }

    private void onclicks() {
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        imgBackToolbarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        chkpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    txtPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else txtPass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=txtPhone.getText().toString().trim();
                String password=txtPass.getText().toString().trim();
                if (!phone.isEmpty() && !password.isEmpty()){

                    loginUser(phone,password);
                }else
                    Toast.makeText(LoginActivity.this, "تمامی فیلد هارا پر کنید", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void loginUser(final String phone, final String password) {
        final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("درحال ارسال اطلاعات");
        dialog.setCancelable(false);
        dialog.setTitle("لطفا صبر کنید");
        StringRequest stringRequest = new StringRequest(1, Links.LINK_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("errrr",response);
                if (response.equals("1")){
                    txtPhone.setText("");
                    txtPass.setText("");
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, "خوش آمدید", Toast.LENGTH_SHORT).show();
                    UserSharedPrefrences userSharedPrefrences = new UserSharedPrefrences(LoginActivity.this);
                    userSharedPrefrences.saveUserLoginInfo(phone);
                    Intent intent= new Intent();
                    intent.putExtra("phone",phone);
                    setResult(MainActivity.RESULT_OK,intent);
                    finish();
                }else
                    Toast.makeText(LoginActivity.this, "دوباره سعی کنید", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "خطایی رخ داد", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("phone",phone);
                map.put("password",password);
                return map;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(LoginActivity.this).addToRequestQueue(stringRequest);
    }

    private void inits() {
        imgBackToolbarLogin=findViewById(R.id.img_back_login_toolbar);
        txtRegister=findViewById(R.id.txt_register);
        login=findViewById(R.id.card_login);
        txtPass=findViewById(R.id.txt_pass_login);
        txtPhone=findViewById(R.id.txt_phone_login);
        chkpass=findViewById(R.id.chk_show_pass_login);
    }


}
