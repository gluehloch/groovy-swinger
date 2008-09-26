package example.reflection

class PersonBean implements Serializable {

    String name;
    String firstname;
    int age
    String street;
    String city

    String toString() {
        return "$name, $firstname";
    }

}
