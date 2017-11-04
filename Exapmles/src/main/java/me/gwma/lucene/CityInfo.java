package me.gwma.lucene;

import java.io.Serializable;

public class CityInfo implements Serializable {
	private static final long serialVersionUID = 7635546756317047866L;

	private long id;
	private String nameCn;
	private String nameEn;
	private String descption;
	private int visitNum;
	private int countryId;

	public CityInfo() {

	}

	public CityInfo(long id, String nameCn, String nameEn, String descption, int visitNum, int countryId) {
		super();
		this.id = id;
		this.nameCn = nameCn;
		this.nameEn = nameEn;
		this.descption = descption;
		this.visitNum = visitNum;
		this.countryId = countryId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNameCn() {
		return nameCn;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getDescption() {
		return descption;
	}

	public void setDescption(String descption) {
		this.descption = descption;
	}

	public int getVisitNum() {
		return visitNum;
	}

	public void setVisitNum(int visitNum) {
		this.visitNum = visitNum;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	@Override
	public String toString() {
		return "CityInfo [id=" + id + ", nameCn=" + nameCn + ", nameEn=" + nameEn + ", descption=" + descption
				+ ", visitNum=" + visitNum + ", countryId=" + countryId + "]";
	}

	public static CityInfo line2CityInfo(String line) {
		CityInfo cityInfo = new CityInfo();
		String[] tokens = line.trim().split("\t");
		if (tokens.length == 6) {
			cityInfo.setId(Long.parseLong(tokens[0].trim()));
			cityInfo.setNameCn(tokens[1]);
			cityInfo.setNameEn(tokens[2]);
			cityInfo.setDescption(tokens[3].trim());
			cityInfo.setVisitNum(Integer.parseInt(tokens[4].trim()));
			cityInfo.setCountryId(Integer.parseInt(tokens[5].trim()));
		} else {
			throw new RuntimeException(
					String.format("parse line Error: tokens.length=%d, \n line = %s", tokens.length, line));
		}
		return cityInfo;
	}
}
