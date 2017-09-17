package com.hezhe.proxy.website;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hezhe.proxy.ProxyInfo;
import com.hezhe.proxy.ProxySite;
import sun.misc.*;  
public class Proxygaz {
	public static List<ProxyInfo> get(String url){
		List<ProxyInfo> list= new ArrayList<ProxyInfo>();
		try {
			Document doc =null;
			if(ProxySite.openProxy){
				doc=Jsoup.connect(url).timeout(ProxySite.My_timeout).proxy("127.0.0.1", 1080).get();
			}else{
				doc=Jsoup.connect(url).timeout(ProxySite.My_timeout).get();
			}
			Elements trs = doc.select(".plbc_bloc_proxy_tr");
			for (Element element : trs) {
				Elements tds = element.getElementsByTag("td");
				if(tds.size()==5){
					String ip="";
					String port="";
					String type="";
					Element tdIP=tds.get(0);
					Element tdPort=tds.get(1);
					Element tdType=tds.get(3);
					Elements tdipscript = tdIP.getElementsByTag("script");
					String tdIPHtml=tdipscript.get(tdipscript.size()-1).html();
					tdIPHtml=tdIPHtml.substring(tdIPHtml.indexOf("\"")+1,tdIPHtml.lastIndexOf("\""));
					ip=getFromBase64(tdIPHtml);
					port=tdPort.text().replaceAll(" ", "");
					type=tdType.text().replaceAll(" ", "");
					if(type.equals("highly anonymous")){
						type="Elite";
					}
					list.add(new ProxyInfo(ip, port, type));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	 public static String getFromBase64(String s) {  
	        byte[] b = null;  
	        String result = null;  
	        if (s != null) {  
	            BASE64Decoder decoder = new BASE64Decoder();  
	            try {  
	                b = decoder.decodeBuffer(s);  
	                result = new String(b, "utf-8");  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        return result;  
	    }  
}