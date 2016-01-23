package cn.ibelieveus.http;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;


public class HttpUtil {
    
    public static String sendPost(String url,String param) {
    	String result="";
    	HttpClient httpClient=new HttpClient();
    	HttpMethod method=new PostMethod(url);
    	try {
			method.setQueryString(param);
			httpClient.executeMethod(method);
			result = new String(method.getResponseBodyAsString().getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
        return result; 
    }    
}