package org.ck.teach.teachplatform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import org.apache.ibatis.jdbc.SQL;
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
 * 成果作品
 * </p>
 *
 * @author 
 * @since 2019-12-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "t_user_achv",resultMap = "BaseResultMap")
@ApiModel(value="UserAchv对象", description="成果作品")
public class UserAchv extends Request {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "作品id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Integer userId;

    @TableField(exist = false)
    private User user;

    @ApiModelProperty(value = "类型id")
    @TableField("type_id")
    private Integer typeId;

    @TableField(exist = false)
    private UserAchvType userAchvType;

    @ApiModelProperty(value = "竞赛作品")
    @TableField(value = "contest",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String contest;

    @ApiModelProperty(value = "作品名")
    @TableField(value = "name",whereStrategy = FieldStrategy.NOT_EMPTY,condition = SqlCondition.LIKE)
    private String name;

    @ApiModelProperty(value = "作品封面图")
    @TableField(value = "cover_url",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String coverUrl;

    @ApiModelProperty(value = "作品内容")
    @TableField(value = "content",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String content;

    @ApiModelProperty(value = "上传时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "点赞数")
    @TableField("like_num")
    private Integer likeNum;

    @ApiModelProperty(value = "下载数")
    @TableField("visit_num")
    private Integer visitNum;

    @ApiModelProperty(value = "收藏数")
    @TableField("fav_num")
    private Integer favNum;


}
