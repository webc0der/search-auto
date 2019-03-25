package codeFile;

import java.io.*;
import java.net.*;
//import java.util.*;
import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class SearchAuto {

	private final String USER_AGENT= "Mozilla/5.0";
	static String res;

	public static void main(String[] args) throws Exception {

		SearchAuto http= new SearchAuto();

		System.out.println("enter the URL");
		Scanner s= new Scanner(System.in);
		String url= s.nextLine();
		System.out.println(url);
		System.out.println("testing 1- send http get request");
		http.sendGetHTTP(url);
		http.htmlPars();


	}

	void sendGetHTTP(String take) throws Exception{
//		System.out.println("sending...");
		
		URL obj = new URL(take);
//		System.out.println("URL execute");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + take);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		res= response.toString();

		//		print result
		//		System.out.println(res);
	}
	
	void sendGetHTTPS(String take) throws Exception{

		URL obj = new URL(take);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + take);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		res= response.toString();

		//		print result
		//		System.out.println(res);
	}

	public void htmlPars() throws Exception{

		String HTMLRes= res;

		Document doc= Jsoup.parse(HTMLRes);
		String title= doc.title();
		System.out.println(title);

		Elements ele=doc.select("a[href]");

		for (Element link : ele) { 

			if(link.text().equals("Wikipedia")){
				String result= link.attr("href");
				int i=0;
				flag:	
					for( i=0;i<result.length();i++)
					{
						if(result.charAt(i)=='&')
							break flag;
					}

				String linkWithHeader=result.substring(7,i);
				String protocol= linkWithHeader.substring(0, 5);
				
				if(protocol.equals("http")){
					
					SearchAuto http= new SearchAuto();
					http.sendGetHTTP(linkWithHeader);
					String HTMLRes1= res;
					System.out.println(HTMLRes1);
				}
				
				else if(protocol.equals("https")){
					
					SearchAuto http= new SearchAuto();
					http.sendGetHTTPS(linkWithHeader);
					String HTMLRes1= res;
					System.out.println(HTMLRes1);
				}


				//				http.sendGet(result);
			}

		}  

	}

}
