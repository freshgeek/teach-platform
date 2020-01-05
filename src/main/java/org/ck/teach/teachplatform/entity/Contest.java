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
 * 擂台竞赛
 * </p>
 *
 * @author 
 * @since 2019-12-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_contest")
@ApiModel(value="Contest对象", description="擂台竞赛")
public class Contest extends Request {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "竞赛id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "举办者id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "竞赛名")
    @TableField(value = "name",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String name;

    @ApiModelProperty(value = "竞赛标题")
    @TableField(value = "title",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String title;

    @ApiModelProperty(value = "竞赛封面")
    @TableField(value = "cover_url",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String coverUrl;

    @ApiModelProperty(value = "简介")
    @TableField(value = "intro",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String intro;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;


}
