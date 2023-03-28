package com.example.dreamland.ui.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dreamland.R;
import com.example.dreamland.entity.ChatGptResponse;
import com.example.dreamland.ui.util.ProxiedHurlStack;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import android.view.inputmethod.EditorInfo;

public class ChatActivity extends AppCompatActivity {

    TextView responseTV;
    TextView questionTV;
    TextInputLayout queryEdt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DynamicColors.applyToActivityIfAvailable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // initializing variables on below line.
        responseTV = findViewById(R.id.idTVResponse);
        questionTV = findViewById(R.id.idTVQuestion);
        queryEdt = findViewById(R.id.idTILQuery);

        queryEdt.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                responseTV.setText("Please wait..");
                if (queryEdt.getEditText().getText().toString().length() > 0) {
                    getResponse(queryEdt.getEditText().getText().toString());
                } else {
                    Toast.makeText(ChatActivity.this, "Please enter your query..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void getResponse(String query) {
        try {
            questionTV.setText(query);
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                responseTV.setText(responseMsg);
                            }
                        });
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
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
}
