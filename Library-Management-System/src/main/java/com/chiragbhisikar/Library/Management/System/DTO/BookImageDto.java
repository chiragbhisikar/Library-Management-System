package com.chiragbhisikar.Library.Management.System.DTO;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class BookImageDto {
    private Long id;
    private String filePath;


    public String getFilePath() {
        String url = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
        url = url.substring(0, url.indexOf("/api"));
        String filePath = url + this.filePath;

        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
