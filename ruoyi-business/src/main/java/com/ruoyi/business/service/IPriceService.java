package com.ruoyi.business.service;

import java.util.List;
import com.ruoyi.business.domain.entity.Price;

/**
 * 物价Service接口
 */
public interface IPriceService
{
    public Price selectPriceById(Long priceId);

    public List<Price> selectPriceList(Price price);

    public int insertPrice(Price price);

    public int updatePrice(Price price);

    public int deletePriceByIds(Long[] priceIds);

    public int deletePriceById(Long priceId);
}
