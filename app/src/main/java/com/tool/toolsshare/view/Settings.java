package com.tool.toolsshare.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tool.Utils.Helper;
import com.tool.Utils.PreferenceMangement;
import com.tool.Utils.Utils;
import com.tool.toolsshare.R;
import com.tool.toolsshare.viewmodel.ProfileUpdateViewModel;
import com.tool.toolsshare.viewmodel.ReqCountViewModel;
import com.tool.toolsshare.viewmodel.RequesterViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class Settings extends AppCompatActivity {
    ImageView search, profileImage, imageViewTosetCapturedImage, post, back;
    EditText name,email,address, mobile;
    TextView titleTv;
    Button updateBt;

    RequesterViewModel requesterViewModel;

    ProfileUpdateViewModel profileUpdateViewModel;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String base64String;

    private static final int CAMERA_REQUEST = 1888;
    private String userChoosenTask;
    String encoded1 =null;

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        search = findViewById(R.id.search_img);
        profileImage = findViewById(R.id.profile_mage);
        name = findViewById(R.id.name_et);
        email = findViewById(R.id.email_et);
        address = findViewById(R.id.address_et);
        mobile = findViewById(R.id.mobile_et);
        titleTv = findViewById(R.id.title_tv);
        updateBt = findViewById(R.id.update_btn);
        post = findViewById(R.id.post_img);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        back = findViewById(R.id.back_img);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.this,Profile.class));
                finish();
            }
        });


        Utils.fullScreenView(this,false);

        if (!Utils.hasNavBar(this)){
            Log.e("hasNav--->", "onCreate: YES---> " );
            Utils.adjustBottomNav(this,R.id.soft_key_layout,R.id.main_layout);
            //navLayout.setVisibility(View.GONE);

        }

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.this, PublishPost.class));
                finish();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.this,InviteFriends.class));
                finish();
            }
        });


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", PreferenceMangement.getPreference(Settings.this,"user_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Helper.showLoader(Settings.this,"");

        requesterViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RequesterViewModel.class);
        requesterViewModel.initialize(getApplication(),jsonObject);

        requesterViewModel.getUser().observe(Settings.this, new Observer<HashMap<String, String>>() {
            @Override
            public void onChanged(HashMap<String, String> stringStringHashMap) {
                Helper.cancelLoader();
                titleTv.setText(stringStringHashMap.get("name"));
                name.setText(stringStringHashMap.get("name"));

                name.setSelection(name.getText().length());

                email.setText(stringStringHashMap.get("email"));
                email.setEnabled(false);
                address.setText(stringStringHashMap.get("location"));
                mobile.setText(stringStringHashMap.get("mobile"));

                Log.e("image",stringStringHashMap.get("image"));

                Glide.with(Settings.this)
                        .load(stringStringHashMap.get("image"))
                        .into(profileImage);

            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                captureProfileImage();

            }
        });

        updateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Helper.showLoader(Settings.this,"");
                JSONObject dataForUpdate = new JSONObject();
                try {
                    dataForUpdate.put("name",name.getText().toString().trim());
                    dataForUpdate.put("mobile",mobile.getText().toString().trim());
                    dataForUpdate.put("id",PreferenceMangement.getPreference(Settings.this,"user_id"));
                   dataForUpdate.put("location",address.getText().toString().trim());
                    if (encoded1 != null){
                        dataForUpdate.put("image",encoded1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                profileUpdateViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ProfileUpdateViewModel.class);
                profileUpdateViewModel.initialize(getApplication(),dataForUpdate);
                profileUpdateViewModel.getStatus().observe(Settings.this, new Observer<HashMap<String, String>>() {
                    @Override
                    public void onChanged(HashMap<String, String> stringStringHashMap) {
                        Helper.cancelLoader();
                        if (stringStringHashMap.get("status").equals("1")){
                            Toast.makeText(Settings.this,"Updated",Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(Settings.this,Profile.class));
                            finish();
                        }
                        else {

                            Toast.makeText(Settings.this,"Whoops !",Toast.LENGTH_SHORT).show();

                        }
                    }
                });


            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
// on favorites clicked
                    Intent intent = new Intent(Settings.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }

                else if (item.getItemId() == R.id.rental){
                    Intent intent = new Intent(Settings.this,MyRentals.class);
                    startActivity(intent);
                    finish();
                    return true;

                }

                else if (item.getItemId() == R.id.profile){
                    Intent intent = new Intent(Settings.this,Profile.class);
                    startActivity(intent);
                    finish();
                    return true;

                }
                return false;
            }
        });


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {
            startActivity(new Intent(Settings.this,Profile.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void captureProfileImage() {

        Log.e("ttapped--->", "captureProfileImage: " );


        imageViewTosetCapturedImage = findViewById(R.id.profile_mage);
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utils.checkPermission(Settings.this);

                Log.e("PERMISSION-->", "onClick: "+result );

                if (result){
                    if (items[item].equals("Take Photo")) {
                        userChoosenTask ="Take Photo";
                        cameraIntent();

                    } else if (items[item].equals("Choose from Library")) {
                        userChoosenTask ="Choose from Library";

                        galleryIntent();

                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }



            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Log.e("camera","carmera");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult(data);
              /*  try {

                    profileImg.setImageBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData()));
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("exception",e.getMessage());
                }*/
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        //Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

        Bitmap bitmap = (Bitmap) data.getExtras().get("data");

        //encode  to byte format
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();


        base64String = Base64.encodeToString(byteArray, Base64.DEFAULT);
        encoded1 = base64String;

        Log.e("BASE64--->", "onCaptureImageResult: "+base64String );

        /*switch (imageViewTosetCapturedImage.getId()){
            case R.id.image1:
                encoded1 = base64String;
                break;
        }*/

        imageViewTosetCapturedImage.post(new Runnable() {
            @Override
            public void run()
            {
                encoded1 = getStringImage(bitmap);
                imageViewTosetCapturedImage.setImageBitmap(bitmap);
            }
        });



    }


    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        encoded1 = getStringImage(bm);
        imageViewTosetCapturedImage.setImageBitmap(bm);
    }

    private String getStringImage(Bitmap thumbnail) {
        String imgString="";
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        imgString = Base64.encodeToString(byteFormat, Base64.DEFAULT);
        return imgString;
        //
        //return encoded1;
    }
}
