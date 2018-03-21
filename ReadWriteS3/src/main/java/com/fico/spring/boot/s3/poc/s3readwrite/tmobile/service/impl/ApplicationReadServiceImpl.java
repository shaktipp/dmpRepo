package com.fico.spring.boot.s3.poc.s3readwrite.tmobile.service.impl;

import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.fico.spring.boot.s3.poc.s3readwrite.tmobile.service.ApplicationReadService;
import com.fico.spring.boot.s3.poc.s3readwrite.tmobile.util.AwsUtil;
import com.fico.spring.boot.s3.poc.s3readwrite.tmobile.util.FileUtil;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.axiom.om.xpath.AXIOMXPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class ApplicationReadServiceImpl implements ApplicationReadService
{
    Logger logger =  LoggerFactory.getLogger(ApplicationReadServiceImpl.class);


    @Override
    public Map<String, Object> appReadS3Service(OMElement om_readReq)
    {
        logger.info("Entered");
        String clientCode = "";
        String tenantName = "";
        String buckerPrefix = ""; //M : Module, N : No Prefix
        String s3BucketKey = "";
        Integer startCount = 0;
        Integer endCount = 0;
        String isDispBucketContent = "N"; //N : No , Y : Yes
        String awsRegionName = "";
        String lv_temp = "";
        Map<String,Object> map_reqInfo = new HashMap<>();
        AXIOMXPath xpath = null;

        try
        {
            xpath = new AXIOMXPath("/applicantRequest/buckerPrefix");
            buckerPrefix = ((OMElement)xpath.selectSingleNode(om_readReq)).getText();

            if(null == buckerPrefix || buckerPrefix.length() > 1 ||"M.N".indexOf(buckerPrefix) == -1) {
                map_reqInfo.put("error","buckerPrefix should be either M for Module or N for No Prefix");
                logger.error("error","buckerPrefix should be either M for Module or N for No Prefix");
                throw new Exception("buckerPrefix should be either M for Module or N for No Prefix");
            }

            xpath = new AXIOMXPath("/applicantRequest/clientCode");
            clientCode = ((OMElement)xpath.selectSingleNode(om_readReq)).getText();

            xpath = new AXIOMXPath("/applicantRequest/tenantName");
            tenantName = ((OMElement)xpath.selectSingleNode(om_readReq)).getText();

            if(buckerPrefix.equalsIgnoreCase("M"))
                s3BucketKey = clientCode + "/" + tenantName + "/" ;
            else
                s3BucketKey = clientCode + "_" + tenantName + "_";
            //s3BucketKey = clientCode + "_" + tenantName ;

            map_reqInfo.put("s3BucketKey",s3BucketKey);

            xpath = new AXIOMXPath("/applicantRequest/startCount");
            lv_temp = ((OMElement)xpath.selectSingleNode(om_readReq)).getText();
            startCount = Integer.parseInt(lv_temp);
            map_reqInfo.put("startCount", startCount);

            xpath = new AXIOMXPath("/applicantRequest/endCount");
            lv_temp = null;
            lv_temp = ((OMElement)xpath.selectSingleNode(om_readReq)).getText();
            endCount = Integer.parseInt(lv_temp);
            map_reqInfo.put("endCount",endCount);

            xpath = new AXIOMXPath("/applicantRequest/isDispBucketContent");
            isDispBucketContent = ((OMElement)xpath.selectSingleNode(om_readReq)).getText();
            map_reqInfo.put("isDispBucketContent",isDispBucketContent);

            xpath = new AXIOMXPath("/applicantRequest/awsRegionName");
            awsRegionName = ((OMElement)xpath.selectSingleNode(om_readReq)).getText();
            map_reqInfo.put("awsRegionName",awsRegionName);

            xpath = new AXIOMXPath("/applicantRequest/buckerPrefix");
            buckerPrefix = ((OMElement)xpath.selectSingleNode(om_readReq)).getText();
            map_reqInfo.put("buckerPrefix",buckerPrefix);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            map_reqInfo.put("error",e.getMessage());
        }
        finally
        {
            clientCode = null;
            tenantName = null;
            buckerPrefix = null;
            s3BucketKey = null;
            startCount = null;
            endCount = null;
            isDispBucketContent = null;
            lv_temp = null;

        }

        logger.info("Exited");
        return map_reqInfo;
    }




    public OMElement processReadOperationService(Map<String,Object> map_applicationInfo)
    {
        logger.info("Entered");
        OMElement om_resInfo = null;
        Integer startCount = 0;
        Integer endCount = 0;
        String lv_temp = "";
        String lv_bucketName = "";
        String lv_bucket_prefix = "";
        String lv_s3BucketKey = "";
        String lv_awsRegionName = "";
        String isDispBucketContent = "";
        String totalResponse = "<response>";

        try
        {
            lv_temp = map_applicationInfo.get("startCount").toString();
            startCount = Integer.parseInt(lv_temp);

            lv_temp = map_applicationInfo.get("endCount").toString();
            endCount = Integer.parseInt(lv_temp);

            lv_bucket_prefix = (String) map_applicationInfo.get("buckerPrefix");
            lv_s3BucketKey = (String) map_applicationInfo.get("s3BucketKey");
            lv_awsRegionName = (String) map_applicationInfo.get("awsRegionName");
            isDispBucketContent = (String) map_applicationInfo.get("isDispBucketContent");
            lv_bucketName = AwsUtil.getBucketName(lv_awsRegionName);


            for (int i = startCount; i <= endCount; i++) {
                totalResponse += "<applicant>";
                if (lv_bucket_prefix.equalsIgnoreCase("M")) {

                    lv_temp = null;
                    lv_temp = lv_s3BucketKey + "APM/req_" + i;
                    //String readS3OnBucket(String bucketName,String bucketKey,String isDispBucketContent, String appType, String awsRegionName)
                    totalResponse += readS3OnBucket(lv_bucketName, lv_temp, isDispBucketContent, "apmReq", lv_awsRegionName);

                    lv_temp = null;
                    lv_temp = lv_s3BucketKey + "APM/res_" + i;
                    totalResponse += readS3OnBucket(lv_bucketName, lv_temp, isDispBucketContent, "apmRes", lv_awsRegionName);

                    lv_temp = null;
                    lv_temp = lv_s3BucketKey + "DM/req_" + i;
                    totalResponse += readS3OnBucket(lv_bucketName, lv_temp, isDispBucketContent, "dmReq", lv_awsRegionName);

                    lv_temp = null;
                    lv_temp = lv_s3BucketKey + "DM/res_" + i;
                    totalResponse += readS3OnBucket(lv_bucketName, lv_temp, isDispBucketContent, "dmRes", lv_awsRegionName);

                    lv_temp = null;
                    lv_temp = lv_s3BucketKey + "DAM/req_" + i;
                    totalResponse += readS3OnBucket(lv_bucketName, lv_temp, isDispBucketContent, "damReq", lv_awsRegionName);

                    lv_temp = null;
                    lv_temp = lv_s3BucketKey + "DAM/res_" + i;
                    totalResponse += readS3OnBucket(lv_bucketName, lv_temp, isDispBucketContent, "damRes", lv_awsRegionName);


                } else {

                    lv_temp = null;
                    lv_temp = lv_s3BucketKey + "APM_req_" + i;
                    totalResponse += readS3OnBucket(lv_bucketName, lv_temp, isDispBucketContent, "apmReq", lv_awsRegionName);

                    lv_temp = null;
                    lv_temp = lv_s3BucketKey + "APM_res_" + i;
                    totalResponse += readS3OnBucket(lv_bucketName, lv_temp, isDispBucketContent, "apmRes", lv_awsRegionName);

                    lv_temp = null;
                    lv_temp = lv_s3BucketKey + "DM_req_" + i;
                    totalResponse += readS3OnBucket(lv_bucketName, lv_temp, isDispBucketContent, "dmReq", lv_awsRegionName);

                    lv_temp = null;
                    lv_temp = lv_s3BucketKey + "DM_res_" + i;
                    totalResponse += readS3OnBucket(lv_bucketName, lv_temp, isDispBucketContent, "dmRes", lv_awsRegionName);

                    lv_temp = null;
                    lv_temp = lv_s3BucketKey + "DAM_req_" + i;
                    totalResponse += readS3OnBucket(lv_bucketName, lv_temp, isDispBucketContent, "damReq", lv_awsRegionName);

                    lv_temp = null;
                    lv_temp = lv_s3BucketKey + "DAM_res_" + i;
                    totalResponse += readS3OnBucket(lv_bucketName, lv_temp, isDispBucketContent, "damRes", lv_awsRegionName);
                }
                totalResponse += "</applicant>";


            }//End of For Loop

            totalResponse += "</response>";
            om_resInfo = AXIOMUtil.stringToOM(totalResponse);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            logger.error(("<error>" + e.getMessage() + "</error>"));
        }
        finally
        {
            startCount = null;
            endCount = null;
            lv_temp =null;
            lv_bucketName =null;
            lv_bucket_prefix = null;
            lv_s3BucketKey = null;
            lv_awsRegionName = null;
            isDispBucketContent = null;
            totalResponse = null;
        }
        logger.info("Exited");
        return om_resInfo;

    }//End of processReadOperationService





    private String displayTextInputStream(InputStream inputStream) throws IOException
    {
        String line = null;
        String result = "";
        if(null != inputStream)
        {
            // Read one text line at a time and display.
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while (true)
            {
                line = reader.readLine();
                result += line ;
                if (line == null) break;
            }

        }//end of if block

        return result;
    }

    @Override
    public String readS3OnBucket(String bucketName,String bucketKey,String isDispBucketContent, String appType, String awsRegionName)
    {
        LocalDateTime start = null;
        LocalDateTime end = null;
        Duration duration = null;
        String bucketFile2Str = "Disabled, To enable the content do isDispBucketContent =Y ";
        S3Object s3object = null;

        String result = "";
        try
        {

            start = LocalDateTime.now();

            //==========================================================================================================================
            //GetS3 Bucket Content
            s3object = AwsUtil.getS3Client(awsRegionName).getObject(new GetObjectRequest(bucketName, bucketKey));
            //==========================================================================================================================
            end = LocalDateTime.now();
            duration = Duration.between(end, start);
            if(isDispBucketContent.equalsIgnoreCase("Y"))
            {
                bucketFile2Str = displayTextInputStream(s3object.getObjectContent());
            }
            if(null != s3object)
                s3object.close();

            //createBucketXml(String bucketName, String keyName, String timeTaken, String appType, String bucketContent)
            result = createBucketXml(bucketName,bucketKey,duration.getNano()+"",appType, bucketFile2Str);



        }
        catch(Exception e)
        {
            e.printStackTrace();
            result = createBucketXml(bucketName,bucketKey,"-99999",appType,"Unable to Read S3 bucket due to error, Actual Error=" + e.getMessage());
        }
        finally
        {
            start = null;
            end = null;
            duration = null;
            bucketFile2Str = null;
            s3object = null;

        }
        return result;
    }


    @Override
    public String createBucketXml(String bucketName, String keyName, String timeTaken, String appType, String bucketContent)
    {
        return String.format("<bucket><bucketName>%s</bucketName><keyName>%s</keyName><appType>%s</appType>" +
                "<timeTaken>%s</timeTaken><bucketContent>%s</bucketContent></bucket>",bucketName,keyName,appType,timeTaken,bucketContent);
    }

}
