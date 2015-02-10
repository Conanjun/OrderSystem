package com.zhangyongcun.msmk;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhangyongcun.adapter.MealList;
import com.zhangyongcun.myclass.Meal;
import com.zhangyongcun.myclass.Order;
import com.zhangyongcun.sqlite.SqliteMeal;
import com.zhangyongcun.unil.MyData;

import info.androidhive.slidingmenu.R;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class UserFragment extends Fragment {
	
	//���췽��
	public UserFragment(){
		
	}
	
	
	
	
	FinalHttp fh = new FinalHttp();
	protected static final int DATA = 1;
	private Handler handler;
	
	List<String> m_nameList = new ArrayList<String>(0);
	List<String> m_imgList = new ArrayList<String>(0);
	
	ListView list;
	
	private TextView name;
	private String apiPath = MyData.Path + "login/check.php";
	private EditText u_name;
	private EditText u_pwd;
	private Button login;
	private Button logout;
	private TextView lregister;
	private TextView info;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);
        this.u_name = (EditText)rootView.findViewById(R.id.u_name);
        this.u_pwd = (EditText)rootView.findViewById(R.id.u_pwd);
        this.login = (Button)rootView.findViewById(R.id.login);
        this.logout = (Button)rootView.findViewById(R.id.logout);
        this.lregister = (TextView)rootView.findViewById(R.id.register);
        this.info = (TextView) rootView.findViewById(R.id.info);
        this.name = (TextView) rootView.findViewById(R.id.name);
		this.list = (ListView) rootView.findViewById(R.id.user_meal_list);
        
        
        SharedPreferences pref = getActivity().getPreferences(0);
        this.u_name.setText(pref.getString("name", ""));
        this.u_pwd.setText(pref.getString("pwd", ""));
        
        this.name.setText(pref.getString("name", ""));
        
        getData();
        
        //handler��Ϣ���ƿ�ʼ------
        handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				
				
				if(msg.what == DATA) {
					@SuppressWarnings("unchecked")
					//�첽��ѯ��ǰ�û����еĶ���
					final List<Order> orderList = (List<Order>)msg.obj;
					
					//��ȡ���ж����е�b_id��Ϣ����sqlite�в�ѯ��ȡ�����в�Ʒ������
					List<String> o_midList = new ArrayList<String>(0);
					
					for (int i = 0; i < orderList.size(); i++) {
						
						o_midList.add(orderList.get(i).getM_id());
						

					}
					
					SqliteMeal sqliteMeal = new SqliteMeal(getActivity());
					List<Meal> ordMealList = sqliteMeal.getUserMealList(o_midList);
					
					
					//����list��������
					for (int i = 0; i < ordMealList.size(); i++) {
						   m_nameList.add(ordMealList.get(i).getM_name());
				    	   m_imgList.add(ordMealList.get(i).getM_img());
				    	   
				    }
					
					
					//ת��
				    final int size = ordMealList.size();
				    String[] m_name = (String[]) m_nameList.toArray(new String[size]);
				    String[] m_img = (String[]) m_imgList.toArray(new String[size]);
					//����listview����Դ 
				    MealList adapter = new MealList(getActivity(),m_name,m_img);
				    list.setAdapter(adapter);
				    list.setItemsCanFocus(false);
				    list.setOnItemClickListener(new OnItemClickListener() {
				    	

						//����List�¼�  �˶�
						public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
							
							AlertDialog.Builder builder = new Builder(getActivity());
							builder.setMessage("ȷ���˶���");
							builder.setTitle("��ʳ����");
							builder.create().show();
							
							
							
							
							
						}
					});
					
				}
			}
        };
      //handler����
      
        
        //ע���¼�����
        this.lregister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {//��ת��ע��ҳ��
				Intent it = new Intent();
				it.setClass(getActivity(), ReActivity.class);
				UserFragment.this.startActivity(it);
			}
		});
        
        //����ע���¼�
        this.logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref = getActivity().getPreferences(0);
			     SharedPreferences.Editor edt = pref.edit();
			     edt.putString("name", "");
			     edt.putString("pwd", "");
			     edt.commit();
			     
			}
		});
       
        
        
        
        
        //��½�¼�����
        this.login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				final String u_name = UserFragment.this.u_name.getText().toString();
				String u_pwd = UserFragment.this.u_pwd.getText().toString();
				
				 SharedPreferences pref = getActivity().getPreferences(0);
			     SharedPreferences.Editor edt = pref.edit();
			     edt.putString("name", u_name);
			     edt.putString("pwd", u_pwd);
			     edt.commit();
				
				//POST����
				
				AjaxParams params = new AjaxParams();
				params.put("u_name", u_name);
				params.put("u_pwd", u_pwd);
				
				FinalHttp fh = new FinalHttp();
				fh.post(apiPath, params,new AjaxCallBack<Object>() {

					

					@Override
					public void onSuccess(Object t) {
						if ("true".equals(t.toString())) { 
							
							getData();
							UserFragment.this.u_pwd.setVisibility(View.VISIBLE);
						}else {
							
							UserFragment.this.info.setText("��½ʧ��");
						}
					}
				});
				//�������
				
			}
		});
        
        return rootView;
    }
	 public void getData () { 
  	   
	      
  	   //-------------------------------
  	   //�ӷ������ϻ�ȡ����
	        if (!this.u_name.getText().toString().equals("")){
		        AjaxParams params = new AjaxParams();
		        params.put("u_name", this.u_name.getText().toString());
		        fh.post(MyData.Path + "ord/order_user_list.php", params,new AjaxCallBack<Object>(){
		        		
		        	@Override
					public void onSuccess(Object t) {
		        		List<Order> orderList;
						//JSON  ����ת��ΪList
						Type listType = new TypeToken<List<Order>>(){}.getType();
						orderList = new Gson().fromJson(t.toString(), listType);
				        Message msg = new Message();
				        msg.what = DATA;
				        msg.obj = orderList;
				        handler.sendMessage(msg);
		        	}
		        
		        });
	        }else {
	        	
	        }
	        //----------------------------------
     }
}



