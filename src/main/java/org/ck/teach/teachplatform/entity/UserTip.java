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
 * 用户动态
 * </p>
 *
 * @author 
 * @since 2019-12-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_user_tip")
@ApiModel(value="UserTip对象", description="用户动态")
public class UserTip extends Request {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "动态id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "来源用户id")
    @TableField("resource_user_id")
    private Integer resourceUserId;

    @ApiModelProperty(value = "提醒用户id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "内容")
    @TableField(value = "content",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String content;

    @ApiModelProperty(value = "时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "已读")
    @TableField(value = "readed",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String readed;


}
