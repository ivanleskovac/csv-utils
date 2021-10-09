package com.ristic.csvutils.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CSV_RECORDS")
public class CsvRecord implements Serializable {

	private static final long serialVersionUID = 0L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "SOURCE")
	private String source;

	@Column(name = "CODE_LIST_CODE")
	private String codeListCode;

	@Column(name = "CODE")
	private String code;

	@Column(name = "DISPLAY_VALUE")
	private String displayValue;

	@Column(name = "LONG_DESCRIPTION")
	private String longDescription;

	@Column(name = "FROM_DATE")
	private LocalDate fromDate;

	@Column(name = "TO_DATE")
	private LocalDate toDate;

	@Column(name = "SORTING_PRIORITY")
	private Integer sortingPriority;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCodeListCode() {
		return codeListCode;
	}

	public void setCodeListCode(String codeListCode) {
		this.codeListCode = codeListCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public Integer getSortingPriority() {
		return sortingPriority;
	}

	public void setSortingPriority(Integer sortingPriority) {
		this.sortingPriority = sortingPriority;
	}

}
