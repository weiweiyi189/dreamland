package com.example.dreamland.ui.util;

import com.android.volley.toolbox.HurlStack;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import static java.net.Proxy.*;

public class ProxiedHurlStack extends HurlStack {

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        System.out.println("代理");
        // Start the connection by specifying a proxy server
        Proxy proxy = new Proxy(Type.HTTP,
                InetSocketAddress.createUnresolved("192.168.2.197", 7890));//the proxy server(Can be your laptop ip or company proxy)
        HttpURLConnection returnThis = (HttpURLConnection) url
                .openConnection(proxy);

        return returnThis;
    }
}