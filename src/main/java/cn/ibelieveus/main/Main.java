package cn.ibelieveus.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import cn.ibelieveus.http.HttpUtil;
import org.apache.commons.codec.digest.DigestUtils;

public class Main {

	public static void main(String[] args) throws Exception {
		System.out.println("================欢迎使用大音开源医疗智能系统!婉莹我就要成功啦!==============");
		Map<String, List<String>> usersynd = new HashMap<String, List<String>>();
		String url = "http://localhost:8080/areY.jhtml";
		do {
			System.out.print("请输入病症:");
			/*Scanner scanner = new Scanner(System.in);
			String synd=scanner.next().trim();*/
			 BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
			//String synd = "大便秘结 ".trim();
			try {
				String param = "syndname=" + DigestUtils.md5Hex("大便秘结");
				String data = HttpUtil.sendPost(url, param);
				String result = new String(data.getBytes(), "UTF-8");
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
							System.out.print("<Enter>键代表确定,<n>键代表否：");
							String r = "";
							do {
								r = bf.readLine();
								if (r.length()==0) {
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
									System.out.println("<Enter>键代表确定,<n>键代表否：");
								}
								System.out.println();
							} while (true);
							
						}
					}
					System.out.println("病证："+dSet.keySet());
					System.out.println("症状："+dSet.values().toString().replaceAll("\\[|\\]",""));
					System.out.println("方剂：白虎汤");
					System.out.println("组方：知母15   粳米20   石膏（碎）30   甘草10");
					System.out.println("用法：煎剂，一日一剂，煎米熟，去渣，将余药另煎后混合，一日三次温服。");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		} while (true);

	}
}
