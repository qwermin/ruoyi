package com.ruoyi.business.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.business.domain.entity.Price;
import com.ruoyi.business.service.IPriceService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 物价管理Controller
 */
@RestController
@RequestMapping("/business/price")
public class PriceController extends BaseController
{
    @Autowired
    private IPriceService priceService;

    @PreAuthorize("@ss.hasPermi('business:price:list')")
    @GetMapping("/list")
    public TableDataInfo list(Price price)
    {
        startPage();
        List<Price> list = priceService.selectPriceList(price);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('business:price:export')")
    @Log(title = "物价管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Price price)
    {
        List<Price> list = priceService.selectPriceList(price);
        ExcelUtil<Price> util = new ExcelUtil<Price>(Price.class);
        util.exportExcel(response, list, "物价管理数据");
    }

    @PreAuthorize("@ss.hasPermi('business:price:query')")
    @GetMapping(value = "/{priceId}")
    public AjaxResult getInfo(@PathVariable("priceId") Long priceId)
    {
        return AjaxResult.success(priceService.selectPriceById(priceId));
    }

    @PreAuthorize("@ss.hasPermi('business:price:add')")
    @Log(title = "物价管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Price price)
    {
        return toAjax(priceService.insertPrice(price));
    }

    @PreAuthorize("@ss.hasPermi('business:price:edit')")
    @Log(title = "物价管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Price price)
    {
        return toAjax(priceService.updatePrice(price));
    }

    @PreAuthorize("@ss.hasPermi('business:price:remove')")
    @Log(title = "物价管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{priceIds}")
    public AjaxResult remove(@PathVariable Long[] priceIds)
    {
        return toAjax(priceService.deletePriceByIds(priceIds));
    }
}
