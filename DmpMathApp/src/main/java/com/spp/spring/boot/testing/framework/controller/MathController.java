package com.spp.spring.boot.testing.framework.controller;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathController
{
    @GetMapping(path = "/add/{num1}/{num2}")
    public Long add(@PathVariable Long num1,@PathVariable Long num2)
    {
        return num1 + num2;
    }
    
    
    @GetMapping(path = "/sub/{num1}/{num2}")
    public Long sub(@PathVariable Long num1,@PathVariable Long num2)
    {
        return num1 - num2;
    }
    
    
    @GetMapping(path = "/mul/{num1}/{num2}")
    public Long mul(@PathVariable Long num1,@PathVariable Long num2)
    {
        return num1 * num2;
    }
    
    @GetMapping(path = "/div/{num1}/{num2}")
    public Long div(@PathVariable Long num1,@PathVariable Long num2)
    {
        return num1 / num2;
    }

}//end of class MathController
