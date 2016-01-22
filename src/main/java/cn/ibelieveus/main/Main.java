package cn.ibelieveus.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import cn.ibelieveus.http.HttpUtil;
import org.apache.commons.codec.digest.DigestUtils;

public class Main {

	public static void main(String[] args) {
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("================欢迎使用大音开源医疗智能系统!婉莹我就要成功啦!==============");
		Map<String, List<String>> usersynd = new HashMap<String, List<String>>();
		String url = "http://localhost:8080/areY.jhtml";
		do {
			System.out.print("请输入病症:");
			Scanner scanner = new Scanner(System.in);
			String synd=scanner.next().trim();
			//String synd = "大便秘结 ".trim();
			try {
				String param = "syndname=" + DigestUtils.md5Hex(synd.trim());
				String data = HttpUtil.sendPost(url, param);
				String result = new String(data.getBytes(), "utf-8");
				if ("0".equals(result)) {
					System.out.println("对不起!系统还未收录此症状,请重新输入");
				} else {
					result = result.replaceAll("\\{|\\}", "");
					for (String r : result.split(",")) {
						String z = (r.split("=")[1]).trim();
						if (usersynd.get(z) == null) {
							List<String> syndList = new ArrayList<String>();
							syndList.add((r.split("=")[0]).trim());
							usersynd.put(z, syndList);
						} else {
							usersynd.get(z).add((r.split("=")[0]).trim());
						}
					}
					Map<String, List<String>> dSet = new HashMap<String, List<String>>();
					Set<String> keySet = usersynd.keySet();
					for (String key : keySet) {
						//System.out.println(key + ":" + usersynd.get(key).toString());
						for (String syndname : usersynd.get(key)) {
							System.out.println("您是否有  [" + syndname + "]  的症状?");
							System.out.print("请输入:<y/n>?");
							String r = "";
							do {
								r = scanner.next();
								if ("y".equalsIgnoreCase(r)) {
									if (dSet.get(key) == null) {
										List<String> syndList = new ArrayList<String>();
										syndList.add(syndname);
										dSet.put(key, syndList);
									} else {
										dSet.get(key).add(syndname);
									}
									System.out.println();
									break;
								} else if ("n".equalsIgnoreCase(r)) {
									System.out.println();
									break;
								}else {
									System.out.println("请输入:<y/n>?");
								}
								System.out.println();
							} while (true);
							
						}
					}
					System.out.println("你得的病是:"+dSet.keySet().toString());
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (true);

	}
}
