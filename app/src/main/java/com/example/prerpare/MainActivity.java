package com.example.prerpare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AdapterMask pAdapter;
    String Image;
    ListView listView;
    EditText editTextTextPersonName;
    public static int keyID;
    private List<Mask> listProduct = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView ivProducts = findViewById(R.id.BD);//Находим лист в который будем класть наши объекты
        pAdapter = new AdapterMask(MainActivity.this, listProduct); //Создаем объект нашего адаптера
        ivProducts.setAdapter(pAdapter); //Cвязывает подготовленный список с адаптером
        listView = findViewById(R.id.BD);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                keyID = (int) id;
                Go();
            }
        });
        new GetProducts().execute(); //Подключение к нашей API в отдельном потоке


    }

    public void onGoTOTheADDPage(View v) {
        startActivity(new Intent(this, Add.class));
    }

    private class GetProducts extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://ngknn.ru:5001/NGKNN/зеленцовдр/api/_1234Model/");//Строка подключения к нашей API
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();


            } catch (Exception exception) {
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                listProduct.clear();
                JSONArray tempArray = new JSONArray(s);//преоброзование строки в json массив
                for (int i = 0; i < tempArray.length(); i++) {

                    JSONObject productJson = tempArray.getJSONObject(i);//Преобразование json объекта в нашу структуру
                    Mask tempProduct = new Mask(
                            productJson.getInt("ID"),
                            productJson.getString("Title"),
                            productJson.getInt("Cost"),
                            productJson.getInt("StockAvailability"),
                            productJson.getInt("AvailabilityInTheStore"),
                            productJson.getString("Description"),
                            productJson.getString("Rewiews"),
                            productJson.getString("Image")

                    );

                    listProduct.add(tempProduct);
                    pAdapter.notifyDataSetInvalidated();
                }
            } catch (Exception ignored) {


            }
        }

    }
    public void Go() {
        startActivity(new Intent(this, ChangeActivity.class));
    }

}