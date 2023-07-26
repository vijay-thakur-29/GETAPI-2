package com.qa.tests;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.qa.client.RestClient;
import com.qa.util.TestUtils;

public class GetAPITest {
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;

	@BeforeMethod
	@DataProvider
	public Object[][] getURLAPI() {
		Object data[][] = TestUtils.getTestdata("GetURL");
		return data;
	}
//excel data
	@Test(dataProvider = "getURLAPI")
	public void getsearchcompanyTest(String URL) throws ClientProtocolException, IOException 
	{
		restClient = new RestClient();
		closeableHttpResponse = restClient.get(URL);

		// a.Status Code:
		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("\n");
		System.out.println("<================================================================>");
		System.out.println("Status Code======>" + statuscode);
		Assert.assertEquals(statuscode, 200, "Status code is not equal 200");

		// b. JSON String:
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("\n");
		System.out.println("The URL is======>" + URL);
		System.out.println("Response JSON from API======>" + responseJson);
		//Assert.assertEquals(responseString, Website, "Website is not equal");
		
		// c. All Headers:
		Header[] headersArray = closeableHttpResponse.getAllHeaders();

		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("\n");
		System.out.println("Headers Array=====>" + allHeaders);
		System.out.println("<================================================================>");
		// System.out.println("\n");
		FileWriter file=new FileWriter(System.getProperty("user.dir") + "\\src\\main\\java\\com\\qa\\config\\output.txt",true);
		file.write("The URL is===>"+URL);
		file.write("\n");
		file.write("The Response JSON is===>"+responseString);
		file.write("\n\n");
		file.close();
	}	
}
