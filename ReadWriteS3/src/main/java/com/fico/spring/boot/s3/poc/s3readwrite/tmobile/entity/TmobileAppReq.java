package com.fico.spring.boot.s3.poc.s3readwrite.tmobile.entity;

import javax.xml.bind.annotation.XmlRootElement;

import com.fico.spring.boot.s3.poc.s3readwrite.tmobile.util.FileUtil;



@XmlRootElement(name="ApplicantRequest")
public class TmobileAppReq
{
    private String clientCode = "";
    private String tenantName = "";
    private String buckerPrefix = "";
    private Integer startCount =0;
    private Integer endCount =0;
    private String applicantName ="";
    private String birthDate = "";
    private String maritalStatus = "";
    private String identificationCode = "";
    private String address = "";
    private String submittedDate = "";
    private String dm_req = "";
    private String dm_res = "";
    private String dam_req = "";
    private String dam_res = "";
    private String apm_req = "";
    private String apm_res = "";

    public String getClientCode() {
        return clientCode;
    }
    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getTenantName() {
        return tenantName;
    }
    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getBuckerPrefix() {
        return buckerPrefix;
    }

    public void setBuckerPrefix(String buckerPrefix) {
        this.buckerPrefix = buckerPrefix;
    }
    public Integer getStartCount() {
        return startCount;
    }

    public void setStartCount(Integer startCount) {
        this.startCount = startCount;
    }
    public Integer getEndCount() {
        return endCount;
    }

    public void setEndCount(Integer endCount) {
        this.endCount = endCount;
    }
    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getIdentificationCode() {
        return identificationCode;
    }

    public void setIdentificationCode(String identificationCode) {
        this.identificationCode = identificationCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(String submittedDate) {
        this.submittedDate = submittedDate;
    }

    public String getDm_req() {
        return dm_req;
    }

    public void setDm_req(String dm_req) {
        this.dm_req = dm_req;
    }

    public String getDm_res() {
        return dm_res;
    }

    public void setDm_res(String dm_res) {
        this.dm_res = dm_res;
    }

    public String getDam_req() {
        return dam_req;
    }

    public void setDam_req(String dam_req) {
        this.dam_req = dam_req;
    }

    public String getDam_res() {
        return dam_res;
    }

    public void setDam_res(String dam_res) {
        this.dam_res = dam_res;
    }

    public String getApm_req() {
        return apm_req;
    }

    public void setApm_req(String apm_req) {
        this.apm_req = apm_req;
    }

    public String getApm_res() {
        return apm_res;
    }

    public void setApm_res(String apm_res) {
        this.apm_res = apm_res;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<applicantRequest>");
        sb.append("<clientCode>" + this.getClientCode() + "</clientCode>");
        sb.append("<tenantName>" + this.getTenantName() + "</tenantName>");
        sb.append("<buckerPrefix>" + this.getBuckerPrefix() + "</buckerPrefix>");
        sb.append("<startCount>" + this.getStartCount() + "</startCount>");
        sb.append("<endCount>" + this.getEndCount() + "</endCount>");
        sb.append("<applicantName>" + this.getApplicantName() + "</applicantName>");
        sb.append("<birthDate>" + this.getBirthDate() + "</birthDate>");
        sb.append("<maritalStatus>" + this.getMaritalStatus() + "</maritalStatus>");
        sb.append("<identificationCode>" + this.getIdentificationCode() + "</identificationCode>");
        sb.append("<address>" + this.getAddress() + "</address>");
        sb.append("<submittedDate>" + this.getSubmittedDate() + "</submittedDate>");


        if(this.getApm_req().length() > 5)
            sb.append("<apm_req>" + this.getApm_req() + "</apm_req>");
        if(this.getApm_res().length() > 5)
            sb.append("<apm_res>" + this.getApm_res() + "</apm_res>");


        if(this.getDm_req().length() > 5)
            sb.append("<dm_req>" + this.getDm_req() + "</dm_req>");
        if(this.getDm_res().length() > 5)
            sb.append("<dm_res>" + this.getDm_res() + "</dm_res>");



        if(this.getDam_req().length() > 5)
            sb.append("<dam_req>" + this.getDam_req() + "</dam_req>");
        if(this.getDam_res().length() > 5)
            sb.append("<dam_res>" + this.getDam_res() + "</dam_res>");


        sb.append("</applicantRequest>");
        return sb.toString();

    }

    public static void main(String[] args)
    {
        String apm_req = FileUtil.getFileContent("C:/04_Dev_Workspace/Intellj_Workspace/SpringS3Storage/s3readwrite/src/test/resources/xmlFiles/T_Mobile/APM/apm_req.xml");
        String apm_res = FileUtil.getFileContent("C:/04_Dev_Workspace/Intellj_Workspace/SpringS3Storage/s3readwrite/src/test/resources/xmlFiles/T_Mobile/APM/apm_res.xml");

        String dm_req = FileUtil.getFileContent("C:/04_Dev_Workspace/Intellj_Workspace/SpringS3Storage/s3readwrite/src/test/resources/xmlFiles/T_Mobile/DM/dm_req.xml");
        String dm_res = FileUtil.getFileContent("C:/04_Dev_Workspace/Intellj_Workspace/SpringS3Storage/s3readwrite/src/test/resources/xmlFiles/T_Mobile/DM/dm_res.xml");

        String dam_req = FileUtil.getFileContent("C:/04_Dev_Workspace/Intellj_Workspace/SpringS3Storage/s3readwrite/src/test/resources/xmlFiles/T_Mobile/DAM/dam_req.xml");
        String dam_res = FileUtil.getFileContent("C:/04_Dev_Workspace/Intellj_Workspace/SpringS3Storage/s3readwrite/src/test/resources/xmlFiles/T_Mobile/DAM/dam_res.xml");

        TmobileAppReq tmAppReq = new TmobileAppReq();


        tmAppReq.setClientCode("TM");//T-Mobile
        tmAppReq.setTenantName("IND");
        tmAppReq.setBuckerPrefix("M");
        tmAppReq.setStartCount(1);
        tmAppReq.setEndCount(2);
        tmAppReq.setApplicantName("Shakti Panda");
        tmAppReq.setBirthDate("21/07/1990");
        tmAppReq.setMaritalStatus("Married");
        tmAppReq.setIdentificationCode("Passport");
        tmAppReq.setAddress("Kodihaki,Bangalore");
        tmAppReq.setSubmittedDate("24/02/2018");

        tmAppReq.setApm_req(apm_req);
        tmAppReq.setApm_res(apm_res);

        tmAppReq.setDm_req(dm_req);
        tmAppReq.setDm_res(dm_res);

        tmAppReq.setDam_req(dam_req);
        tmAppReq.setDam_res(dam_res);

        System.out.println(tmAppReq);

    }
}//end of Class TmobileAppReq
