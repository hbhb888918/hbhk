package org.hbhk.aili.security.server.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.hbhk.aili.core.server.context.RequestContext;
import org.hbhk.aili.core.share.consts.Protocol;
import org.hbhk.aili.security.server.context.UserContext;

public class AppFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {

	}

	/**
	 * 设置应用信息
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		/** 取得HttpServletRequest,这里可以拿到HttpSession **/
		HttpServletRequest sreq = null;
		if (request instanceof HttpServletRequest) {
			try {
				sreq = (HttpServletRequest) request;
				/** 寻找客户端是否采用Hessian协议,HTTP头部有此定义 **/
				String remoteReqMethod = sreq
						.getHeader(Protocol.SECURITY_HEADER);
				String remoteReqURL = sreq.getRequestURI();
				String contextPath = sreq.getContextPath();
				/** 去掉应用名称，具体部署的应用名称是可变的 **/
				if (contextPath != null && !"/".equals(contextPath)
						&& remoteReqURL.startsWith(contextPath)) {
					remoteReqURL = remoteReqURL.substring(contextPath.length());
				}
				/** 将当前访问的路径和远程头信息放入权限上下文 **/
				RequestContext.setCurrentContext(remoteReqMethod, remoteReqURL,
						request.getRemoteAddr());

				// String localeLanguage = sreq
				// .getParameter(LocaleConst.KEY_LOCALE_LANGUAGE);
				// String localeCountry = sreq
				// .getParameter(LocaleConst.KEY_LOCALE_COUNTRY);
				// if (localeLanguage != null && localeCountry != null) {
				// Locale locale = new Locale(localeLanguage, localeCountry);
				// }
				filterChain.doFilter(request, response);
			} finally {
				// 清除ThreadLocal中持有的信息
				RequestContext.remove();
				UserContext.remove();
			}
		}
	}

	@Override
	public void destroy() {

	}
}
