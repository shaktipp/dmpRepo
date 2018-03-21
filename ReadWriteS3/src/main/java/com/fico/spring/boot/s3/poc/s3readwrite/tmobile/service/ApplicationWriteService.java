package com.fico.spring.boot.s3.poc.s3readwrite.tmobile.service;

import org.apache.axiom.om.OMElement;

import java.util.Map;

public interface ApplicationWriteService
{
    public Map<String,Object> appSubmitService(OMElement om_request);

    public OMElement writeS3OnBucket(Map<String,Object> map_applicationInfo);

    public String createBucketXml(String bucketName,String keyName,String timeTaken,String appType);

}//End of interface ApplicationWriteService
