package org.juhewu.data.sensitive;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.juhewu.data.context.RequestDataContextHolder;

import lombok.SneakyThrows;

/**
 * 请求数据 filter，请求结束后，清空
 *
 * @author duanjw
 * @since 2022/09/21
 */
public class RequestDataFilter implements Filter {

    /**
     * 容器初始化
     *
     * @param filterConfig filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {

    }

    /**
     * 请求结束后，清空 RequestDataContextHolder
     *
     * @param servletRequest request
     * @param servletResponse response
     * @param filterChain chain
     */
    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        filterChain.doFilter(servletRequest, servletResponse);
        RequestDataContextHolder.remove();
    }

    /**
     * 容器销毁
     */
    @Override
    public void destroy() {
    }
}
