package cn.itcast.bos.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import cn.itcast.bos.web.action.take_delivery.WayBillAction;

public class LoginFormAuthenticationFilter extends FormAuthenticationFilter {

	private static final Logger LOGGER = Logger
			.getLogger(LoginFormAuthenticationFilter.class);
	
	
	public LoginFormAuthenticationFilter() {
//		System.out.println("LoginFormAuthenticationFilter构造器="+super.getSuccessUrl()+" "+this.getSuccessUrl());
		super.setSuccessUrl("/pages/base/fixed_area.html");
	}
	
	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
//		System.out.println("LoginFormAuthenticationFilter onAccessDenied="+getSuccessUrl());
		request.setAttribute(getSuccessUrl(), "/pages/base/fixed_area.html");
		return super.onAccessDenied(request, response);
	}
	
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		// return super.onLoginSuccess(token, subject, request, response);

//		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//		subject.getSession().setAttribute("CURRENT_USER",
//				subject.getPrincipal()); // 设置用户身份进session属性
//		LOGGER.info("用户 " + subject.getPrincipal() + " 登陆成功");
//		String url = this.getSuccessUrl();
//		httpServletResponse.sendRedirect(httpServletRequest.getContextPath()
//				+ url); // 页面跳转
//		return false;

//		System.out.println("LoginFormAuthenticationFilter onLoginSuccess");
//		LOGGER.info("LoginFormAuthenticationFilter onLoginSuccess");
		 boolean contextRelative = true;
		 String successUrl = this.getSuccessUrl();
		 System.out.println("come in..."+successUrl);
		 if("".equals(successUrl)){
			 successUrl = DEFAULT_SUCCESS_URL;
		 }
		 WebUtils.issueRedirect(request, response, successUrl, null,contextRelative);
		 return false;
	}

}
