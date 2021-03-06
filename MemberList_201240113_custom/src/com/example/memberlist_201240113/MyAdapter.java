package com.example.memberlist_201240113;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    // Adapter? ์ถ๊?? ?ฐ?ด?ฐ๋ฅ? ???ฅ?๊ธ? ?? ArrayList
   
  //??ฑ?๋ก? ? ?ฌ๋ฐ์? ?ฐ?ด?ฐ๋ฅ? ???ฅ?  ๋งด๋ฒ ??๋ฅ? ? ???ค. 
  	Context context;
  	int layoutRes;
  	ArrayList<MemberVO> list;
  	//?? ????? xml ๋ฌธ์๋ฅ? ?ด?ฉ?ด? ? ?ด??? ? ๊ฐํ  ? ?ด??? ๊ฐ์ ๊ฐ์ฒด๊ฐ? ????ค.
  	LayoutInflater inflater;
  //??ฑ?
  	public MyAdapter(Context context, 
  			int layoutRes, ArrayList<MemberVO> list){
  		this.context=context;
  		this.layoutRes=layoutRes;
  		this.list=list;
  		//??ฑ??? LayoutInflater ๊ฐ์ฒด๋ฅ? ?ป?ด??? ๋งด๋ฒ??? ???ฅ??ค.
  		inflater=LayoutInflater.from(context);
  	}

    // Adapter? ?ฌ?ฉ?? ?ฐ?ด?ฐ? ๊ฐ์๋ฅ? ๋ฆฌํด. : ?? ๊ตฌํ
    @Override
    public int getCount() {
        return list.size() ;
    }

    // position? ?์นํ ?ฐ?ด?ฐ๋ฅ? ?๋ฉด์ ์ถ๋ ฅ???ฐ ?ฌ?ฉ?  View๋ฅ? ๋ฆฌํด. : ?? ๊ตฌํ
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout? inflate??ฌ convertView ์ฐธ์กฐ ??.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item, parent, false);
        }

        // ?๋ฉด์ ???  View(Layout?ด inflate?)?ผ๋ก๋??ฐ ?? ฏ? ??? ์ฐธ์กฐ ??
        
        TextView name = (TextView) convertView.findViewById(R.id.tAddr);
        TextView addr = (TextView) convertView.findViewById(R.id.tName);
        TextView pnum = (TextView) convertView.findViewById(R.id.tNum);
        
        MemberVO mem=list.get(position);
        name.setText("ภฬธง : "+mem.getName());
        addr.setText("มึผา "+ mem.getAddr());
        pnum.setText("ฦ๙น๘ศฃ"  +mem.getPhoneNum());
        
        return convertView;
    }

    // ์ง?? ? ?์น?(position)? ?? ?ฐ?ด?ฐ?? ๊ด?๊ณ๋ ??ด?(row)? ID๋ฅ? ๋ฆฌํด. : ?? ๊ตฌํ
    @Override
    public long getItemId(int position) {
        return position;
    }

    // ์ง?? ? ?์น?(position)? ?? ?ฐ?ด?ฐ ๋ฆฌํด : ?? ๊ตฌํ
    @Override
    public Object getItem(int position) {
        return list.get(position) ;
    }

 
}
