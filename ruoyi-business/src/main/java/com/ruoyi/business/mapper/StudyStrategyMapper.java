package com.ruoyi.business.mapper;

import java.util.List;
import com.ruoyi.business.domain.StudyStrategy;

/**
 * 学习攻略Mapper接口
 *
 * @author ruoyi
 * @date 2026-02-12
 */
public interface StudyStrategyMapper
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
     * 删除学习攻略
     *
     * @param strategyId 学习攻略主键
     * @return 结果
     */
    public int deleteStudyStrategyByStrategyId(Long strategyId);

    /**
     * 批量删除学习攻略
     *
     * @param strategyIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStudyStrategyByStrategyIds(Long[] strategyIds);

    /**
     * 获取当天最大序号
     *
     * @param dateStr 日期字符串(YYYYMMDD)
     * @return 最大序号
     */
    public Integer getMaxSeqByDate(String dateStr);

    /**
     * 根据类型统计数量
     *
     * @param strategyType 攻略类型
     * @return 数量
     */
    public int countByType(String strategyType);
}