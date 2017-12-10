package cn.itcast.bos.service.take_delivery.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder.Operator;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.take_delivery.WayBillRepository;
import cn.itcast.bos.domain.take_delivery.WayBill;
import cn.itcast.bos.index.WayBillIndexRepository;
import cn.itcast.bos.service.take_delivery.WayBillService;

@Service
@Transactional
public class WayBillServiceImpl implements WayBillService {

	@Autowired
	private WayBillRepository wayBillRepository;

	@Autowired
	private WayBillIndexRepository wayBillIndexRepository;

	@Override
	public void save(WayBill wayBill) {
		// 判断运单号是否存在
		WayBill persistWayBill = wayBillRepository.findByWayBillNum(wayBill
				.getWayBillNum());
		if (persistWayBill == null) {//说明运单是不存在的，则进行保存操作
			/*
			 * 如果运单号存在 ，我们需要 更新操作
			 * 如果运单号不存在 ，我们需要 保存操作
			 * 
			 */
			wayBill.setSignStatus(1);// 待发货(如果是新增的运单，则创建运单时，先将运行类型设置为默认：待发货）
//			wayBillRepository.save(wayBill);
		}else{//如果进入到else，说明运单号是存在 的，则进行更新操作
			//注意：操作过程：1）输入运单号，回显运单号查询出来的运单信息   2）更新该运单中的其他信息（同时也可以修改运单前已经存在 的信息）  3）提交运单信息
			//只将根据运单编号查询出来的persistWayBill中的ID设置到wayBill中
			wayBill.setId(persistWayBill.getId());
		}
		
		wayBillRepository.save(wayBill);//直接调save的方法
		// 保存索引
		wayBillIndexRepository.save(wayBill);
//			// 运单不存在
//			wayBill.setSignStatus(1); // 待发货
//			wayBillRepository.save(wayBill);
//			// 保存索引
//			wayBillIndexRepository.save(wayBill);
//		} else {
//			// 运单存在
//			try {
//				// 判断运单状态是否 为待发货
//				if (persistWayBill.getSignStatus() == 1) {
//					Integer id = persistWayBill.getId();
//					BeanUtils.copyProperties(persistWayBill, wayBill);
//					persistWayBill.setId(id);
//					persistWayBill.setSignStatus(1);// 待发货
//					// 保存索引
//					wayBillIndexRepository.save(persistWayBill);
//				} else {
//					// 运单状态 已经运输中，不能修改
//					throw new RuntimeException("运单已经发出，无法修改保存！！");
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				throw new RuntimeException(e.getMessage());
//			}
//		}

	}

	@Override
	public Page<WayBill> findPageData(WayBill wayBill, Pageable pageable) {
		// 判断WayBill 中条件是否存在
		if (StringUtils.isBlank(wayBill.getWayBillNum())
				&& StringUtils.isBlank(wayBill.getSendAddress())
				&& StringUtils.isBlank(wayBill.getRecAddress())
				&& StringUtils.isBlank(wayBill.getSendProNum())
				&& (wayBill.getSignStatus() == null || wayBill.getSignStatus() == 0)) {
			// 无条件查询 、查询数据库
			return wayBillRepository.findAll(pageable);
		} else {
			// 查询条件
			// must 条件必须成立 and     
			// must not 条件必须不成立 not
			// should 条件可以成立 or
			BoolQueryBuilder query = new BoolQueryBuilder(); // 布尔查询 ，多条件组合查询
			// 向组合查询对象添加条件
			if (StringUtils.isNoneBlank(wayBill.getWayBillNum())) {
				// 运单号查询
				QueryBuilder tempQuery = new TermQueryBuilder("wayBillNum",
						wayBill.getWayBillNum());
				query.must(tempQuery);
			}
			if (StringUtils.isNoneBlank(wayBill.getSendAddress())) {
				// 发货地 模糊查询
				// 情况一： 输入"北" 是查询词条一部分， 使用模糊匹配词条查询
				QueryBuilder wildcardQuery = new WildcardQueryBuilder(
						"sendAddress", "*" + wayBill.getSendAddress() + "*");

				// 情况二： 输入"北京市海淀区" 是多个词条组合，进行分词后 每个词条匹配查询
				QueryBuilder queryStringQueryBuilder = new QueryStringQueryBuilder(wayBill.getSendAddress())
				.field("sendAddress").defaultOperator(Operator.AND);

				// 两种情况取or关系
				BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
				boolQueryBuilder.should(wildcardQuery);
				boolQueryBuilder.should(queryStringQueryBuilder);

				query.must(boolQueryBuilder); 
			}
			if (StringUtils.isNoneBlank(wayBill.getRecAddress())) {
				// 收货地 模糊查询
				QueryBuilder wildcardQuery = new WildcardQueryBuilder(
						"recAddress", "*" + wayBill.getRecAddress() + "*");
				query.must(wildcardQuery);
			}
			if (StringUtils.isNoneBlank(wayBill.getSendProNum())) {
				// 速运类型 等值查询
//				QueryBuilder termQuery = new TermQueryBuilder("sendProNum",
//						wayBill.getSendProNum());
//				query.must(termQuery);
				
				QueryBuilder queryStringQueryBuilder = new QueryStringQueryBuilder(wayBill.getSendProNum())
				.field("sendProNum").defaultOperator(Operator.AND);
				query.must(queryStringQueryBuilder);
			}
			if (wayBill.getSignStatus() != null && wayBill.getSignStatus() != 0) {
				// 签收状态查询
				QueryBuilder termQuery = new TermQueryBuilder("signStatus",
						wayBill.getSignStatus());
				query.must(termQuery);
			}

			SearchQuery searchQuery = new NativeSearchQuery(query);
			searchQuery.setPageable(pageable); // 分页效果
			// 有条件查询 、查询索引库
			return wayBillIndexRepository.search(searchQuery);
		}

	}

	@Override
	public WayBill findByWayBillNum(String wayBillNum) {
		return wayBillRepository.findByWayBillNum(wayBillNum);
	}

	@Override
	public void syncIndex() {
		// 查询数据库
		List<WayBill> wayBills = wayBillRepository.findAll();
		// 同步索引库
		wayBillIndexRepository.save(wayBills);
	}

	@Override
	public List<WayBill> findWayBills(WayBill wayBill) {
		// 判断WayBill 中条件是否存在
		if (StringUtils.isBlank(wayBill.getWayBillNum())
				&& StringUtils.isBlank(wayBill.getSendAddress())
				&& StringUtils.isBlank(wayBill.getRecAddress())
				&& StringUtils.isBlank(wayBill.getSendProNum())
				&& (wayBill.getSignStatus() == null || wayBill.getSignStatus() == 0)) {
			// 无条件查询 、查询数据库
			return wayBillRepository.findAll();
		} else {
			// 查询条件
			// must 条件必须成立 and
			// must not 条件必须不成立 not
			// should 条件可以成立 or
			BoolQueryBuilder query = new BoolQueryBuilder(); // 布尔查询 ，多条件组合查询
			// 向组合查询对象添加条件
			if (StringUtils.isNoneBlank(wayBill.getWayBillNum())) {
				// 运单号查询
				QueryBuilder tempQuery = new TermQueryBuilder("wayBillNum",
						wayBill.getWayBillNum());
				query.must(tempQuery);
			}
			if (StringUtils.isNoneBlank(wayBill.getSendAddress())) {
				// 发货地 模糊查询
				// 场景： 输入"北京市海淀区" 
				//第一种查询：使用模糊匹配词条查询
				QueryBuilder wildcardQuery = new WildcardQueryBuilder(
						"sendAddress", "*" + wayBill.getSendAddress() + "*");

				// 第二种查询： 进行分词后 每个词条匹配查询
				QueryBuilder queryStringQueryBuilder = new QueryStringQueryBuilder(
						wayBill.getSendAddress()).field("sendAddress")
						.defaultOperator(Operator.AND);
				
				// 两种情况取or关系
				BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
				boolQueryBuilder.should(wildcardQuery);
				boolQueryBuilder.should(queryStringQueryBuilder);

				query.must(boolQueryBuilder);
			}
			if (StringUtils.isNoneBlank(wayBill.getRecAddress())) {
				// 收货地 模糊查询
				QueryBuilder wildcardQuery = new WildcardQueryBuilder(
						"recAddress", "*" + wayBill.getRecAddress() + "*");
				query.must(wildcardQuery);
			}
			if (StringUtils.isNoneBlank(wayBill.getSendProNum())) {
				// 速运类型 等值查询
//				QueryBuilder termQuery = new TermQueryBuilder("sendProNum",
//						wayBill.getSendProNum());
//				query.must(termQuery);
				
				QueryBuilder queryStringQueryBuilder = new QueryStringQueryBuilder(wayBill.getSendProNum())
				.field("sendProNum").defaultOperator(Operator.AND);
				query.must(queryStringQueryBuilder);
			}
			if (wayBill.getSignStatus() != null && wayBill.getSignStatus() != 0) {
				// 签收状态查询
				QueryBuilder termQuery = new TermQueryBuilder("signStatus",
						wayBill.getSignStatus());
				query.must(termQuery);
			}

			SearchQuery searchQuery = new NativeSearchQuery(query);
			// ElasticSearch 允许搜索分页查询，最大数据条数10000
			Pageable pageable = new PageRequest(0, 10000);
			searchQuery.setPageable(pageable); // 分页效果

			// 有条件查询 、查询索引库
			return wayBillIndexRepository.search(searchQuery).getContent();
		}
	}

}
