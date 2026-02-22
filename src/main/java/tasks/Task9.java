package tasks;

import common.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)

ну ладно :| Вообще простите за тот мусор, который я написал с описанием правок
 */

public class Task9 {
  // убрал count добрался до счётчика, а зачем он тут (оставил на всякий)
  // private long count;

  // Костыль, эластик всегда выдает в топе "фальшивую персону".
  // Конвертируем начиная со второй
  public List<String> getNames(List<Person> persons) {

    /*
      1. Ладно...
      2. Воспользуемся в stream skip(1) - что позволит нам избежать абсолютно бесполезную операцию удаления элемента из
      массива, да и вообще удалять что-то, кроме конфиденциальных данных плохо)
      3. Стрим нам и так вернёт пустой лист, так зачем это условие прописывать в начале (хотя честно просто ради
      душевного спокойствия можно было оставить)
     */

    return persons.stream()
                  .skip(1)
                  .map(Person::firstName)
                  .toList();
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {

    /*
      удалил distinct (оставляет различные элементы), если это делает set
      а так как это стало преобразованием коллекций
      теперь это плохой случай использования стрим (по презе), просто перепишем без стримов
      (спасибо ideшке, я бы не заметил)

      ну держать что-то с "no usages" это круто, но бесполезно (в общем саму функцию можно удалить)
     */

    return new HashSet<>(getNames(persons));
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {

    /*
      1. Поменял косяк с Отечеством было secondName() -> стало middleName()
      2. Заменил "бесполезные и плохо работающие ifы" на классный стрим, который сам чистит null и выдаёт склеенное ФИО

      Ну я экспериментировал (я же хочу узнать, что вы от нас ждёте),
      тотальное использование стримов это последнее, что я ожидал (хотя я научился пользоваться ими)
     */
    return Stream.of(person.secondName(), person.firstName(), person.middleName())
                 .filter(Objects::nonNull)
                 .collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {

    /*
      поменял только размерность начального hashmap с 1 до person.size()

      (в целом функция итак нормальная была, да можно вроде, что-то со стримами сделать, но раз работает,
       то зачем трогать)

       ну так это не if на ifе, который на другом ifе
       Ладно будут стримы...
     */
    return persons.stream()
        .collect(Collectors.toMap(Person::id,
                                  this::convertPersonToString,
                                  (exitingValue, newValue) -> exitingValue));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {

    /*
        заменил неприятные вложенный for на "замечательный hashset и stream"
     */
    Set<Person> personSet1 = new HashSet<>(persons1);
    return persons2.stream().anyMatch(personSet1::contains);
  }

  // Посчитать число четных чисел
  public long countEven(Stream<Integer> numbers) {

    /*
      1. поправил косяк с атрибутом класса (писал выше об этом) (удалил её вообще из этой функции)
      2. заменил foreach на count
     */

    return numbers.filter(num -> num % 2 == 0).count();
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  void listVsSet() {

    /*
      после того как я зашёл в онлайн компилятор стало всё понятно

      мы инициируем integers, как лист с числами от 1 до 10000 включительно (по возрастанию),
      потом его перемешали и сделали из него сет, который оставил все числа от 1 до 10000 включительно,
      получив две коллекции с одинаковым содержанием!!! (сейчас мы не считаем порядок)
      а так как порядок обхода HashSet будет по возрастанию hashcode, и у Integer он будет совпадать со значением
      так как это всего 10000 чисел, что не превысит порог, и эти числа натуральные и до 10000, что идеально для пула хешкодов,
      поэтому они будут обходиться как от 1 до 10000 включительно, то есть порядок обхода integers и set будут одинаковыми
      тогда при приведении в другой тип, они будут обходиться компилятором одинаково и иметь одинаковое содержание
      значит assert увидит два одинаковых String и будет верен

      на самом деле интересная тонкость, я что-то и не думал об этом
     */

    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
}
