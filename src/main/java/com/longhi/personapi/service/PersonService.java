package com.longhi.personapi.service;

import com.longhi.personapi.dto.request.PersonDTO;
import com.longhi.personapi.dto.response.MessageResponseDTO;
import com.longhi.personapi.mapper.PersonMapper;
import com.longhi.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    @Autowired
    public PersonService(PersonRepository repository) {
        this.personRepository = repository;
    }

    public MessageResponseDTO createPerson(PersonDTO personDTO) {
        var person2Save = personMapper.toModel(personDTO);

        var savedPerson = personRepository.save(person2Save);

        return MessageResponseDTO
            .builder()
            .message("Created person with ID: " + savedPerson.getId())
            .build();
    }

}
