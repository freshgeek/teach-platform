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
 * 项目记录
 * </p>
 *
 * @author 
 * @since 2019-12-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "t_activity_log",resultMap = "BaseResultMap")
@ApiModel(value="ActivityLog对象", description="项目记录")
public class ActivityLog extends Request {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "项目id")
    @TableField("atv_id")
    private Integer atvId;

    @ApiModelProperty(value = "参与者id")
    @TableField("user_id")
    private Integer userId;

    @TableField(exist = false)
    private User user;

    @ApiModelProperty(value = "内容")
    @TableField(value = "content",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String content;

    @ApiModelProperty(value = "记录时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "项目发起者评论")
    @TableField(value = "comment",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String comment;

    @ApiModelProperty(value = "评论时间")
    @TableField("comment_time")
    private Date commentTime;


}
