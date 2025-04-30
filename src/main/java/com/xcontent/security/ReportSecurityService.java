package com.xcontent.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;
import com.xcontent.model.ReportTemplate;
import com.xcontent.model.User;
import java.nio.file.Path;

@Service
public class ReportSecurityService {
    private final TextEncryptor encryptor;

    public ReportSecurityService(TextEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    @PreAuthorize("hasPermission(#template, 'READ')")
    public boolean canAccessTemplate(ReportTemplate template, User user) {
        return template.getOwner().equals(user) || 
               template.isPublic() || 
               hasSharedAccess(template, user);
    }

    public String encryptSensitiveData(String data) {
        return encryptor.encrypt(data);
    }

    public String decryptSensitiveData(String encryptedData) {
        return encryptor.decrypt(encryptedData);
    }

    private boolean hasSharedAccess(ReportTemplate template, User user) {
        return template.getSharedUsers().contains(user);
    }
} 