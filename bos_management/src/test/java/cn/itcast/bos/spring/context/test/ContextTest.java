package cn.itcast.bos.spring.context.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class ContextTest {
	
	@Test
	public void testContext(){
		// IOC(Inversion Of Control控制反转)/DI(Dependency Injection依赖注入)
		//要得到Bean-->BeanFactory -->Resource -->applicationContext.xml
//		BeanFactory beanFactory = new ClassPathXmlApplicationContext("applicationContext.xml");
//		
//		Resource resource = new ClassPathResource("applicationContext.xml");
//		BeanFactory factory = new XmlBeanFactory(resource);
//		PromotionJob promotionJob = (PromotionJob) factory.getBean("promotionJob");
//		System.out.println("promotionJob="+promotionJob);
		
		
		//D:/workspace/bos_teach/bos_management/src/main/resources/
		
//		JobDetailImpl jobDetailImpl = (JobDetailImpl) factory.getBean("promotionJob");
//		System.out.println("jobDetailImpl="+jobDetailImpl);
//		Class<PromotionJob> classPromotionJob = (Class<PromotionJob>)jobDetailImpl.getJobClass();
//		System.out.println("classPromotionJob="+classPromotionJob);
		
		
//		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//		JobDetailImpl jobDetailImpl = (JobDetailImpl) context.getBean("promotionJob");
//		System.out.println("jobDetailImpl="+jobDetailImpl);
		
		
//		ApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(ContextLoaderListener.getCurrentWebApplicationContext().getServletContext());
//		JobDetailImpl jobDetailImpl = (JobDetailImpl) factory.getBean("promotionJob");
//		System.out.println("jobDetailImpl="+jobDetailImpl);
		
	}
}
