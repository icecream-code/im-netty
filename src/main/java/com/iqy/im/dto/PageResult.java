package com.iqy.im.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询结果返回对象
 *
 * @author iQiyi
 * @since 2020-03-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = -6021420952882571995L;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 数据
     */
    private List<T> rows;
}
