package lottery.pojo;

public class User {
	private int id;//自增id
	private String userId;//用户ID
	private String userName;//用户名
	private int userType;//用户类型
	private boolean isUseKeyword;//是否抽奖关键词
	private boolean isInTime;//是否在抽奖时间段
	private int activeDay;//有效发言天数
	private int continuousActiveDay;//最大连续有效发言天数
	private int activeRecord;//有效发言条数
	private boolean isPrize;//是否中奖
	
	public void setId(int id) {
		this.id=id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setUserId(String userId) {
		this.userId=userId;
	}
	
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserName(String userName) {
		this.userName=userName;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserType(int userType) {
		this.userType=userType;
	}
	
	public int getUserType() {
		return this.userType;
	}
	
	public void setIsUseKeyword(boolean isUserKeyword) {
		this.isUseKeyword=isUserKeyword;
	}
	
	public boolean getIsUseKeyword() {
		return this.isUseKeyword;
	}
	
	public void setIsInTime(boolean isInTine) {
		this.isInTime=isInTime;
	}
	
	public boolean getIsInTime() {
		return this.isInTime;
	}
	
	public void setActiveDay(int activeDay) {
		this.activeDay=activeDay;
	}
	
	public int getActiveDay() {
		return this.activeDay;
	}
	
	public void setContinuousActiveDay(int continuousActiveDay) {
		this.continuousActiveDay=continuousActiveDay;
	}
	
	public int getContinuousActiveDay() {
		return this.continuousActiveDay;
	}
	
	public void setActiveRecord(int activeRecord) {
		this.activeRecord=activeRecord;
	}
	
	public int getActiveRecord() {
		return this.activeRecord;
	}
	
	public void setIsPrize(boolean isPrize) {
		this.isPrize=isPrize;
	}
	
	public boolean getIsPrize() {
		return this.isPrize;
	}
}
