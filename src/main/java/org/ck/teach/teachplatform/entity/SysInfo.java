package org.ck.teach.teachplatform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import org.ck.teach.teachplatform.common.Request;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 创客资讯
 * </p>
 *
 * @author 
 * @since 2019-12-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "t_sys_info",resultMap = "BaseResultMap")
@ApiModel(value="SysInfo对象", description="创客资讯")
public class SysInfo extends Request {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "资讯类型")
    @TableField("type_id")
    private Integer typeId;

    @TableField(exist = false)
    private SysInfoType sysInfoType;

    @ApiModelProperty(value = "上传人")
    @TableField(value = "up_user",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String upUser;

    @ApiModelProperty(value = "资讯标题")
    @TableField(value = "title",whereStrategy = FieldStrategy.NOT_EMPTY,condition = SqlCondition.LIKE)
    private String title;

    @ApiModelProperty(value = "资讯封面")
    @TableField(value = "cover_url",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String coverUrl;

    @ApiModelProperty(value = "资讯内容")
    @TableField(value = "content",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String content;

    @ApiModelProperty(value = "排序字段")
    @TableField("sort_field")
    private Integer sortField;

    @ApiModelProperty(value = "资讯日期")
    @TableField("create_time")
    private Date createTime;


}
