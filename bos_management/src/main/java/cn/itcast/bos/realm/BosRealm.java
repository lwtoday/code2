package cn.itcast.bos.realm;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.PermissionService;
import cn.itcast.bos.service.system.RoleService;
import cn.itcast.bos.service.system.UserService;

// 自定义Realm ，实现安全数据 连接
// @Service("bosRealm")
public class BosRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PermissionService permissionService;

	@Override
	// 认证...
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		System.out.println("shiro 认证管理... ");
		// 转换token
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		// 根据用户名 查询 用户信息
		User user = userService.findByUsername(usernamePasswordToken
				.getUsername());
		if (user == null) {
			// 用户名不存在
			return null;
		} else {
			// 用户名存在
			// 参数一： 期望登录后，保存在Subject中信息
			// 参数二：用户密码
			// 参数三 ：realm名称
			return new SimpleAuthenticationInfo(user, user.getPassword(),getName());
		}
	}
	
	@Override
	// 授权...
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		System.out.println("shiro 授权管理...");
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		// 根据当前登录用户 查询对应角色和权限
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
		// 调用业务层，查询角色
		List<Role> roles = roleService.findByUser(user);
		for (Role role : roles) {
			authorizationInfo.addRole(role.getKeyword());
		}
		// 调用业务层，查询权限
		List<Permission> permissions = permissionService.findByUser(user);
		for (Permission permission : permissions) {
			authorizationInfo.addStringPermission(permission.getKeyword());
		}
		return authorizationInfo;
	}

}
