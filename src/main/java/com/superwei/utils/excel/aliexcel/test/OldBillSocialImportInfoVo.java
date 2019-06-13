package com.superwei.utils.excel.aliexcel.test;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

/**
 *
 * @Description: 导入老账单导入实体.
 *
 * @params: 
 * @return: 
 * @auther: weidongge
 * @date: 2019/5/16 9:26
 */
public class OldBillSocialImportInfoVo extends BaseRowModel {
    @ExcelProperty(index = 1)
    private String personName;
    @ExcelProperty(index = 2)
    private String personIdCard;
    @ExcelProperty(index = 4)
    private String insuredIdentityName;
    @ExcelProperty(index = 5)
    private String billTime;
    @ExcelProperty(index = 6)
    private String importType;
    @ExcelProperty(index = 7)
    private String socialPayWay;
    @ExcelProperty(index = 8)
    private String payWay;
    @ExcelProperty(index = 9)
    private String oldCompanyBase;
    @ExcelProperty(index = 10)
    private String oldCompanyRate;
    @ExcelProperty(index = 11)
    private String oldCompanyCost;
    @ExcelProperty(index = 12)
    private String oldPersonRate;
    @ExcelProperty(index = 13)
    private String oldPersonCost;
    @ExcelProperty(index = 14)
    private String oldTotalCost;
    @ExcelProperty(index = 15)
    private String loseCompanyBase;
    @ExcelProperty(index = 16)
    private String loseCompanyRate;
    @ExcelProperty(index = 17)
    private String loseCompanyCost;
    @ExcelProperty(index = 18)
    private String losePersonRate;
    @ExcelProperty(index = 19)
    private String losePersonCost;
    @ExcelProperty(index = 20)
    private String loseTotalCost;
    @ExcelProperty(index = 21)
    private String injureCompanyBase;
    @ExcelProperty(index = 22)
    private String injureCompanyRate;
    @ExcelProperty(index = 23)
    private String injureCompanyCost;
    @ExcelProperty(index = 24)
    private String birthCompanyBase;
    @ExcelProperty(index = 25)
    private String birthCompanyRate;
    @ExcelProperty(index = 26)
    private String birthCompanyCost;
    @ExcelProperty(index = 27)
    private String basicMedicalCompanyBase;
    @ExcelProperty(index = 28)
    private String basicMedicalCompanyRate;
    @ExcelProperty(index = 29)
    private String basicMedicalCompanyCost;
    @ExcelProperty(index = 30)
    private String basicMedicalPersonRate;
    @ExcelProperty(index = 31)
    private String basicMedicalPersonCost;
    @ExcelProperty(index = 32)
    private String basicMedicalTotalCost;
    @ExcelProperty(index = 33)
    private String largeMedicalCompanyBase;
    @ExcelProperty(index = 34)
    private String largeMedicalCompanyRate;
    @ExcelProperty(index = 35)
    private String largeMedicalCompanyCost;
    @ExcelProperty(index = 36)
    private String largeMedicalPersonRate;
    @ExcelProperty(index = 37)
    private String largeMedicalPersonCost;
    @ExcelProperty(index = 38)
    private String largeMedicalTotalCost;
    @ExcelProperty(index = 39)
    private String fiveCompanyCost;
    @ExcelProperty(index = 40)
    private String fivePersonCost;
    @ExcelProperty(index = 41)
    private String fiveAllCost;
    @ExcelProperty(index = 42)
    private String fundInsuredArea;
    @ExcelProperty(index = 43)
    private String fundPayWay;
    @ExcelProperty(index = 44)
    private String fundCompanyBase;
    @ExcelProperty(index = 45)
    private String fundCompanyRate;
    @ExcelProperty(index = 46)
    private String fundCompanyCost;
    @ExcelProperty(index = 47)
    private String fundPersonRate;
    @ExcelProperty(index = 48)
    private String fundPersonCost;
    @ExcelProperty(index = 49)
    private String fundTotalCost;
    @ExcelProperty(index = 50)
    private String fiveFundCompanyCost;
    @ExcelProperty(index = 51)
    private String fiveFundPersonCost;
    @ExcelProperty(index = 52)
    private String fiveFundAllCost;
    @ExcelProperty(index = 53)
    private String overDueCost;
    @ExcelProperty(index = 54)
    private String disableCost;
    @ExcelProperty(index = 55)
    private String otherCost;
    @ExcelProperty(index = 56)
    private String otherRemark;
    @ExcelProperty(index = 57)
    private String companyAllCost;
    @ExcelProperty(index = 58)
    private String personAllCost;
    @ExcelProperty(index = 59)
    private String allCost;
    @ExcelProperty(index = 60)
    private String serviceCost;
    @ExcelProperty(index = 61)
    private String remark;


    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonIdCard() {
        return personIdCard;
    }

    public void setPersonIdCard(String personIdCard) {
        this.personIdCard = personIdCard;
    }

    public String getInsuredIdentityName() {
        return insuredIdentityName;
    }

    public void setInsuredIdentityName(String insuredIdentityName) {
        this.insuredIdentityName = insuredIdentityName;
    }

    public String getBillTime() {
        return billTime;
    }

    public void setBillTime(String billTime) {
        this.billTime = billTime;
    }

    public String getImportType() {
        return importType;
    }

    public void setImportType(String importType) {
        this.importType = importType;
    }

    public String getSocialPayWay() {
        return socialPayWay;
    }

    public void setSocialPayWay(String socialPayWay) {
        this.socialPayWay = socialPayWay;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getOldCompanyBase() {
        return oldCompanyBase;
    }

    public void setOldCompanyBase(String oldCompanyBase) {
        this.oldCompanyBase = oldCompanyBase;
    }

    public String getOldCompanyRate() {
        return oldCompanyRate;
    }

    public void setOldCompanyRate(String oldCompanyRate) {
        this.oldCompanyRate = oldCompanyRate;
    }

    public String getOldCompanyCost() {
        return oldCompanyCost;
    }

    public void setOldCompanyCost(String oldCompanyCost) {
        this.oldCompanyCost = oldCompanyCost;
    }

    public String getOldPersonRate() {
        return oldPersonRate;
    }

    public void setOldPersonRate(String oldPersonRate) {
        this.oldPersonRate = oldPersonRate;
    }

    public String getOldPersonCost() {
        return oldPersonCost;
    }

    public void setOldPersonCost(String oldPersonCost) {
        this.oldPersonCost = oldPersonCost;
    }

    public String getOldTotalCost() {
        return oldTotalCost;
    }

    public void setOldTotalCost(String oldTotalCost) {
        this.oldTotalCost = oldTotalCost;
    }

    public String getLoseCompanyBase() {
        return loseCompanyBase;
    }

    public void setLoseCompanyBase(String loseCompanyBase) {
        this.loseCompanyBase = loseCompanyBase;
    }

    public String getLoseCompanyRate() {
        return loseCompanyRate;
    }

    public void setLoseCompanyRate(String loseCompanyRate) {
        this.loseCompanyRate = loseCompanyRate;
    }

    public String getLoseCompanyCost() {
        return loseCompanyCost;
    }

    public void setLoseCompanyCost(String loseCompanyCost) {
        this.loseCompanyCost = loseCompanyCost;
    }

    public String getLosePersonRate() {
        return losePersonRate;
    }

    public void setLosePersonRate(String losePersonRate) {
        this.losePersonRate = losePersonRate;
    }

    public String getLosePersonCost() {
        return losePersonCost;
    }

    public void setLosePersonCost(String losePersonCost) {
        this.losePersonCost = losePersonCost;
    }

    public String getLoseTotalCost() {
        return loseTotalCost;
    }

    public void setLoseTotalCost(String loseTotalCost) {
        this.loseTotalCost = loseTotalCost;
    }

    public String getInjureCompanyBase() {
        return injureCompanyBase;
    }

    public void setInjureCompanyBase(String injureCompanyBase) {
        this.injureCompanyBase = injureCompanyBase;
    }

    public String getInjureCompanyRate() {
        return injureCompanyRate;
    }

    public void setInjureCompanyRate(String injureCompanyRate) {
        this.injureCompanyRate = injureCompanyRate;
    }

    public String getInjureCompanyCost() {
        return injureCompanyCost;
    }

    public void setInjureCompanyCost(String injureCompanyCost) {
        this.injureCompanyCost = injureCompanyCost;
    }

    public String getBirthCompanyBase() {
        return birthCompanyBase;
    }

    public void setBirthCompanyBase(String birthCompanyBase) {
        this.birthCompanyBase = birthCompanyBase;
    }

    public String getBirthCompanyRate() {
        return birthCompanyRate;
    }

    public void setBirthCompanyRate(String birthCompanyRate) {
        this.birthCompanyRate = birthCompanyRate;
    }

    public String getBirthCompanyCost() {
        return birthCompanyCost;
    }

    public void setBirthCompanyCost(String birthCompanyCost) {
        this.birthCompanyCost = birthCompanyCost;
    }

    public String getBasicMedicalCompanyBase() {
        return basicMedicalCompanyBase;
    }

    public void setBasicMedicalCompanyBase(String basicMedicalCompanyBase) {
        this.basicMedicalCompanyBase = basicMedicalCompanyBase;
    }

    public String getBasicMedicalCompanyRate() {
        return basicMedicalCompanyRate;
    }

    public void setBasicMedicalCompanyRate(String basicMedicalCompanyRate) {
        this.basicMedicalCompanyRate = basicMedicalCompanyRate;
    }

    public String getBasicMedicalCompanyCost() {
        return basicMedicalCompanyCost;
    }

    public void setBasicMedicalCompanyCost(String basicMedicalCompanyCost) {
        this.basicMedicalCompanyCost = basicMedicalCompanyCost;
    }

    public String getBasicMedicalPersonRate() {
        return basicMedicalPersonRate;
    }

    public void setBasicMedicalPersonRate(String basicMedicalPersonRate) {
        this.basicMedicalPersonRate = basicMedicalPersonRate;
    }

    public String getBasicMedicalPersonCost() {
        return basicMedicalPersonCost;
    }

    public void setBasicMedicalPersonCost(String basicMedicalPersonCost) {
        this.basicMedicalPersonCost = basicMedicalPersonCost;
    }

    public String getBasicMedicalTotalCost() {
        return basicMedicalTotalCost;
    }

    public void setBasicMedicalTotalCost(String basicMedicalTotalCost) {
        this.basicMedicalTotalCost = basicMedicalTotalCost;
    }

    public String getLargeMedicalCompanyBase() {
        return largeMedicalCompanyBase;
    }

    public void setLargeMedicalCompanyBase(String largeMedicalCompanyBase) {
        this.largeMedicalCompanyBase = largeMedicalCompanyBase;
    }

    public String getLargeMedicalCompanyRate() {
        return largeMedicalCompanyRate;
    }

    public void setLargeMedicalCompanyRate(String largeMedicalCompanyRate) {
        this.largeMedicalCompanyRate = largeMedicalCompanyRate;
    }

    public String getLargeMedicalCompanyCost() {
        return largeMedicalCompanyCost;
    }

    public void setLargeMedicalCompanyCost(String largeMedicalCompanyCost) {
        this.largeMedicalCompanyCost = largeMedicalCompanyCost;
    }

    public String getLargeMedicalPersonRate() {
        return largeMedicalPersonRate;
    }

    public void setLargeMedicalPersonRate(String largeMedicalPersonRate) {
        this.largeMedicalPersonRate = largeMedicalPersonRate;
    }

    public String getLargeMedicalPersonCost() {
        return largeMedicalPersonCost;
    }

    public void setLargeMedicalPersonCost(String largeMedicalPersonCost) {
        this.largeMedicalPersonCost = largeMedicalPersonCost;
    }

    public String getLargeMedicalTotalCost() {
        return largeMedicalTotalCost;
    }

    public void setLargeMedicalTotalCost(String largeMedicalTotalCost) {
        this.largeMedicalTotalCost = largeMedicalTotalCost;
    }

    public String getFiveCompanyCost() {
        return fiveCompanyCost;
    }

    public void setFiveCompanyCost(String fiveCompanyCost) {
        this.fiveCompanyCost = fiveCompanyCost;
    }

    public String getFivePersonCost() {
        return fivePersonCost;
    }

    public void setFivePersonCost(String fivePersonCost) {
        this.fivePersonCost = fivePersonCost;
    }

    public String getFiveAllCost() {
        return fiveAllCost;
    }

    public void setFiveAllCost(String fiveAllCost) {
        this.fiveAllCost = fiveAllCost;
    }

    public String getFundInsuredArea() {
        return fundInsuredArea;
    }

    public void setFundInsuredArea(String fundInsuredArea) {
        this.fundInsuredArea = fundInsuredArea;
    }

    public String getFundPayWay() {
        return fundPayWay;
    }

    public void setFundPayWay(String fundPayWay) {
        this.fundPayWay = fundPayWay;
    }

    public String getFundCompanyBase() {
        return fundCompanyBase;
    }

    public void setFundCompanyBase(String fundCompanyBase) {
        this.fundCompanyBase = fundCompanyBase;
    }

    public String getFundCompanyRate() {
        return fundCompanyRate;
    }

    public void setFundCompanyRate(String fundCompanyRate) {
        this.fundCompanyRate = fundCompanyRate;
    }

    public String getFundCompanyCost() {
        return fundCompanyCost;
    }

    public void setFundCompanyCost(String fundCompanyCost) {
        this.fundCompanyCost = fundCompanyCost;
    }

    public String getFundPersonRate() {
        return fundPersonRate;
    }

    public void setFundPersonRate(String fundPersonRate) {
        this.fundPersonRate = fundPersonRate;
    }

    public String getFundPersonCost() {
        return fundPersonCost;
    }

    public void setFundPersonCost(String fundPersonCost) {
        this.fundPersonCost = fundPersonCost;
    }

    public String getFundTotalCost() {
        return fundTotalCost;
    }

    public void setFundTotalCost(String fundTotalCost) {
        this.fundTotalCost = fundTotalCost;
    }

    public String getFiveFundCompanyCost() {
        return fiveFundCompanyCost;
    }

    public void setFiveFundCompanyCost(String fiveFundCompanyCost) {
        this.fiveFundCompanyCost = fiveFundCompanyCost;
    }

    public String getFiveFundPersonCost() {
        return fiveFundPersonCost;
    }

    public void setFiveFundPersonCost(String fiveFundPersonCost) {
        this.fiveFundPersonCost = fiveFundPersonCost;
    }

    public String getFiveFundAllCost() {
        return fiveFundAllCost;
    }

    public void setFiveFundAllCost(String fiveFundAllCost) {
        this.fiveFundAllCost = fiveFundAllCost;
    }

    public String getOverDueCost() {
        return overDueCost;
    }

    public void setOverDueCost(String overDueCost) {
        this.overDueCost = overDueCost;
    }

    public String getDisableCost() {
        return disableCost;
    }

    public void setDisableCost(String disableCost) {
        this.disableCost = disableCost;
    }

    public String getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(String otherCost) {
        this.otherCost = otherCost;
    }

    public String getOtherRemark() {
        return otherRemark;
    }

    public void setOtherRemark(String otherRemark) {
        this.otherRemark = otherRemark;
    }

    public String getCompanyAllCost() {
        return companyAllCost;
    }

    public void setCompanyAllCost(String companyAllCost) {
        this.companyAllCost = companyAllCost;
    }

    public String getPersonAllCost() {
        return personAllCost;
    }

    public void setPersonAllCost(String personAllCost) {
        this.personAllCost = personAllCost;
    }

    public String getAllCost() {
        return allCost;
    }

    public void setAllCost(String allCost) {
        this.allCost = allCost;
    }

    public String getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(String serviceCost) {
        this.serviceCost = serviceCost;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "OldBillSocialImportInfoVo{" +
                "personName='" + personName + '\'' +
                ", personIdCard='" + personIdCard + '\'' +
                ", insuredIdentityName='" + insuredIdentityName + '\'' +
                ", billTime='" + billTime + '\'' +
                ", importType='" + importType + '\'' +
                ", socialPayWay='" + socialPayWay + '\'' +
                ", payWay='" + payWay + '\'' +
                ", oldCompanyBase=" + oldCompanyBase +
                ", oldCompanyRate=" + oldCompanyRate +
                ", oldCompanyCost=" + oldCompanyCost +
                ", oldPersonRate=" + oldPersonRate +
                ", oldPersonCost=" + oldPersonCost +
                ", oldTotalCost=" + oldTotalCost +
                ", loseCompanyBase=" + loseCompanyBase +
                ", loseCompanyRate=" + loseCompanyRate +
                ", loseCompanyCost=" + loseCompanyCost +
                ", losePersonRate=" + losePersonRate +
                ", losePersonCost=" + losePersonCost +
                ", loseTotalCost=" + loseTotalCost +
                ", injureCompanyBase=" + injureCompanyBase +
                ", injureCompanyRate=" + injureCompanyRate +
                ", injureCompanyCost=" + injureCompanyCost +
                ", birthCompanyBase=" + birthCompanyBase +
                ", birthCompanyRate=" + birthCompanyRate +
                ", birthCompanyCost=" + birthCompanyCost +
                ", basicMedicalCompanyBase=" + basicMedicalCompanyBase +
                ", basicMedicalCompanyRate=" + basicMedicalCompanyRate +
                ", basicMedicalCompanyCost=" + basicMedicalCompanyCost +
                ", basicMedicalPersonRate=" + basicMedicalPersonRate +
                ", basicMedicalPersonCost=" + basicMedicalPersonCost +
                ", basicMedicalTotalCost=" + basicMedicalTotalCost +
                ", largeMedicalCompanyBase=" + largeMedicalCompanyBase +
                ", largeMedicalCompanyRate=" + largeMedicalCompanyRate +
                ", largeMedicalCompanyCost=" + largeMedicalCompanyCost +
                ", largeMedicalPersonRate=" + largeMedicalPersonRate +
                ", largeMedicalPersonCost=" + largeMedicalPersonCost +
                ", largeMedicalTotalCost=" + largeMedicalTotalCost +
                ", fiveCompanyCost='" + fiveCompanyCost + '\'' +
                ", fivePersonCost='" + fivePersonCost + '\'' +
                ", fiveAllCost='" + fiveAllCost + '\'' +
                ", fundInsuredArea='" + fundInsuredArea + '\'' +
                ", fundPayWay='" + fundPayWay + '\'' +
                ", fundCompanyBase=" + fundCompanyBase +
                ", fundCompanyRate=" + fundCompanyRate +
                ", fundCompanyCost=" + fundCompanyCost +
                ", fundPersonRate=" + fundPersonRate +
                ", fundPersonCost=" + fundPersonCost +
                ", fundTotalCost=" + fundTotalCost +
                ", fiveFundCompanyCost='" + fiveFundCompanyCost + '\'' +
                ", fiveFundPersonCost='" + fiveFundPersonCost + '\'' +
                ", fiveFundAllCost='" + fiveFundAllCost + '\'' +
                ", overDueCost=" + overDueCost +
                ", disableCost=" + disableCost +
                ", otherCost=" + otherCost +
                ", otherRemark='" + otherRemark + '\'' +
                ", companyAllCost=" + companyAllCost +
                ", personAllCost=" + personAllCost +
                ", allCost=" + allCost +
                ", serviceCost=" + serviceCost +
                ", remark='" + remark + '\'' +
                '}';
    }
}
