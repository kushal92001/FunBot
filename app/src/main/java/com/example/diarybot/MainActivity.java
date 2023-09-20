package com.example.diarybot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView chatsRV;
    private EditText userMsgEdt;
    private FloatingActionButton sendMsgFAB;
    private final String BOT_KEY = "bot";
    private final String USER_KEY = "user";
//    FirebaseDatabase database;
//    FirebaseAuth firebaseAuth;
//    String senderRoom, recieverRoom;

    private ArrayList<ChatsModel> chatsModelArrayList;
    private ChatRVAdapter chatRVAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatsRV = findViewById(R.id.idRVChats);
        userMsgEdt = findViewById(R.id.idEdtMessage);
        sendMsgFAB = findViewById(R.id.idFABSend);

//        database = FirebaseDatabase.getInstance();
//        firebaseAuth = FirebaseAuth.getInstance();
//        reciverUID = getIntent().getStringExtra("uid");

        chatsModelArrayList = new ArrayList<>();

        chatRVAdapter = new ChatRVAdapter(chatsModelArrayList,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        chatsRV.setLayoutManager(manager);
        chatsRV.setAdapter(chatRVAdapter);

//        SenderUID = firebaseAuth.getUid();
//        senderRoom = SenderUID+reciverUID;
//        recieverRoom = reciverUID+SenderUID;

//        DatabaseReference reference = database.getReference().child("user").child(firebaseAuth.getUid());
//        DatabaseReference chatreference = database.getReference().child("chats").child(senderRoom).child("messages");

//        chatreference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                chatsModelArrayList.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    ChatsModel messages = dataSnapshot.getValue(ChatsModel.class);
//                    chatsModelArrayList.add(messages);
//                }
//                chatRVAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        sendMsgFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userMsgEdt.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "How's your day going?", Toast.LENGTH_SHORT).show();
                    return;
                }
                getResponse(userMsgEdt.getText().toString());
                userMsgEdt.setText("");
//                Date date = new Date();
//                ChatsModel messagess = new ChatsModel(message,SenderUID,date.getTime());
//                database=FirebaseDatabase.getInstance();
//                database.getReference().child("chats").child("senderRoom").child("messages").push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        database.getReference().child("chats").child("recieverRoom").child("messages").push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//
//                            }
//                        });
                    }
                });
            }
            private void getResponse(String message){
                Date d=new Date();
                chatsModelArrayList.add(new ChatsModel(message,USER_KEY,d.getTime()));
                chatRVAdapter.notifyDataSetChanged();
                String url="http://api.brainshop.ai/get?bid=176467&key=VOXzODXM1THt2dns&uid=[uid]&msg="+message;
                String BASE_URL="http://api.brainshop.ai/";
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
                Call<MsgModel> call = retrofitAPI.getMessage(url);
                call.enqueue(new Callback<MsgModel>() {
                    @Override
                    public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {
                        if(response.isSuccessful()) {
                            MsgModel model = response.body();//pass resp to msgmodel class
                            chatsModelArrayList.add(new ChatsModel(model.getCnt(),BOT_KEY,d.getTime()));
                            chatRVAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<MsgModel> call, Throwable t) {
                        chatsModelArrayList.add(new ChatsModel("Please re-enter what you want me to remember",BOT_KEY,d.getTime()));
                        chatRVAdapter.notifyDataSetChanged();
                    }
                });
            }
//        });
//    }
}