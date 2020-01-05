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
 * 访问足迹
 * </p>
 *
 * @author 
 * @since 2019-12-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_user_visit_log")
@ApiModel(value="UserVisitLog对象", description="访问足迹")
public class UserVisitLog extends Request {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "足迹id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "访问名")
    @TableField(value = "name",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String name;

    @ApiModelProperty(value = "访问路径")
    @TableField(value = "visit_url",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String visitUrl;

    @ApiModelProperty(value = "访问时间")
    @TableField("create_time")
    private Date createTime;


}
