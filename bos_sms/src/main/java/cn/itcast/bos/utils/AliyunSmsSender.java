package cn.itcast.bos.utils;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
/**
 * http方式进行短信验证码发送
 * 短信后台管理平台登录入口：https://dayu.aliyun.com/
 * 帐号+密 码 = 淘宝登录帐号密码
 * 
 * @author Administrator
 *
 */
public class AliyunSmsSender {
	// 选择短信验证码请求的URL方式
	private static String url = "http://gw.api.taobao.com/router/rest";
	// 申请的项目appkey
	private static String appkey = "24571186";
	// 申请的项目对应的 密钥
	private static String secret = "167fa542a0c8ae9212820b2d5a070f08";

	/**
	 * 阿里大于短信发送封装方法
	 * @param extend 公共回传参数，在“消息返回”中会透传回该参数；<br /> 举例：用户可以传入自己下级的会员ID，在消息返回时，该会员ID会包含在内，用户可以根据该会员ID识别是哪位会员使用了你的应用 
	 * @param smsType 短信类型，传入值请填写normal (必填参数)
	 * @param smsFreeSignName 短信签名，传入的短信签名必须是在阿里大于“管理中心-验证码/短信通知/推广短信-配置短信签名”中的可用签名。<br/>如“阿里大于”已在短信签名管理中通过审核，则可传入”阿里大于“（传参时去掉引号）作为短信签名。短信效果示例：【阿里大于】欢迎使用阿里大于服务。
	 * @param smsParamString 短信模板变量，传参规则{"key":"value"}，key的名字须和申请模板中的变量名一致，多个变量之间以逗号隔开。<br/>示例：针对模板“验证码${code}，您正在进行${product}身份验证，打死不要告诉别人哦！”，传参时需传入{"code":"1234","product":"alidayu"} 
	 * @param recNum 短信接收号码。支持单个或多个手机号码，传入号码为11位手机号码，不能加0或+86。群发短信需传入多个号码，以英文逗号分隔，一次调用最多传入200个号码。<br/>示例：18600000000,13911111111,13322222222
	 * @param smsTemplateCode 短信模板ID，传入的模板必须是在阿里大于“管理中心-短信模板管理”中的可用模板。示例：SMS_585014 
	 * @throws Exception 
	 */
	
	public static void senderSmsInfo(String extend, String smsType, String smsFreeSignName, String smsParamString,
			String recNum, String smsTemplateCode) throws Exception {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend(extend);
		req.setSmsType(smsType);
		req.setSmsFreeSignName(smsFreeSignName);
		req.setSmsParamString(smsParamString);
		req.setRecNum(recNum);
		req.setSmsTemplateCode(smsTemplateCode);
		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
		
		System.out.println(rsp.getBody());
	}
	
	/**
	 * 简化版验证码发送操作（只发送一条验证码信息给指定的手机号，其他参数均设置为默认值
	 * @param smsCode 验证码
	 * @param recNum 接收验证码的手机号
	 */
	public static void senderSmsCheckInfo(String smsCode,String recNum) throws Exception{
		senderSmsInfo(smsCode, "normal", "传智bos项目", "{\"smsCode\":\""+smsCode+"\"}", recNum, "SMS_82180012");
	}
	
	
	
	
	public static void main(String[] args) throws Exception {
		senderSmsInfo("需要回传的参数值", "normal", "传智bos项目", "{\"smsCode\":\"5201314\"}", "18672304510", "SMS_82180012");
	}
}
