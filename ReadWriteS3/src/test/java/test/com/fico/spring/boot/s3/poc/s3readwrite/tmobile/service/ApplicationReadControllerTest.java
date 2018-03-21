package test.com.fico.spring.boot.s3.poc.s3readwrite.tmobile.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fico.spring.boot.s3.poc.s3readwrite.tmobile.ReadWriteS3Application;
import com.fico.spring.boot.s3.poc.s3readwrite.tmobile.util.FileUtil;
import junit.framework.Assert;


@RunWith(SpringRunner.class)
@SpringBootTest(classes =ReadWriteS3Application.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ApplicationReadControllerTest
{
    Logger logger =  LoggerFactory.getLogger(ApplicationReadControllerTest.class);  
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    TestRestTemplate restTemplate = new TestRestTemplate();
    
    @Test
    public void readS3StorageTest()
    {
        String sampleWriteReqStr = FileUtil.getFileContent( "C:/04_Dev_Workspace/SpringIDE_Workspace/S3StorageWithSpring/ReadWriteS3/src/test/resources/SampleReadRequest.xml" );
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(  MediaType.APPLICATION_XML );
        
        HttpEntity<String> entity = new HttpEntity<>(sampleWriteReqStr, headers);
        
        ResponseEntity<String> response = restTemplate.exchange("/tmobile/read/usa", HttpMethod.POST, entity, String.class);
        
        String responseStr = response.getBody( );
        
        logger.info( "\nResponse Received\n" + responseStr + "\n....End...\n" );
        Assert.assertEquals( "No Response found from Backend Server", true, responseStr.length( ) > 1 );
    }

}//End of class ApplicationReadControllerTest
