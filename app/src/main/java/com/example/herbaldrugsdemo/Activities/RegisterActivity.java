package com.example.herbaldrugsdemo.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.herbaldrugsdemo.Assets.Links;
import com.example.herbaldrugsdemo.Assets.MySingleton;
import com.example.herbaldrugsdemo.Assets.UserSharedPrefrences;
import com.example.herbaldrugsdemo.R;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    TextView txtPassword,txtPhone,txtEmail,txtAddress;
    CardView register;
    ImageView imgBack;
    CheckBox chkShowPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        chkShowPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    txtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else txtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=txtEmail.getText().toString().trim();
                String phone=txtPhone.getText().toString().trim();
                String address=txtAddress.getText().toString().trim();
                String pass=txtPassword.getText().toString().trim();
                if (!email.isEmpty() && !phone.isEmpty() && !address.isEmpty() && !pass.isEmpty()){
                    if (pass.length()>=4){
                        if (isEmailValid(email)){
                            if (phone.startsWith("09")){
                                registerUser(phone,email,pass,address);
                            }else
                            Toast.makeText(RegisterActivity.this, "تلفن همراه معتبر وارد کنید", Toast.LENGTH_SHORT).show();

                        }else
                        Toast.makeText(RegisterActivity.this, "لطفا ایمیل معتبر وارد کنید", Toast.LENGTH_SHORT).show();
                    }else
                    Toast.makeText(RegisterActivity.this, "طول پسورد حداقل باید چهار کاراکتر باشد", Toast.LENGTH_SHORT).show();

                }else
                Toast.makeText(RegisterActivity.this, "لطفا تمامی فیلد هارا پر کنید", Toast.LENGTH_SHORT).show();

            }


        });


    }
    private boolean isEmailValid(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void registerUser(final String phone, final String email, final String password, final String address) {
        final ProgressDialog dialog = new ProgressDialog(RegisterActivity.this);
        dialog.setMessage("درحال ارسال اطلاعات");
        dialog.setCancelable(false);
        dialog.setTitle("لطفا صبر کنید");
        StringRequest stringRequest =new StringRequest(1, Links.LINK_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("214")){
                    Toast.makeText(RegisterActivity.this, "این نام کاربری وجود دارد لطفا وارد شوید", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    txtAddress.setText("");
                    txtEmail.setText("");
                    txtPassword.setText("");
                    txtPhone.setText("");
                }else if (response.equals("218")){
                    Toast.makeText(RegisterActivity.this, "ثبت نام شما با موفقیت انجام شد. خوش آمدید", Toast.LENGTH_SHORT).show();
                    txtAddress.setText("");
                    txtEmail.setText("");
                    txtPassword.setText("");
                    txtPhone.setText("");
                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                    UserSharedPrefrences userSharedPrefrences=new UserSharedPrefrences(RegisterActivity.this);
                    userSharedPrefrences.saveUserLoginInfo(phone);
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "خطایی رخ داد", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String,String> map = new HashMap<>();
                map.put("phone",phone);
                map.put("password",password);
                map.put("email",email);
                map.put("address",address);
                return map;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(RegisterActivity.this).addToRequestQueue(stringRequest);
    }

    private void findViews() {
        register=findViewById(R.id.card_Register);
        chkShowPass=findViewById(R.id.chk_show_pass_register);
        txtPassword=findViewById(R.id.txt_password_register);
        txtAddress=findViewById(R.id.txt_address_register);
        txtEmail=findViewById(R.id.txt_email_register);
        txtPhone=findViewById(R.id.txt_phone_register);
        imgBack=findViewById(R.id.img_back_login_toolbar);
    }
}
