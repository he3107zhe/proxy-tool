package com.hezhe.proxy;
/**
 * @Title: ProxyInfo.java 
 * @Package: com.hezhe.proxy 
 * @Description: 代理IP信息
 * @author 何喆  
 * @date 2017年9月15日 上午10:07:59 
 * @version 1.0.1 
 * @see
 */
public class ProxyInfo {
	private String ip;
	private String port;
	private String type;
	private Long time;
	
	public ProxyInfo(String ip, String port, String type) {
		super();
		this.ip = ip;
		this.port = port;
		this.type = type;
	}
	public ProxyInfo() {
		super();
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
}
