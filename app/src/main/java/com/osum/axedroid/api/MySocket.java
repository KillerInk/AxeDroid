package com.osum.axedroid.api;
import android.util.Log;

import java.io.Closeable;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
public class MySocket implements Closeable  {

    private final OkHttpClient client;
    private final String websocketUrl;
    private MySocketListner listener;
    private WebSocket webSocket;
    private boolean isOpen = false;

    public MySocket(OkHttpClient client, String websockerUrl)
    {
        this.client = client;
        this.websocketUrl = websockerUrl;
    }

    public void createNewWebSocket(MySocketListner listener) {

        Request request = new Request.Builder().url(websocketUrl).build();
        webSocket = client.newWebSocket(request, listener);
        if (webSocket != null)
            isOpen = true;
        else Log.i(MySocket.class.getSimpleName(), "websocket not open");
    }

    @Override
    public void close() throws IOException {
        isOpen = false;
        final int code = 1000;
        if (listener != null && webSocket != null)
            listener.onClosing(webSocket, code, "");
        if (webSocket != null)
            webSocket.close(code, null);
        if (listener != null && webSocket != null)
            listener.onClosed(webSocket, code, "");
    }

    public WebSocket getWebSocket() {
        return webSocket;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
