package com.ruoyi.business.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 学习攻略编码生成工具类
 *
 * @author ruoyi
 */
public class StudyStrategyCodeGenerator
{
    private static final String PREFIX = "ST";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final int SEQ_LENGTH = 4;

    /**
     * 生成学习攻略编码
     * 格式：ST + 年月日(YYYYMMDD) + 4位序列号(从1开始)
     *
     * @param seq 序列号
     * @return 学习攻略编码
     */
    public static String generateStudyStrategyCode(Integer seq)
    {
        String dateStr = LocalDate.now().format(DATE_FORMATTER);
        String seqStr = String.format("%0" + SEQ_LENGTH + "d", seq);
        return PREFIX + dateStr + seqStr;
    }

    /**
     * 生成当天的日期前缀
     *
     * @return 日期前缀
     */
    public static String getDatePrefix()
    {
        return PREFIX + LocalDate.now().format(DATE_FORMATTER);
    }
}