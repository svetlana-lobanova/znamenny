package com.znamen.tones.domain.entity;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class Tone {
    public String startSchema;
    public List<String> middleSchemasList;
    public List<String> endingSchemasList;
    public String endingSchema;
}
