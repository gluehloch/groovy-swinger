package example.reflection;

class BeanTest extends GroovyTestCase {

    def callCounter;

	void testSomething() {
		PersonBean.metaClass.getProperty = { String key ->
		   def metaProperty = PersonBean.metaClass.getMetaProperty(key); 
		   def result = metaProperty.getProperty(delegate);
		   callCounter++;
		   return result;
		}

		PersonBean person = new PersonBean(name: "Winkler", firstname: "Andre",
		    age: 36, street: "Mensingstr", city: "Hamburg");

		// Funktioniert erst mit Groovy 1.1
		def metaProperty = PersonBean.metaClass.getProperty("name");

		PersonBean.metaClass.setProperty = { String key, value ->
			def metaProperty2 = PersonBean.metaClass.getMetaProperty(key);
			metaProperty2.setProperty(key, value);
			println "MetaProperty::setProperty";
		}
	
		// Hier wird getProperty() leider nicht aufgerufen.
		assert person.getName() == "Winkler";
		assert callCounter == 0;

		// Hier wird getProperty() aufgerufen.
		assert person.name == "Winkler";
		assert callCounter == 1;
		assert person.age == 36;
		assert callCounter == 2;
		assert person.toString() == "Winkler, Andre";
		assert callCounter == 2;

		println "Hallo (1)";

		person.setName("Kroschk");
		assert person.getName() == "Kroschk";

		println "Hallo (2)";
	}

	void setUp() {
	    callCounter = 0;
	}

}
