package com.nivida.bossb2b;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FileDownloader {
	
	
	private static final int  MEGABYTE = 1024 * 1024;

public static void downloadFile(String fileUrl, File directory){
	
	/* try {

         FileOutputStream f = new FileOutputStream(directory);
         URL u = new URL(fileUrl);
         HttpURLConnection c = (HttpURLConnection) u.openConnection();
         c.setRequestMethod("GET");
         c.setDoOutput(true);
         c.connect();

         InputStream in = c.getInputStream();

         byte[] buffer = new byte[1024];
         int len1 = 0;
         while ((len1 = in.read(buffer)) > 0) {
             f.write(buffer, 0, len1);
         }
         f.close();
     } catch (Exception e) {
         e.printStackTrace();
     }

 }*/
    try {

    	URL url = new URL(fileUrl);
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        InputStream inputStream = urlConnection.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream(directory);
        int totalSize = urlConnection.getContentLength();

        byte[] buffer = new byte[MEGABYTE];
        int bufferLength = 0;
        while((bufferLength = inputStream.read(buffer))>0 ){
            fileOutputStream.write(buffer, 0, bufferLength);
        }
        fileOutputStream.close();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (MalformedURLException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}}
