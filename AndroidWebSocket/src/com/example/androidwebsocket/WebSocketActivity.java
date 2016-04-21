package com.example.androidwebsocket;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import de.tavendo.autobahn.WebSocket;
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;

public class WebSocketActivity extends Activity {

	private static final String TAG = "WebSocketActivity";
	private WebSocketConnection mWebConnection = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
			connetWebSocket();
		} catch (WebSocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void connetWebSocket() throws WebSocketException {
		mWebConnection = new WebSocketConnection();
		mWebConnection.connect("ws://10.100.13.151:8080/websocket", new WebSocket.ConnectionHandler(){

			@Override
			public void onOpen() {
				// TODO Auto-generated method stub
				Log.d(TAG, "WebSocketConnection & onOpen()");
				
				mWebConnection.sendTextMessage("Hello Android Web Socket");
			}

			@Override
			public void onClose(int code, String reason) {
				// TODO Auto-generated method stub
				Log.d(TAG, "WebSocketConnection & onClose()");
			}

			@Override
			public void onTextMessage(String payload) {
				// TODO Auto-generated method stub
				Log.d(TAG, "Receive:"+payload);
			}

			@Override
			public void onRawTextMessage(byte[] payload) {
				// TODO Auto-generated method stub
				Log.d(TAG, "Receive:"+payload.toString());
			}

			@Override
			public void onBinaryMessage(byte[] payload) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
}
