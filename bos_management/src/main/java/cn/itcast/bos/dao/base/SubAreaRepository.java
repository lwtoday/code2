package cn.itcast.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.SubArea;

public interface SubAreaRepository extends JpaRepository<SubArea, Integer>,JpaSpecificationExecutor<SubArea>{

	@Query("delete from SubArea where id = ?")
	@Modifying
	public void deleteSubAreaById(String id);
}
