package com.hezhe.proxy.website;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hezhe.proxy.ProxyInfo;
import com.hezhe.proxy.ProxySite;

public class Proxynova {
	public static List<ProxyInfo> get(String url){
		List<ProxyInfo> list= new ArrayList<ProxyInfo>();
		try {
			Document doc =null;
			if(ProxySite.openProxy){
				doc=Jsoup.connect(url).timeout(ProxySite.My_timeout).proxy("127.0.0.1", 1080).get();
			}else{
				doc=Jsoup.connect(url).timeout(ProxySite.My_timeout).get();
			}
			Element table= doc.getElementById("tbl_proxy_list");
			Elements trs = table.select("tbody tr");
			for (Element element : trs) {
				Elements tds = element.getElementsByTag("td");
				if(tds.size()==8){
					String ip="";
					String port="";
					String type="";
					Element tdIP=tds.get(0);
					Element tdPort=tds.get(1);
					Element tdType=tds.get(6);
					String tdIPHtml=tdIP.html();
					tdIPHtml=tdIPHtml.substring(tdIPHtml.indexOf("(")+1,tdIPHtml.lastIndexOf(")"));
					String ipB=tdIPHtml.substring(1,tdIPHtml.indexOf("'."));
					String ipSub=tdIPHtml.substring(tdIPHtml.indexOf("substr(")+7,tdIPHtml.indexOf(")"));
					tdIPHtml=tdIPHtml.substring(tdIPHtml.indexOf("+"));
					String ipE=tdIPHtml.substring(tdIPHtml.indexOf("'")+1,tdIPHtml.lastIndexOf("'"));
					ip=ipB.substring(Integer.parseInt(ipSub))+ipE;
					port=tdPort.text().replaceAll(" ", "");
					type=tdType.getElementsByTag("span").get(0).text().replaceAll(" ", "");
					list.add(new ProxyInfo(ip, port, type));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}