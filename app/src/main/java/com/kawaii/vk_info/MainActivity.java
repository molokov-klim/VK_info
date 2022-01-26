package com.kawaii.vk_info;

import static com.kawaii.vk_info.utils.NetworkUtils.generateURL;
import static com.kawaii.vk_info.utils.NetworkUtils.getResponseFromURL;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private EditText searchField;
    private Button searchButton;
    private TextView result;

    //создаем класс для потока. AsyncTask<что отправляем, что получаем в процессе, что получаем в итоге>
    class VKQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        // метод ожидает массив из URL
        protected String doInBackground(URL... urls) {
            String response = null;
            try {
                response = getResponseFromURL(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }
        // вставляем ответ в элемент
        @Override
        protected void onPostExecute(String response){
            String firstName = null;
            String  lastName = null;
            //парсим JSON "{response:[{"id":123,"first_name":"Klim","last_name":"Molokov"}]}
            try {
                JSONObject jsonResponse = new JSONObject(response);
                // это массив в json'е
                JSONArray jsonArray = jsonResponse.getJSONArray(response);
                JSONObject userInfo = jsonArray.getJSONObject(0);

                firstName = userInfo.getString("first_name");
                lastName = userInfo.getString("last_name");


            } catch (JSONException e) {
                e.printStackTrace();
            }
            String resultingString = "Имя: " + firstName + "\n" + "Фамилия: " + lastName;
            result.setText(resultingString);
            System.out.print(result);
            System.out.print(firstName);
            System.out.print(lastName);
        }
    }

    // главный поток
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchField = findViewById(R.id.et_search_field);
        searchButton = findViewById(R.id.b_search_vk);
        result = findViewById(R.id.tv_result);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URL generatedURL = generateURL(searchField.getText().toString());

                new VKQueryTask().execute(generatedURL);


            }
        };

        searchButton.setOnClickListener(onClickListener);


    }
}


