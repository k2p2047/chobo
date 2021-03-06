package com.example.memberlist_201240113;




import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Register extends Activity {
	
	
	
	Button button;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		
		button = (Button)findViewById(R.id.button01);
		button.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				EditText name = (EditText)findViewById(R.id.name);
				EditText phone = (EditText)findViewById(R.id.phone);
				EditText addr = (EditText)findViewById(R.id.addr);
				String na =  name.getText().toString();
				String ph = phone.getText().toString();
				String ad = addr.getText().toString();
				
				
				
				
				//화면 전환(리스트)
				
				MemberVO mvo=new MemberVO();
				
				mvo.setName(na);
				mvo.setAddr(ad);
				mvo.setPhoneNum(ph);
				
				
				
				
				
				Intent intent = new Intent(Register.this, ListActivity.class);

				intent.putExtra("mvo",mvo);
				
				
				startActivity(intent);
				
           
	                         
			
			}
		});
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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
