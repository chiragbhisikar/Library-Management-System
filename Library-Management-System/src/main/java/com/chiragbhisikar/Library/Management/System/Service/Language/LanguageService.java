package com.chiragbhisikar.Library.Management.System.Service.Language;

import com.chiragbhisikar.Library.Management.System.DTO.LanguageDto;
import com.chiragbhisikar.Library.Management.System.Exceptions.AlreadyExistsException;
import com.chiragbhisikar.Library.Management.System.Exceptions.NotFoundException;
import com.chiragbhisikar.Library.Management.System.Model.Language;
import com.chiragbhisikar.Library.Management.System.Repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LanguageService implements iLanguageService {
    private final LanguageRepository languageRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    @Override
    public Language getLanguageById(Long id) {
        return languageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Language not found!"));
    }

    @Override
    public Language getLanguageByName(String name) {
        return languageRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Language not found!"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public Language addLanguage(Language language) {
        return Optional.of(language).filter(c -> !languageRepository.existsByName(c.getName()))
                .map(languageRepository::save)
                .orElseThrow(() -> new AlreadyExistsException(language.getName() + " already exists"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public Language updateLanguage(Language language, Long id) {
        return Optional.ofNullable(getLanguageById(id)).map(oldLanguage -> {
            oldLanguage.setName(language.getName());
            return languageRepository.save(oldLanguage);
        }).orElseThrow(() -> new NotFoundException("Language not found!"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public void deleteLanguage(Long id) {
        languageRepository.findById(id)
                .ifPresentOrElse(languageRepository::delete, () -> {
                    throw new NotFoundException("Language not found!");
                });
    }

    @Override
    public LanguageDto convertLanguageToDto(Language language) {
        return modelMapper.map(language, LanguageDto.class);
    }

    @Override
    public List<LanguageDto> convertLanguagesToDto(List<Language> languages) {
        return languages.stream().map(this::convertLanguageToDto).collect(Collectors.toList());
    }
}