package com.zhangyongcun.msmk;

import info.androidhive.slidingmenu.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MessageFragment extends Fragment {
	
	WebView webView = null;
	
	public MessageFragment(){
		
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_message, container, false);
        
        this.webView = (WebView) rootView.findViewById(R.id.webView);
        
        this.webView.setWebViewClient(new MyWebViewClient()); 
        webView.loadUrl("http://zycblog.jd-app.com/");
         
        return rootView;
    }
}


class MyWebViewClient extends WebViewClient {

    @Override

    public boolean shouldOverrideUrlLoading(WebView view, String url){

    // ��д�˷������������ҳ��������ӻ����ڵ�ǰ��webview����ת��������������Ǳ�

       view.loadUrl(url);

       return true;

       }

}
