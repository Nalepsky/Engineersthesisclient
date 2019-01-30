package com.example.nalepsky.engineers_thesis_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nalepsky.engineers_thesis_client.Utils.SingletonUser;

import java.util.List;

import io.futurestud.retrofit1.api.model.dataHolder.SelectorWithoutEntries;
import io.futurestud.retrofit1.api.service.SelectorClient;
import io.futurestud.retrofit1.api.service.ServerProperties;
import io.futurestud.retrofit1.api.service.UserClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    Button logIn;
    Button createAccount;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        logIn = findViewById(R.id.log_in);
        createAccount = findViewById(R.id.create_account);
        email = findViewById(R.id.email_input);
        password = findViewById(R.id.password_input);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl(ServerProperties.IP)
                            .addConverterFactory(GsonConverterFactory.create());

                    Retrofit retrofit = builder.build();

                    UserClient client = retrofit.create(UserClient.class);
                    Call<Long> call = client.createUser(email.getText().toString(), password.getText().toString());

                    call.enqueue(new Callback<Long>() {
                        @Override
                        public void onResponse(Call<Long> call, Response<Long> response) {
                            if(response.body() != -1L){
                                SingletonUser.setUserId(response.body());
                                Toast.makeText(LoginActivity.this, "Logged as: " + email.getText().toString(), Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                                startActivity(i);
                            } else{
                                Toast.makeText(LoginActivity.this, "email already in base", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Long> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Something went wrong :<", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this, "Please fill both fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl(ServerProperties.IP)
                            .addConverterFactory(GsonConverterFactory.create());

                    Retrofit retrofit = builder.build();

                    UserClient client = retrofit.create(UserClient.class);
                    Call<Long> call = client.loginUser(email.getText().toString(), password.getText().toString());

                    call.enqueue(new Callback<Long>() {
                        @Override
                        public void onResponse(Call<Long> call, Response<Long> response) {
                            if(response.body() != -1L){
                                SingletonUser.setUserId(response.body());
                                Toast.makeText(LoginActivity.this, "Logged as: " + email.getText().toString(), Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                                startActivity(i);
                            } else{
                                Toast.makeText(LoginActivity.this, "Wrong credentials", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Long> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Something went wrong :<", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this, "Please fill both fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
