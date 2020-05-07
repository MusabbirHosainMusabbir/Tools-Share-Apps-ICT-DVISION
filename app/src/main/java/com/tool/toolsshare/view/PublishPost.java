package com.tool.toolsshare.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.flexbox.FlexboxLayout;
import com.tool.Utils.Helper;
import com.tool.Utils.PreferenceMangement;
import com.tool.Utils.Utils;
import com.tool.toolsshare.R;
import com.tool.toolsshare.viewmodel.CategoryListViewModel;
import com.tool.toolsshare.viewmodel.PublishPostViewModel;
import com.tool.toolsshare.viewmodel.RegistrationViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class PublishPost extends AppCompatActivity {
    Button publishButton;
    ImageView addBtn;

    EditText title, description, location, phone, price;

    ArrayList<TextView>categoryTvList;
    ArrayList<String> imageBas64List;

    int categoryId;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String base64String;

    ImageView imageViewTosetCapturedImage;

    FlexboxLayout flexboxLayout;
    CategoryListViewModel categoryListViewModel;
    private static final int CAMERA_REQUEST = 1888;
    private String userChoosenTask;
    String encoded1 =null,encoded2 = null,encoded3=null;

    PublishPostViewModel publishPostViewModel;

    View.OnClickListener catClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.e("cat ---->", "onClick: cat id : "+view.getTag() );

            if (categoryId != Integer.valueOf(view.getTag().toString())){
                view.setBackgroundResource(R.drawable.selected_capsule);
                if (categoryId != 0){
                    categoryTvList.get(categoryId-1).setBackgroundResource(R.drawable.unselected_capsule);
                }
            }

            categoryId = Integer.valueOf(view.getTag().toString());


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_post);
        publishButton = findViewById(R.id.publish_btn);

        flexboxLayout = findViewById(R.id.flex_category_layout);

        imageBas64List = new ArrayList<>();

        title = findViewById(R.id.title_et);
        description = findViewById(R.id.description_et);
        phone = findViewById(R.id.phone_et);
        location = findViewById(R.id.location_et);
        price = findViewById(R.id.price_et);


        Log.e("first time--->", "onCreate: "+categoryId );

        Utils.fullScreenView(this,false);

        if (!Utils.hasNavBar(this)){
            Log.e("hasNav--->", "onCreate: YES---> " );
            Utils.adjustBottomNav(this,R.id.soft_key_layout,R.id.main_layout);
            //navLayout.setVisibility(View.GONE);

        }

        categoryTvList = new ArrayList<>();

        Helper.showLoader(PublishPost.this,"");

        JSONObject reqJsonObj = new JSONObject();

        try {
            reqJsonObj.put("user_id", PreferenceMangement.getPreference(this,"user_id"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        categoryListViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(CategoryListViewModel.class);
        categoryListViewModel.initialize(getApplication(),reqJsonObj);
        categoryListViewModel.getCategories().observe(PublishPost.this, new Observer<ArrayList<HashMap<String, String>>>() {
            @Override
            public void onChanged(ArrayList<HashMap<String, String>> hashMaps) {
                Helper.cancelLoader();
                Log.e("size--->", "onChanged: "+hashMaps.size() );
                if (hashMaps.size()>0){
                    Log.e("categoy name--->", "onChanged: "+hashMaps.get(0).get("name") );

                    for (int i = 0; i<hashMaps.size();i++) {

                        TextView tv = new TextView(PublishPost.this);
                        tv.setGravity(Gravity.CENTER);
                        tv.setText(hashMaps.get(i).get("name"));
                        tv.setTag(hashMaps.get(i).get("id"));
                        tv.setOnClickListener(catClickListener);
                        tv.setPadding(15,15,15,15);
                        tv.setBackgroundResource(R.drawable.unselected_capsule);

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.setMargins(5,5,5,5);
                        tv.setLayoutParams(params);

                        categoryTvList.add(tv);

                        flexboxLayout.addView(tv);


                    }
                }
            }
        });



        publishButton.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 //startActivity(new Intent(PublishPost.this,SendRequest.class));
                                                 //finish();

                                                 Helper.showLoader(PublishPost.this, "");


                                                 JSONObject reqJsonObj = new JSONObject();

                                                 JSONArray imageJsonArray = new JSONArray();
                                                 JSONObject imageObj = new JSONObject();

                                                 try {
                                                     reqJsonObj.put("name", title.getText().toString().trim());
                                                     reqJsonObj.put("description", description.getText().toString().trim());
                                                     reqJsonObj.put("phone", phone.getText().toString().trim());
                                                     reqJsonObj.put("location", location.getText().toString().trim());
                                                     reqJsonObj.put("price", price.getText().toString().trim());
                                                     reqJsonObj.put("category_id", categoryId);
                                                     reqJsonObj.put("top_gift", "1");
                                                     reqJsonObj.put("conditions", "1");
                                                     reqJsonObj.put("reason_for_gift", "ddd");
                                                     reqJsonObj.put("target_to_gift", "Developers");
                                                     reqJsonObj.put("user_id", PreferenceMangement.getPreference(PublishPost.this,"user_id"));

                                                     for (int i = 0; i < imageBas64List.size(); i++) {
                                                         //imageObj.put("image", imageBas64List.get(i));
                                                         imageJsonArray.put(imageBas64List.get(i));
                                                     }

                                                     reqJsonObj.put("images", imageJsonArray);

                                                 } catch (JSONException e) {
                                                     e.printStackTrace();
                                                 }

                                                 publishPostViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(PublishPostViewModel.class);
                                                 publishPostViewModel.initialize(getApplication(), reqJsonObj);
                                                 publishPostViewModel.getPublishStatus().observe(PublishPost.this, new Observer<HashMap<String, String>>() {
                                                     @Override
                                                     public void onChanged(HashMap<String, String> stringStringHashMap) {

                                                         Helper.cancelLoader();

                                                         if (stringStringHashMap.get("status").equals("1")) {
                                                             startActivity(new Intent(PublishPost.this,HomeActivity.class));
                                                             finish();
                                                             Log.e("check--->", "onChanged: " + "Success");
                                                         } else {
                                                             Log.e("check--->", "onChanged: " + "failed");
                                                         }
                                                     }

                                                 });
                                             }
        });
    }


    /*public void captureImage(View view) {

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {
            Intent intent = new Intent(PublishPost.this,HomeActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void captureImage(View view) {


        imageViewTosetCapturedImage = findViewById(view.getId());
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(PublishPost.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utils.checkPermission(PublishPost.this);

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
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

        Bitmap bitmap = (Bitmap) data.getExtras().get("data");

        //encode  to byte format
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        /*Log.e("thumbnail",String.valueOf(thumbnail==null));
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        imageViewTosetCapturedImage.setImageBitmap(bitmap);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        base64String = Base64.encodeToString(byteArray, Base64.DEFAULT);

        Log.e("BASE64--->", "onCaptureImageResult: "+base64String );


        imageViewTosetCapturedImage.setOnClickListener(null);
        switch (imageViewTosetCapturedImage.getId()){
            case R.id.image1:
                encoded1 = base64String;
                imageBas64List.add(encoded1);
                findViewById(R.id.image2_layout).setVisibility(View.VISIBLE);
                break;
            case R.id.image2:
                encoded2 = base64String;
                imageBas64List.add(encoded2);
                findViewById(R.id.image3_layout).setVisibility(View.VISIBLE);
                break;
            case R.id.image3:
                encoded3 = base64String;
                imageBas64List.add(encoded3);
                break;
        }

        imageViewTosetCapturedImage.post(new Runnable() {
            @Override
            public void run()
            {
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

        base64String = getStringImage(bm);
        imageViewTosetCapturedImage.setImageBitmap(bm);
    }

    private String getStringImage(Bitmap thumbnail) {
        String imgString="",convertbase64="";
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        convertbase64 = "data:image/png;base64,"+imgString;
        //convertbase64 = imgString;
        return convertbase64;
        //
        //return encoded1;
    }

}
