package cn.itcast.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.SubArea;

public interface SubAreaService {

	Page<SubArea> findPageData(Pageable pageable);

	Page<SubArea> findPageData(Specification<SubArea> specification,Pageable pageable);

	void saveSubArea(SubArea subArea);

	void modifySubArea(SubArea subArea);
	
	void deleteSubAreaById(String[] idArray);
}
