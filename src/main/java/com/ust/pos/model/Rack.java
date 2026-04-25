package com.ust.pos.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Rack extends CommonFields {

    private boolean active;
    private List<String> shelfIdentifiers;
}