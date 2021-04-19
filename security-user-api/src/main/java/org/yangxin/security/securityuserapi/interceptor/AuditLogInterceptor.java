package org.yangxin.security.securityuserapi.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.yangxin.security.securityuserapi.log.AuditLog;
import org.yangxin.security.securityuserapi.log.AuditLogRepository;
import org.yangxin.security.securityuserapi.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author yangxin
 * 2021/4/15 下午9:29
 */
@SuppressWarnings("NullableProblems")
@Component
public class AuditLogInterceptor implements HandlerInterceptor {

    private final AuditLogRepository auditLogRepository;

    @Autowired
    public AuditLogInterceptor(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        AuditLog auditLog = new AuditLog();
        auditLog.setMethod(request.getMethod());
        auditLog.setPath(request.getRequestURI());

        auditLogRepository.save(auditLog);
        request.setAttribute("auditLogId", auditLog.getId());

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        long auditLogId = (long) request.getAttribute("auditLogId");
        Optional<AuditLog> auditLogOptional = auditLogRepository.findById(auditLogId);
        if (auditLogOptional.isPresent()) {
            AuditLog auditLog = auditLogOptional.get();
            auditLog.setStatus(response.getStatus());

            auditLogRepository.save(auditLog);
        }
    }
}
