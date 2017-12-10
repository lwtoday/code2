package cn.itcast.bos.dao.base;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.service.base.StandardService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class StandardRepositoryTest {
	@Autowired
	private StandardRepository standardRepository; 
	
	@Autowired
	private StandardService standardService;
	
	@Test
	public void testQuery(){
		
//		System.out.println(standardRepository.findByName("战狼"));
//		System.out.println(standardRepository.queryNameNative("战狼"));
		
//		System.out.println(standardRepository.queryName("战狼"));
//		System.out.println(standardRepository.queryName2("标准2"));
		
//		System.out.println("空字符串="+StringUtils.isBlank(""));
//		System.out.println("带空格的空字符串="+StringUtils.isBlank(" "));
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void testUpdate(){
		standardRepository.updateMinLength(21, 666);
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void deleteById(){
		standardRepository.deleteById(184);
	}
}
