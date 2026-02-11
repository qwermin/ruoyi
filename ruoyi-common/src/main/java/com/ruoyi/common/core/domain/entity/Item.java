package com.ruoyi.common.core.domain.entity;

import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.annotation.Excel.Type;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 物品对象 item
 *
 * @author ruoyi
 * @date 2026-02-11
 */
public class Item extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 物品ID */
    private Long itemId;

    /** 物品编码 */
    @Excel(name = "物品编码")
    private String itemCode;

    /** 物品名称 */
    @Excel(name = "物品名称")
    private String name;

    /** 图片路径 */
    @Excel(name = "图片路径")
    private String imagePath;

    /** 购买价格 */
    @Excel(name = "购买价格")
    private BigDecimal purchasePrice;

    /** 购买时间 */
    @Excel(name = "购买时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    private Date purchaseTime;

    /** 售出价格 */
    @Excel(name = "售出价格")
    private BigDecimal sellPrice;

    /** 售出时间 */
    @Excel(name = "售出时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    private Date sellTime;

    /** 差价 */
    @Excel(name = "差价")
    private BigDecimal priceDifference;

    /** 盈亏 */
    @Excel(name = "盈亏")
    private BigDecimal profitLoss;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public Long getItemId()
    {
        return itemId;
    }

    public void setItemId(Long itemId)
    {
        this.itemId = itemId;
    }

    public String getItemCode()
    {
        return itemCode;
    }

    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getImagePath()
    {
        return imagePath;
    }

    public void setImagePath(String imagePath)
    {
        this.imagePath = imagePath;
    }

    public BigDecimal getPurchasePrice()
    {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice)
    {
        this.purchasePrice = purchasePrice;
    }

    public Date getPurchaseTime()
    {
        return purchaseTime;
    }

    public void setPurchaseTime(Date purchaseTime)
    {
        this.purchaseTime = purchaseTime;
    }

    public BigDecimal getSellPrice()
    {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice)
    {
        this.sellPrice = sellPrice;
    }

    public Date getSellTime()
    {
        return sellTime;
    }

    public void setSellTime(Date sellTime)
    {
        this.sellTime = sellTime;
    }

    public BigDecimal getPriceDifference()
    {
        return priceDifference;
    }

    public void setPriceDifference(BigDecimal priceDifference)
    {
        this.priceDifference = priceDifference;
    }

    public BigDecimal getProfitLoss()
    {
        return profitLoss;
    }

    public void setProfitLoss(BigDecimal profitLoss)
    {
        this.profitLoss = profitLoss;
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
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("itemId", getItemId())
            .append("itemCode", getItemCode())
            .append("name", getName())
            .append("imagePath", getImagePath())
            .append("purchasePrice", getPurchasePrice())
            .append("purchaseTime", getPurchaseTime())
            .append("sellPrice", getSellPrice())
            .append("sellTime", getSellTime())
            .append("priceDifference", getPriceDifference())
            .append("profitLoss", getProfitLoss())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}