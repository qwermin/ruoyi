package com.ruoyi.common.service;

import java.util.List;
import com.ruoyi.common.core.domain.entity.Item;

/**
 * 物品Service接口
 * 
 * @author ruoyi
 * @date 2026-02-11
 */
public interface IItemService 
{
    /**
     * 查询物品
     * 
     * @param itemId 物品ID
     * @return 物品
     */
    public Item selectItemById(Long itemId);

    /**
     * 查询物品根据编码
     * 
     * @param itemCode 物品编码
     * @return 物品
     */
    public Item selectItemByCode(String itemCode);

    /**
     * 查询物品列表
     * 
     * @param item 物品
     * @return 物品集合
     */
    public List<Item> selectItemList(Item item);

    /**
     * 新增物品
     * 
     * @param item 物品
     * @return 结果
     */
    public int insertItem(Item item);

    /**
     * 修改物品
     * 
     * @param item 物品
     * @return 结果
     */
    public int updateItem(Item item);

    /**
     * 批量逻辑删除物品
     * 
     * @param itemIds 需要删除的物品ID
     * @return 结果
     */
    public int deleteItemByIds(Long[] itemIds);

    /**
     * 逻辑删除物品信息
     * 
     * @param itemId 物品ID
     * @return 结果
     */
    public int deleteItemById(Long itemId);
}