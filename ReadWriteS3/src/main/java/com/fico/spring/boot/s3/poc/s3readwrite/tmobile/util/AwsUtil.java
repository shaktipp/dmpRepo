package com.fico.spring.boot.s3.poc.s3readwrite.tmobile.util;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

public class AwsUtil
{
    private static AmazonS3 s3client_usa = null; //For USA
    private static AmazonS3 s3client_sin = null; //For Singapore
    private static AmazonS3 s3client_mum = null; //For Mumbai

    public static AmazonS3 getS3Client(String awsRegionName)
    {

        if(awsRegionName.equalsIgnoreCase("usa"))
        {
            if(null == s3client_usa)
            {
                AWSCredentials credentials = new BasicAWSCredentials( "AKIAJCFFAW264XPBNEJQ", "SLYAY4uVZFJIFqpFVPK3TjvFXACcGxOGImlWag/U" );
                s3client_usa = new AmazonS3Client( credentials );
                s3client_usa.setRegion(Region.getRegion(Regions.US_WEST_2));
            }
            return s3client_usa;
        }
        else if(awsRegionName.equalsIgnoreCase("sin"))
        {
            if(null == s3client_sin)
            {
                AWSCredentials credentials = new BasicAWSCredentials( "AKIAJCFFAW264XPBNEJQ", "SLYAY4uVZFJIFqpFVPK3TjvFXACcGxOGImlWag/U" );
                s3client_sin = new AmazonS3Client( credentials );
                s3client_sin.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_1));
            }
            return s3client_sin;
        }
        if(null == s3client_mum)
        {
            AWSCredentials credentials = new BasicAWSCredentials( "AKIAJCFFAW264XPBNEJQ", "SLYAY4uVZFJIFqpFVPK3TjvFXACcGxOGImlWag/U " );
            s3client_mum = new AmazonS3Client( credentials );
            s3client_mum.setRegion(Region.getRegion(Regions.AP_SOUTH_1));
        }
        return s3client_mum;
    }//End of getS3Client

    public static String getBucketName(String awsRegionName)
    {
        if(awsRegionName.equalsIgnoreCase("usa"))
            return "om-dmp30-poc"; //For USA
        else if(awsRegionName.equalsIgnoreCase("sin"))
            return "om-dmp30-poc-asia-pacfic"; //For Singapore
        else
            return "aws-logs-478731584683-ap-south-1"; //For Mumbai
    }
    
}//End of class AwsUtil
