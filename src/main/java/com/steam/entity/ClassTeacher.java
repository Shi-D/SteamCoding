package com.steam.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STEAM_CLASS_TEACHER")
public class ClassTeacher {

	@Id
	private Integer id;
	
	@Column(name = "CLASS_ID")
	private Integer classId;

	@Column(name = "TEACHER_ID")
	private Integer teacherId;

	@Column(name = "CREATION_TIME")
	private Date creationTime;

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
}
