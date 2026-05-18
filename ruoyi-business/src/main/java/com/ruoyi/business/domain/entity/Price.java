package com.ruoyi.business.domain.entity;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 物价对象 price
 */
public class Price extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long priceId;

    /** 物品名称 */
    @Excel(name = "物品名称")
    private String itemName;

    /** 服务器 */
    @Excel(name = "服务器")
    private String serverName;

    /** 物品类型 */
    @Excel(name = "物品类型")
    private String itemType;

    /** 物品金额 */
    @Excel(name = "物品金额")
    private BigDecimal itemAmount;

    /** 换算金额 */
    @Excel(name = "换算金额")
    private BigDecimal convertedAmount;

    /** 删除标志（0存在 2删除） */
    private String delFlag;

    public Long getPriceId()
    {
        return priceId;
    }

    public void setPriceId(Long priceId)
    {
        this.priceId = priceId;
    }

    public String getItemName()
    {
        return itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public String getServerName()
    {
        return serverName;
    }

    public void setServerName(String serverName)
    {
        this.serverName = serverName;
    }

    public String getItemType()
    {
        return itemType;
    }

    public void setItemType(String itemType)
    {
        this.itemType = itemType;
    }

    public BigDecimal getItemAmount()
    {
        return itemAmount;
    }

    public void setItemAmount(BigDecimal itemAmount)
    {
        this.itemAmount = itemAmount;
    }

    public BigDecimal getConvertedAmount()
    {
        return convertedAmount;
    }

    public void setConvertedAmount(BigDecimal convertedAmount)
    {
        this.convertedAmount = convertedAmount;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("priceId", getPriceId())
            .append("itemName", getItemName())
            .append("serverName", getServerName())
            .append("itemType", getItemType())
            .append("itemAmount", getItemAmount())
            .append("convertedAmount", getConvertedAmount())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
