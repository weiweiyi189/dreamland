package com.example.dreamland.ui.chat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.*;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dreamland.R;
import com.example.dreamland.entity.BaseMessage;
import com.example.dreamland.entity.ChatGptResponse;
import com.example.dreamland.entity.User;
import com.example.dreamland.ui.adapter.MessageListAdapter;
import com.example.dreamland.ui.util.ProxiedHurlStack;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MessageListActivity extends AppCompatActivity {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;

    TextInputLayout queryEdt;

    User chatBot = new User();

    private List<BaseMessage> messageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_list_activity);
        this.chatBot.setUsername("小梦");

        mMessageRecycler = (RecyclerView) findViewById(R.id.recycler_gchat);
        mMessageAdapter = new MessageListAdapter(this, messageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);
        this.setListener();

        BaseMessage waitMessage = new BaseMessage();
        waitMessage.setMessage("输入梦境小梦可以帮你解梦哦😄。示例输入：“梦见鲸鱼意味着什么？”");
        waitMessage.setSender(chatBot);
        waitMessage.setCreatedAt(new Date().getTime());
        waitMessage.setType(VIEW_TYPE_MESSAGE_RECEIVED);
        messageList.add(waitMessage);
        mMessageAdapter.notifyDataSetChanged();
        scrollToBottom();

        mMessageRecycler.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    scrollToBottom();
                }
            }
        });

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setListener() {
        queryEdt = findViewById(R.id.idTILQuery);

        queryEdt.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (queryEdt.getEditText().getText().toString().length() > 0) {
                    view.setEnabled(false);
                    queryEdt.setEndIconDrawable(R.drawable.block);
                    getResponse(queryEdt.getEditText().getText().toString(), view);
                } else {
                    Toast.makeText(MessageListActivity.this, "请输入您的查询", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getResponse(String query, View view) {
        try {
            BaseMessage message = new BaseMessage();
            message.setMessage(query);
            message.setSender(chatBot);
            message.setCreatedAt(new Date().getTime());
            message.setType(VIEW_TYPE_MESSAGE_SENT);
            messageList.add(message);
            mMessageAdapter.notifyDataSetChanged();
            scrollToBottom();
            queryEdt.getEditText().setText("");

            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    BaseMessage waitMessage = new BaseMessage();
                    waitMessage.setMessage("小梦正在查询，请稍后..");
                    waitMessage.setSender(chatBot);
                    waitMessage.setCreatedAt(new Date().getTime());
                    waitMessage.setType(VIEW_TYPE_MESSAGE_RECEIVED);
                    messageList.add(waitMessage);
                    mMessageAdapter.notifyDataSetChanged();
                    scrollToBottom();
                }
            }, 1500);

            RequestQueue requestQueue = Volley.newRequestQueue(this, new ProxiedHurlStack());
            String URL = "https://api.openai.com/v1/chat/completions";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("model", "gpt-3.5-turbo");
            JSONArray array = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("role", "user");
            jsonObject.put("content", query);
            array.put(jsonObject);
            jsonBody.put("messages", array);

            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("VOLLEY", String.valueOf(error));
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("Authorization", "Bearer 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");
                    return params;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = new String(response.data, StandardCharsets.UTF_8);
                    // 创建ObjectMapper对象。
                    ObjectMapper mapper = new ObjectMapper();
                    // Json格式字符串转Java对象。
                    try {
                        ChatGptResponse javaEntity = mapper.readValue(responseString, ChatGptResponse.class);
                        String responseMsg = javaEntity.getChoices().get(0).getMessage().getContent();
                        BaseMessage waitMessage = new BaseMessage();
                        waitMessage.setMessage(responseMsg);
                        waitMessage.setSender(chatBot);
                        waitMessage.setCreatedAt(new Date().getTime());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                messageList.remove(messageList.size() - 1);
                                messageList.add(waitMessage);
                                mMessageAdapter.notifyDataSetChanged();
                                scrollToBottom();
                                queryEdt.setEndIconDrawable(R.drawable.send);
                                view.setEnabled(true);
                            }
                        });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            stringRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {
                    Log.i("VOLLEY", String.valueOf(error));
                }
            });
            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void scrollToBottom() {
        mMessageRecycler.post(new Runnable() {
            @Override
            public void run() {
                if (mMessageAdapter.getItemCount() > 0) {
                    mMessageRecycler.smoothScrollToPosition(mMessageAdapter.getItemCount() - 1);
                }
            }
        });
    }
}
