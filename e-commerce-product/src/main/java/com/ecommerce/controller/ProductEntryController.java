package com.ecommerce.controller;

import com.ecommerce.common.base.*;
import com.ecommerce.common.exception.BusinessException;
import com.ecommerce.common.validationGroup.DeleteGroup;
import com.ecommerce.common.validationGroup.InsertGroup;
import com.ecommerce.common.validationGroup.UpdateGroup;
import com.ecommerce.service.ProductEntryService;
import com.ecommerce.vojo.entry.ProductAddVO;
import com.ecommerce.vojo.entry.ProductDeleteVO;
import com.ecommerce.vojo.entry.ProductUpdateVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Api(value = "品牌商-商品录入模块控制器", tags = "品牌商-商品录入模块控制器")
@RestController
@RequestMapping("/productEntry")
public class ProductEntryController extends BaseController {

    @Autowired
    private ProductEntryService productEntryService;

    @ApiOperation("通过商品标题模糊匹配商品")
    @GetMapping("/searchProductByTitle")
    public CommonResult<CommonPage> searchProductByTitle(@RequestParam(value = "title") String title, @RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize) {
        CommonPage result = productEntryService.searchProductByTitle(title, pageNum, pageSize);
        if (!result.getList().isEmpty()) {
            return CommonResult.success(result, "匹配成功");
        } else {
            return CommonResult.failed(ResultCode.THINGS_NOT_FOUND);
        }
    }

    @ApiOperation("通过商品ID更新商品信息")
    @PatchMapping("/updateProductInfo")
    public CommonResult updateProductInfo(@Validated({UpdateGroup.class}) @RequestBody ProductUpdateVO productUpdateVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw BusinessException.UPDATE_FAIL.newInstance(this.getErrorResponse(bindingResult), productUpdateVO.toString());
        } else {
            if (productEntryService.updateProductInfo(productUpdateVO)) {
                return CommonResult.success("更新成功");
            } else {
                throw BusinessException.UPDATE_FAIL;
            }
        }
    }

    @ApiOperation("通过商品ID删除商品信息")
    @DeleteMapping("/deleteProductInfo")
    public CommonResult deleteProductInfo(@Validated({DeleteGroup.class}) @RequestBody ProductDeleteVO productDeleteVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw BusinessException.DELETE_FAIL.newInstance(this.getErrorResponse(bindingResult), productDeleteVO.toString());
        } else {
            if (productEntryService.deleteProductInfo(productDeleteVO)) {
                return CommonResult.success("更新成功");
            } else {
                throw BusinessException.DELETE_FAIL;
            }
        }
    }

    @ApiOperation("添加新的商品")
    @PutMapping("/addProductInfo")
    public CommonResult addProductInfo(@Validated({InsertGroup.class}) @RequestBody ProductAddVO productAddVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw BusinessException.INSERT_FAIL.newInstance(this.getErrorResponse(bindingResult), productAddVO.toString());
        } else {
            if (productEntryService.addProductInfo(productAddVO)) {
                return CommonResult.success("添加成功");
            } else {
                throw BusinessException.INSERT_FAIL;
            }
        }
    }
}
