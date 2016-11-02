package android.sample;



import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	UserDatabaseHelper helper;
     SQLiteDatabase sqlitedb;
     Button button;
     Button button2;
     UserrVO uvo=new UserrVO();
     ListAdapter adapter;
     CheckBox chb;
     ListView list;
     Button btn;
   
     @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
        
        // SQLiteOpenHelper�� Ȯ�� ������ ���� �����ͺ��̽� ����
        helper= new UserDatabaseHelper(this);
		sqlitedb = helper.getWritableDatabase();
        
        Bundle extras = getIntent().getExtras();//��ü ����ȭ�� bundle�� ���� ����Ʈ�� �Ѿ�� �͵��� �޾ƿ�
        if (extras != null) {//�װ��� ���� �ƴ϶��
            uvo = (UserrVO)getIntent().getSerializableExtra("uvo"); //Obtaining data //mvo Ű���� value����  MemberVO ���·� ĳ�����ؼ� ������
        }
        
       
        if(uvo.getName()!=null){
        	//System.out.println("�̸� :" +uvo.getName()  +"����"+uvo.getAge() +"����" +uvo.getCity());
        	sqlitedb.execSQL("INSERT INTO users " + "(name, city, age)" + "VALUES ('"+uvo.getName()+"', '" + uvo.getCity() + "',"+uvo.getAge()+");");
        }
        
       
      
        
        OnItemClickListener item = null;
       // chb= (CheckBox)findViewById(R.id.CheckBox01);
       
        // �������� ���� ������ ��ȸ
        final Cursor cursor = sqlitedb.rawQuery("SELECT * FROM users ", null);
        
        list= (ListView) findViewById(R.id.ListView01);

        String[] data_columns = new String[] {"name", "city", "age"};
        
        int[] widgets = new int[] {R.id.TextView01, R.id.TextView02, R.id.TextView03};

        // ����Ʈ ����͸� �̿� ����Ʈ �信 ���
        if (cursor != null ){
            startManagingCursor(cursor);
                       
            adapter =new SimpleCursorAdapter(this, R.layout.dbview, cursor, data_columns, widgets);
            
            
            
            list.setAdapter(adapter);
          
         
            item = new OnItemClickListener()
            {
            		public void onItemClick(AdapterView<?> adapter, View clickedView, int position, long id)
            		{
            			Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
            			intent.putExtra("data_name", cursor.getString(cursor.getColumnIndex("name")));
            			intent.putExtra("data_city", cursor.getString(cursor.getColumnIndex("city")));
            			intent.putExtra("data_age", cursor.getString(cursor.getColumnIndex("age")));
            			intent.putExtra("data_id", cursor.getString(cursor.getColumnIndex("_id")));
            			startActivity(intent);
            		
            		}
            };
            
            list.setOnItemClickListener(item);
        }        
         
        // �����ͺ��̽� ����
        //sqlitedb.close();
        
        
        button = (Button)findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(MainActivity.this, Register.class);

				startActivity(intent);
				
			}
		});
		 
        
  }
    
    @Override
    protected void onDestroy() {
    	sqlitedb.close();//������� �����ͺ��̽� ���� 
    	super.onDestroy();
    }
}    
    

