package com.smarr.android.healthylifestyle.utilities.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
//uduwa package utilities imported to this package
import com.smarr.android.healthylifestyle.utilities.constants.GlobalConstants;
import com.smarr.android.healthylifestyle.utilities.exception.BadDataException;
import com.smarr.android.healthylifestyle.utilities.exception.BaseException;
import com.smarr.android.healthylifestyle.utilities.exception.NetworkException;
import com.smarr.android.healthylifestyle.utilities.exception.UnauthorizedException;

/**
 * This class provides utility methods for getting json Object,Json Array
 * also have postLoginData method
 * 
 */
public class HttpConnectionUtilities {

	private static DefaultHttpClient httpClient; //for handling session

	private static void handleResponse(HttpResponse resp) throws BaseException{
		if(resp==null)
			throw new NetworkException("No response received from server");
		 int statusCode = resp.getStatusLine().getStatusCode();
		 String reasonPhrase=resp.getStatusLine().getReasonPhrase();
		 if(reasonPhrase==null){
			 reasonPhrase="";
		 }
		 if(statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_ACCEPTED  || statusCode == HttpStatus.SC_CONTINUE  || statusCode == HttpStatus.SC_CREATED  || statusCode == HttpStatus.SC_NO_CONTENT){
			 return;
		 }
		 else{
			 try{
				 String response =  entityToString(resp.getEntity());
				  if(response != null){

					 int firstMatch=response.indexOf(GlobalConstants.TABLET_UNIQUE_MSG_MARKER);
					 if(firstMatch>=0 && response.length()>GlobalConstants.TABLET_UNIQUE_MSG_MARKER.length()){
						 firstMatch=firstMatch+GlobalConstants.TABLET_UNIQUE_MSG_MARKER.length();
						 int secondMatch=response.indexOf(GlobalConstants.TABLET_UNIQUE_MSG_MARKER,firstMatch);
						 if(secondMatch>=0){
							 reasonPhrase=response.substring(firstMatch, secondMatch);
						 }
					 }
				  }
			 }
			 catch (Exception e) {
				 e.printStackTrace();
				//swallow as this is not critical
			}

			 switch(statusCode){
			 	case HttpStatus.SC_BAD_GATEWAY:
			 		throw new NetworkException("Bad Gateway - URL may be invalid or servers may be down. " + reasonPhrase);
			 	case HttpStatus.SC_BAD_REQUEST:
			 		throw new BadDataException("Request contained bad data. " + reasonPhrase);
			 	case HttpStatus.SC_REQUEST_TIMEOUT :
			 		throw new NetworkException("Server Timeout - servers may be down or your network connection may be slow. Please try again later. " + reasonPhrase);
			 	case HttpStatus.SC_FORBIDDEN:
			 		throw new NetworkException("Access denied to a restricted area. " + reasonPhrase);
			 	case HttpStatus.SC_GATEWAY_TIMEOUT:
			 		throw new NetworkException("Server Timeout - servers may be down or your network connection may be slow. Please try again later.  " + reasonPhrase);
			 	case HttpStatus.SC_INTERNAL_SERVER_ERROR:
			 		throw new NetworkException("There was an error encountered on the server. " + reasonPhrase);
			 	case HttpStatus.SC_METHOD_NOT_ALLOWED:
			 		throw new NetworkException("The URL requested could not be found and is unavailable. " + reasonPhrase);
			 	case HttpStatus.SC_NOT_FOUND:
			 		throw new NetworkException("The URL or page requested could not be found.  " + reasonPhrase);
			 	case HttpStatus.SC_SERVICE_UNAVAILABLE:
			 		throw new NetworkException("Servers are down or unreachable or your internet connection is malfunctioning. " + reasonPhrase);
			 	case HttpStatus.SC_UNAUTHORIZED:
			 		throw new UnauthorizedException("Unauthorized failure: " + reasonPhrase + " \n\n  If you are receiving this message after having logged in earlier you may have been logged out due to inactivity- please login again.");
			 	case HttpStatus.SC_UNPROCESSABLE_ENTITY:
			 		throw new NetworkException("The data that was sent was not processable.  " + reasonPhrase);

			 }

		 }
	}

	public static String entityToString(HttpEntity entity)  throws IOException{
		  InputStream is = entity.getContent();
		  BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
		  StringBuilder str = new StringBuilder();

		  String line = null;
		  try {
		    while ((line = bufferedReader.readLine()) != null) {
		      str.append(line + "\n");
		    }
		  } catch (IOException e) {
		     throw new RuntimeException(e);
		  } finally {
		    try {
		      is.close();
		    } catch (IOException e) {
		      //tough luck...
		    }
		  }
		  return str.toString();
		}


	public static synchronized DefaultHttpClient getClient() {
		    if(httpClient!=null){
		    	return httpClient;
		    }
	        DefaultHttpClient ret = null;

	        //SETS UP PARAMETERS
	        HttpParams params = new BasicHttpParams();
	        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	        HttpProtocolParams.setContentCharset(params, "utf-8");
	        params.setBooleanParameter("http.protocol.expect-continue", false);

	        //REGISTERS SCHEMES FOR BOTH HTTP AND HTTPS
	        SchemeRegistry registry = new SchemeRegistry();
	        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	        final SSLSocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();
	        sslSocketFactory.setHostnameVerifier(SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	        registry.register(new Scheme("https", sslSocketFactory, 443));

	        ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(params, registry);
	        ret = new DefaultHttpClient(manager, params);
	        httpClient=ret;
	        return ret;
	    }


	public static boolean isNetworkAvailable(Context mContext) {
	    ConnectivityManager cm = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
	    if (networkInfo != null && networkInfo.isConnected()) {
	        return true;
	    }
	    return false;
	}


	public static String postDataWithExpectedReturnMessage(String aServerURL, List<NameValuePair> postData) throws BaseException{
		HttpPost httpPost = null;
		HttpResponse httpResponse = null;
		HttpEntity httpEntity = null;
		String ret=null;
		DefaultHttpClient httpClient=HttpConnectionUtilities.getClient();
		httpPost = new HttpPost(aServerURL);
	    try {
	        httpPost.setEntity(new UrlEncodedFormEntity(postData));
	        httpResponse = httpClient.execute(httpPost);
	        handleResponse(httpResponse);
	        httpEntity = httpResponse.getEntity();
			if(httpEntity!=null){
				ret=entityToString(httpEntity);
			}
			return ret;
	    }catch (HttpHostConnectException e) {
			throw new NetworkException(e.getMessage());
		} catch (ConnectException e) {
			throw new NetworkException(e.getMessage());
		} catch (SocketException e) {
			throw new NetworkException(e.getMessage());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new NetworkException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new NetworkException(e.getMessage());
		}
	    catch(BaseException e){
	    	throw e;
	    }
	    catch (Exception e) {
			e.printStackTrace();
			throw new NetworkException(e.getMessage());
		}
	}

	public static String doGet(final String url) throws BaseException {

		HttpGet httpGet = null;
		HttpResponse httpResponse = null;
		HttpEntity httpEntity = null;
		httpGet = new HttpGet(url);
		String ret=null;

		HttpClient httpClient=HttpConnectionUtilities.getClient();
		try {
			httpResponse = httpClient.execute(httpGet);
			handleResponse(httpResponse);
			httpEntity = httpResponse.getEntity();
			if(httpEntity!=null){
				ret=entityToString(httpEntity);
			}
			return ret;
		} catch (HttpHostConnectException e) {
			throw new NetworkException(e.getMessage());
		} catch (ConnectException e) {
			throw new NetworkException(e.getMessage());
		} catch (SocketException e) {
			throw new NetworkException(e.getMessage());
		} catch (ClientProtocolException e) {
			throw new NetworkException(e.getMessage());
		} catch (IOException e) {
			throw new NetworkException(e.getMessage());
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new NetworkException(e.getMessage());
		}
		 catch(BaseException e){
		    	throw e;
		    }
		    catch (Exception e) {
				e.printStackTrace();
				throw new NetworkException(e.getMessage());
			}

	}

	public static int postDataNoReturnMessage(String aServerURL, List<NameValuePair> postData) throws BaseException{
		HttpPost httpPost = null;
		HttpResponse httpResponse = null;
		int statusCode;

		DefaultHttpClient httpClient=HttpConnectionUtilities.getClient();
		httpPost = new HttpPost(aServerURL);
	    try {
	        httpPost.setEntity(new UrlEncodedFormEntity(postData));
	        httpResponse = httpClient.execute(httpPost);
	        handleResponse(httpResponse);
	        statusCode = httpResponse.getStatusLine().getStatusCode();
	        return statusCode;
	    }catch (HttpHostConnectException e) {
			throw new NetworkException(e.getMessage());
		} catch (ConnectException e) {
			throw new NetworkException(e.getMessage());
		} catch (SocketException e) {
			throw new NetworkException(e.getMessage());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new NetworkException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new NetworkException(e.getMessage());
		}
	    catch(BaseException e){
	    	throw e;
	    }
	    catch (Exception e) {
			e.printStackTrace();
			throw new NetworkException(e.getMessage());
		}
	}

	public static List<NameValuePair> createPostDataHolder(){
		return new ArrayList<NameValuePair>();
	}

	public static NameValuePair createNameValuePair(String name, String value){
		if(name==null || name.trim().equals(""))
			return null;
		return new BasicNameValuePair(name, value);
	}

	public static void addNameValuePair(String name, String value, List<NameValuePair> myData){
		if(name==null || name.trim().equals("") || myData==null)
			return;
		myData.add(new BasicNameValuePair(name, value));
	}


}
