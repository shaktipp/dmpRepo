package com.fico.spring.boot.s3.poc.s3readwrite.tmobile.util;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.util.ResourceUtils;


public class FileUtil
{
    public static String getFileContent(String fileInfo)
    {
        File file = null;
        try
        {
            file = ResourceUtils.getFile(fileInfo);
            return new String(Files.readAllBytes(file.toPath()));
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args)
    {
        System.out.println(FileUtil.getFileContent("dm\\dm_req.xml"));
    }
}

