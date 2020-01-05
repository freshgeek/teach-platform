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
 * 讨论帖子
 * </p>
 *
 * @author 
 * @since 2019-12-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_tie")
@ApiModel(value="Tie对象", description="讨论帖子")
public class Tie extends Request {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "帖子id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "发帖人id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "帖标签")
    @TableField(value = "tag",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String tag;

    @ApiModelProperty(value = "标题")
    @TableField(value = "title",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String title;

    @ApiModelProperty(value = "内容")
    @TableField(value = "content",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String content;

    @ApiModelProperty(value = "官方发帖")
    @TableField(value = "role",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String role;

    @ApiModelProperty(value = "发帖时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "贴回复数")
    @TableField("cmt_num")
    private Integer cmtNum;


}
