package com.example.prerpare;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangeActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edit_title;
    EditText edit_cost;
    EditText edit_stockAvailability;
    EditText edit_availabilityInTheStore;
    EditText edit_description;
    EditText edit_rewiews;
    ImageView buttonDel;
    ImageView buttonChange;

    String nPicture;
    ImageView picture;
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Bitmap bitmap = null;
                    ImageView imageView = (ImageView) findViewById(R.id.add_image);
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Uri selectedImage = result.getData().getData();
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        imageView.setImageBitmap(null);
                        imageView.setImageBitmap(bitmap);
                        ImageView deletePicture = findViewById(R.id.change_item_delete_btn);
                        deletePicture.setVisibility(View.VISIBLE);
                        nPicture = BitMapToString(bitmap);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        edit_title = findViewById(R.id.edit_title);
        edit_cost = findViewById(R.id.edit_cost);
        edit_stockAvailability = findViewById(R.id.edit_stockAvailability);
        edit_availabilityInTheStore = findViewById(R.id.edit_availabilityInTheStore);
        edit_description = findViewById(R.id.edit_description);
        edit_rewiews = findViewById(R.id.edit_rewiews);
        picture = findViewById(R.id.edit_image);
        buttonChange = findViewById(R.id.complete_edit_item_btn);
        buttonDel = findViewById(R.id.change_item_delete_btn);

        buttonDel.setOnClickListener(this);
        buttonChange.setOnClickListener(this);
        picture.setOnClickListener(this);

    }
    private void showAll() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/ngknn/зеленцовдр/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitAPI = retrofit.create(RetrofitApi.class);
        Call<DataModal> call = retrofitAPI.getDATA(MainActivity.keyID);
        call.enqueue(new Callback<DataModal>() {

            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ChangeActivity.this, "При выводе данных возникла ошибка", Toast.LENGTH_SHORT).show();
                }
                edit_title.setText(response.body().getTitle());

                edit_cost.setText(response.body().getCost());
                edit_stockAvailability.setText(response.body().getStockAvailability());
                edit_availabilityInTheStore.setText(response.body().getAvailabilityInTheStore());
                edit_description.setText(response.body().getDescription());
                edit_rewiews.setText(response.body().getRewiews());

                nPicture = response.body().getImage();

                if (response.body().getImage() == null) {
                    picture.setImageResource(R.drawable.img);
                } else {
                    Bitmap bitmap = StringToBitMap(response.body().getImage());
                    picture.setImageBitmap(bitmap);
                }


//
            }

            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {
                Toast.makeText(ChangeActivity.this, "При выводе данных возникла ошибка: " + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public void nonePicture(View v) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        someActivityResultLauncher.launch(photoPickerIntent);

    }


    public void onClick2(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void getChangeRow(String Title, String Cost, String StockAvailability, String AvailabilityInTheStore, String Description, String Rewiews, String Image) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/зеленцовдр/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitAPI = retrofit.create(RetrofitApi.class);

        DataModal modal = new DataModal(Title, Integer.parseInt(Cost) , Integer.parseInt(StockAvailability), Integer.parseInt(AvailabilityInTheStore), Description,Rewiews, Image);
        Call<DataModal> call = retrofitAPI.updateData(MainActivity.keyID, modal);
        call.enqueue(new Callback<DataModal>() {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(ChangeActivity.this, "В процессе изменения данных произошла ошибка", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ChangeActivity.this, "Данные успешно изменены", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {
                Toast.makeText(ChangeActivity.this, "При изменении записи возникла ошибка: " + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }


    public static String encodeImage(Bitmap bitmap) {
        int prevW = 500;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();

        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return java.util.Base64.getEncoder().encodeToString(bytes);
        }
        return "";
    }

    public static Bitmap getImgBitmap(Context context, String encodedImg) {
        if (!encodedImg.equals("null")) {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = java.util.Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }

        return BitmapFactory.decodeResource(context.getResources(), R.drawable.img);
    }

    public final ActivityResultLauncher<Intent> pickImg = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null) {
                        Uri uri = result.getData().getData();
                        try {
                            InputStream is = getContentResolver().openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(is);
                            picture.setImageBitmap(bitmap);
                            nPicture = encodeImage(bitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.complete_edit_item_btn: {

                String Title = edit_title.getText().toString();
                String Cost = edit_cost.getText().toString();
                String StockAvailability = edit_stockAvailability.getText().toString();
                String AvailabilityInTheStore = edit_availabilityInTheStore.getText().toString();
                String Description = edit_description.getText().toString();
                String Rewiews = edit_rewiews.getText().toString();


                getChangeRow(Title, Cost, StockAvailability, AvailabilityInTheStore, Description, Rewiews,nPicture);
                new Handler().postDelayed(() -> startActivity(
                        new Intent(ChangeActivity.this, MainActivity.class)), 1000);


            }
            break;
            case R.id.change_item_delete_btn:

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://ngknn.ru:5001/NGKNN/зеленцовдр/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitApi retrofitAPI = retrofit.create(RetrofitApi.class);
                Call call = retrofitAPI.deleteData(MainActivity.keyID);
                call.enqueue(new Callback<DataModal>() {
                    @Override
                    public void onResponse(Call<DataModal> call, Response<DataModal> response) {

                        if (!response.isSuccessful()) {
                            Toast.makeText(ChangeActivity.this, "При удание записи возникла ошибка", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(ChangeActivity.this, "Удаление прошло успешно", Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(() -> startActivity(
                                new Intent(ChangeActivity.this, MainActivity.class)), 1000);
                    }

                    @Override
                    public void onFailure(Call<DataModal> call, Throwable t) {
                        Toast.makeText(ChangeActivity.this, "При удаление записи возникла ошибка: " + t.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

                break;

            case R.id.edit_image:

                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                pickImg.launch(intent);
                break;

        }
    }
}