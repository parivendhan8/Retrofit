package com.example.pagination;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pagination.Pojo.Pages;
import com.example.pagination.Pojo.PagesData;
import com.example.pagination.Retrofit.RetrofitConstant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView_Adapter adapter;
    List<PagesData> arrayList = new ArrayList<PagesData>();
    ArrayList<String> stringArrayList = new ArrayList<String>();
    private Integer page_no = 1;
    private Integer count = 1;
    ProgressBar progressBar;
    private Boolean isLoading = true;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.ProgressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));




        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringArrayList.clear();
                count = 0;

                for (int i = 0; i < 20; i++) {
                    stringArrayList.add(String.valueOf(count));
                    count++;

                }

                adapter = new RecyclerView_Adapter(MainActivity.this, stringArrayList);
                recyclerView.setAdapter(adapter);
            }
        });





        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        Call<Pages> pagesDataCall = RetrofitConstant.getInstance().getApi().getPageDate(page_no, 5);

        pagesDataCall.enqueue(new Callback<Pages>() {
            @Override
            public void onResponse(Call<Pages> call, Response<Pages> response) {

                if (response.isSuccessful()) {

                    Pages data = response.body();
//                    arrayList = data.getData();
//                    adapter = new RecyclerView_Adapter(MainActivity.this, arrayList);
//                    recyclerView.setAdapter(adapter);

                    Log.d("response", response + "");

                }
            }

            @Override
            public void onFailure(Call<Pages> call, Throwable t) {

            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {

                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int visibleItem = linearLayoutManager.getChildCount();
                    int totalItem = linearLayoutManager.getItemCount();
                    int scrollOut = linearLayoutManager.findFirstVisibleItemPosition();



//                        Log.d("On_scroll" , "On scroll");

//                        !isLoading &&

//                        if (totalItem > previousTotal) {
//                            loading = false;
//                            previousTotal = totalItem;
//                        }

                    if (isLoading) {
                        if ((visibleItem + scrollOut) >= totalItem)
                        {
                            isLoading = false;
                            putdata();
                        }


                    }

//                    if (isLoading) {
//
//
////                        page_no++;
////                        if (page_no < 4)
////                            getDataMethod(page_no);
//
//
//
//                    }


                }
            }
        });


    }

    public void getDataMethod(final int page_no) {
        progressBar.setVisibility(View.VISIBLE);
        Call<Pages> pagesDataCall = RetrofitConstant.getInstance().getApi().getPageDate(page_no, 3);

        pagesDataCall.enqueue(new Callback<Pages>() {
            @Override
            public void onResponse(Call<Pages> call, Response<Pages> response) {

                if (response.isSuccessful()) {

                    progressBar.setVisibility(View.INVISIBLE);
                    Pages data = response.body();
                    arrayList.addAll(data.getData());
//                    adapter = new RecyclerView_Adapter(MainActivity.this, arrayList);
//                    recyclerView.setAdapter(adapter);
                    Log.d("page_no", page_no + "");

                    Log.d("response", response + "");

                }
            }

            @Override
            public void onFailure(Call<Pages> call, Throwable t) {

            }
        });
    }

    public void putdata() {

        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                isLoading = true;
                for (int i = 0; i < 20; i++) {
                    stringArrayList.add(String.valueOf(count));
                    count++;

                }
                Toast.makeText(MainActivity.this, "Size" + stringArrayList.size(), Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();


            }
        }, 3000);


    }

    public class RecyclerView_Adapter extends RecyclerView.Adapter<RecyclerView_Adapter.MyViewHolder> {


        Context context;
        List<PagesData> list = new ArrayList<PagesData>();
        ArrayList<String> stringsList = new ArrayList<String>();

        public RecyclerView_Adapter(Context context, List<PagesData> list) {
            this.context = context;
            this.list = list;
        }

        public RecyclerView_Adapter(Context context, ArrayList<String> stringsList) {
            this.context = context;
            this.stringsList = stringsList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.child_list, null);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

            holder.first_name.setText(stringsList.get(i));
//        holder.first_name.setText(list.get(i).getFirstName());
//        holder.last_name.setText(list.get(i).getLastName());
//        holder.url.setText(list.get(i).getAvatar());

        }

        @Override
        public int getItemCount() {
            return stringsList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView last_name, first_name, url;

            public MyViewHolder(@NonNull View v) {
                super(v);

                first_name = (TextView) v.findViewById(R.id.first_name);
                last_name = (TextView) v.findViewById(R.id.last_name);
                url = (TextView) v.findViewById(R.id.url);
            }
        }
    }
}
