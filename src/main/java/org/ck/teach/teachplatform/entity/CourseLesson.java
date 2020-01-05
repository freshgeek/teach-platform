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
 * 课程小节
 * </p>
 *
 * @author 
 * @since 2019-12-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_course_lesson")
@ApiModel(value="CourseLesson对象", description="课程小节")
public class CourseLesson extends Request {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程小节id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "课程id")
    @TableField("course_id")
    private Integer courseId;

    @ApiModelProperty(value = "课程小节标题")
    @TableField(value = "title",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String title;

    @ApiModelProperty(value = "课程小节名")
    @TableField(value = "name",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String name;

    @ApiModelProperty(value = "课程小节简介")
    @TableField(value = "intro",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String intro;

    @ApiModelProperty(value = "课程小节内容")
    @TableField(value = "content",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String content;

    @ApiModelProperty(value = "课程小节封面")
    @TableField(value = "cover_url",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String coverUrl;

    @ApiModelProperty(value = "课程小节ppt")
    @TableField(value = "ppt_url",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String pptUrl;

    @ApiModelProperty(value = "课程小节视频")
    @TableField(value = "video_url",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String videoUrl;

    @ApiModelProperty(value = "课程排序")
    @TableField("sort_field")
    private Integer sortField;


}
