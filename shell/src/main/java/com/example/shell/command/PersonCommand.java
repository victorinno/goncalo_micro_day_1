package com.example.shell.command;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@ShellComponent
public class PersonCommand {

    private EurekaClient eurekaClient;

    public PersonCommand(final EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
    }

    private String getUrl(final String service, final String url) {
        final InstanceInfo personService = this.eurekaClient.getNextServerFromEureka(service, false);
        return personService.getHomePageUrl() + url;
    }

    @ShellMethod(value = "Creates a person", key = "person_create")
    public String createPerson(@ShellOption final String name, @ShellOption final String nif, @ShellOption final String email) {
        //Crio o dto
        final PersonDto personDto = new PersonDto();
        personDto.setName(name);
        personDto.setNif(nif);

        //Faço uma requisição post para o serviço de person
        final ResponseEntity<PersonDto> result = new RestTemplate().postForEntity(this.getUrl("person_service", "person"), personDto, PersonDto.class);

        //Transformo em lista somente para facilitar o uso da tabela
        final PersonDto dto = result.getBody();

        final ContactDto contactDto = new ContactDto();
        contactDto.setEmail(email);
        final ResponseEntity<ContactDto> contactDtoResponseEntity = new RestTemplate().postForEntity(this.getUrl("contact_service", "contact"), contactDto, ContactDto.class);

        final ContactDto contactDtoResult = contactDtoResponseEntity.getBody();

        dto.setIdContact(contactDtoResult.getId());
        dto.setEmail(contactDtoResult.getEmail());

        final List<PersonDto> data = Optional.ofNullable(dto).map(List::of).orElse(List.of());

        //Crio uma table oldschool de person
        final TableBuilder tableBuilder = new TableBuilder(new BeanListTableModel<>(PersonDto.class, data))
                .addFullBorder(BorderStyle.oldschool);

        //Buildo e renderizo com 80 colunas
        return tableBuilder.build().render(80);
    }


    @ShellMethod(value = "find all persons", key = "find_all_persons")
    public String findAll() {

        final ResponseEntity<PersonDto[]> forEntity = new RestTemplate().getForEntity(this.getUrl("person_service", "person"), PersonDto[].class);
        final List<PersonDto> data = Optional.ofNullable(forEntity).map(ResponseEntity::getBody).map(Arrays::asList).orElse(new LinkedList<>());

        final TableBuilder tableBuilder = new TableBuilder(new BeanListTableModel<>(PersonDto.class, data))
                .addFullBorder(BorderStyle.oldschool);

        return tableBuilder.build().render(80);
    }

}
