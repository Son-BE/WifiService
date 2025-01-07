//import com.google.gson.Gson;
//
//public class GsonTest {
//    public static void main(String[] args) {
//        Gson gson = new Gson();
//        String json = gson.toJson(new Person("Son", 31));
//        System.out.println(json);
//
//        Person person = gson.fromJson(json, Person.class);
//        System.out.println(person);
//    }
//}
//
//class Person {
//    String name;
//    int age;
//
//    Person(String name, int age) {
//        this.name = name;
//        this.age = age;
//    }
//
//    @Override
//    public String toString() {
//        return "Person{name='" + name + "', age=" + age + '}';
//    }
//}
