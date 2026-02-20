package tasks;

import common.Person;
import common.PersonService;
import common.PersonWithResumes;
import common.Resume;

import java.util.*;
import java.util.function.Function;
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

    List<Integer> perIds = persons.stream()
        .map(Person::id)
        .toList();

    Set<Resume> resumes = personService.findResumes(perIds);

    Map<Integer, Set<Resume>> perResumesMapa = new HashMap<>();

    for (var per : persons) {
      var perId = per.id();
      if (!perResumesMapa.containsKey(perId)) {
        perResumesMapa.put(perId, new HashSet<>());
      }
    }

    for (var resume : resumes) {
      var perId = resume.personId();
      perResumesMapa.get(perId).add(resume);
    }

    Set<PersonWithResumes> personsWithResumes = new LinkedHashSet<>();

    for (var per : persons) {
      var perId = per.id();
      LinkedHashSet<Resume> personResumes = null;
      if (perResumesMapa.containsKey(perId))
        personResumes = new LinkedHashSet<>(perResumesMapa.get(perId));
      personsWithResumes.add(new PersonWithResumes(per, personResumes));
    }
    return personsWithResumes;
  }
}
