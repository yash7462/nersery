package com.nursery.vo.userrole;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nursery.vo.userfront.UserFrontVo;

import lombok.Data;

@Data
@Entity
@Table(name = "user_role")
public class UserRoleVo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_role_id")
	private long userRoleId;
	
	@Column(name = "userrole_name", length = 50)
    private String userRoleName;
	
	@ManyToMany(mappedBy = "roles", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Set<UserFrontVo> userFrontVo;
	
}
