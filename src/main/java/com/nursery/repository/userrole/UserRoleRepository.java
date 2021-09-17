package com.nursery.repository.userrole;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.nursery.vo.userrole.UserRoleVo;


public interface UserRoleRepository extends CrudRepository<UserRoleVo, Long> {
    //@Query("from GlobalUserVo where global_user_id =:uid")
    public UserRoleVo findByUserRoleId(long id);
    
    public UserRoleVo findByUserRoleName(String name);

    //@Query(value = "select * from nav_menu_permission", nativeQuery = true)
    //@Query("from NavMenuPermissionVo where navSubMenuVo.type='customers' and userRoleVo.userRoleId=1 and navMenuActionVo.actionName='create'")
    //public List<NavMenuPermissionVo> getSubmenuListWithGlobalPermission(String type, long parseLong, String action);

    @Query(value = "select * from user_role", nativeQuery = true)
    public List getCheckList();

    //List<UserRoleVo> findByBranchId(long branchId);
}
