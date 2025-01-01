package com.chiragbhisikar.Library.Management.System.Service.Language;

import com.chiragbhisikar.Library.Management.System.DTO.LanguageDto;
import com.chiragbhisikar.Library.Management.System.Model.Language;

import java.util.List;

public interface iLanguageService {
    List<Language> getAllLanguages();

    Language getLanguageById(Long id);

    Language getLanguageByName(String name);

    Language addLanguage(Language language);

    Language updateLanguage(Language language, Long id);

    void deleteLanguage(Long id);


    LanguageDto convertLanguageToDto(Language language);

    List<LanguageDto> convertLanguagesToDto(List<Language> languages);
}
