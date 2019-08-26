package me.sc.video;

//导入一些包，编辑器自动生成
import java.io.ByteArrayInputStream;
import me.sc.video.API;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

@SuppressLint("SetJavaScriptEnabled") public class Main extends Activity implements OnTabChangeListener{
	private TabHost Th = null;
	private String Youku_Home_URL = "https://www.youku.com/";
	private String Youku_PlayString = "youku.com/video";
	private String Tencent_Home_URL = "https://m.v.qq.com/";
	private String Tencent_PlayString = "qq.com/play";
	private String Tencent_PlayString2 = "qq.com/x/cover";
	private String Tencent_PlayString3 = "qq.com/cover/";
	private String IQIYI_Home_URL = "https://m.iqiyi.com/";
	private String IQIYI_PlayingString = "iqiyi.com/v";
	private boolean VipPlaying = false;
	private int NowWebView = 1;
	
	private String SelectedAPI = API.api1;
	private Button Btnjx = null;//解析按钮
	private com.tencent.smtt.sdk.WebView YoukuView;
	private com.tencent.smtt.sdk.WebView TencentView;
	private com.tencent.smtt.sdk.WebView HelpView;
	private com.tencent.smtt.sdk.WebView IQIYIView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		setupTabs();
		getWindow().setFormat(PixelFormat.TRANSLUCENT);//防止视频闪屏
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//防止输入法问题
		
		Btnjx = (Button) findViewById(R.id.jx);//得到这个VIP解析按钮
		Btnjx.setEnabled(true);
		Btnjx.setWidth(48);
		Btnjx.setHeight(48);
		Btnjx.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				openOptionsMenu();//弹出选择菜单
			}
		});
		LoadWebView();
	}//这个窗口被创建时
	
	public void setupTabs(){
		Th = (TabHost) findViewById(android.R.id.tabhost);
		Th.setup();
		Th.addTab(Th.newTabSpec("tab1").setIndicator("优酷",getResources().getDrawable(R.drawable.yk)).setContent(R.id.Youku));
		Th.addTab(Th.newTabSpec("tab2").setIndicator("腾讯",getResources().getDrawable(R.drawable.tx)).setContent(R.id.Tencent));
		Th.addTab(Th.newTabSpec("tab3").setIndicator("爱奇艺").setContent(R.id.IQIYI));
		Th.addTab(Th.newTabSpec("tab4").setIndicator("帮助",getResources().getDrawable(R.drawable.help)).setContent(R.id.Help));
		for (int i = 0; i < Th.getTabWidget().getChildCount(); i++)
		 {
		 //获取标题
		 TextView tabs_title = (TextView)Th.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
		 tabs_title.setTextColor(Color.WHITE);
		 tabs_title.setTextSize(16);
		 }
		Th.setCurrentTab(0);
		Th.setOnTabChangedListener(this);
	}//这个函数用来构造选项卡
	
	@Override
	public void onTabChanged(String arg0) {
		// TODO Auto-generated method stub
		if(arg0.equals("tab1")){//优酷
			Btnjx.setEnabled(true);
			NowWebView = 1;
			VipPlaying = false;
			TencentView.loadUrl("/");
			IQIYIView.loadUrl("/");
			YoukuView.loadUrl(Youku_Home_URL);
		}else if(arg0.equals("tab2")){//腾讯
			Btnjx.setEnabled(true);
			VipPlaying = false;
			NowWebView = 2;
			YoukuView.loadUrl("/");
			IQIYIView.loadUrl("/");
			TencentView.loadUrl(Tencent_Home_URL);
		}else if (arg0.equals("tab3")) {//爱奇艺
			Btnjx.setEnabled(true);
			VipPlaying = false;
			NowWebView = 3;
			YoukuView.loadUrl("/");
			TencentView.loadUrl("/");
			IQIYIView.loadUrl(IQIYI_Home_URL);
		}
		else if(arg0.equals("tab4")){//帮助
			Btnjx.setEnabled(false);
			VipPlaying = false;
			NowWebView = 4;
			HelpView.loadUrl("file:///android_asset/index.html");
		}
	}//切换选项卡时

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add("接口①").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				// TODO Auto-generated method stub
				System.out.println(API.api1);
				SelectedAPI = API.api1;
				VipPlay();
				return false;
			}
		});
		menu.add("接口②").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				// TODO Auto-generated method stub
				System.out.println(API.api2);
				SelectedAPI = API.api2;
				VipPlay();
				return false;
			}
		});
		menu.add("接口③").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				// TODO Auto-generated method stub
				System.out.println(API.api3);
				SelectedAPI = API.api3;
				VipPlay();
				return false;
			}
		});
		menu.add("接口④(勿信广告!)").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				// TODO Auto-generated method stub
				System.out.println(API.api4);
				SelectedAPI = API.api4;
				VipPlay();
				return false;
			}
		});
		menu.add("接口⑤").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				// TODO Auto-generated method stub
				System.out.println(API.api5);
				SelectedAPI = API.api5;
				VipPlay();
				return false;
			}
		});
		menu.add("接口⑥").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				// TODO Auto-generated method stub
				System.out.println(API.api6);
				SelectedAPI = API.api6;
				VipPlay();
				return false;
			}
		});
		menu.add("接口⑦").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				// TODO Auto-generated method stub
				System.out.println(API.api7);
				SelectedAPI = API.api7;
				VipPlay();
				return false;
			}
		});
		menu.add("接口⑧").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				// TODO Auto-generated method stub
				System.out.println(API.api8);
				SelectedAPI = API.api8;
				VipPlay();
				return false;
			}
		});
		menu.add("接口⑨").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				// TODO Auto-generated method stub
				System.out.println(API.api9);
				SelectedAPI = API.api9;
				VipPlay();
				return false;
			}
		});
		menu.add("接口⑩(全网最稳、推荐)").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				// TODO Auto-generated method stub
				System.out.println(API.api10);
				SelectedAPI = API.api10;
				VipPlay();
				return false;
			}
		});
		return super.onCreateOptionsMenu(menu);
	}//创建选择菜单
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB) private void LoadWebView(){
		//Get WebView
		YoukuView = (WebView) findViewById(R.id.YoukuWeb);
		TencentView = (WebView) findViewById(R.id.TencentWeb);
		HelpView = (WebView) findViewById(R.id.HelpWeb);
		IQIYIView = (WebView) findViewById(R.id.IQIYIWeb);
		
		//Allow JavaScript
		YoukuView.getSettings().setJavaScriptEnabled(true);
		TencentView.getSettings().setJavaScriptEnabled(true);
		HelpView.getSettings().setJavaScriptEnabled(true);
		IQIYIView.getSettings().setJavaScriptEnabled(true);
		
		//Allow Cookies...
		{YoukuView.getSettings().setAppCacheEnabled(true);
		YoukuView.getSettings().setAppCacheMaxSize(1024 * 1024 * 30);//30M缓存空间
		YoukuView.getSettings().setDomStorageEnabled(true);
		YoukuView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		YoukuView.getSettings().setSavePassword(true);
		
		
		TencentView.getSettings().setAppCacheEnabled(true);
		TencentView.getSettings().setAppCacheMaxSize(1024 * 1024 * 30);//30M缓存空间
		TencentView.getSettings().setDomStorageEnabled(true);
		TencentView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		TencentView.getSettings().setSavePassword(true);
		
		HelpView.getSettings().setAppCacheEnabled(true);
		HelpView.getSettings().setAppCacheMaxSize(1024 * 1024 * 30);//30M缓存空间
		HelpView.getSettings().setDomStorageEnabled(true);
		HelpView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		HelpView.getSettings().setSavePassword(true);
		
		IQIYIView.getSettings().setAppCacheEnabled(true);
		IQIYIView.getSettings().setAppCacheMaxSize(1024 * 1024 * 30);//30M缓存空间
		IQIYIView.getSettings().setDomStorageEnabled(true);
		IQIYIView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		IQIYIView.getSettings().setSavePassword(true);
		
		}
		
		//Prevent Video Broken
		if (Build.VERSION.SDK_INT >= 21) {
	          YoukuView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	          TencentView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	          HelpView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	          IQIYIView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	        }
		
		//Prevent WebBrowser
		YoukuView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(!url.startsWith("youku://"))//防止跳转APP
            	view.loadUrl(url);
                if(ForEachCheck(url)){
                	VipPlaying = true;
                }else{
                	VipPlaying = false;
                }
                return false;
            }
            @Override
            public void onReceivedSslError(WebView arg0, SslErrorHandler arg1,
            		SslError arg2) {
            	// TODO Auto-generated method stub
            	arg1.proceed();
            	super.onReceivedSslError(arg0, arg1, arg2);
            }
        });
		TencentView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(!url.startsWith("tenvideo"))//防止跳转APP
            	view.loadUrl(url);
                if(ForEachCheck(url)){
                	VipPlaying = true;
                }else{
                	VipPlaying = false;
                }
                return false;
            }
            @Override
            public void onReceivedSslError(WebView arg0, SslErrorHandler arg1,
            		SslError arg2) {
            	// TODO Auto-generated method stub
            	arg1.proceed();
            	super.onReceivedSslError(arg0, arg1, arg2);
            }
        });
		HelpView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
            @Override
            public void onReceivedSslError(WebView arg0, SslErrorHandler arg1,
            		SslError arg2) {
            	// TODO Auto-generated method stub
            	arg1.proceed();
            	super.onReceivedSslError(arg0, arg1, arg2);
            }
        });
		IQIYIView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(!url.startsWith("iqiyi")){
                	view.loadUrl(url);                	
                }else{
                	view.goBack();
                }
                if(ForEachCheck(url)){
                	VipPlaying = true;
                }else{
                	VipPlaying = false;
                }
                return false;
            }
            //爱奇艺有些特别
            @Override
            public void onReceivedSslError(WebView arg0, SslErrorHandler arg1,
            		SslError arg2) {
            	// TODO Auto-generated method stub
            	arg1.proceed();
            	super.onReceivedSslError(arg0, arg1, arg2);
            }
            
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView arg0,
            		String arg1) {
            	// TODO Auto-generated method stub

            	if(arg1.startsWith("iqiyi")){
            		String Back = "<script>window.history.back();</script>";
            		return  new WebResourceResponse("text/html", "utf-8", new ByteArrayInputStream(Back.getBytes()));
            	}
            	return super.shouldInterceptRequest(arg0, arg1);
            }
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView arg0,
            		WebResourceRequest arg1) {
            	// TODO Auto-generated method stub
            	if(arg1.getUrl().toString().startsWith("iqiyi")){
            		String Back = "<script>window.history.back();</script>";
            		return  new WebResourceResponse("text/html", "utf-8", new ByteArrayInputStream(Back.getBytes()));
            	}
            	return super.shouldInterceptRequest(arg0, arg1);
            }
           });
		
		//默认优酷
		YoukuView.loadUrl(Youku_Home_URL);
	}
	//初始化所有浏览器
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		// TODO Auto-generated method stub
		switch(keyCode){
		case KeyEvent.KEYCODE_BACK:
			switch(NowWebView){
			case 1:
				if(YoukuView.canGoBack()){
					YoukuView.goBack();
					if(ForEachCheck(YoukuView.getUrl())){
						VipPlaying = false;
					}
					else{
						VipPlaying = true;
					}
				}
				else 
					finish();
				break;
			case 2:
				if(TencentView.canGoBack()){
					TencentView.goBack();
					if(ForEachCheck(TencentView.getUrl())){
						VipPlaying = false;
					}else{
						VipPlaying = true;
					}
				}

				else {
					finish();
				}
				break;
			case 3:
				if(IQIYIView.canGoBack()){
					IQIYIView.goBack();
					if(ForEachCheck(IQIYIView.getUrl())){
						VipPlaying = false;
					}else {
						VipPlaying = true;
					}
				}
				break;
			case 4:
				if(HelpView.canGoBack()){
					VipPlaying = false;
					HelpView.goBack();					
				}
				else {
					finish();
				}
				break;
			}
			
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	//返回键

	private void VipPlay(){
		if(NowWebView == 1){
			if(YoukuView.getUrl().indexOf(Youku_PlayString)!=-1 && !VipPlaying){
				VipPlaying = true;
				YoukuView.loadUrl(SelectedAPI+YoukuView.getUrl());
			}
			else{
				Toast.makeText(getApplicationContext(), "必须到 优酷播放页面 才可以换接口哦!", Toast.LENGTH_SHORT).show();
			}
		}else if(NowWebView == 2){
			if((TencentView.getUrl().indexOf(Tencent_PlayString)!=-1 || TencentView.getUrl().indexOf(Tencent_PlayString2)!=-1 || TencentView.getUrl().indexOf(Tencent_PlayString3)!=-1) &&!VipPlaying){
				VipPlaying = true;
				TencentView.loadUrl(SelectedAPI+TencentView.getUrl());
			}
			else{
				Toast.makeText(getApplicationContext(), "必须到 腾讯播放页面 才可以换接口哦!", Toast.LENGTH_SHORT).show();
			}
		}else if (NowWebView == 3) {
			if(IQIYIView.getUrl().indexOf(IQIYI_PlayingString)!=-1 && !VipPlaying){
				VipPlaying = true;
				IQIYIView.loadUrl(SelectedAPI+IQIYIView.getUrl());
			}
			else{
				Toast.makeText(getApplicationContext(), "必须到 爱奇艺播放页面 才可以换接口哦!", Toast.LENGTH_SHORT).show();
			}
		}
	}
	//核心函数 VIP播放
	
	private boolean ForEachCheck(String URL){
		return API.ForEachCheckAPI(URL);
	}
}

