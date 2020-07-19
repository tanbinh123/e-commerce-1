package com.ecommerce.controller;

import com.ecommerce.common.base.BaseController;
import com.ecommerce.common.base.CommonResult;
import com.ecommerce.common.base.ResultCode;
import com.ecommerce.common.exception.BusinessException;
import com.ecommerce.common.validationGroup.InsertGroup;
import com.ecommerce.common.validationGroup.SelectGroup;
import com.ecommerce.common.validationGroup.UpdateGroup;
import com.ecommerce.service.WalletService;
import com.ecommerce.vojo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 钱包控制类
 * Created by yousabla on 2020/7/3.
 */

@Api(value = "钱包控制器,负责控制钱包账户的注册，登录（获取钱包信息），更改密码", tags = "钱包控制器")
@CrossOrigin
@RestController
@RequestMapping("/wallet")
public class WalletController extends BaseController {
    @Resource
    private WalletService walletService;

    @ApiOperation("注册新的钱包账户")
    @PutMapping("/register")
    public CommonResult<List<WalletBalanceVO>> register(@Validated({InsertGroup.class}) @RequestBody WalletAccountVO info, BindingResult bindingResult) throws BusinessException {
        if (bindingResult.hasErrors()) {
            throw BusinessException.INSERT_FAIL.newInstance(this.getErrorResponse(bindingResult), info.toString());
        } else {
            if (walletService.addWallet(info)) {
                return new CommonResult<>(20000,"register new wallet account successful",walletService.getWalletInfo(info.getAccountName()));
            } else {
                throw BusinessException.INSERT_FAIL;
            }
        }
    }

    @ApiOperation("获取账户钱包信息")
    @PostMapping("/getInfo")
    public CommonResult<List<WalletBalanceVO>> getInfo(@RequestBody StringVO info){
        List<WalletBalanceVO> balanceVOs = walletService.getWalletInfo(info.getAccountName());
        if (balanceVOs != null) {
            return CommonResult.success(balanceVOs,"get wallet info successful");
        } else {
            return CommonResult.failed("未注册需要注册才能访问钱包！");
        }
    }

    @ApiOperation("更改支付密码")
    @PatchMapping("/changePassword")
    public CommonResult changePassword(@Validated({UpdateGroup.class}) @RequestBody WalletPasswordVO info, BindingResult bindingResult) throws BusinessException {
        if (bindingResult.hasErrors()) {
            throw BusinessException.UPDATE_FAIL.newInstance(this.getErrorResponse(bindingResult), info.toString());
        } else {
            if (walletService.changePassword(info)) {
                return new CommonResult(20000,"change password successful");
            } else {
                throw BusinessException.UPDATE_FAIL;
            }
        }
    }

    @GetMapping("/test")
    public String test(){
        return "success";
    }

}
