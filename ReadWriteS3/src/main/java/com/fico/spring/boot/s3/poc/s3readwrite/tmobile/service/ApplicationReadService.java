package com.fico.spring.boot.s3.poc.s3readwrite.tmobile.service;


import org.apache.axiom.om.OMElement;
import java.util.Map;


public interface ApplicationReadService
{


    public String createBucketXml(String bucketName,String keyName,String timeTaken,String appType, String bucketContent);

    public Map<String,Object> appReadS3Service(OMElement om_readReq);

    public OMElement processReadOperationService(Map<String,Object> map_applicationInfo);

    public String readS3OnBucket(String bucketName,String bucketKey,String isDispBucketContent, String appType, String bucketContent);
}
