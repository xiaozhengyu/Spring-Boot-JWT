package com.xzy.sbjt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xzy
 * @date 2020-12-12 13:38
 * 说明：
 */
@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 3903797053438334664L;

    @Id
    @GenericGenerator(name = "genericGenerator", strategy = "uuid")
    @GeneratedValue(generator = "genericGenerator")
    private String id;

    private String realName;

    private String username;

    private String password;

    @Column(name = "is_deleted")
    private Integer deleted;

    @Column(name = "is_locked")
    private Integer locked;

    private String createBy;
    private String updateBy;
    private Date createTime;
    private Date updateTime;

    public boolean isDeleted() {
        return this.deleted == 1;
    }

    public boolean isLocked() {
        return this.locked == 1;
    }
}
