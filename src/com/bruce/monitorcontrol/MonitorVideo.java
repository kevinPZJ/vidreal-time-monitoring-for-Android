package com.bruce.monitorcontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MonitorVideo extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ȫ������
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// ��ȡԶ����Ƶ����豸��ַ
		Intent intent = getIntent();
		String addressString = intent.getStringExtra("address");
		Log.i("address", addressString);

		// ��ʾ��Ƶ��ؽ���
		setContentView(R.layout.monitor_video);

		// ����SurfaceView��ַ����
		MontiorSurfaceView r = (MontiorSurfaceView) findViewById(R.id.SurfaceViewVideo);
		if (r == null) {
			Log.e("r", "r == null");
		}
		r.setMontiorAddress(addressString);
		Log.i("address", "setMontiorAddress complete");
	}

	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
