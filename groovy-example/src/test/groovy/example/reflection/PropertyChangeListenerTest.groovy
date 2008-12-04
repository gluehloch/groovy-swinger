/*
 * $Id: DefaultComponentFactory.java 1384 2008-09-14 00:05:09Z andrewinkler $
 * ============================================================================
 * Project groovy-example
 * Copyright (c) 2008 by Andre Winkler. All rights reserved.
 * ============================================================================
 *          GNU LESSER GENERAL PUBLIC LICENSE
 *  TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package example.reflection

import java.beans.*

class PropertyChangeListenerAdapter implements PropertyChangeListener {

	private Closure c;

	PropertyChangeListenerAdapter(Closure _c) {
		c = _c;
	}

	void propertyChange(PropertyChangeEvent event) {
		c.call(event);
	}

}

class PropertyBean {

	final PropertyChangeSupport support = new PropertyChangeSupport(this);

	String name;
	String firstname;
	String location;

	boolean equals(Object object) {
		boolean result = false;
		if (object != null && object instanceof PropertyBean) {
			def propertyBean = (PropertyBean) object;
			result = propertyBean.name == name;
		}
		return result;
	}

	void addPropertyClosureChangeListener(listener) {
		support.addPropertyChangeListener(new PropertyChangeListenerAdapter(listener));
	}

	void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}

	void setName(String _name) {
		def oldName = name;
		name = _name;
		support.firePropertyChange("name", oldName, name);
	}

}

class PropertyChangeListenerTest extends GroovyTestCase {

	PropertyBean bean;
	int callCounter;

	void testPropertyBeanEquals() {
		def localBean = new PropertyBean();
		assert bean == localBean;
	}

	void testPropertyChangeEvent() {
		def Closure listener1 = { event -> callCounter++; println("Call_1: ${event}") };
		bean.addPropertyClosureChangeListener(listener1);

		def Closure listener2 = { event -> callCounter++; println("Call_2: ${event}") };
		bean.addPropertyClosureChangeListener(listener2);

		bean.name = "winkler";
		assert bean.name == "winkler";
		assert callCounter == 2;
	
		bean.setName("Andre");
		assert bean.name == "Andre";
		assert callCounter == 4;

		println "ENDE: ${bean.name}";
	}

    void testGrooyLike1PropertyChangeEvent() {
		def x = { event -> callCounter++; println("Call_3: ${event}") } as PropertyChangeListener
		bean.addPropertyChangeListener(x);

		bean.setName("classic_winkler");
		assert bean.getName() == "classic_winkler";
		assert callCounter == 1;
	
		bean.setName("classic_Andre");
		assert bean.getName() == "classic_Andre";
		assert callCounter == 2;

		println "ENDE: ${bean.name}";
    }

    void testGrooyLike2PropertyChangeEvent() {
		bean.propertyChange = { event -> callCounter++; println("Call_3: ${event}") };

		bean.setName("classic_winkler");
		assert bean.getName() == "classic_winkler";
		assert callCounter == 1;
	
		bean.setName("classic_Andre");
		assert bean.getName() == "classic_Andre";
		assert callCounter == 2;

		println "ENDE: ${bean.name}";
    }

	void setUp() {
		bean = new PropertyBean();
		callCounter = 0;
	}

}
