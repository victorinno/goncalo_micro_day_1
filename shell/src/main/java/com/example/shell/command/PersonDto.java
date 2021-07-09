package com.example.shell.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonDto {

    private Long id;

    private String name;

    private String nif;

    private Long idContact;

    private String email;

}
