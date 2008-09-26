package example.reflection

import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

import org.junit.Before
import org.junit.Test

/**
 * Das Delegate wird in ein Composite verpackt. Dieses kümmert sich um das 
 * versenden der PropertyChangeEvents.
 */
class PropertyChangeSupportCompositeTest extends GroovyTestCase {

    def callCounter

    @Test
    void testPropertyChangeSupportComposite() {
        PersonBean pb = new PersonBean();
        PropertyChangeSupportComposite composite = new PropertyChangeSupportComposite(pb);

        def x = { event ->
            callCounter++
            println("Event: ${event}, Name: ${event.getPropertyName()}, Old: ${event.getOldValue()}, New: ${event.getNewValue()}")
        } as PropertyChangeListener

        composite.addPropertyChangeListener(x)
        assertEquals 0, callCounter
        composite.name = "Hallo"
        assertEquals 1, callCounter

        composite.id = 'my.id'
        assertEquals 1, callCounter
    }

    @Before
    void setUp() {
        callCounter = 0
    }

}
