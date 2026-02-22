package tasks;

import common.ApiPersonDto;
import common.Area;
import common.Person;

import java.util.*;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 {

  public static Set<String> getPersonDescriptions(Collection<Person> persons,
                                                  Map<Integer, Set<Integer>> personAreaIds,
                                                  Collection<Area> areas) {
    Map<Integer, String> areaMapa = new HashMap<>();
    for (var area : areas) {
      areaMapa.put(area.getId(), area.getName());
    }

    Set<String> personDescribe = new HashSet<>();
    for (var per : persons) {
      var perAreasIds = personAreaIds.get(per.id());
      for (var areaId : perAreasIds) {
        personDescribe.add(per.firstName() + " - " + areaMapa.get(areaId));
      }
    }

    return personDescribe;
  }
}
