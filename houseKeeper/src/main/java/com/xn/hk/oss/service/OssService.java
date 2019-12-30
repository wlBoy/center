package com.xn.hk.oss.service;


import javax.servlet.http.HttpServletRequest;

import com.xn.hk.oss.model.OssCallbackResult;
import com.xn.hk.oss.model.OssPolicyResult;

/**
 * oss上传管理Service
 * Created by macro on 2018/5/17.
 */
public interface OssService {
    /**
     * oss上传策略生成
     */
    OssPolicyResult policy();
    /**
     * oss上传成功回调
     */
    OssCallbackResult callback(HttpServletRequest request);
}
