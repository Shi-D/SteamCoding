package com.steam.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STEAM_CLASS_STUDENT")
public class ClassStudent {

	@Id
	private Integer id;

	@Column(name = "CLASS_ID")
	private Integer classId;

	@Column(name = "STUDENT_ID")
	private Integer studentId;

	@Column(name = "CREATE_TIME")
	private Date createTime;

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
