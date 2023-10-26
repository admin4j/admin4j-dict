// package com.admin4j.dict.json.mvc;
//
//
// import com.admin4j.dict.anno.DictCache;
// import com.admin4j.dict.anno.DictCacheManager;
// import lombok.RequiredArgsConstructor;
// import org.apache.commons.lang3.ObjectUtils;
// import org.springframework.core.MethodParameter;
// import org.springframework.http.MediaType;
// import org.springframework.http.converter.HttpMessageConverter;
// import org.springframework.http.server.ServerHttpRequest;
// import org.springframework.http.server.ServerHttpResponse;
// import org.springframework.web.bind.annotation.RestControllerAdvice;
// import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
//
// import java.util.List;
// import java.util.Objects;
//
// /**
//  * 需要自己时间开启缓存
//  *
//  * @author andanyang
//  * @since 2022/8/30 10:23
//  */
// @RequiredArgsConstructor
// @RestControllerAdvice
// public class DictResponseBodyAdvice<Object> implements ResponseBodyAdvice<Object> {
//     private final DictCacheManager dictCacheManager;
//
//     @Override
//     public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
//         DictCache methodAnnotation = returnType.getMethodAnnotation(DictCache.class);
//         return methodAnnotation != null;
//     }
//
//     /**
//      * Invoked after an {@code HttpMessageConverter} is selected and just before
//      * its write method is invoked.
//      *
//      * @param body                  the body to be written
//      * @param returnType            the return type of the controller method
//      * @param selectedContentType   the content type selected through content negotiation
//      * @param selectedConverterType the converter type selected to write to the response
//      * @param request               the current request
//      * @param response              the current response
//      * @return the body that was passed in or a modified (possibly new) instance
//      */
//     @Override
//     public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
//         // 开始添加缓存
//         //  dictCacheManager.startTransaction();
//         DictCache methodAnnotation = returnType.getMethodAnnotation(DictCache.class);
//         if (methodAnnotation == null) {
//             return body;
//         }
//         try {
//             if (body instanceof CommonPage) {
//                 CommonPage.PageResultVO<?> data = ((CommonPage<?>) body).getData();
//                 List<?> records = data.getRecords();
//                 if (ObjectUtils.isEmpty(records)) {
//                     return body;
//                 }
//
//                 if (Objects.equals(methodAnnotation.value(), DictCache.class)) {
//                     dictCacheManager.startCache(records);
//                 } else {
//                     dictCacheManager.startCache(records, methodAnnotation.value());
//                 }
//
//
//             } else {
//                 Object data = ((CommonResult<?>) body).getData();
//                 if (data instanceof List) {
//                     if (Objects.equals(methodAnnotation.value(), DictCache.class)) {
//                         dictCacheManager.startCache((List<?>) data);
//                     } else {
//                         dictCacheManager.startCache((List<?>) data, methodAnnotation.value());
//                     }
//                 }
//             }
//         } catch (IllegalAccessException e) {
//             throw new SysBizException(e);
//         }
//         return body;
//     }
// }
