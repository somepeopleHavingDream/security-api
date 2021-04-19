package org.yangxin.security.securityuserapi.log;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author yangxin
 * 2021/4/15 下午9:20
 */
@Entity(name = "t_audit_log")
@Data
@EntityListeners(AuditingEntityListener.class)
public class AuditLog implements Serializable {

    private static final long serialVersionUID = 5955818299951885997L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date modifyTime;

    private String method;

    private String path;

    private Integer status;

    @CreatedBy
    private String username;
}
