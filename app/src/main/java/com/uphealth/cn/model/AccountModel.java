package com.uphealth.cn.model;

/**
 * @description 用户信息 
 * @data 2016年6月13日

 * @author jun.wang
 */
public class AccountModel{
	
    private String birthday ;
	
	private String sex ;
	
	private String accountId ;
	
	private String weight ;
	
	private String skinType ;
	
	private String remarks ;
	
	private String sportLevel ;
	
	// 祛痘指数
	private int skinAcne ;
	
	// 美白指数
	private int skinWhite ;
	
	// 补水指数
	private int skinWater ;
	
	// 抗衰老指数
	private int skinAntiAging ;
	
	private int cellulose ;  // 当前纤维素总量
	
	private String area ;
	
	private String height ;
	
	private String nickName ;
	
	private int protein ;      // 当前蛋白质总量
	
	private float BMI ;
	
	private String faceType ;
	
	private int vitamin ;     // 当前维生素总量
	
	private int minerals ;   // 当前矿物质总量
	
	private String pitUrl ;
	
	private String makeupStyle ;
	
	private int testBodyType ;
	
	private int testBodyType2 ;
	
	private int testBodyType3 ;
	
	private int testBodyType4 ;
	
	private int testBodyType5 ;
	
	private int testBodyType6 ;
	
	private int testBodyType7 ;
	
	private int testBodyType8 ;
	
	private int testBodyType9 ;
	
	// 我的标签
	private String healthTagNames ;
	
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSkinType() {
		return skinType;
	}

	public void setSkinType(String skinType) {
		this.skinType = skinType;
	}

	public String getSportLevel() {
		return sportLevel;
	}

	public void setSportLevel(String sportLevel) {
		this.sportLevel = sportLevel;
	}

	public int getSkinAcne() {
		return skinAcne;
	}

	public void setSkinAcne(int skinAcne) {
		this.skinAcne = skinAcne;
	}

	public int getSkinWhite() {
		return skinWhite;
	}

	public void setSkinWhite(int skinWhite) {
		this.skinWhite = skinWhite;
	}

	public int getSkinWater() {
		return skinWater;
	}

	public void setSkinWater(int skinWater) {
		this.skinWater = skinWater;
	}

	public int getSkinAntiAging() {
		return skinAntiAging;
	}

	public void setSkinAntiAging(int skinAntiAging) {
		this.skinAntiAging = skinAntiAging;
	}

	public float getBMI() {
		return BMI;
	}

	public void setBMI(float bMI) {
		BMI = bMI;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPitUrl() {
		return pitUrl;
	}

	public void setPitUrl(String pitUrl) {
		this.pitUrl = pitUrl;
	}
	
	public int getProtein() {
		return protein;
	}

	public void setProtein(int protein) {
		this.protein = protein;
	}

	public String getFaceType() {
		return faceType;
	}

	public void setFaceType(String faceType) {
		this.faceType = faceType;
	}

	public int getVitamin() {
		return vitamin;
	}

	public void setVitamin(int vitamin) {
		this.vitamin = vitamin;
	}

	public int getMinerals() {
		return minerals;
	}

	public void setMinerals(int minerals) {
		this.minerals = minerals;
	}

	public String getMakeupStyle() {
		return makeupStyle;
	}

	public void setMakeupStyle(String makeupStyle) {
		this.makeupStyle = makeupStyle;
	}

	public int getTestBodyType() {
		return testBodyType;
	}

	public void setTestBodyType(int testBodyType) {
		this.testBodyType = testBodyType;
	}

	public int getTestBodyType2() {
		return testBodyType2;
	}

	public void setTestBodyType2(int testBodyType2) {
		this.testBodyType2 = testBodyType2;
	}

	public int getTestBodyType3() {
		return testBodyType3;
	}

	public void setTestBodyType3(int testBodyType3) {
		this.testBodyType3 = testBodyType3;
	}

	public int getTestBodyType4() {
		return testBodyType4;
	}

	public void setTestBodyType4(int testBodyType4) {
		this.testBodyType4 = testBodyType4;
	}

	public int getTestBodyType5() {
		return testBodyType5;
	}

	public void setTestBodyType5(int testBodyType5) {
		this.testBodyType5 = testBodyType5;
	}

	public int getTestBodyType6() {
		return testBodyType6;
	}

	public void setTestBodyType6(int testBodyType6) {
		this.testBodyType6 = testBodyType6;
	}

	public int getTestBodyType7() {
		return testBodyType7;
	}

	public void setTestBodyType7(int testBodyType7) {
		this.testBodyType7 = testBodyType7;
	}

	public int getTestBodyType8() {
		return testBodyType8;
	}

	public void setTestBodyType8(int testBodyType8) {
		this.testBodyType8 = testBodyType8;
	}

	public int getTestBodyType9() {
		return testBodyType9;
	}

	public void setTestBodyType9(int testBodyType9) {
		this.testBodyType9 = testBodyType9;
	}

	public int getCellulose() {
		return cellulose;
	}

	public void setCellulose(int cellulose) {
		this.cellulose = cellulose;
	}
	
	public String getHealthTagNames() {
		return healthTagNames;
	}

	public void setHealthTagNames(String healthTagNames) {
		this.healthTagNames = healthTagNames;
	}

	@Override
	public String toString() {
		return "AccountModel [birthday=" + birthday + ", sex=" + sex
				+ ", accountId=" + accountId + ", weight=" + weight
				+ ", skinType=" + skinType + ", remarks=" + remarks
				+ ", sportLevel=" + sportLevel + ", skinAcne=" + skinAcne
				+ ", skinWhite=" + skinWhite + ", skinWater=" + skinWater
				+ ", skinAntiAging=" + skinAntiAging + ", cellulose="
				+ cellulose + ", area=" + area + ", height=" + height
				+ ", nickName=" + nickName + ", protein=" + protein + ", BMI="
				+ BMI + ", faceType=" + faceType + ", vitamin=" + vitamin
				+ ", minerals=" + minerals + ", pitUrl=" + pitUrl
				+ ", makeupStyle=" + makeupStyle + ", testBodyType="
				+ testBodyType + ", testBodyType2=" + testBodyType2
				+ ", testBodyType3=" + testBodyType3 + ", testBodyType4="
				+ testBodyType4 + ", testBodyType5=" + testBodyType5
				+ ", testBodyType6=" + testBodyType6 + ", testBodyType7="
				+ testBodyType7 + ", testBodyType8=" + testBodyType8
				+ ", testBodyType9=" + testBodyType9 + ", healthTagNames="
				+ healthTagNames + "]";
	}

}
