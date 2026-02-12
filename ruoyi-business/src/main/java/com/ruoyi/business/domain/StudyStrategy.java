package com.ruoyi.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学习攻略对象 study_strategy
 *
 * @author ruoyi
 * @date 2026-02-12
 */
public class StudyStrategy extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 攻略ID */
    private Long strategyId;

    /** 攻略编码 */
    @Excel(name = "攻略编码")
    private String strategyCode;

    /** 攻略名称 */
    @Excel(name = "攻略名称")
    private String strategyName;

    /** 攻略地址链接 */
    @Excel(name = "攻略地址链接")
    private String strategyUrl;

    public void setStrategyId(Long strategyId)
    {
        this.strategyId = strategyId;
    }

    public Long getStrategyId()
    {
        return strategyId;
    }

    public void setStrategyCode(String strategyCode)
    {
        this.strategyCode = strategyCode;
    }

    public String getStrategyCode()
    {
        return strategyCode;
    }

    public void setStrategyName(String strategyName)
    {
        this.strategyName = strategyName;
    }

    public String getStrategyName()
    {
        return strategyName;
    }

    public void setStrategyUrl(String strategyUrl)
    {
        this.strategyUrl = strategyUrl;
    }

    public String getStrategyUrl()
    {
        return strategyUrl;
    }

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("strategyId", getStrategyId())
            .append("strategyCode", getStrategyCode())
            .append("strategyName", getStrategyName())
            .append("strategyUrl", getStrategyUrl())
            .append("delFlag", getDelFlag())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}