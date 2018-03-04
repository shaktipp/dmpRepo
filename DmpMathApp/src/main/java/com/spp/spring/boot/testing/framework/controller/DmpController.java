package com.spp.spring.boot.testing.framework.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

@RestController
@RequestMapping("/rest/dmp")
public class DmpController 
{
	@GetMapping(path = "/component.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getComponentDescriptor()
    {        
        return "true";
    }

    @GetMapping(path = "/env")
    public String listEnvProperties()
    {
        StringBuilder sb = new StringBuilder();
        Map<String, String> env = System.getenv();
        for (Map.Entry<String, String> entry : env.entrySet()) 
        {
            sb.append("<p>Env: ");
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append("</p>");
        }

        Properties props = System.getProperties();
        for (Map.Entry<Object, Object> entry : props.entrySet()) 
        {
            sb.append("<p>Prop: ");
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append("</p>");
        }
        return sb.toString();
    }

}//end of class DmpController
