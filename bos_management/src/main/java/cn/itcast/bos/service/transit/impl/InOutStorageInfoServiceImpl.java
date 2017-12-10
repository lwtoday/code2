package cn.itcast.bos.service.transit.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.transit.InOutStorageInfoRepository;
import cn.itcast.bos.dao.transit.TransitInfoRepository;
import cn.itcast.bos.domain.transit.InOutStorageInfo;
import cn.itcast.bos.domain.transit.TransitInfo;
import cn.itcast.bos.service.transit.InOutStorageInfoService;

@Service
@Transactional
public class InOutStorageInfoServiceImpl implements InOutStorageInfoService {

	@Autowired
	private InOutStorageInfoRepository inOutStorageInfoRepository;

	@Autowired
	private TransitInfoRepository transitInfoRepository;

	@Override
	public void save(String transitInfoId, InOutStorageInfo inOutStorageInfo) {
		// 保存出入库信息
		inOutStorageInfoRepository.save(inOutStorageInfo);

		// 查询TransitInfo
		TransitInfo transitInfo = transitInfoRepository.findOne(Integer
				.parseInt(transitInfoId));

		// 关联出入库信息 到 运输配送对象
		transitInfo.getInOutStorageInfos().add(inOutStorageInfo);

		// 修改状态
		/*
		 * 项目开发中，强烈要求关键的状态字段使用整数来表示（0，1，2，3....)
		 * 		去给状态字段的表示的意思在 表字段的注释中去描述
		 * 	理由：1、用数字表示状态，在判断时，更加的方便
		 * 		2、如果状态直接显示为了中文，那么程序在后期如果要修改显示的状态的名字时，会变的非常复杂
		 * 
		 * 
		 */
		if (inOutStorageInfo.getOperation().equals("到达网点")) {
			transitInfo.setStatus("到达网点");
			// 更新网点地址 ，显示配送路径
			transitInfo.setOutletAddress(inOutStorageInfo.getAddress());
		}
	}

}
