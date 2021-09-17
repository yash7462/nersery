package com.nursery.repository.userfront;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nursery.vo.userfront.UserFrontVo;

@Repository
public interface UserFrontRepository extends JpaRepository<UserFrontVo, Long> {

	public UserFrontVo findByEmailIdAndIsDeleted(String email, int isDeleted);
}
