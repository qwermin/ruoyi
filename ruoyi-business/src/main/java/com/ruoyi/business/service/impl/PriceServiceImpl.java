package com.ruoyi.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.domain.entity.Price;
import com.ruoyi.business.mapper.PriceMapper;
import com.ruoyi.business.service.IPriceService;

/**
 * 物价Service业务层处理
 */
@Service
public class PriceServiceImpl implements IPriceService
{
    @Autowired
    private PriceMapper priceMapper;

    @Override
    public Price selectPriceById(Long priceId)
    {
        return priceMapper.selectPriceById(priceId);
    }

    @Override
    public List<Price> selectPriceList(Price price)
    {
        return priceMapper.selectPriceList(price);
    }

    @Override
    public int insertPrice(Price price)
    {
        price.setDelFlag("0");
        return priceMapper.insertPrice(price);
    }

    @Override
    public int updatePrice(Price price)
    {
        return priceMapper.updatePrice(price);
    }

    @Override
    public int deletePriceByIds(Long[] priceIds)
    {
        return priceMapper.deletePriceByIds(priceIds);
    }

    @Override
    public int deletePriceById(Long priceId)
    {
        return priceMapper.deletePriceById(priceId);
    }
}
