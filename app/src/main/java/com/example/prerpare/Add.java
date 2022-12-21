package com.example.prerpare;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Add extends AppCompatActivity implements View.OnClickListener{

    EditText add_title;
    EditText add_cost;
    EditText add_stockAvailability;
    EditText add_availabilityInTheStore;
    EditText add_description;
    EditText add_rewiews;

    String nPicture;
    ImageView picture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        add_title = findViewById(R.id.add_title);
        add_cost = findViewById(R.id.add_cost);
        add_stockAvailability = findViewById(R.id.add_stockAvailability);
        add_availabilityInTheStore = findViewById(R.id.add_availabilityInTheStore);
        add_description = findViewById(R.id.add_description);
        add_rewiews = findViewById(R.id.add_rewiews);
        picture = findViewById(R.id.add_image);

        ImageView buttonChange = findViewById(R.id.complete_add_item_btn);
        buttonChange.setOnClickListener(this);
        picture.setOnClickListener(this);



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

    public void onClick2(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }


    private void postCreate(String Title, int Cost, int StockAvailability, int AvailabilityInTheStore, String Description, String Rewiews, String Image) {


        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://ngknn.ru:5001/NGKNN/зеленцовдр/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitApi retrofitAPI = retrofit.create(RetrofitApi.class);
            DataModal modal = new DataModal(Title, Cost, StockAvailability, AvailabilityInTheStore, Description, Rewiews, Image);
            Call<DataModal> call = retrofitAPI.createPost(modal);
            call.enqueue(new Callback<DataModal>() {
                @Override
                public void onResponse(Call<DataModal> call, Response<DataModal> response) {

                    if (!response.isSuccessful()) {
                        Toast.makeText(Add.this, "Во время добавления что-то пошло не по плану. Мы уже разбираемся", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(Add.this, "Товар успешно добавлен", Toast.LENGTH_SHORT).show();
                    add_title.setText("");
                    add_cost.setText("");
                    add_stockAvailability.setText("");
                    add_availabilityInTheStore.setText("");
                    add_description.setText("");
                    add_rewiews.setText("");



                }

                @Override
                public void onFailure(Call<DataModal> call, Throwable t) {
                    Toast.makeText(Add.this, "Еще секундочку....: " + t.getMessage(), Toast.LENGTH_LONG).show();


                }
            });
        }
        catch (Exception e) {
            Toast.makeText(Add.this, "Что-то пошло не так в процессе добавления: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.complete_add_item_btn: {

                String Title = add_title.getText().toString();
                String Cost = add_cost.getText().toString();
                String StockAvailability = add_stockAvailability.getText().toString();
                String AvailabilityInTheStore = add_availabilityInTheStore.getText().toString();
                String Description = add_description.getText().toString();
                String Rewiews = add_rewiews.getText().toString();

                postCreate(Title, Integer.parseInt(Cost), Integer.parseInt(StockAvailability),Integer.parseInt(AvailabilityInTheStore),Description,Rewiews, nPicture);
                new Handler().postDelayed(() -> startActivity(
                        new Intent(Add.this, MainActivity.class)), 1000);


            }
            break;

            case R.id.add_image:

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                pickImg.launch(intent);
                break;

        }
    }
}