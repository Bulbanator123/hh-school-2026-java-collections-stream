package tasks;

import common.Person;
import common.PersonService;

import java.util.*;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds);

    Map<Integer, Person> personSort = new HashMap<>();;

    for (var per : persons) {
      personSort.put(per.id(), per);
    }

    List<Person> personList = personIds.stream().
        map(personSort::get)
        .toList();

    return personList;

    /*
       И наконец-то асимптотика
       Пусть n - длина persons, m - длина personIds, тогда Сложность O(n + m)
       первый проход записываем в мап O(n), вторым вытаскиваем из мапа O(1) и идём так O(m)
       Просто на всякий, если мы добавляем к этому полноценный findPersons а не вывод из return,
       то скорее всего это ещё + log(n)
     */

    // честно я очень долго думал, что здесь вообще надо сделать
  }
}
