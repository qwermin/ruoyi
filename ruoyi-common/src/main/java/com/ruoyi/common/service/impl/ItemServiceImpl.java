package com.ruoyi.common.service.impl;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.utils.ItemCodeGenerator;
import com.ruoyi.common.mapper.ItemMapper;
import com.ruoyi.common.core.domain.entity.Item;
import com.ruoyi.common.service.IItemService;

/**
 * 物品Service业务层处理
 *
 * @author ruoyi
 * @date 2026-02-11
 */
@Service
public class ItemServiceImpl implements IItemService
{
    @Autowired
    private ItemMapper itemMapper;

    /**
     * 查询物品
     *
     * @param itemId 物品ID
     * @return 物品
     */
    @Override
    public Item selectItemById(Long itemId)
    {
        return itemMapper.selectItemById(itemId);
    }

    /**
     * 查询物品根据编码
     *
     * @param itemCode 物品编码
     * @return 物品
     */
    @Override
    public Item selectItemByCode(String itemCode)
    {
        return itemMapper.selectItemByCode(itemCode);
    }

    /**
     * 查询物品列表
     *
     * @param item 物品
     * @return 物品
     */
    @Override
    public List<Item> selectItemList(Item item)
    {
        return itemMapper.selectItemList(item);
    }

    /**
     * 新增物品
     *
     * @param item 物品
     * @return 结果
     */
    @Override
    public int insertItem(Item item)
    {
        // 生成物品编码
        String datePrefix = ItemCodeGenerator.getDatePrefix();
        Integer maxSeq = itemMapper.getMaxSeqByDate(datePrefix);
        int nextSeq = (maxSeq == null) ? 1 : maxSeq + 1;

        String itemCode = ItemCodeGenerator.generateItemCode(nextSeq);
        item.setItemCode(itemCode);

        item.setDelFlag("0");
        calculatePriceFields(item); // Calculate price difference and profit/loss
        return itemMapper.insertItem(item);
    }

    /**
     * 修改物品
     *
     * @param item 物品
     * @return 结果
     */
    @Override
    public int updateItem(Item item)
    {
        calculatePriceFields(item); // Calculate price difference and profit/loss
        return itemMapper.updateItem(item);
    }

    /**
     * 批量逻辑删除物品
     *
     * @param itemIds 需要删除的物品ID
     * @return 结果
     */
    @Override
    public int deleteItemByIds(Long[] itemIds)
    {
        return itemMapper.deleteItemByIds(itemIds);
    }

    /**
     * 逻辑删除物品信息
     *
     * @param itemId 物品ID
     * @return 结果
     */
    @Override
    public int deleteItemById(Long itemId)
    {
        return itemMapper.deleteItemById(itemId);
    }

    /**
     * 计算价格相关字段
     *
     * @param item 物品
     */
    private void calculatePriceFields(Item item)
    {
        BigDecimal purchasePrice = item.getPurchasePrice();
        BigDecimal sellPrice = item.getSellPrice();

        if (purchasePrice != null && sellPrice != null) {
            // 计算差价：售出价格 - 购买价格
            BigDecimal priceDiff = sellPrice.subtract(purchasePrice);
            item.setPriceDifference(priceDiff);

            // 盈亏等于差价
            item.setProfitLoss(priceDiff);
        } else if (purchasePrice != null && sellPrice == null) {
            // 如果只有购买价格没有售出价格，差价和盈亏为负数（表示投入）
            item.setPriceDifference(BigDecimal.ZERO.subtract(purchasePrice));
            item.setProfitLoss(BigDecimal.ZERO.subtract(purchasePrice));
        } else {
            // 其他情况设为0
            item.setPriceDifference(BigDecimal.ZERO);
            item.setProfitLoss(BigDecimal.ZERO);
        }
    }
}