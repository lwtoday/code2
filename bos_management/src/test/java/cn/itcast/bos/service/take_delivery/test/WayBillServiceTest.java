package cn.itcast.bos.service.take_delivery.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.bos.service.take_delivery.WayBillService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class WayBillServiceTest {

	@Autowired
	private WayBillService wayBillService;

	//测试方法的修辞符，不能定义为private,否则会导致无法正常运行
	@Test
	public void testSyncIndex() {
		wayBillService.syncIndex();
	}

}
