package com.xcomm.mina.test;

/**
 * @function : 
 * @author   :jy
 * @company  :万里网
 * @date     :2011-8-7
 */
public class Flight {
	public String startCity;
	public String endCity;
	public String flightway;
	public String date;
	public String fromDate;
	public String subclass1;
	public String flight1;
	
	/**
	 * 返回出发城市
	 * @return
	 */
	public String getStartCity() {
		return startCity;
	}
	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}
	/**
	 * 返回到达城市
	 * @return
	 */
	public String getEndCity() {
		return endCity;
	}
	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}
	/**
	 * 返回行程类型
	 * @return
	 */
	public String getFlightway() {
		return flightway;
	}
	public void setFlightway(String flightway) {
		this.flightway = flightway;
	}
	/**
	 * 返回出发日期
	 * @return
	 */
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Flight [startCity=" + startCity + ", endCity=" + endCity + ", flightway=" + flightway + ", date="
				+ date + "]";
	}
	/**
	 * 返回往返日期
	 * @return
	 */
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getFlight1() {
		return flight1;
	}
	public void setFlight1(String flight1) {
		this.flight1 = flight1;
	}
	public String getSubclass1() {
		return subclass1;
	}
	public void setSubclass1(String subclass1) {
		this.subclass1 = subclass1;
	}
}
