package com.bruce.monitorcontrol;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	public static final String DEFAULT_ADDRESS = "http://192.168.1.209:9090/?action=stream";
	public static final String ADDRESS_SAVE_FILE = "addressFile";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// get the remote monitor address, use default if not saved before
		SharedPreferences settings = getSharedPreferences(ADDRESS_SAVE_FILE, 0);
		String address = settings.getString("address", DEFAULT_ADDRESS);
		EditText editText = (EditText) findViewById(R.id.EditTextAddress);
		editText.setText(address);
		editText.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					EditText editText = (EditText) findViewById(R.id.EditTextAddress);
					String address = editText.getText().toString();

					// save new address
					SharedPreferences settings = getSharedPreferences(ADDRESS_SAVE_FILE, 0);
					SharedPreferences.Editor editor = settings.edit();
					editor.putString("address", address);
					editor.commit();
				}
				return false;
			}
		});

		Button connectButton = (Button) findViewById(R.id.buttonConnect);
		connectButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				SharedPreferences settings = getSharedPreferences(ADDRESS_SAVE_FILE, 0);
				String address = settings.getString("address", DEFAULT_ADDRESS);
				Log.i("--", address);

				Intent intent = new Intent();
				// 在Intent对象当中添加一个键值对
				intent.putExtra("address", address);
				// 设置Intent对象要启动的Activity
				intent.setClass(MainActivity.this, MonitorVideo.class);
				// 通过Intent对象启动另外一个Activity
				MainActivity.this.startActivity(intent);
			}
		});

		Button closeButton = (Button) findViewById(R.id.buttonClose);
		closeButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
				System.exit(0);
			}
		});
	}

	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onRestart() {
		Log.i("MainActivity", "onRestart");
		super.onRestart();
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
