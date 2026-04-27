package com.ruoyi.business.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.business.domain.StudyStrategy;
import com.ruoyi.business.service.IStudyStrategyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 学习攻略Controller
 *
 * @author ruoyi
 * @date 2026-02-12
 */
@RestController
@RequestMapping("/business/strategy")
public class StudyStrategyController extends BaseController
{
    @Autowired
    private IStudyStrategyService studyStrategyService;

    /**
     * 查询学习攻略列表
     */
    @PreAuthorize("@ss.hasPermi('business:strategy:list')")
    @GetMapping("/list")
    public TableDataInfo list(StudyStrategy studyStrategy)
    {
        startPage();
        List<StudyStrategy> list = studyStrategyService.selectStudyStrategyList(studyStrategy);
        return getDataTable(list);
    }


    /**
     * 获取学习攻略详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:strategy:query')")
    @GetMapping(value = "/{strategyId}")
    public AjaxResult getInfo(@PathVariable("strategyId") Long strategyId)
    {
        return AjaxResult.success(studyStrategyService.selectStudyStrategyByStrategyId(strategyId));
    }

    /**
     * 新增学习攻略
     */
    @PreAuthorize("@ss.hasPermi('business:strategy:add')")
    @Log(title = "学习攻略", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StudyStrategy studyStrategy)
    {
        return toAjax(studyStrategyService.insertStudyStrategy(studyStrategy));
    }

    /**
     * 修改学习攻略
     */
    @PreAuthorize("@ss.hasPermi('business:strategy:edit')")
    @Log(title = "学习攻略", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StudyStrategy studyStrategy)
    {
        return toAjax(studyStrategyService.updateStudyStrategy(studyStrategy));
    }

    /**
     * 删除学习攻略
     */
    @PreAuthorize("@ss.hasPermi('business:strategy:remove')")
    @Log(title = "学习攻略", businessType = BusinessType.DELETE)
    @DeleteMapping("/{strategyIds}")
    public AjaxResult remove(@PathVariable Long[] strategyIds)
    {
        return toAjax(studyStrategyService.deleteStudyStrategyByStrategyIds(strategyIds));
    }
}