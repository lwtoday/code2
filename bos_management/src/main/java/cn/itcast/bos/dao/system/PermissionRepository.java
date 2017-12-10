package cn.itcast.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.system.Permission;

public interface PermissionRepository extends
		JpaRepository<Permission, Integer> {

	@Query("from Permission p inner join fetch p.roles r inner join fetch r.users u where u.id =?")
	/*
	 select *
  from T_PERMISSION      p,
       T_ROLE_PERMISSION rp,
       T_ROLE            r,
       T_USER            u,
       T_USER_ROLE       ur
 where (p.c_id = rp.c_permission_id and r.c_id = rp.c_role_id)
   and (r.c_id = ur.c_role_id and u.c_id = ur.c_user_id)
   and u.c_id =2001

	 */
	List<Permission> findByUser(Integer id);

}
