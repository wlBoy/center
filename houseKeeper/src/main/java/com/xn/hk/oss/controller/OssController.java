package com.xn.hk.oss.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xn.hk.common.model.CommonResult;
import com.xn.hk.oss.model.OssCallbackResult;
import com.xn.hk.oss.model.OssPolicyResult;
import com.xn.hk.oss.service.impl.OssServiceImpl;

/**
 * Oss相关操作接口
 * Created by macro on 2018/4/26.
 */
@Controller
@RequestMapping("/aliyun/oss")
public class OssController {
    @Autowired
    private OssServiceImpl ossService;

    /**
     * oss上传签名生成
     * @return
     */
    @RequestMapping(value = "/policy", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<OssPolicyResult> policy() {
        OssPolicyResult result = ossService.policy();
        return CommonResult.success(result);
    }

    /**
     * oss上传成功回调
     * @param request
     * @return
     */
    @RequestMapping(value = "callback", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<OssCallbackResult> callback(HttpServletRequest request) {
        OssCallbackResult ossCallbackResult = ossService.callback(request);
        return CommonResult.success(ossCallbackResult);
    }

}
