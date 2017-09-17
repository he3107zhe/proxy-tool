package com.hezhe.proxy;
/**
 * @Title: ProxySite.java 
 * @Package: com.hezhe.proxy 
 * @Description: 代理IP的网站[如果发现不能访问，及时删除]
 * @author 何喆  
 * @date 2017年9月14日 下午1:42:20 
 * @version 1.0.1 
 * @see
 */
public class ProxySite {
	public static final boolean openProxy=true;
	public static final String My_ProxyIP="127.0.0.1";
	public static final Integer My_ProxyPort=8080;
	public static final Integer My_timeout=60*1000;
	
	//www.gatherproxy.com
	public static final String Site_gatherproxy_JAPAN="http://www.gatherproxy.com/proxylist/country/?c=japan";//日本
	public static final String Site_gatherproxy_KOREA="http://www.gatherproxy.com/proxylist/country/?c=Republic%20of%20Korea";//韩国
	public static final String Site_gatherproxy_HK="http://www.gatherproxy.com/proxylist/country/?c=Hong%20Kong";//香港
	public static final String Site_gatherproxy_TAIWAN="http://www.gatherproxy.com/proxylist/country/?c=Taiwan";//台湾
	public static final String Site_gatherproxy_Singapore="http://www.gatherproxy.com/proxylist/country/?c=Singapore";//新加坡
	//www.proxynova.com
	public static final String Site_proxynova_JAPAN="https://www.proxynova.com/proxy-server-list/country-jp/";
	public static final String Site_proxynova_KOREA="https://www.proxynova.com/proxy-server-list/country-kr/";
	public static final String Site_proxynova_TAIWAN="https://www.proxynova.com/proxy-server-list/country-tw/";
	public static final String Site_proxynova_HK="https://www.proxynova.com/proxy-server-list/country-hk/";
	public static final String Site_proxynova_Singapore="https://www.proxynova.com/proxy-server-list/country-sg/";
	//free-proxy.cz 手动添加
	public static final String Site_freeProxy_JAPAN="http://free-proxy.cz/en/proxylist/country/JP/all/ping/all";
	//proxygaz.com
	public static final String Site_proxygaz_JAPAN="http://proxygaz.com/country/japan-proxy/";
}
