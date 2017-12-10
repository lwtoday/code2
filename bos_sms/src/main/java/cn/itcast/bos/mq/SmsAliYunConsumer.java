package cn.itcast.bos.mq;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.itcast.bos.utils.AliyunSmsSender;

@Service("smsAliYunConsumer")
public class SmsAliYunConsumer implements MessageListener{

	@Override
	public void onMessage(Message msg) {
		MapMessage mapMessage = (MapMessage) msg;
		try {
			String smsCode = mapMessage.getString("smsCode");
			String recNum = mapMessage.getString("recNum");
			System.out.println("activeMQ插件，已接收到 发送过来的短信验证码【"+smsCode+"】和接收验证码的手机号【"+recNum+"】");
			//测试阶段，暂先注释 短信发送调用接口
			if(StringUtils.isNotBlank(smsCode)&& StringUtils.isNotBlank(recNum)){
				try {
					AliyunSmsSender.senderSmsCheckInfo(smsCode, recNum);
				} catch (Exception e) {
					e.printStackTrace();
					new RuntimeException("调用 阿里大于验证码短信发送接口失败");
				}
			}
			
		} catch (JMSException e) {
			e.printStackTrace();
			new RuntimeException("调用消息队列发送报错");
		}
		
	}

}
