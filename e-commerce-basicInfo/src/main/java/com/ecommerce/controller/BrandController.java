package com.ecommerce.controller;

import com.ecommerce.common.base.BaseController;
import com.ecommerce.common.base.CommonPage;
import com.ecommerce.common.base.CommonResult;
import com.ecommerce.common.base.ResultCode;
import com.ecommerce.common.exception.BusinessException;
import com.ecommerce.common.validationGroup.InsertGroup;
import com.ecommerce.common.validationGroup.UpdateGroup;
import com.ecommerce.service.BrandService;
import com.ecommerce.vojo.brand.BrandAddVO;
import com.ecommerce.vojo.brand.BrandEntryVO;
import com.ecommerce.vojo.brand.BrandUpdateVO;
import com.ecommerce.vojo.brand.GetAllBrandVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Api(value = "MVO-品牌信息管理控制器", tags = "MVO-品牌信息管理控制器")
@RestController
@RequestMapping("/brand")
public class BrandController extends BaseController {

    @Autowired
    private BrandService brandService;


    @ApiOperation("找到此公司所有品牌")
    @PostMapping("/getBrands")
    public CommonResult<CommonPage<BrandEntryVO>> getBrands(@RequestBody GetAllBrandVO getAllBrandVO) {
        CommonPage<BrandEntryVO> result = brandService.getAllBrand(getAllBrandVO);
        if (!result.getList().isEmpty()) {
            return CommonResult.success(result, "Match Successfully");
        } else {
            return CommonResult.failed(ResultCode.THINGS_NOT_FOUND);
        }
    }

    @ApiOperation("添加新的品牌")
    @PutMapping("/addBrand")
    public CommonResult addBrand(@Validated({InsertGroup.class}) @RequestBody BrandAddVO brandAddVO,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw BusinessException.INSERT_FAIL.newInstance(this.getErrorResponse(bindingResult), brandAddVO.toString());
        } else {
            if (brandService.addBrandInfo(brandAddVO)) {
                return CommonResult.success("Add Successfully");
            } else {
                throw BusinessException.INSERT_FAIL;
            }
        }
    }

    @ApiOperation("更新品牌信息")
    @PatchMapping("/updateBrand")
    public CommonResult updateBrand(@Validated({UpdateGroup.class}) @RequestBody BrandUpdateVO brandUpdateVO,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw BusinessException.UPDATE_FAIL.newInstance(this.getErrorResponse(bindingResult), brandUpdateVO.toString());
        } else {
            if (brandService.updateBrandInfo(brandUpdateVO)) {
                return CommonResult.success("Update Successfully");
            } else {
                throw BusinessException.UPDATE_FAIL;
            }
        }
    }

    @ApiOperation("删除品牌信息")
    @DeleteMapping("deleteBrand")
    public CommonResult deleteBrand(@RequestParam(value = "brdId") Integer brdId) {
        if (brandService.deleteBrandInfo(brdId)) {
            return CommonResult.success("Delete Successfully");
        } else {
            throw BusinessException.DELETE_FAIL;
        }
    }

}
