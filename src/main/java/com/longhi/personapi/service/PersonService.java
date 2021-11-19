package com.longhi.personapi.service;

import com.longhi.personapi.dto.request.PersonDTO;
import com.longhi.personapi.dto.response.MessageResponseDTO;
import com.longhi.personapi.entity.Person;
import com.longhi.personapi.exception.PersonNotFoundException;
import com.longhi.personapi.mapper.PersonMapper;
import com.longhi.personapi.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    public MessageResponseDTO createPerson(PersonDTO personDTO) {
        var person2Save = personMapper.toModel(personDTO);

        var savedPerson = personRepository.save(person2Save);

        return createMessageResponse(savedPerson.getId(), "Created person with ID: ");
    }

    public List<PersonDTO> listAll() {
        return personRepository.findAll().stream()
            .map(personMapper::toDTO)
            .collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {
        var person = verifyIfExists(id);

        return personMapper.toDTO(person);
    }

    public void deleteById(Long id) throws PersonNotFoundException {
        verifyIfExists(id);

        personRepository.deleteById(id);
    }

    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfExists(id);

        var person2Update = personMapper.toModel(personDTO);

        var updated = personRepository.save(person2Update);

        return createMessageResponse(updated.getId(), "Updated person with ID: ");
    }

    private MessageResponseDTO createMessageResponse(Long id, String message) {
        return MessageResponseDTO
            .builder()
            .message(message + id)
            .build();
    }

    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id)
            .orElseThrow(() -> new PersonNotFoundException(id));
    }
}
