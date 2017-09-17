package com.hezhe.proxy.website;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hezhe.proxy.ProxyInfo;
import com.hezhe.proxy.ProxySite;

public class Gatherproxy {
	public static List<ProxyInfo> get(String url){
		List<ProxyInfo> list= new ArrayList<ProxyInfo>();
		try {
			Document doc =null;
			if(ProxySite.openProxy){
				doc=Jsoup.connect(url).timeout(ProxySite.My_timeout).proxy("127.0.0.1", 1080).get();
			}else{
				doc=Jsoup.connect(url).timeout(ProxySite.My_timeout).get();
			}
			Element table= doc.getElementById("tblproxy");
			Elements trs = table.select("script");
			for (Element element : trs) {
				try {
					String text=element.html();
					String json=text.substring(text.indexOf("{")+1,text.lastIndexOf("}"));
					String[] val=json.split(",");
					String ip="";
					String port="";
					String type="";
					for (String  v: val) {
						if(v.contains("PROXY_IP")){
							ip=v.split(":")[1].replaceAll("\"", "");
						}
						if(v.contains("PROXY_PORT")){
							port=v.split(":")[1].replaceAll("\"", "");
						}
						if(v.contains("PROXY_TYPE")){
							type=v.split(":")[1].replaceAll("\"", "");
						}
					}
					port=String.valueOf(Integer.parseInt(port, 16));
					list.add(new ProxyInfo(ip, port, type));
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}