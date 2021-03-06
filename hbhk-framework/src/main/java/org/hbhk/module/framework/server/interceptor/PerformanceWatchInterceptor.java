package org.hbhk.module.framework.server.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class PerformanceWatchInterceptor extends HandlerInterceptorAdapter implements Ordered {
	private  Log   log =LogFactory.getLog(getClass());
	ThreadLocal<StopWatch> stopWatchLocal = new ThreadLocal<StopWatch>();
	private boolean usePerformance = true;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (usePerformance) {
			StopWatch stopWatch = new StopWatch(handler.toString());
			stopWatchLocal.set(stopWatch);
			stopWatch.start(handler.toString());
		}

		return super.preHandle(request, response, handler);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (usePerformance) {
			StopWatch stopWatch = stopWatchLocal.get();
			stopWatch.stop();
			String currentPath = request.getRequestURI();
			String queryString = request.getQueryString();
			queryString = queryString == null ? "" : "?" + queryString;
			log.info("access url path:" + currentPath + queryString + " |time:"
					+ stopWatch.getTotalTimeMillis());
			stopWatchLocal.set(null);
		}
	}

	@Override
	public int getOrder() {
		return 2;
	}

}
