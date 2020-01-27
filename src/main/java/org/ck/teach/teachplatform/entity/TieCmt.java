package org.ck.teach.teachplatform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import org.ck.teach.teachplatform.common.Request;
import java.util.Date;
import java.util.List;

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
 * 帖子回复
 * </p>
 *
 * @author 
 * @since 2019-12-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "t_tie_cmt",resultMap = "BaseResultMap")
@ApiModel(value="TieCmt对象", description="帖子回复")
public class TieCmt extends Request {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "回复id")
    @TableField("cmt_id")
    private Integer cmtId;

    @ApiModelProperty(value = "帖子id")
    @TableField("tie_id")
    private Integer tieId;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Integer userId;

    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private List<TieCmt> subTieCmts;

    @ApiModelProperty(value = "内容")
    @TableField(value = "content",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String content;

    @ApiModelProperty(value = "时间")
    @TableField("create_time")
    private Date createTime;



}
