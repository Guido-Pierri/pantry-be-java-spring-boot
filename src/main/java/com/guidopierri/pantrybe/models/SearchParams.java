package com.guidopierri.pantrybe.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Search")
public class SearchParams implements Serializable {
    @Schema(description = "Sub string matching on display name, or all if null")
    @Nullable
    private String freeTextSearch;

    @Nullable
    public String getFreeTextSearch() {
        return freeTextSearch;
    }
}
