package com.fico.spring.boot.s3.poc.s3readwrite.tmobile.service.impl;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fico.spring.boot.s3.poc.s3readwrite.tmobile.service.ApplicationWriteService;
import com.fico.spring.boot.s3.poc.s3readwrite.tmobile.util.AwsUtil;
import com.fico.spring.boot.s3.poc.s3readwrite.tmobile.util.FileUtil;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNode;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.axiom.om.xpath.AXIOMXPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class ApplicationWriteServiceImpl implements ApplicationWriteService
{
    Logger logger =  LoggerFactory.getLogger(ApplicationWriteServiceImpl.class);

    @Override
    public Map<String,Object> appSubmitService(OMElement om_appReq)
    {
        logger.info("Entered");
        String clientCode = "";
        String tenantName = "";
        String buckerPrefix = ""; //M : Module, N : No Prefix
        String s3BucketKey = "";

        String applicantName = "";
        String birthDate = "";
        String maritalStatus = "";
        String identificationCode = "";
        String address = "";
        String submittedDate = "";
        String awsRegionName = "";
        OMNode apm_req = null;
        OMNode apm_res =  null;
        OMNode dm_req = null;
        OMNode dm_res =  null;
        OMNode dam_req = null;
        OMNode dam_res =  null;
        String lv_temp = null;

        AXIOMXPath xpath = null;
        Map<String,Object> map_reqInfo = new HashMap<>();
        try
        {
            xpath = new AXIOMXPath("/applicantRequest/buckerPrefix");
            buckerPrefix = ((OMElement)xpath.selectSingleNode(om_appReq)).getText();
            if(null == buckerPrefix || buckerPrefix.length() > 1 ||"M.N".indexOf(buckerPrefix) == -1) {
                map_reqInfo.put("error","buckerPrefix should be either M for Module or N for No Prefix");
                logger.error("error","buckerPrefix should be either M for Module or N for No Prefix");
                throw new Exception("buckerPrefix should be either M for Module or N for No Prefix");
            }

            xpath = new AXIOMXPath("/applicantRequest/clientCode");
            clientCode = ((OMElement)xpath.selectSingleNode(om_appReq)).getText();

            xpath = new AXIOMXPath("/applicantRequest/tenantName");
            tenantName = ((OMElement)xpath.selectSingleNode(om_appReq)).getText();

            if(buckerPrefix.equalsIgnoreCase("M"))
                s3BucketKey = clientCode + "/" + tenantName + "/" ;
            else
                s3BucketKey = clientCode + "_" + tenantName + "_";
            //s3BucketKey = clientCode + "_" + tenantName ;

            map_reqInfo.put("s3BucketKey",s3BucketKey);

            xpath = new AXIOMXPath("/applicantRequest/startCount");
            lv_temp = ((OMElement)xpath.selectSingleNode(om_appReq)).getText();
            map_reqInfo.put("startCount",Integer.parseInt(lv_temp));

            xpath = new AXIOMXPath("/applicantRequest/endCount");
            lv_temp = null;
            lv_temp = ((OMElement)xpath.selectSingleNode(om_appReq)).getText();
            map_reqInfo.put("endCount",Integer.parseInt(lv_temp));

            xpath = new AXIOMXPath("/applicantRequest/applicantName");
            applicantName = ((OMElement)xpath.selectSingleNode(om_appReq)).getText();

            xpath = new AXIOMXPath("/applicantRequest/birthDate");
            birthDate = ((OMElement)xpath.selectSingleNode(om_appReq)).getText();

            xpath = new AXIOMXPath("/applicantRequest/maritalStatus");
            maritalStatus = ((OMElement)xpath.selectSingleNode(om_appReq)).getText();

            xpath = new AXIOMXPath("/applicantRequest/identificationCode");
            identificationCode = ((OMElement)xpath.selectSingleNode(om_appReq)).getText();

            xpath = new AXIOMXPath("/applicantRequest/address");
            address = ((OMElement)xpath.selectSingleNode(om_appReq)).getText();

            xpath = new AXIOMXPath("/applicantRequest/submittedDate");
            submittedDate = ((OMElement)xpath.selectSingleNode(om_appReq)).getText();

            xpath = new AXIOMXPath("/applicantRequest/awsRegionName");
            awsRegionName = ((OMElement)xpath.selectSingleNode(om_appReq)).getText();

            xpath = new AXIOMXPath("/applicantRequest/apm_req");
            apm_req = ((OMElement)xpath.selectSingleNode(om_appReq));

            xpath = new AXIOMXPath("/applicantRequest/apm_res");
            apm_res = ((OMElement)xpath.selectSingleNode(om_appReq));

            xpath = new AXIOMXPath("/applicantRequest/dm_req");
            dm_req = ((OMElement)xpath.selectSingleNode(om_appReq));

            xpath = new AXIOMXPath("/applicantRequest/dm_res");
            dm_res = ((OMElement)xpath.selectSingleNode(om_appReq));

            xpath = new AXIOMXPath("/applicantRequest/dam_req");
            dam_req = ((OMElement)xpath.selectSingleNode(om_appReq));

            xpath = new AXIOMXPath("/applicantRequest/dam_res");
            dam_res = ((OMElement)xpath.selectSingleNode(om_appReq));

            map_reqInfo.put("clientCode",clientCode);
            map_reqInfo.put("tenantName",tenantName);
            map_reqInfo.put("buckerPrefix",buckerPrefix);
            map_reqInfo.put("s3BucketKey",s3BucketKey);
            map_reqInfo.put("applicantName",applicantName);
            map_reqInfo.put("birthDate",birthDate);
            map_reqInfo.put("maritalStatus",maritalStatus);
            map_reqInfo.put("identificationCode",identificationCode);
            map_reqInfo.put("address",address);
            map_reqInfo.put("submittedDate",submittedDate);
            map_reqInfo.put("awsRegionName",awsRegionName);
            map_reqInfo.put("apm_req",apm_req);
            map_reqInfo.put("apm_res",apm_res);
            map_reqInfo.put("dm_req",dm_req);
            map_reqInfo.put("dm_res",dm_res);
            map_reqInfo.put("dam_req",dam_req);
            map_reqInfo.put("dam_res",dam_res);
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
            applicantName = null;
            birthDate = null;
            maritalStatus = null;
            identificationCode = null;
            address = null;
            submittedDate = null;
            awsRegionName = null;
            apm_req = null;
            apm_res =  null;
            dm_req = null;
            dm_res =  null;
            dam_req = null;
            dam_res =  null;
            lv_temp = null;
            xpath = null;
        }
        logger.info("Exited");
        return map_reqInfo;
    }


    private String uploadFileOnS3(String keyName,String uploadFileContentStr,String awsRegionName,String appType)
    {
        logger.info("Entered");
        String result = "";
        String bucketName = "";
        LocalDateTime start = null;
        LocalDateTime end = null;
        Duration duration = null;
        InputStream fileInputStream  = null;
        ObjectMetadata objectMetadata = null;

        try
        {
            bucketName = AwsUtil.getBucketName(awsRegionName);
            fileInputStream = new ByteArrayInputStream(uploadFileContentStr.getBytes());
            objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType("application/xml");
            objectMetadata.setContentLength(uploadFileContentStr.getBytes().length);
            start = LocalDateTime.now();
            //==========================================================================================================================
            //Store the xml to AWS Bucket
            AwsUtil.getS3Client(awsRegionName).putObject(new PutObjectRequest(bucketName, keyName, fileInputStream,objectMetadata));
            //==========================================================================================================================
            end = LocalDateTime.now();
            duration = Duration.between(end, start);
            result = createBucketXml(bucketName,keyName,duration.getNano()+"",appType);
            //System.out.println( result);
            logger.info(result);
        }
        catch(Exception e)
        {
            e.printStackTrace( );
            //System.out.println( "<error>" +e.getMessage()+"</error>" );
            logger.error("<error>" +e.getMessage()+"</error>");
            //result = "";
        }
        finally
        {
            bucketName = null;
            start = null;
            end = null;
            duration = null;
            fileInputStream  = null;
            objectMetadata = null;
        }
        logger.info("Exited");
        return result;
    }

    @Override
    public OMElement writeS3OnBucket(Map<String,Object> map_applicationInfo)
    {
        logger.info("Entered");
        OMElement om_resInfo = null;
        Integer startCount = 0;
        Integer endCount = 0;
        String lv_temp ="";
        String lv_bucketName ="";
        String lv_bucket_prefix = "";
        String lv_s3BucketKey = "";
        String lv_awsRegionName = "";
        String totalResponse = "<response>";

        try
        {
            lv_temp = map_applicationInfo.get("startCount").toString();
            startCount = Integer.parseInt(lv_temp);

            lv_temp = map_applicationInfo.get("endCount").toString();
            endCount = Integer.parseInt(lv_temp);

            lv_bucket_prefix = (String)map_applicationInfo.get("buckerPrefix");
            lv_s3BucketKey = (String)map_applicationInfo.get("s3BucketKey");
            lv_awsRegionName = (String)map_applicationInfo.get("awsRegionName");


            for(int i=startCount; i <= endCount; i++)
            {
                totalResponse += "<applicant>";
                if(lv_bucket_prefix.equalsIgnoreCase("M"))
                {
                    if(null != map_applicationInfo.get("apm_req"))
                    {
                        lv_temp = null;
                        lv_temp= lv_s3BucketKey  +  "APM/req_" + i;
                        totalResponse += uploadFileOnS3(lv_temp,map_applicationInfo.get("apm_req").toString(),lv_awsRegionName,"apmReq");
                    }
                    if(null != map_applicationInfo.get("apm_res"))
                    {
                        lv_temp = null;
                        lv_temp =lv_s3BucketKey  +  "APM/res_" + i;
                        totalResponse += uploadFileOnS3(lv_temp,map_applicationInfo.get("apm_res").toString(),lv_awsRegionName,"apmRes");
                    }
                    if(null != map_applicationInfo.get("dm_req"))
                    {
                        lv_temp = null;
                        lv_temp =lv_s3BucketKey  + "DM/req_" + i;
                        totalResponse += uploadFileOnS3(lv_temp,map_applicationInfo.get("dm_req").toString(),lv_awsRegionName,"dmReq");
                    }
                    if(null != map_applicationInfo.get("dm_res"))
                    {
                        lv_temp = null;
                        lv_temp =lv_s3BucketKey  + "DM/res_" + i;
                        totalResponse += uploadFileOnS3(lv_temp,map_applicationInfo.get("dm_res").toString(),lv_awsRegionName,"dmRes");
                    }
                    if(null != map_applicationInfo.get("dam_req"))
                    {
                        lv_temp = null;
                        lv_temp =lv_s3BucketKey  + "DAM/req_" + i;
                        totalResponse += uploadFileOnS3(lv_temp,map_applicationInfo.get("dam_req").toString(),lv_awsRegionName,"damReq");
                    }
                    if(null != map_applicationInfo.get("dam_res"))
                    {
                        lv_temp = null;
                        lv_temp =lv_s3BucketKey  + "DAM/res_" + i;
                        totalResponse += uploadFileOnS3(lv_temp,map_applicationInfo.get("dam_res").toString(),lv_awsRegionName,"damRes");
                    }

                }
                else
                {
                    if(null != map_applicationInfo.get("apm_req"))
                    {
                        lv_temp = null;
                        lv_temp =lv_s3BucketKey  + "APM_req_" + i;
                        totalResponse += uploadFileOnS3(lv_temp,map_applicationInfo.get("apm_req").toString(),lv_awsRegionName,"apmReq");
                    }
                    if(null != map_applicationInfo.get("apm_res"))
                    {
                        lv_temp = null;
                        lv_temp =lv_s3BucketKey  + "APM_res_" + i;
                        totalResponse += uploadFileOnS3(lv_temp,map_applicationInfo.get("apm_res").toString(),lv_awsRegionName,"apmRes");
                    }
                    if(null != map_applicationInfo.get("dm_req"))
                    {
                        lv_temp = null;
                        lv_temp =lv_s3BucketKey  + "DM_req_" + i;
                        totalResponse += uploadFileOnS3(lv_temp,map_applicationInfo.get("dm_req").toString(),lv_awsRegionName,"dmReq");
                    }
                    if(null != map_applicationInfo.get("dm_res"))
                    {
                        lv_temp = null;
                        lv_temp =lv_s3BucketKey  + "DM_res_" + i;
                        totalResponse += uploadFileOnS3(lv_temp,map_applicationInfo.get("dm_res").toString(),lv_awsRegionName,"dmRes");
                    }
                    if(null != map_applicationInfo.get("dam_req"))
                    {
                        lv_temp = null;
                        lv_temp =lv_s3BucketKey  + "DAM_req_" + i;
                        totalResponse += uploadFileOnS3(lv_temp,map_applicationInfo.get("dam_req").toString(),lv_awsRegionName,"damReq");
                    }
                    if(null != map_applicationInfo.get("dam_res"))
                    {
                        lv_temp = null;
                        lv_temp =lv_s3BucketKey  + "DAM_res_" + i;
                        totalResponse += uploadFileOnS3(lv_temp,map_applicationInfo.get("dam_res").toString(),lv_awsRegionName,"damRes");
                    }


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
            totalResponse = null;
        }
        logger.info("Exited");
        return om_resInfo;
    }

    @Override
    public String createBucketXml(String bucketName,String keyName,String timeTaken,String appType)
    {
        return String.format("<bucket><bucketName>%s</bucketName><keyName>%s</keyName><appType>%s</appType>" +
                        "<timeTaken>%s</timeTaken></bucket>",bucketName,keyName,appType,timeTaken);
    }


}
