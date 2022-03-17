package com.example.customerservice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    public EditText newNIN, newName, newLastname, newBirthday, newPhone, newEmail, newAddress, newCity;
    String customerId;
    Spinner newGenderId;
    RequestQueue requestQueue;
    Button search, update, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        newNIN = (EditText) findViewById(R.id.customerNIN);
        newName = (EditText) findViewById(R.id.customerName);
        newLastname = (EditText) findViewById(R.id.customerLastname);
        newBirthday = (EditText) findViewById(R.id.customerBirthday);
        newAddress = (EditText) findViewById(R.id.customerAddress);
        newCity = (EditText) findViewById(R.id.customerCity);
        newEmail = (EditText) findViewById(R.id.customerEmail);
        newPhone = (EditText) findViewById(R.id.customerPhone);
        newGenderId = (Spinner) findViewById(R.id.genderId);
        search=(Button) findViewById(R.id.search);
        update=(Button)findViewById(R.id.update);
        delete=(Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCustomer(view);
            }
        });

        requestQueue = Volley.newRequestQueue(this);

    }
    public void searchCustomer(View view) {
        String NIN = newNIN.getText().toString().trim();
        String consultURL = "http://192.168.0.5/customerServiceApp/consultCustomer.php?nin=" + NIN;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                consultURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String name, lastname, address, city, email, phone, birthday, gender,id;
                        try {
                            customerId =response.getString("id");
                            name = response.getString("name");
                            lastname = response.getString("lastname");
                            birthday = response.getString("birthday");
                            address = response.getString("address");
                            city = response.getString("city");
                            email = response.getString("email");
                            phone = response.getString("phone");
                            gender = response.getString("gender_id");
                            newName.setText(name);
                            newLastname.setText(lastname);
                            newBirthday.setText(birthday);
                            newAddress.setText(address);
                            newCity.setText(city);
                            newEmail.setText(email);
                            newPhone.setText(phone);
                            newGenderId.setSelection(Integer.parseInt(gender)-1);
                            search.setVisibility(View.GONE);
                            newName.setVisibility(View.VISIBLE);
                            newLastname.setVisibility(View.VISIBLE);
                            newBirthday.setVisibility(View.VISIBLE);
                            newAddress.setVisibility(View.VISIBLE);
                            newCity.setVisibility(View.VISIBLE);
                            newEmail.setVisibility(View.VISIBLE);
                            newPhone.setVisibility(View.VISIBLE);
                            newGenderId.setVisibility(View.VISIBLE);
                            update.setVisibility(View.VISIBLE);
                            delete.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                newName.setText("");
                newLastname.setText("");
                newBirthday.setText("");
                newAddress.setText("");
                newCity.setText("");
                newEmail.setText("");
                newPhone.setText("");
                newGenderId.setSelection(0);
                Toast.makeText(MainActivity2.this,"Customer not found.",Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(jsonObjectRequest);
    }
    public void updateCustomer(View view){
        String updateURL = "http://192.168.0.5/customerServiceApp/updateCustomer.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                updateURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity2.this, "Customer Updated!", Toast.LENGTH_SHORT).show();
                        cleanData();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity2.this, "Error trying to update customer.", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> customer = new HashMap<>();
                customer.put("id", customerId);
                customer.put("name", newName.getText().toString());
                customer.put("lastname", newLastname.getText().toString());
                customer.put("birthday", newBirthday.getText().toString());
                customer.put("nin", newNIN.getText().toString());
                customer.put("gender_id", newGenderId.getSelectedItemPosition()+1+"");
                customer.put("address", newAddress.getText().toString());
                customer.put("city", newCity.getText().toString());
                customer.put("email", newEmail.getText().toString());
                customer.put("phone", newPhone.getText().toString());
                return customer;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void deleteCustomer(View view){


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Customer deletion confirm");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //The code inside was outside before.
                String urlService="http://192.168.0.5/customerServiceApp/deleteCustomer.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlService, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Customer Delete!", Toast.LENGTH_SHORT).show();
                        cleanData();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> customer = new HashMap<>();
                        customer.put("id", customerId);
                        return customer;

                    }
                };
                requestQueue.add(stringRequest);
                ////The code inside was outside before.
                dialogInterface.dismiss();
            };
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alert =builder.create();
        alert.show();



    }
    private void cleanData(){
        search.setVisibility(View.VISIBLE);
        newName.setVisibility(View.GONE);
        newLastname.setVisibility(View.GONE);
        newBirthday.setVisibility(View.GONE);
        newAddress.setVisibility(View.GONE);
        newCity.setVisibility(View.GONE);
        newEmail.setVisibility(View.GONE);
        newPhone.setVisibility(View.GONE);
        newGenderId.setVisibility(View.GONE);
        update.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);
    }
}