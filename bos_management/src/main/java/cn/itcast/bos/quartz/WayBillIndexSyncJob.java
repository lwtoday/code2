package cn.itcast.bos.quartz;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import cn.itcast.bos.service.take_delivery.PromotionService;
import cn.itcast.bos.service.take_delivery.WayBillService;

/**
 * 定时查询数据库，同步索引库
 * 
 * @author itcast
 *
 */
public class WayBillIndexSyncJob implements Job {
	@Autowired
	private WayBillService wayBillService;

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
//		wayBillService.syncIndex();
//		System.out.println("定时查询数据库，同步索引库");
	}
}
