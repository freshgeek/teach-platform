package org.ck.teach.teachplatform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import org.ck.teach.teachplatform.common.Request;
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
 * 用户表
 * </p>
 *
 * @author 
 * @since 2019-12-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_user")
@ApiModel(value="User对象", description="用户表")
public class User extends Request {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户角色")
    @TableField("role_id")
    private Integer roleId;

    @ApiModelProperty(value = "手机号码")
    @TableField(value = "phone",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String phone;

    @ApiModelProperty(value = "密码")
    @TableField(value = "password",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String password;

    @ApiModelProperty(value = "昵称")
    @TableField(value = "nick_name",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String nickName;

    @ApiModelProperty(value = "qq")
    @TableField(value = "qq",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String qq;

    @ApiModelProperty(value = "学校")
    @TableField(value = "school",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String school;

    @ApiModelProperty(value = "头像")
    @TableField(value = "head_url",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String headUrl;

    @ApiModelProperty(value = "用户标签")
    @TableField(value = "user_tag",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String userTag;

    @ApiModelProperty(value = "积分称号")
    @TableField(value = "point_level",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String pointLevel;

    @ApiModelProperty(value = "积分")
    @TableField("point")
    private Integer point;

    @ApiModelProperty(value = "个人简介")
    @TableField(value = "intro",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String intro;


}
