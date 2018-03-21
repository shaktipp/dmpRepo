package com.fico.spring.boot.s3.poc.s3readwrite.tmobile.controller;

import com.fico.spring.boot.s3.poc.s3readwrite.tmobile.service.ApplicationReadService;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/tmobile")
public class ApplicationReadController
{
    Logger logger =  LoggerFactory.getLogger(ApplicationReadController.class);

    @Autowired
    ApplicationReadService appReadService;

    @PostMapping(path = "/read/{awsRegionName}", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> readS3Storage(@PathVariable String awsRegionName, @RequestBody String appReq)
    {
        logger.info("Entered");
        OMElement lv_om_result = null;
        OMElement lv_om_temp = null;
        Map<String,Object> lv_map_reqInfo =null;
        String lv_temp = "";
        String lv_xmlFragment = "";
        try
        {
            if(null == awsRegionName || "usa,sing,mum".indexOf(awsRegionName) == -1)
            {
                awsRegionName = awsRegionName != null ? awsRegionName : "No Region Code Provided";
                lv_temp = String.format("<error>%s is invalid region code. Valid code are usa,sin,mum</error>",awsRegionName);
                logger.error(lv_temp);
                return ResponseEntity
                        .status(400)
                        .body(lv_temp);

            }
            lv_om_result = AXIOMUtil.stringToOM(appReq);
            lv_xmlFragment = "<awsRegionName>" + awsRegionName + "</awsRegionName>";
            lv_om_temp = AXIOMUtil.stringToOM(lv_xmlFragment);
            lv_om_result.addChild(lv_om_temp);

            lv_map_reqInfo = appReadService.appReadS3Service(lv_om_result);

            if(null != lv_map_reqInfo && lv_map_reqInfo.get("error") != null)
            {
                logger.error("<error>" + lv_map_reqInfo.get("error").toString() + "</error>");
                return ResponseEntity
                        .status(500)
                        .body("<error>" + lv_map_reqInfo.get("error").toString() + "</error>");
            }
            else
            {
                lv_om_result = null;
                lv_om_result = appReadService.processReadOperationService(lv_map_reqInfo);
            }


        }
        catch(Exception e)
        {
            e.printStackTrace();
            logger.error("<error>" + e.getMessage() + "</error>");
            return ResponseEntity
                    .status(500)
                    .body("<error>" + e.getMessage() + "</error>");
        }
        finally
        {
            lv_om_temp = null;
            lv_map_reqInfo =null;
            lv_temp = null;
            lv_xmlFragment = null;
        }
        logger.info("Exited");
        return ResponseEntity
                .status(201)
                .body(lv_om_result.toString());
    }//End of readS3Storage()

}//End of class ApplicationReadController
