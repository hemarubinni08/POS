package com.ust.pos.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommonDto extends PaginationDto {
    private Long id;
    private String identifier;
    private String message;
    private boolean success = true;
    private boolean status = true;
    private String description;
}
