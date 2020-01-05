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
 * 文件记录
 * </p>
 *
 * @author 
 * @since 2019-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_file")
@ApiModel(value="File对象", description="文件记录")
public class File extends Request {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "上传用户id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "上传文件名")
    @TableField(value = "name",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String name;

    @ApiModelProperty(value = "上传文件大小")
    @TableField("size")
    private Integer size;

    @ApiModelProperty(value = "路径")
    @TableField(value = "path",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String path;

    @ApiModelProperty(value = "上传时间")
    @TableField("create_time")
    private Date createTime;


}
