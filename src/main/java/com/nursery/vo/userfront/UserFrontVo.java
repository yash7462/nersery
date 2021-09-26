package com.nursery.vo.userfront;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.nursery.vo.userrole.UserRoleVo;

import lombok.Data;

@Data
@Entity
@Table(name = "user_front")
public class UserFrontVo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_front_id")
	private long userFrontId;
	
	@ManyToMany
    @JoinTable(name = "user_role_new", joinColumns = @JoinColumn(name = "user_front_id"), inverseJoinColumns = @JoinColumn(name = "user_role_id"))
    private List<UserRoleVo> roles;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "emailId")
	private String emailId;
	
	@Column(name = "pass_word")
	private String passWord;
	
	@Column(name = "is_deleted", length = 1,columnDefinition = "int default 0")
	private int isDeleted;
	
	@Column(name = "age",columnDefinition = "int default 0")
	private int age;
	
	@Column(name = "gender",columnDefinition = "int default 0")
	private String gender;
	
	@Column(name="height",columnDefinition = "double precision default 0.0")
	private double height;
	
	@Column(name="weight",columnDefinition = "double precision default 0.0")
	private double weight;
	
    @CreationTimestamp
    @Column(name = "created_on", updatable = false)
    private Date createdOn;

    @UpdateTimestamp
    @Column(name = "modified_on")
    private Date modifiedOn;
	
}
