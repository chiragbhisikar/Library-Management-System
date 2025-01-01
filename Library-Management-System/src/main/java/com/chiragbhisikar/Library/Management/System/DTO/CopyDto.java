package com.chiragbhisikar.Library.Management.System.DTO;

import lombok.Data;

@Data
public class CopyDto {
    private Long id;
    private String bookBarcodeNumber;
    private BookDto book;
}
