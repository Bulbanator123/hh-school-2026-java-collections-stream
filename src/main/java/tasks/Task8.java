package tasks;

import common.Person;
import common.PersonService;
import common.PersonWithResumes;
import common.Resume;

import java.util.*;
import java.util.stream.Collectors;

/*
  Еще один вариант задачи обогащения
  На вход имеем коллекцию персон
  Сервис умеет по personId искать их резюме (у каждой персоны может быть несколько резюме)
  На выходе хотим получить объекты с персоной и ее списком резюме
 */
public class Task8 {
  private final PersonService personService;

  public Task8(PersonService personService) {
    this.personService = personService;
  }

  public Set<PersonWithResumes> enrichPersonsWithResumes(Collection<Person> persons) {

    List<Integer> personIds = persons.stream()
                                     .map(Person::id)
                                     .toList();

    Set<Resume> resumes = personService.findResumes(personIds);

    Map<Integer, Set<Resume>> personResumesMapa = resumes.stream()
        .collect(Collectors.groupingBy(Resume::personId, Collectors.toSet()));

    persons.forEach(person -> personResumesMapa.putIfAbsent(person.id(), new HashSet<>()));

    return persons.stream()
                  .map(person -> new PersonWithResumes(person, personResumesMapa.get(person.id())))
                  .collect(Collectors.toSet());

    // дебажить это восьмое задание уже как смысл жизни (поэтому я был рад что то решение exception не выдавало)
  }
}
