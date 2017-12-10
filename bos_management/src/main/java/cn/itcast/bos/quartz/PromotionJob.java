package cn.itcast.bos.quartz;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import cn.itcast.bos.service.take_delivery.PromotionService;

/**
 * 定时设置宣传任务 状态
 * 
 * @author itcast
 *
 */
public class PromotionJob implements Job {
	@Autowired
	private PromotionService promotionService;

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		
//		System.out.println("context="+context);
//		context=JobExecutionContext: trigger: 'DEFAULT.cronTrigger job: DEFAULT.promotionJob 
//			fireTime: 'Thu May 25 10:46:15 CST 2017 scheduledFireTime: Thu May 25 10:46:15 CST 2017 
//			previousFireTime: 'Thu May 25 10:46:10 CST 2017 nextFireTime: Thu May 25 10:46:20 CST 2017 isRecovering: false refireCount: 0
		
//		try {
//			ApplicationContext ac = (ApplicationContext)getApplicationContext(context);
//			System.out.println("cronTrigger="+ac.getBean("cronTrigger"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
//		System.out.println("活动过期处理程序执行....");
//		// 每分钟执行一次，当前时间 大于 promotion数据表 endDate， 活动已经过期，设置status='2'
//		promotionService.updateStatus(new Date());
	}
	
//	private static final String APPLICATION_CONTEXT_KEY = "applicationContextKey";
//	private ApplicationContext getApplicationContext(JobExecutionContext context) throws Exception {
//			ApplicationContext appCtx = null;
//			appCtx = (ApplicationContext) context.getScheduler().getContext().get(APPLICATION_CONTEXT_KEY);
//			if (appCtx == null) {
//				throw new JobExecutionException("No application context available in scheduler context for key \"" + APPLICATION_CONTEXT_KEY + "\"");
//			}
//			return appCtx;
//	}

}
