package cn.itcast.bos.web.action.base;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.service.base.SubAreaService;
import cn.itcast.bos.web.action.common.BaseAction;

public class SubAreaAction extends BaseAction<SubArea> {

	@Autowired
	private SubAreaService subAreaService;

	private SubArea subArea = new SubArea();

	public SubArea getModel() {
		return this.subArea;
	}

	@Action(value = "subarea_page", results = { @Result(name = "success", type = "json") })
	public String subAreaPageQuery() {
		Pageable pageable = new PageRequest(page - 1, rows);

		Specification<SubArea> specification = new Specification<SubArea>() {
			@Override
			public Predicate toPredicate(Root<SubArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> precList = new ArrayList<>();
				
				if(StringUtils.isNotBlank(subArea.getKeyWords())){//关键字判断
					Predicate pr1 = cb.like(root.get("keyWords").as(String.class), "%"+subArea.getKeyWords()+"%");
					precList.add(pr1);
				}
				//与区域表进行关联 查询
				Join<SubArea, Area> areaJoin = root.join("area",JoinType.INNER);
				//条件：省份查询
				if(subArea.getArea() != null && StringUtils.isNotBlank(subArea.getArea().getProvince())){
					Predicate pr2 = cb.equal(areaJoin.get("province").as(String.class), subArea.getArea().getProvince());
					precList.add(pr2);
				}
				//条件：城市 查询
				if(subArea.getArea() != null && StringUtils.isNotBlank(subArea.getArea().getCity())){
					Predicate pr3 = cb.equal(areaJoin.get("city").as(String.class), subArea.getArea().getCity());
					precList.add(pr3);
				}
				//条件：区域 查询
				if(subArea.getArea() != null && StringUtils.isNotBlank(subArea.getArea().getDistrict())){
					Predicate pr4 = cb.equal(areaJoin.get("district").as(String.class), subArea.getArea().getDistrict());
					precList.add(pr4);
				}
				//外连接 定区表
				Join<SubArea, FixedArea> fixedAreaJoin = root.join("fixedArea",JoinType.LEFT);
				if(subArea.getFixedArea()!= null && StringUtils.isNotBlank(subArea.getFixedArea().getFixedAreaName())){
					Predicate pre5 = cb.like(fixedAreaJoin.get("fixedAreaName").as(String.class), "%"+subArea.getFixedArea().getFixedAreaName()+"%");
					precList.add(pre5);
				}
				
				return cb.and(precList.toArray(new Predicate[0]));
			}
		};
		Page<SubArea> pageData = subAreaService.findPageData(specification,pageable);

		pushPageDataToValueStack(pageData);

		return SUCCESS;
	}

	@Action(value="subarea_modify",results={@Result(name="success",type="redirect",location="./pages/base/sub_area.html")})
	public String modifySubArea(){
		subAreaService.modifySubArea(subArea);
		return SUCCESS;
	}
	
	private String ids;
	
	public void setIds(String ids) {
		this.ids = ids;
	}

	@Action(value="subarea_delete",
			results={@Result(name="success",type="redirect",location="./pages/base/sub_area.html")})
	public String deleteById(){
		String[] idArray = ids.split(",");
		subAreaService.deleteSubAreaById(idArray);
		return SUCCESS;
	}
}
