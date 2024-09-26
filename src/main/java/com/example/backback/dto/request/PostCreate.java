package com.example.backback.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCreate {
    String description;
    String image;
    String mp3url;
    Integer status;
}
