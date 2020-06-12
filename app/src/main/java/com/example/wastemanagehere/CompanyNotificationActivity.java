package com.example.wastemanagehere;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CompanyNotificationActivity extends AppCompatActivity {
    RecyclerView CustomerList;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("Customer_data", MODE_PRIVATE);
        setContentView(R.layout.activity_company_notification);
        CustomerList = findViewById(R.id.CustomerList);
        CustomerList.setLayoutManager(new LinearLayoutManager(this));
        CustomerList.setAdapter(new MyAdapter());

    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.CustomViewHolder> {
        JSONArray array = new JSONArray();

        public MyAdapter() {
            try {
                array = new JSONArray(preferences.getString("CustomerList", ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @NonNull
        @Override
        public MyAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.customer_list_row, parent, false);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.CustomViewHolder holder, int position) {
            try {
                JSONObject object = array.getJSONObject(position);
                holder.name.setText(object.getString("customer_name"));
                holder.type.setText(object.getString("type"));
                holder.location.setText(object.getString("location"));
                holder.ll_customer_row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(CompanyNotificationActivity.this,CompanyOnSelectActivity.class).putExtra("object",object.toString()).putExtra("position",position));
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {

            return array.length();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            TextView name, type, location;
            LinearLayout ll_customer_row;

            public CustomViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.c_name);
                type = itemView.findViewById(R.id.c_type);
                location = itemView.findViewById(R.id.c_loation);
                ll_customer_row = itemView.findViewById(R.id.ll_customer_row);
            }
        }
    }

}