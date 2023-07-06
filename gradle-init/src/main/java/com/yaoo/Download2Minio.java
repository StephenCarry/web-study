package com.yaoo;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class Download2Minio {
    public static void main(String[] args) throws IOException {
        System.out.println(new Date());
        long start = new Date().getTime();
        URL url = new URL("http://120.24.218.7:9000/test/ideaIU-2021.2.exe");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(5*60*1000);
        httpURLConnection.setReadTimeout(5*60*1000);
        int code = httpURLConnection.getResponseCode();
        if (code != HttpURLConnection.HTTP_OK) System.out.println("download failed");
        BufferedInputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("C:\\idea.exe"));
        byte[] buffer = new byte[4096];
        int count = 0;
        while ((count = in.read(buffer)) > 0) {
            out.write(buffer, 0, count);
        }
        out.close();
        in.close();
        long end = new Date().getTime();
        System.out.println((end-start)/1000);
    }
}
