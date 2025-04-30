package com.xcontent.service;

import com.xcontent.model.ReportTemplate;
import com.xcontent.repository.ReportTemplateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ReportTemplateService {
    private final ReportTemplateRepository templateRepository;

    public ReportTemplateService(ReportTemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Transactional(readOnly = true)
    public List<ReportTemplate> getAllTemplates() {
        return templateRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<ReportTemplate> searchTemplates(String query) {
        return templateRepository.searchByNameOrDescription(query);
    }

    @Transactional
    public ReportTemplate saveTemplate(ReportTemplate template) {
        // テンプレートファイルの検証
        validateTemplate(template);
        return templateRepository.save(template);
    }

    @Transactional
    public void deleteTemplate(Long id) {
        templateRepository.deleteById(id);
    }

    private void validateTemplate(ReportTemplate template) {
        // テンプレートの構文チェック
        // 必要なパラメータの存在確認
        // テンプレートファイルの存在確認
    }
} 