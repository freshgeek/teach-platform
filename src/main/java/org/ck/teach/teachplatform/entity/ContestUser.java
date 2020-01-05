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
 * 竞赛参与
 * </p>
 *
 * @author 
 * @since 2019-12-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_contest_user")
@ApiModel(value="ContestUser对象", description="竞赛参与")
public class ContestUser extends Request {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "竞赛id")
    @TableField("contest_id")
    private Integer contestId;

    @ApiModelProperty(value = "参与用户id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "参与作品id")
    @TableField("achv_id")
    private Integer achvId;

    @ApiModelProperty(value = "参与时间")
    @TableField("create_time")
    private Date createTime;


}
