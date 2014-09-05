package edu.oregonState.scheduler.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "osu_catalog")
@NamedQueries({
    @NamedQuery(
        name = "edu.oregonState.scheduler.core.catalog.findbyName",
        query = "SELECT p FROM User p WHERE p.instructor = :instructor"
    )
})

public class Catalog {
	@Id
	@Column(name="id",nullable=false)
	private String id;
	
	@Column(name="courseID",nullable=false)
	private String courseID;
	@Column(name="courseName",nullable=false)
	private String courseName;
	@Column(name="instructor",nullable=false)
	private String instructor;
	@Column(name="term",nullable=false)
	private String term;
	@Column(name="dateStart",nullable=false)
	private String dateStart;
	@Column(name="dateEnd",nullable=false)
	private String dateEnd;
	@Column(name="classDays",nullable=false)
	private String classDays;
	@Column(name="classTimeStart",nullable=false)
	private String classTimeStart;
	@Column(name="classTimeEnd",nullable=false)
	private String classTimeEnd;
	
	public Catalog() {
	
	}
	
	public String getCourseId() {
		return courseID;
	}
	public void setCourseId(String courseId){
		this.courseID = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName){
		this.courseName = courseName;
	}
	public String getInstructor() {
		return courseName;
	}
	public void setInstructor(String instructor){
		this.instructor = instructor;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term){
		this.term = term;
	}
	public String getDateStart() {
		return dateStart;
	}
	public void setDateStart(String dateStart){
		this.dateStart = dateStart;
	}
	public String getDateEnd() {
		return dateEnd;
	}
	public void setdateEnd(String dateEnd){
		this.dateEnd = dateEnd;
	}
	public String getClassDays() {
		return classDays;
	}
	public void setClassDays(String classDays){
		this.classDays = classDays;
	}
	public String getClassTimeStart() {
		return classTimeStart;
	}
	public void setClassTimeStart(String classTimeStart){
		this.classTimeStart = classTimeStart;
	}
	public String getClassTimeEnd() {
		return classTimeEnd;
	}
	public void setClassTimeEnd(String classTimeEnd){
		this.classTimeEnd = classTimeEnd;
	}
}
