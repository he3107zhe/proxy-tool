package com.hezhe.proxy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;

import com.hezhe.proxy.website.Gatherproxy;
import com.hezhe.proxy.website.Proxygaz;
import com.hezhe.proxy.website.Proxynova;

/**
 * @Title: ProxySite.java
 * @Package: com.hezhe.proxy
 * @Description: 代理IP的网站[如果发现不能访问，及时删除]
 * @author 何喆
 * @date 2017年9月14日 下午1:42:20
 * @version 1.0.1
 * @see
 */
public class ProxyJsoup {
	static List<ProxyInfo> resultList = new Vector<ProxyInfo>();
	public static void main(String[] args) throws InterruptedException {
		System.out.println("开始抓取IP");
		List<ProxyInfo> list = get();
		System.out.println("获取到IP个数："+list.size());
		// 计数器
		ExecutorService exec = Executors.newFixedThreadPool(list.size());
		final CountDownLatch end = new CountDownLatch(list.size());
		for (final ProxyInfo info : list) {
			exec.submit(new Runnable() {
				public void run() {
					try {
						String type = info.getType().toLowerCase();
						if (type.equals("elite") || type.equals("highlyanonymous")) {
							Long time = check(info);
							if (time != null) {
								info.setTime(time);
								resultList.add(info);
							}
						}
					} finally {
						end.countDown();
					}
				}
			});
		}
		System.out.println("开始验证IP可用性 ");
		end.await();
		exec.shutdown();
		Collections.sort(resultList, new Comparator<ProxyInfo>() {
			public int compare(ProxyInfo o1, ProxyInfo o2) {
				int i = (int) (o1.getTime() - o2.getTime());
				return i;
			}
		});
		System.out.println("可用IP个数："+resultList.size());
		// 写入文件
		writeProxyTxt(resultList);
		System.out.println("写入文件成功：");
	}
	/**
	 * @Description 验证是否能访问
	 * @author 何喆
	 * @date 2017年9月15日 下午2:58:30
	 * @param info
	 * @return
	 */
	public static Long check(ProxyInfo info) {
		String url = "https://itp.ne.jp";
		try {
			long b = System.currentTimeMillis();
			Jsoup.connect(url).timeout(60 * 1000).proxy(info.getIp(), Integer.parseInt(info.getPort())).get();
			b = System.currentTimeMillis() - b;
//			System.out.println(info.getIp());
			return b;
		} catch (Exception e) {
			return null;
		}
	}

	public static List<ProxyInfo> get() {
		List<ProxyInfo> list = new ArrayList<ProxyInfo>();
		list.addAll(Gatherproxy.get(ProxySite.Site_gatherproxy_JAPAN));
		list.addAll(Gatherproxy.get(ProxySite.Site_gatherproxy_KOREA));
		list.addAll(Gatherproxy.get(ProxySite.Site_gatherproxy_HK));
		list.addAll(Gatherproxy.get(ProxySite.Site_gatherproxy_TAIWAN));
		list.addAll(Gatherproxy.get(ProxySite.Site_gatherproxy_Singapore));
		list.addAll(Proxynova.get(ProxySite.Site_proxynova_JAPAN));
		list.addAll(Proxynova.get(ProxySite.Site_proxynova_KOREA));
		list.addAll(Proxynova.get(ProxySite.Site_proxynova_TAIWAN));
		list.addAll(Proxynova.get(ProxySite.Site_proxynova_HK));
		list.addAll(Proxynova.get(ProxySite.Site_proxynova_Singapore));
		list.addAll(Proxygaz.get(ProxySite.Site_proxygaz_JAPAN));
		// 手动添加 http://free-proxy.cz/en/proxylist/country/JP/all/ping/all
		list.addAll(readManual());
		return list;
	}

	/**
	 * @Description 读取手动添加文件
	 * @author 何喆
	 * @date 2017年9月15日 下午2:58:01
	 * @return
	 */
	public static List<ProxyInfo> readManual() {
		List<ProxyInfo> list = new ArrayList<ProxyInfo>();
		String path = System.getProperty("user.dir");
		File file = new File(path + "/manual.txt");
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
				String s = null;
				while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
					if (s != null && s.contains(",")) {
						String[] data = s.split(",");
						list.add(new ProxyInfo(data[0], data[1], "Elite"));
					}
				}
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}

	/**
	 * @Description 写入文件
	 * @author 何喆
	 * @date 2017年9月15日 下午2:57:45
	 * @param list
	 */
	public static void writeProxyTxt(List<ProxyInfo> list) {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = format.format(date);
		String path = System.getProperty("user.dir");
		String filePath = path + "/proxyIp-" + time + ".txt";
		FileWriter fw = null;
		try {
			fw = new FileWriter(filePath);
			for (ProxyInfo proxyInfo : list) {
				fw.write(proxyInfo.getIp() + "," + proxyInfo.getPort()+ "," + proxyInfo.getTime() + System.lineSeparator());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fw != null) {
					fw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
