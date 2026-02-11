package com.ruoyi.common.core.domain.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.annotation.Excel.Type;
import com.ruoyi.common.core.domain.BaseEntity;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;

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

    /** 物品类别 */
    @Excel(name = "物品类别", readConverterExp = "0=实体物品,1=虚拟物品")
    private String itemType;

    /** 图片路径 */
    @Excel(name = "图片路径")
    private String imagePath;

    /** 购买价格 */
    @Excel(name = "购买价格")
    @Digits(integer=10, fraction=2, message = "购买价格整数位最多10位，小数位最多2位")
    @DecimalMin(value = "0.00", message = "购买价格不能小于0")
    private BigDecimal purchasePrice;

    /** 购买时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "购买时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    private Date purchaseTime;

    /** 售出价格 */
    @Excel(name = "售出价格")
    @Digits(integer=10, fraction=2, message = "售出价格整数位最多10位，小数位最多2位")
    @DecimalMin(value = "0.00", message = "售出价格不能小于0")
    private BigDecimal sellPrice;

    /** 售出时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "售出时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    private Date sellTime;

    /** 差价 */
    @Excel(name = "差价")
    @Digits(integer=10, fraction=2, message = "差价整数位最多10位，小数位最多2位")
    private BigDecimal priceDifference;

    /** 盈亏 */
    @Excel(name = "盈亏")
    @Digits(integer=10, fraction=2, message = "盈亏整数位最多10位，小数位最多2位")
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

    public String getItemType()
    {
        return itemType;
    }

    public void setItemType(String itemType)
    {
        this.itemType = itemType;
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

    // 购买时间范围（开始）
    private String purchaseTimeBegin;

    // 购买时间范围（结束）
    private String purchaseTimeEnd;

    // 售出时间范围（开始）
    private String sellTimeBegin;

    // 售出时间范围（结束）
    private String sellTimeEnd;

    public String getPurchaseTimeBegin()
    {
        return purchaseTimeBegin;
    }

    public void setPurchaseTimeBegin(String purchaseTimeBegin)
    {
        this.purchaseTimeBegin = purchaseTimeBegin;
    }

    public String getPurchaseTimeEnd()
    {
        return purchaseTimeEnd;
    }

    public void setPurchaseTimeEnd(String purchaseTimeEnd)
    {
        this.purchaseTimeEnd = purchaseTimeEnd;
    }

    public String getSellTimeBegin()
    {
        return sellTimeBegin;
    }

    public void setSellTimeBegin(String sellTimeBegin)
    {
        this.sellTimeBegin = sellTimeBegin;
    }

    public String getSellTimeEnd()
    {
        return sellTimeEnd;
    }

    public void setSellTimeEnd(String sellTimeEnd)
    {
        this.sellTimeEnd = sellTimeEnd;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("itemId", getItemId())
            .append("itemCode", getItemCode())
            .append("name", getName())
            .append("itemType", getItemType())
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
            .append("purchaseTimeBegin", getPurchaseTimeBegin())
            .append("purchaseTimeEnd", getPurchaseTimeEnd())
            .append("sellTimeBegin", getSellTimeBegin())
            .append("sellTimeEnd", getSellTimeEnd())
            .toString();
    }
}