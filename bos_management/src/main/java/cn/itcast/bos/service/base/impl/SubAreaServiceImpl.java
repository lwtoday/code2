package cn.itcast.bos.service.base.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.SubAreaRepository;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.service.base.SubAreaService;

@Service
@Transactional
public class SubAreaServiceImpl implements SubAreaService {
	@Autowired
	private SubAreaRepository subAreaRepository;

	@Override
	public Page<SubArea> findPageData(Pageable pageable) {
		return subAreaRepository.findAll(pageable);
	}

	@Override
	public Page<SubArea> findPageData(Specification<SubArea> specification, Pageable pageable) {
		return subAreaRepository.findAll(specification, pageable);
	}

	@Override
	public void saveSubArea(SubArea subArea) {
		subAreaRepository.save(subArea);
	}

	@Override
	public void modifySubArea(SubArea subArea) {
		subAreaRepository.saveAndFlush(subArea);
	}
	
	@Override
	public void deleteSubAreaById(String[] idArray){
		for(String id : idArray){
			if(StringUtils.isNotBlank(id)){
				subAreaRepository.deleteSubAreaById(id);
			}
		}
	}

}
