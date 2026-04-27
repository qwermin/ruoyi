package com.ruoyi.business.service;

import java.util.List;
import com.ruoyi.business.domain.StudyStrategy;

/**
 * 学习攻略Service接口
 *
 * @author ruoyi
 * @date 2026-02-12
 */
public interface IStudyStrategyService
{
    /**
     * 查询学习攻略
     *
     * @param strategyId 学习攻略主键
     * @return 学习攻略
     */
    public StudyStrategy selectStudyStrategyByStrategyId(Long strategyId);

    /**
     * 查询学习攻略列表
     *
     * @param studyStrategy 学习攻略
     * @return 学习攻略集合
     */
    public List<StudyStrategy> selectStudyStrategyList(StudyStrategy studyStrategy);

    /**
     * 新增学习攻略
     *
     * @param studyStrategy 学习攻略
     * @return 结果
     */
    public int insertStudyStrategy(StudyStrategy studyStrategy);

    /**
     * 修改学习攻略
     *
     * @param studyStrategy 学习攻略
     * @return 结果
     */
    public int updateStudyStrategy(StudyStrategy studyStrategy);

    /**
     * 批量删除学习攻略
     *
     * @param strategyIds 需要删除的学习攻略主键
     * @return 结果
     */
    public int deleteStudyStrategyByStrategyIds(Long[] strategyIds);

    /**
     * 删除学习攻略信息
     *
     * @param strategyId 学习攻略主键
     * @return 结果
     */
    public int deleteStudyStrategyByStrategyId(Long strategyId);
}