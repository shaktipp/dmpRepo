package com.fico.spring.boot.s3.poc.s3readwrite.tmobile.util;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class FileUtil
{
    public static String getFileContent(String fileInfo)
    {
        try
        {
            return new String(Files.readAllBytes(Paths.get(fileInfo)));
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args)
    {
        System.out.println(FileUtil.getFileContent("C:\\04_Dev_Workspace\\SpringIDE_Workspace\\s3readwrite\\src\\test\\resources\\xmlFiles\\dm\\dm_req.xml"));
    }
}

