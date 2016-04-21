package com.example.androidwebsocket;

import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import de.tavendo.autobahn.WebSocket;
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import org.apache.http.Header;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	private WebSocketConnection mWebConnection = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try {
			connetWebSocket();
		} catch (WebSocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.findViewById(R.id.button1).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (mWebConnection != null)
				{
					mWebConnection.sendTextMessage("Test");
				}
			}
			
		});
		
		final JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
			public void onSuccess(JSONArray arg0) { // 成功后返回一个JSONArray数据
                Log.i("hck", arg0.length() + "");
                try {
                    
                } catch (Exception e) {
                    Log.e("hck", e.toString());
                }
            };
            public void onFailure(Throwable arg0) {
                Log.e("hck", " onFailure" + arg0.toString());
            };
            public void onFinish() {
                Log.i("hck", "onFinish");
            };
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {
                Log.i("hck", "onSuccess ");
                
                Log.d("hck", responseBody.toString());
            };

		};
		
		this.findViewById(R.id.button2).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AsyncHttpClient client =new AsyncHttpClient();
				RequestParams params = new RequestParams();
				params.put("intValue", 1);
				params.put("stringValue", "test");
				client.post("http://10.100.13.64:8080/data2", params, handler);
			}
			
		});
		
		
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
