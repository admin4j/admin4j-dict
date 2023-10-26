package com.admin4j.dict.json.mvc;


import com.admin4j.dict.anno.DictCacheManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 清理缓存
 *
 * @author andanyang
 * @since 2022/8/30 9:26
 */
@RequiredArgsConstructor
public class DictInterceptor implements HandlerInterceptor {
    private final DictCacheManager dictCacheManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 这里可以添加需要在接口处理前执行的逻辑
        return true; // 返回true继续执行后续拦截器，返回false终止执行
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 这里可以添加需要在接口处理后，但在视图渲染前的逻辑
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 这里可以添加接口处理完成后的回调逻辑，包括视图渲染完成后的逻辑
        // 清理缓存
        dictCacheManager.clearCache();
    }
}
