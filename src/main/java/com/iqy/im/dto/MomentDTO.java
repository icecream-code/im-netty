package com.iqy.im.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MomentDTO {

    private String id;

    private String text;

    private List<String> medias;

    private Integer visibility;

    private String fidList;

    private Double longitude;

    private Double latitude;

    private String location;
}
