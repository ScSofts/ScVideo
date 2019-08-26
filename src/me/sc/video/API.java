package me.sc.video;

public class API {
//10个VIP解析接口
	
	public static String api1="http://api.smq1.com/?url=";
	public static String api2="http://jx.du2.cc/?url=";
	public static String api3="http://api.ilingku.com/?url=";
	public static String api4="http://vip.jlsprh.com/index.php?url=";
	public static String api5="http://660e.com/?url=";
	public static String api6="http://www.2gty.com/apiurl/yun.php?url=";
	public static String api7="http://jx.drgxj.com/?url=";
	public static String api8="http://jiexi.071811.cc/jx2.php?url=";
	public static String api9="http://jx.618g.com/?url=";
	public static String api10="https://tool.bitefu.net/video/?type=0&url=";
	public static boolean ForEachCheckAPI(String URL){
		
		if(URL.indexOf(api1)!=-1)
			return true;
		else if (URL.indexOf(api2)!=-1)
			return true;
		else if (URL.indexOf(api3)!=-1)
			return true;
		else if (URL.indexOf(api4)!=-1)
			return true;
		else if (URL.indexOf(api5)!=-1)
			return true;
		else if (URL.indexOf(api6)!=-1)
			return true;
		else if (URL.indexOf(api7)!=-1)
			return true;
		else if (URL.indexOf(api8)!=-1)
			return true;
		else if (URL.indexOf(api9)!=-1)
			return true;
		else if (URL.indexOf(api10)!=-1)
			return true;
		
		return false;
	}
}
