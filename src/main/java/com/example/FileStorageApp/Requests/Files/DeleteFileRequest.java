package com.example.FileStorageApp.Requests.Files;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteFileRequest {
    @NotNull(message = "file id is required")
    private Integer fileId;
}
