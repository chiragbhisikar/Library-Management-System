package com.chiragbhisikar.Library.Management.System.Request.Publication;

import lombok.Data;

import java.util.Optional;

@Data
public class UpdatePublicationRequest {
    private Optional<String> name = Optional.empty();
    private Optional<String> location = Optional.empty();
}

/*
All Parameter Are Optional
{
    "name":"Technical Publication",
    "location":"Office No. 1, Amit Residency, Shaniwar Peth, Pune, Maharashtra 411030"
}
*/