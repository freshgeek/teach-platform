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
 * 创客资源
 * </p>
 *
 * @author 
 * @since 2019-12-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "t_resource",resultMap = "BaseResultMap")
@ApiModel(value="Resource对象", description="创客资源")
public class Resource extends Request {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资源id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "资源分类")
    @TableField("type_id")
    private Integer typeId;

    @TableField(exist = false)
    private ResourceType resourceType;

    @ApiModelProperty(value = "上传资源人")
    @TableField("user_id")
    private Integer userId;

    @TableField(exist = false)
    private User user;

    @ApiModelProperty(value = "资源名")
    @TableField(value = "name",whereStrategy = FieldStrategy.NOT_EMPTY,condition = SqlCondition.LIKE)
    private String name;

    @ApiModelProperty(value = "资源标题")
    @TableField(value = "title",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String title;

    @ApiModelProperty(value = "资源内容")
    @TableField(value = "content",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String content;

    @ApiModelProperty(value = "资源简介")
    @TableField(value = "intro",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String intro;

    @ApiModelProperty(value = "资源大小")
    @TableField(value = "size",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String size;

    @ApiModelProperty(value = "上传时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "资源置顶")
    @TableField(value = "top",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String top;

    @ApiModelProperty(value = "状态")
    @TableField(value = "status",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String status;

    @ApiModelProperty(value = "访问量")
    @TableField("visit_num")
    private Integer visitNum;

    @ApiModelProperty(value = "点赞量")
    @TableField("like_num")
    private Integer likeNum;


}
