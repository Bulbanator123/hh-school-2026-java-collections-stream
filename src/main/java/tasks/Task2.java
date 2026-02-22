package tasks;

import common.Person;

import java.util.*;
import java.util.stream.Stream;

/*
Задача 2
На вход принимаются две коллекции объектов Person и величина limit
Необходимо объеденить обе коллекции
отсортировать персоны по дате создания и выдать первые limit штук.
 */
public class Task2 {

  public static List<Person> combineAndSortWithLimit(Collection<Person> persons1,
                                                     Collection<Person> persons2,
                                                     int limit) {

    /*
     это задание я сделал первым, так как в java я - 0, а подобные штуки я решал на ваших любимых плюсах,
     только во время выполнения задания дошло, что можно использовать стримы и они очень удобные
     дополнение: на обычных задачах (в одно два действия)
    */
    return Stream.concat(persons1.stream(), persons2.stream())
        .sorted(Comparator.comparing(Person::createdAt))
        .limit(limit)
        .toList();
  }
}
