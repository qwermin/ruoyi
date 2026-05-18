package com.ruoyi.business.mapper;

import java.util.List;
import com.ruoyi.business.domain.entity.Price;

/**
 * 物价Mapper接口
 */
public interface PriceMapper
{
    public Price selectPriceById(Long priceId);

    public List<Price> selectPriceList(Price price);

    public int insertPrice(Price price);

    public int updatePrice(Price price);

    public int deletePriceById(Long priceId);

    public int deletePriceByIds(Long[] priceIds);
}
