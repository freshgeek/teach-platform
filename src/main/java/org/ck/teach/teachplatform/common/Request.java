package org.ck.teach.teachplatform.common;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 通用 请求
 *
 * @author cc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request implements Serializable {

    private static final long serialVersionUID = 8436121449044850416L;

    @ApiModelProperty(value = "分页:页面定位")
    @TableField(exist = false)
    private Integer pageIndex = 1;

    @ApiModelProperty(value = "分页:页面大小")
    @TableField(exist = false)
    private Integer pageSize = 12;

    @ApiModelProperty(value = "排序字段")
    @TableField(exist = false)
    private String pageSort;

    public Page convertPage() {
        return new Page(this.pageIndex, this.pageSize);
    }

}