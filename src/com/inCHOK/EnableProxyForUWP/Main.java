package com.inCHOK.EnableProxyForUWP;

import java.io.*;
import java.nio.charset.Charset;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String path=System.getenv("USERPROFILE")+"\\AppData\\Local\\Packages";
        System.out.println(path);
        File packages=new File(path);
        Runtime runtime=Runtime.getRuntime();
        Process process=null;
        BufferedReader inputReader=null;
        try {
            String line=null;
            for (File file:packages.listFiles()){
                if (file.isDirectory()){
                    process=runtime.exec("CheckNetIsolation.exe LoopbackExempt -a -n=\""+file.getName()+"\"");
                    inputReader=new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
                    StringBuilder builder=new StringBuilder();
                    System.out.println("CheckNetIsolation.exe LoopbackExempt -a -n=\""+file.getName()+"\"");
                    while ((line=inputReader.readLine())!=null) builder.append(line).append("\n");
                    System.out.print(builder.toString());
                    inputReader.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }finally {
            if (process!=null) process.destroy();
            try {
                if (inputReader!=null) inputReader.close();
            }catch (IOException e){
                e.printStackTrace();
                System.out.println(e.toString());
            }
        }
    }
}
