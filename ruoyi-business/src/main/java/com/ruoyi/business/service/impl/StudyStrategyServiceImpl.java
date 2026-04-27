package com.ruoyi.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.StudyStrategyMapper;
import com.ruoyi.business.domain.StudyStrategy;
import com.ruoyi.business.service.IStudyStrategyService;
import com.ruoyi.business.utils.StudyStrategyCodeGenerator;

/**
 * 学习攻略Service业务层处理
 *
 * @author ruoyi
 * @date 2026-02-12
 */
@Service
public class StudyStrategyServiceImpl implements IStudyStrategyService
{
    @Autowired
    private StudyStrategyMapper studyStrategyMapper;

    /**
     * 查询学习攻略
     *
     * @param strategyId 学习攻略主键
     * @return 学习攻略
     */
    @Override
    public StudyStrategy selectStudyStrategyByStrategyId(Long strategyId)
    {
        return studyStrategyMapper.selectStudyStrategyByStrategyId(strategyId);
    }

    /**
     * 查询学习攻略列表
     *
     * @param studyStrategy 学习攻略
     * @return 学习攻略
     */
    @Override
    public List<StudyStrategy> selectStudyStrategyList(StudyStrategy studyStrategy)
    {
        return studyStrategyMapper.selectStudyStrategyList(studyStrategy);
    }

    /**
     * 新增学习攻略
     *
     * @param studyStrategy 学习攻略
     * @return 结果
     */
    @Override
    public int insertStudyStrategy(StudyStrategy studyStrategy)
    {
        // 生成攻略编码
        String datePrefix = StudyStrategyCodeGenerator.getDatePrefix();
        Integer maxSeq = studyStrategyMapper.getMaxSeqByDate(datePrefix);
        int nextSeq = (maxSeq == null) ? 1 : maxSeq + 1;

        String strategyCode = StudyStrategyCodeGenerator.generateStudyStrategyCode(nextSeq);
        studyStrategy.setStrategyCode(strategyCode);

        studyStrategy.setDelFlag("0");
        return studyStrategyMapper.insertStudyStrategy(studyStrategy);
    }

    /**
     * 修改学习攻略
     *
     * @param studyStrategy 学习攻略
     * @return 结果
     */
    @Override
    public int updateStudyStrategy(StudyStrategy studyStrategy)
    {
        // 在更新时不改变策略编码
        return studyStrategyMapper.updateStudyStrategy(studyStrategy);
    }

    /**
     * 批量删除学习攻略
     *
     * @param strategyIds 需要删除的学习攻略主键
     * @return 结果
     */
    @Override
    public int deleteStudyStrategyByStrategyIds(Long[] strategyIds)
    {
        return studyStrategyMapper.deleteStudyStrategyByStrategyIds(strategyIds);
    }

    /**
     * 删除学习攻略信息
     *
     * @param strategyId 学习攻略主键
     * @return 结果
     */
    @Override
    public int deleteStudyStrategyByStrategyId(Long strategyId)
    {
        return studyStrategyMapper.deleteStudyStrategyByStrategyId(strategyId);
    }
}