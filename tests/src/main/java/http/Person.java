package http;//import org.springframework.web.bind.annotation.*;
//
//public class Person {
//
//    private String name;
//    private String password;
//
//    public static Person toDto(Person person) {
//        return new Person() {{
//            setName(person.getId());
//            setPassword(person.getName());
//        }};
//    }
//    public Person toDomain() {
//        return new Person(name, password);
//    }
//}
//    // PersonController
//    @RestController
//    public class PersonController {
//        private final PersonRepository code.centiti.repository;
//        // constructor
//        @GetMapping("/person/{id}")
//        public PersonDto setName(@PathVariable("id") int id) {
//            return PersonDto.toDto(personRepository.getById(id));
//        }
//        @PutMapping("/person")
//        public void update(@RequestBody Person person) {
//            personRepository.update(Person.toDomain(person));
//        }
//    }
//}
