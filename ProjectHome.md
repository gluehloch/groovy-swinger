

# A try for groovier swing programming?!? #
The project, Groovy-Swinger, contains an idea to add PropertyChangeEvent handling to a naked Groovy bean. The new Groovy version 1.7.0 supports the `@Bindable` annotation. That is a slightly different approach.

The current development release is running under 'groovy-swinger-0.4.0'. You find the [Release Notes](ReleaseNotes.md) here.

# Examples #

## Add PropertyChangeSupport to a Groovy bean ##
```
class Person
    def name
    def age
}

def bean = new Person(name: 'Winkler', age: 24)
GroovyPropertyChangeSupportBuilder.preparePCLMechanics(bean)

def listener = { event -> println event }
bean.addPropertyChangeListener(listener as PropertyChangeListener)
// or
bean.addPropertyChangeListener({ event -> println event })
```

## Bind a bean property to a GUI component ##
This example uses JGoodies Data Binding.
```
// Import the classes from the sandbox...
import de.gluehloch.sandbox.groovy.bean.*

// Import JGoodies classes...
import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.value.ValueModel;

GroovyPresentationModel gpm = new GroovyPresentationModel(bean)
ValueModel name = gpm.getModel('name')
ValueModel age = gpm.getModel('age')

JTextField nameTextField = new JTextField()
Bindings.bind(nameTextField, name)

JTextField ageTextField = new JTextField()
Bindings.bind(ageTextField, age)

person.setProperty("name", "Andre Winkler");
assertEquals("Andre Winkler", nameTextField.getText());
```

There is a difference, if you call from Java the Groovy property. See at JavaCallGroovyBeanProperty.

## Groovy´s @Bindable ##
```
class Address {
    @Bindable
    def street

    @Bindable
    def plz

    @Bindable
    def city
}

Address address = new Address();
address.setStreet("Mensingstr");

GroovyPresentationModel gpm = new GroovyPresentationModel(address);
ValueModel model = gpm.getModel("street");

assertEquals("Mensingstr", model.getValue());
address.setStreet("Beisenstr");
assertEquals("Beisenstr", model.getValue());
```

# Learning Groovy #

Here i try to learn the language. Example: DelegateExample

An interesting article about Groovy and a DSL: http://entwickler.de/zonen/portale/psecom,id,101,online,2154,p,0.html.