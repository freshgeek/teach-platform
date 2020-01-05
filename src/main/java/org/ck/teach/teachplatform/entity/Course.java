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
 * 课程
 * </p>
 *
 * @author 
 * @since 2019-12-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "t_course",resultMap = "BaseResultMap" )
@ApiModel(value="Course对象", description="课程")
public class Course extends Request {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "课程类别")
    @TableField("type_id")
    private Integer typeId;

    @TableField(exist = false)
    private CourseType courseType;

    @ApiModelProperty(value = "课程创建人")
    @TableField("create_user_id")
    private Integer createUserId;

    @TableField(exist = false)
    private User createUser;


    @ApiModelProperty(value = "课程标题")
    @TableField(value = "title",whereStrategy = FieldStrategy.NOT_EMPTY,condition = SqlCondition.LIKE)
    private String title;

    @ApiModelProperty(value = "课程名")
    @TableField(value = "name",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String name;

    @ApiModelProperty(value = "课程简介")
    @TableField(value = "intro",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String intro;

    @ApiModelProperty(value = "课时时长")
    @TableField("lesson_num")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程内容")
    @TableField(value = "content",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String content;

    @ApiModelProperty(value = "课程封面")
    @TableField(value = "cover_url",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String coverUrl;

    @ApiModelProperty(value = "课程简介ppt")
    @TableField(value = "ppt_url",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String pptUrl;

    @ApiModelProperty(value = "课程简介视频")
    @TableField(value = "video_url",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String videoUrl;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "学习人数")
    @TableField("learn_num")
    private Integer learnNum;


}
