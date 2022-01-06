package cm.ringo.dialer.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebServiceInterf {
	
	//
	//
	public static Map<String, String> getUserInfo(String login, String password){
		Properties result = new Properties();
		
		if(login == null || login.trim().equals("") ){result.put("errorCode", "dialer.login.required");	processResult(result);	}		
		if(password == null || password.trim().equals("") ){result.put("errorCode", "dialer.password.required");	processResult(result);	}
		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("login", login.trim());
		params.put("password", password);
		
		try{result.load(new StringReader(doPostHttp(USER_INFO_URL, params)));}catch(Exception e){e.printStackTrace();}
		return processResult(result);
	}
	
	public static Map<String, String> getUserConnectionParams(String login, String password){
		Properties result = new Properties();
		
		if(login == null || login.trim().equals("") ){result.put("errorCode", "dialer.login.required");	processResult(result);	}		
		if(password == null || password.trim().equals("") ){result.put("errorCode", "dialer.password.required");	processResult(result);	}
		
		Map<String, String> params = new HashMap<String, String>();		
		params.put("login", login.trim());
		params.put("password", password);
		
		try{result.load(new StringReader(doPostHttp(USER_CONNECT_URL, params)));}catch(Exception e){e.printStackTrace();}
		return processResult(result);	
	}
	
	
	public static Map<String, String> refillUserAccount(String login, String password, String refillCode){
		Properties result = new Properties();
		
		if(login == null || login.trim().equals("") ){result.put("errorCode", "dialer.login.required");	processResult(result);	}		
		if(password == null || password.trim().equals("") ){result.put("errorCode", "dialer.password.required");	processResult(result);	}
		if(refillCode == null || refillCode.trim().equals("") ){result.put("errorCode", "dialer.refillCode.required");	processResult(result);}	
		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("login", login.trim());
		params.put("password", password);
		params.put("refillCode", refillCode);
		
		try{result.load(new StringReader(doPostHttp(USER_REFILL_URL, params)));}catch(Exception e){e.printStackTrace();}
		return processResult(result);	
	}	
	
	public static Map<String, String> transferCreditFromUserAccount(String login, String password, String destinationLogin, Integer amount){
		Properties result = new Properties();
		
		if(login == null || login.trim().equals("") ){result.put("errorCode", "dialer.login.required");	processResult(result);	}		
		if(password == null || password.trim().equals("") ){result.put("errorCode", "dialer.password.required");	processResult(result);	}
		if(destinationLogin == null || destinationLogin.trim().equals("") ){result.put("errorCode", "dialer.destinationLogin.required");	processResult(result);	}
		if(amount == null){result.put("errorCode", "dialer.amount.required");	processResult(result);	}
		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("login", login.trim());
		params.put("password", password);
		params.put("destinationLogin", destinationLogin);
		params.put("amount", amount.toString());
		
		try{result.load(new StringReader(doPostHttp(USER_TRANSFER_URL, params)));}catch(Exception e){e.printStackTrace();}
		return processResult(result);		
	}	

	
	
	

	static final String USER_INFO_URL = "https://publicws.ringo.cm:8443/dialer/status";
	static final String USER_CONNECT_URL = "https://publicws.ringo.cm:8443/dialer/connect";
	static final String USER_REFILL_URL = "https://publicws.ringo.cm:8443/dialer/refill";
	static final String USER_TRANSFER_URL = "https://publicws.ringo.cm:8443/dialer/transfer";
	static Properties messages;
	
	static {
		Locale locale = Locale.getDefault();
		messages = new Properties();
		String messagesPath = null;
		messagesPath = locale == Locale.ENGLISH? "cm/ringo/dialer/util/messages_EN.properties" : "cm/ringo/dialer/util/messages_FR.properties";
		try {messages.load(WebServiceInterf.class.getClassLoader().getResourceAsStream(messagesPath));} catch (Exception e) {e.printStackTrace();}		
	}
	
	static String doPostHttps(String submitUrl, Map<String, String> params) throws Exception {
		// if()
		OutputStreamWriter out = null;
		BufferedReader brd = null;
		
		try{
			StringBuilder input = new StringBuilder();
			for(String key : params.keySet()){
				input.append(key).append("=").append(URLEncoder.encode(params.get(key), "UTF-8")).append("&");
			}
			String urlData = input.toString();
		
			
			URL url =  new URL(submitUrl);

			URLConnection conn = url.openConnection();
			
			conn.setDoOutput(true);
			// conn.
			
	        out = new OutputStreamWriter(conn.getOutputStream());
	        out .write(urlData);
	        out .flush();

			conn.connect();
	        
	        brd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        StringBuilder resultData = new StringBuilder("");
	        String line;
	        while ((line = brd.readLine()) != null) {
	        	resultData.append(line).append("\n");
	        }
	        
	        // System.out.println(resultData);
	        return resultData.toString();		        
		}finally{
			//
			if(out != null) out.close();
			if(brd != null) brd.close();   
		}

	}
	
	static String doPostHttp(String submitUrl, Map<String, String> params) throws Exception {		
		return doPostHttps(submitUrl.replace("https://", "http://").replace(":8443/", ":8080/"), params);
	}
		
	public static void main(String[] argsv) throws Exception {		
		System.out.println(getUserInfo("40003", "1234"));
		System.out.println(getUserConnectionParams("40003", "1234"));
		System.out.println(refillUserAccount("", "exceptional4me", "854457338508"));
		System.out.println(transferCreditFromUserAccount("81547", "exceptional4me2", "50258", 10));
	}
	
	static Pattern PATTERN = Pattern.compile("<(\\w+)>");
	private static Map<String, String> processResult(Properties props){
		
		Map<String, String> result = new HashMap<String, String>();
		for(Object key : props.keySet()){
			result.put((String)key, props.getProperty((String)key));
		}
		
		if(result.get("errorCode") != null){
			String text = (String)messages.get(result.get("errorCode"));
			
			if(text != null){
				
				Matcher m = PATTERN.matcher(text);
				StringBuffer sb = new StringBuffer();
				while(m.find() && result.containsKey(m.group(1))){
					String key = m.group(1);
					m.appendReplacement(sb, result.get(key));
				}
				m.appendTail(sb);
				result.put("errorText", sb.toString());
			
			}else result.put("errorText", result.get("errorCode"));	
		}
		return result;
	}
}
