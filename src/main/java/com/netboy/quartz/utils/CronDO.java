package com.netboy.quartz.utils;

/**
 * deal CronExpression
 * 
 * @author netboy 2015年3月8日下午3:56:38
 */
public class CronDO {
	private String second;
	private String minute;
	private String hour;
	private String dayOfMonth;
	private String month;
	private String dayOfWeek;

	public CronDO(String second, String minute, String hour, String dayOfMonth, String month, String dayOfWeek) {
		this.second = second;
		this.minute = minute;
		this.hour = hour;
		this.dayOfMonth = dayOfMonth;
		this.month = month;
		this.dayOfWeek = dayOfWeek;
	}

	public CronDO() {

	}

	public String getCronExpression() {
		String secondStr = (second == null ? "*" : second.trim());
		String minuteStr = (minute == null ? "*" : minute.trim());
		String hourStr = (hour == null ? "*" : hour.trim());
		String dayOfMonthStr = (dayOfMonth == null ? "*" : dayOfMonth.trim());
		String dayOfWeekStr = (dayOfWeek == null ? "*" : dayOfWeek.trim());
		return parse(secondStr, minuteStr, hourStr, dayOfMonthStr, dayOfMonthStr, dayOfWeekStr);
	}

	/**
	 * 将 CronDO 转换为 CronExpression eg:'0 1 * * * ?'
	 * 
	 * @return
	 */
	private String parse(String second, String minute, String hour, String dayOfMonth, String month, String dayOfWeek) {
		StringBuilder builder = new StringBuilder();
		builder.append(second);
		builder.append(" ");
		builder.append(minute);
		builder.append(" ");
		builder.append(hour);
		builder.append(" ");
		builder.append(dayOfMonth);
		builder.append(" ");
		builder.append(month);
		builder.append(" ");
		builder.append(dayOfWeek);
		return builder.toString();
	}

	public String getSecond() {
		return second;
	}

	public void setSecond(String second) {
		this.second = second;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getDayOfMonth() {
		return dayOfMonth;
	}

	public void setDayOfMonth(String dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

}
