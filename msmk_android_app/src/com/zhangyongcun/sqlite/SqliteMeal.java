package com.zhangyongcun.sqlite;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalDb;

import com.zhangyongcun.myclass.Meal;
import com.zhangyongcun.unil.MyApplication;

import android.app.Activity;

public class SqliteMeal {
	
	Activity activity;
	
	/**
	 * ���췽��
	 * @param activity ���������Ķ���
	 */
	public SqliteMeal(Activity activity) {
		this.activity = activity;
	}
	
	
	/**
	 * ��meal�������ݿ���
	 */
	public void savaData(){
		
		
		
		
		MyApplication myApplication = (MyApplication)activity.getApplicationContext();
		List<Meal> mealList = myApplication.getMealsList();
		FinalDb db = myApplication.getDb();
		
		if (!(mealList.size() == db.findAll(Meal.class).size())) {
			for (int i= 0; i < mealList.size(); i++) {
				db.save(mealList.get(i));
			}
		}
		
		return ;
		
	}
	/**
	 * �����û�������b_id list��sqlite�л�ȡMeal������
	 * @param o_bidlist   order���е�b_id��list���
	 * @return   ���ظ��û�������Meal���
	 */
	
	
	
	public List<Meal> getUserMealList(List<String> o_midList) {
		MyApplication myApplication = (MyApplication)activity.getApplicationContext();
		FinalDb db = myApplication.getDb();
		List<Meal> mealList = new ArrayList<Meal>(0);
		for (int i = 0; i < o_midList.size(); i++) {
			String strWhere = "m_id = \"" + o_midList.get(i).toString() + "\"";
			Meal meal = db.findAllByWhere(Meal.class, strWhere).get(0);
			mealList.add(meal);
		}
			
		
		return mealList;
	}
	
}
