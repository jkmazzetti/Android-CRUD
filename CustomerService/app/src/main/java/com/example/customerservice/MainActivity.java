package com.example.customerservice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText name, lastname, birthday, nin, address, city, email, phone;
    Spinner genderId;
    RequestQueue requestQueue;
    private String insertCustomerURL ="http://192.168.0.5/customerServiceApp/insertCustomer.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue= Volley.newRequestQueue(this);

        name =(EditText) findViewById(R.id.customerName);
        lastname =(EditText) findViewById(R.id.customerLastname);
        birthday =(EditText) findViewById(R.id.customerBirthday);
        nin =(EditText) findViewById(R.id.customerNIN);
        address =(EditText) findViewById(R.id.customerAddress);
        city =(EditText) findViewById(R.id.customerCity);
        email =(EditText) findViewById(R.id.customerEmail);
        phone =(EditText) findViewById(R.id.customerPhone);
        genderId =(Spinner) findViewById(R.id.genderId);

    }

    public void saveCustomer(View view) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                insertCustomerURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "Customer Was Registered!", Toast.LENGTH_SHORT).show();

                        name.setText("");
                        lastname.setText("");
                        birthday.setText("");
                        nin.setText("");
                        address.setText("");
                        city.setText("");
                        email.setText("");
                        phone.setText("");
                        genderId.setSelection(0);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error trying to register customer.", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> customer = new HashMap<>();
                customer.put("name", name.getText().toString());
                customer.put("lastname", lastname.getText().toString());
                customer.put("birthday", birthday.getText().toString());
                customer.put("nin", nin.getText().toString());
                customer.put("gender_id", genderId.getSelectedItemPosition()+1+"");
                customer.put("address", address.getText().toString());
                customer.put("city", city.getText().toString());
                customer.put("email", email.getText().toString());
                customer.put("phone", phone.getText().toString());
                return customer;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void consultCustomer(View view){
        Intent intent= new Intent(this, MainActivity2.class);
        startActivity(intent);

    }
}