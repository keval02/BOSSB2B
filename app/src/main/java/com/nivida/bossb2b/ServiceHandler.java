package com.nivida.bossb2b;

/**
 * Created by S!D on 28-03-2016.
 */
import android.util.Log;

import com.android.internal.http.multipart.MultipartEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.mime.MIME;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by S!D on 28-03-2016.
 */
public class ServiceHandler {

    static InputStream is = null;
    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;

    public ServiceHandler() {

    }

    public String makeServiceCall(String url, int method) {
        return this.makeServiceCall(url, method, null);
    }

    public String makeServiceCall(String url, int method,
                                  List<NameValuePair> params) {
        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;


            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);

                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }

                httpResponse = httpClient.execute(httpPost);

            } else if (method == GET) {

                if (params != null) {
                    String paramString = URLEncodedUtils
                            .format(params, "utf-8");
                    url += "?" + paramString;
                }
                HttpGet httpGet = new HttpGet(url);

                httpResponse = httpClient.execute(httpGet);

            }
            httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e("exception",e.getMessage());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            Log.e("exception",e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("exception",e.getMessage());
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            response = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error: " + e.toString());
        }

        return response;
    }

    public String makeServiceCall(String url, int method,
                                  List<NameValuePair> params, boolean multiMedia) {

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();

            MultipartEntityBuilder builder=MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            for(int index=0; index < params.size(); index++) {
                if(params.get(index).getName().equalsIgnoreCase("image")) {
                    FileBody fileBody = new FileBody(new File(params.get(index).getValue())); //image should be a String
                    //builder.addBinaryBody("image", new File(params.get(index).getValue()), ContentType.MULTIPART_FORM_DATA, new File(params.get(index).getValue()).getName());

                    //InputStreamBody inputStreamBody = new InputStreamBody(new ByteArrayInputStream(data), "abc.png");
                    builder.addPart("image",new FileBody(new File(params.get(index).getValue())));

                    //builder.addBinaryBody("image", fileBody);
                } else {
                    builder.addPart(params.get(index).getName(), new StringBody(params.get(index).getValue()));
                    // Normal string data
                    //builder.addTextBody(params.get(index).getName(), params.get(index).getValue(), ContentType.create("text/plain", MIME.UTF8_CHARSET));
                    //builder.addTextBody(params.get(index).getName(), new StringBody(params.get(index).getValue()));
                }
                //Log.e("part",builder.toString());
            }

            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;


            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);

                if (params != null) {
                    //httpPost.setHeader("Content-type", "multipart/form-data;");
                    httpPost.setEntity(builder.build());
                    //Log.e("params",builder.build().getContent().toString());
                }

                httpResponse = httpClient.execute(httpPost);

            } else if (method == GET) {

                if (params != null) {
                    String paramString = URLEncodedUtils
                            .format(params, "utf-8");
                    url += "?" + paramString;
                }
                HttpGet httpGet = new HttpGet(url);

                httpResponse = httpClient.execute(httpGet);

            }
            httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            response = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error: " + e.toString());
        }

        return response;
    }


    public String makeServiceCall(String url, int method,
                                  List<NameValuePair> params,int multimedia) {

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();

            MultipartEntityBuilder builder=MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            for(int index=0; index < params.size(); index++) {
                if(params.get(index).getName().contains("attachments1")) {
                    
                    //InputStreamBody inputStreamBody = new InputStreamBody(new ByteArrayInputStream(data), "abc.png");
                    builder.addPart(params.get(index).getName(),new FileBody(new File(params.get(index).getValue())));

                    //builder.addBinaryBody("image", fileBody);
                }
                else if(params.get(index).getName().contains("attachments2")) {

                    //InputStreamBody inputStreamBody = new InputStreamBody(new ByteArrayInputStream(data), "abc.png");
                    builder.addPart(params.get(index).getName(),new FileBody(new File(params.get(index).getValue())));

                    //builder.addBinaryBody("image", fileBody);
                }
                else if(params.get(index).getName().contains("attachment")) {

                    //InputStreamBody inputStreamBody = new InputStreamBody(new ByteArrayInputStream(data), "abc.png");
                    builder.addPart(params.get(index).getName(),new FileBody(new File(params.get(index).getValue())));

                    //builder.addBinaryBody("image", fileBody);
                }
                else {
                    builder.addPart(params.get(index).getName(), new StringBody(params.get(index).getValue()));
                    // Normal string data
                    //builder.addTextBody(params.get(index).getName(), params.get(index).getValue(), ContentType.create("text/plain", MIME.UTF8_CHARSET));
                    //builder.addTextBody(params.get(index).getName(), new StringBody(params.get(index).getValue()));
                }
                //Log.e("part",builder.toString());
            }

            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;


            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);

                if (params != null) {
                    //httpPost.setHeader("Content-type", "multipart/form-data;");
                    httpPost.setEntity(builder.build());
                    //Log.e("params",builder.build().getContent().toString());
                }

                httpResponse = httpClient.execute(httpPost);

            } else if (method == GET) {

                if (params != null) {
                    String paramString = URLEncodedUtils
                            .format(params, "utf-8");
                    url += "?" + paramString;
                }
                HttpGet httpGet = new HttpGet(url);

                httpResponse = httpClient.execute(httpGet);

            }
            httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            response = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error: " + e.toString());
        }

        return response;
    }

    public String makeServiceCallWithWWW(String url, int method,
                                  List<NameValuePair> params) {

        Log.e("URL",url);
        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;


            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                httpPost.setHeader(HTTP.CONTENT_TYPE,
                        "application/x-www-form-urlencoded;charset=UTF-8");

                if (params != null) {
                    UrlEncodedFormEntity entity=new UrlEncodedFormEntity(params,"UTF-8");
                    entity.setContentEncoding(HTTP.UTF_8);
                    entity.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
                    httpPost.setEntity(entity);
                }

                httpResponse = httpClient.execute(httpPost);

            } else if (method == GET) {

                if (params != null) {
                    String paramString = URLEncodedUtils
                            .format(params, "utf-8");
                    url += "?" + paramString;
                }
                HttpGet httpGet = new HttpGet(url);

                httpResponse = httpClient.execute(httpGet);

            }
            httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e("exception",e.getMessage());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            Log.e("exception",e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("exception",e.getMessage());
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            response = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error: " + e.toString());
        }

        return response;
    }
}

