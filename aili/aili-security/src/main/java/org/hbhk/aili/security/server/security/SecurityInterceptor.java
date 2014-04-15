package org.hbhk.aili.security.server.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.security.server.service.IUserService;
import org.hbhk.aili.security.share.define.UserConstants;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.UrlPathHelper;

public class SecurityInterceptor implements Filter {

	private Log log = LogFactory.getLog(getClass());
	private UrlPathHelper urlPathHelper = new UrlPathHelper();
	private IUserService userService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(filterConfig.getServletContext());
		userService = (IUserService) ctx.getBean("userService");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		// 用户请求URL
		String url = urlPathHelper.getLookupPathForRequest(httpServletRequest);
		// 使用req对象获取RequestDispatcher对象
		RequestDispatcher dispatcher = httpServletRequest
				.getRequestDispatcher("/core/login");
		String username = (String) httpServletRequest.getSession()
				.getAttribute(UserConstants.CURRENT_USER_NAME);
		String loginUser = null;
		if (StringUtils.isEmpty(username)) {
			loginUser = (String) httpServletRequest.getParameter("username");
		} else {
			loginUser = username;
		}
		if (StringUtils.isEmpty(username)) {
			// 得到application验证用户登录次数
			ServletContext application = httpServletRequest.getSession()
					.getServletContext();
			List<String> countStr = (List<String>) application
					.getAttribute(UserConstants.USER_INFO_COUNT);
			List<String> userCount = new ArrayList<String>();
			if (!CollectionUtils.isEmpty(countStr)
					&& !StringUtils.isEmpty(loginUser)) {
				for (String user : countStr) {
					if (user.equals(loginUser)) {
						log.info("username:" + user);
						userCount.add(user);
					}
				}
			}
			Integer count = (Integer) application
					.getAttribute(UserConstants.USER_LOGIN_COUNT);
			if (userCount.size() > 0 && count != null
					&& userCount.size() >= count) {
				dispatcher = httpServletRequest
						.getRequestDispatcher("/core/authorizeError");
				request.setAttribute("errorMsg", "你(" + loginUser
						+ ")已经在别处登录,服务器只允许" + count + "次登录!");
				dispatcher.forward(request, response);
				return;
			}
		}
		if (StringUtils.isEmpty(username)) {
			request.setAttribute("errorMsg", "你还没有登录");
			dispatcher.forward(request, response);
			return;
		}
		boolean group = userService.validate(url);
		// 请求的资源不需要保护.
		if (group == false) {
			chain.doFilter(request, response);
			return;
		}
		if (group == true) {
			boolean uurl = userService.validate(url, username);
			if (uurl) {

				dispatcher.forward(request, response);
				return;
			} else {
				dispatcher = httpServletRequest
						.getRequestDispatcher("/core/authorizeError");
				request.setAttribute("errorMsg", "请求的URL不存在或没有权限访问!");
				dispatcher.forward(request, response);
				return;
			}
		}

	}

	@Override
	public void destroy() {

	}

}
