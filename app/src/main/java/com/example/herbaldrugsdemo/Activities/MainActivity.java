package com.example.herbaldrugsdemo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.herbaldrugsdemo.Adapters.CategoryAdapter;
import com.example.herbaldrugsdemo.Adapters.ProductAdapter;
import com.example.herbaldrugsdemo.ApiServices;
import com.example.herbaldrugsdemo.Assets.JalaliCalendar;
import com.example.herbaldrugsdemo.Assets.Links;
import com.example.herbaldrugsdemo.Assets.MySingleton;
import com.example.herbaldrugsdemo.Assets.UserSharedPrefrences;
import com.example.herbaldrugsdemo.DataModels.FakeCategory;
import com.example.herbaldrugsdemo.DataModels.Product;
import com.example.herbaldrugsdemo.R;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener{

    private static final int Time_Between_Two_Back =1200;
    private long TimeBackPressed;
    NavigationView navigationView;
    ImageView search_bar_icon, cart,userAvatarImageView,imgNaviToolbar;
    TextView date,userEmailTextView;
    NotificationBadge badge;
    String phone;
    RecyclerView categoryListRecycler;
    RecyclerView productListRecycler;
    SliderLayout sliderLayout;
    DrawerLayout drawerLayout;
    CardView category;
    public static final int REQUEST_CODE_LOGIN=1001;
    public static final int RESULT_OK=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        UserSharedPrefrences userSharedPrefrences = new UserSharedPrefrences(MainActivity.this);
        phone = userSharedPrefrences.getUserLoginInfo();
        init();
        recyclerInitialize();
        sliderInitialize();
        setUpToolbar();
        setUpCalendar();
        setUpNavigation();


        search_bar_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BasketActivity.class));
            }
        });
        imgNaviToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.RIGHT) ) {
            drawerLayout.closeDrawer(Gravity.RIGHT);
        }else if(TimeBackPressed + Time_Between_Two_Back >System.currentTimeMillis() ){
            super.onBackPressed();
            return;
        }else{
            Toast.makeText(getBaseContext(),"به منظور خروج دوباره کلیک کنید",Toast.LENGTH_SHORT).show();
        }
        TimeBackPressed =System.currentTimeMillis();
    }

    private void setUpNavigation() {
        userEmailTextView=navigationView.getHeaderView(0).findViewById(R.id.txt_name_navigation);
        userAvatarImageView=navigationView.getHeaderView(0).findViewById(R.id.img_user_avatar);

        if (!phone.isEmpty()){
            userEmailTextView.setText(phone);
            userAvatarImageView.setVisibility(View.VISIBLE);
        }

//        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_login:

                        startActivityForResult(new Intent(MainActivity.this,LoginActivity.class),REQUEST_CODE_LOGIN);
                }

                return false;
            }
        });
    }

    private void showUserEmail(String email){
        userEmailTextView.setText(email);
        userAvatarImageView.setVisibility(View.VISIBLE);
    }


//    public static final int REQUEST_CODE_BADGE=1002;
//    public static final int RESULT_INCREASE=2;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE_LOGIN){
            if (resultCode==RESULT_OK){
                recreate();
                String email = data.getStringExtra("email");
                if (email!=null && !email.isEmpty()){
                    showUserEmail(email);

                }
            }
        }

//        if (requestCode==REQUEST_CODE_BADGE){
//            if (resultCode==RESULT_INCREASE){
//                badge.setNumber(+1);
//            }
//        }

    }

    private void setUpCalendar() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int mounth = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        JalaliCalendar.gDate miladi = new JalaliCalendar.gDate(year,mounth,day);
        JalaliCalendar.gDate jalali = JalaliCalendar.MiladiToJalali(miladi);
        date.setText(jalali.toString());


    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void getCount(final String phone){
        StringRequest stringRequest = new StringRequest(1, Links.LINK_COUNT_BADGE , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                badge.setNumber(Integer.parseInt(response));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String,String> map = new HashMap<>();
                map.put("phone",phone);
                return map;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void recyclerInitialize() {
        ApiServices apiServices = new ApiServices(MainActivity.this);

        apiServices.getCategory(new ApiServices.OnCategoryReceived() {
            @Override
            public void onCatReceived(List<FakeCategory> categories) {
                CategoryAdapter adapter = new CategoryAdapter(MainActivity.this,categories);
                categoryListRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,true));
                categoryListRecycler.setAdapter(adapter);
            }
        });

        apiServices.getProducts(new ApiServices.OnPostReceived() {
            @Override
            public void onReceived(List<Product> products) {
                ProductAdapter productAdapter = new ProductAdapter(MainActivity.this,products);
                productListRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,true));
                productListRecycler.setAdapter(productAdapter);

            }
        });

    }

    private void sliderInitialize() {
        HashMap<String,String> url_map = new HashMap<String,String>();
        url_map.put("Majid Dehghan","http://192.168.1.3/Medicknowledge/uploads/banners/banner1.jpg");
        url_map.put("Pooyan Habibi","http://192.168.1.3/Medicknowledge/uploads/banners/banner2.jpg");
        url_map.put("Majid dehghan","http://192.168.1.3/Medicknowledge/uploads/banners/banner3.jpg");
        url_map.put("BloodBorn","http://192.168.1.3/Medicknowledge/uploads/banners/banner4.jpg");
        url_map.put("Uncharted","http://192.168.1.3/Medicknowledge/uploads/banners/banner2.jpg");

        for (String name :url_map.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
//                    .description(name)
                    .empty(R.drawable.placeholder)
                    .error(R.drawable.placeholder_error)
                    .image(url_map.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);



            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            sliderLayout.addSlider(textSliderView);

        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(6000);
//        sliderLayout.addOnPageChangeListener(this);

    }
    @Override
    public void onSliderClick(BaseSliderView slider) {

        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();

    }
    @Override
    protected void onStart() {
        getCount(phone);
        super.onStart();
    }

    private void init() {

        navigationView = findViewById(R.id.nav_view);
        sliderLayout=findViewById(R.id.slider);
        category=findViewById(R.id.card_category);
        search_bar_icon = findViewById(R.id.search_bar_icon_toolbar);
        cart = findViewById(R.id.cart_icon_toolbar);
        date = findViewById(R.id.date_toobar);
        badge=findViewById(R.id.badge_icon_toolbar);
        categoryListRecycler=findViewById(R.id.categoryList);
        productListRecycler=findViewById(R.id.productList);
        imgNaviToolbar=findViewById(R.id.meno_toolbar);
        drawerLayout=findViewById(R.id.drawerr);
//      slider = findViewById(R.id.slider);
//      category_list= findViewById(R.id.category_list);
    }




//    @Override
//    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//    }
//
//    @Override
//    public void onPageSelected(int position) {
//    }
//
//    @Override
//    public void onPageScrollStateChanged(int state) {
//
//    }
}
