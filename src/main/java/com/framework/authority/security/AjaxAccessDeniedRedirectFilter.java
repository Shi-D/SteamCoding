package com.framework.authority.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.security.web.util.ThrowableCauseExtractor;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 自定义的过滤器完成对AJAX请求时权限问题的处理，放在ExceptionTranslationFilter之后
 * 
 * @author Administrator
 * 
 */
public class AjaxAccessDeniedRedirectFilter extends OncePerRequestFilter {

	private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();
	private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

	private int customAccessDeniedErrorCode = 403;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		try {
			chain.doFilter(request, response);
		} catch (Exception e) {
			// 这里主要是从异常堆栈中提取SpringSecurityException
			Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);
			RuntimeException ase = (AuthenticationException) throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class, causeChain);
			if (ase == null) {
				ase = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
			}
			if (ase != null) {
				if (ase instanceof AuthenticationException) {
					throw ase;
				} else if (ase instanceof AccessDeniedException) {
					if (authenticationTrustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {
						// 根据' x-requested-with'请求头的信息判断是否为AJAX请求
						String ajaxHeader = request.getHeader("X-Requested-With");
						// 如果为AJAX请求则进行AJAX处理，如果为传统请求则继续抛出异常让ExceptionTranslationFilter处理
						if ("XMLHttpRequest".equals(ajaxHeader)) {
							HttpServletResponse resp = (HttpServletResponse) response;
							resp.sendError(this.customAccessDeniedErrorCode);
						} else {
							throw ase;
						}
					} else {
						throw ase;
					}
				}
			}
		}
	}

	private static final class DefaultThrowableAnalyzer extends ThrowableAnalyzer {
		/**
		 * @see org.springframework.security.web.util.ThrowableAnalyzer#initExtractorMap()
		 */
		protected void initExtractorMap() {
			super.initExtractorMap();

			registerExtractor(ServletException.class, new ThrowableCauseExtractor() {
				public Throwable extractCause(Throwable throwable) {
					ThrowableAnalyzer.verifyThrowableHierarchy(throwable, ServletException.class);
					return ((ServletException) throwable).getRootCause();
				}
			});
		}
	}

	public void setCustomAccessDeniedErrorCode(int customAccessDeniedErrorCode) {
		this.customAccessDeniedErrorCode = customAccessDeniedErrorCode;
	}

}
