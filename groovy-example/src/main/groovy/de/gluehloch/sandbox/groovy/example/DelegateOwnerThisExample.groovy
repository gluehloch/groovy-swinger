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

package de.gluehloch.sandbox.groovy.example

class ClassAsExample  {
    def closure = {
    	// this referenziert das umgebende Objekt. 
    	assert 'de.gluehloch.sandbox.groovy.example.ClassAsExample' == this.class.name
        println 'this: ' + this.class.name

        // Delegate wurde umgebogen auf das Objekt DelegateOwnerThisExample.
        assert 'de.gluehloch.sandbox.groovy.example.DelegateOwnerThisExample' == delegate.class.name
        println 'delegate: ' + delegate.class.name

        // Demo für Owner. Liefert das gleiche Ergebnis wie this.
        println 'owner: ' + owner.class.name
        def nestedClos = {
    		assert 'de.gluehloch.sandbox.groovy.example.ClassAsExample$_closure1' == owner.class.name
            println 'owner (in inner closure): ' + owner.class.name
            // In einer verschachtelten Closure bleibt this auf das umgebende
            // Objekt kleben.
            assert 'de.gluehloch.sandbox.groovy.example.ClassAsExample' == this.class.name
            println 'this (in inner closure): ' + this.class.name
        }
        nestedClos()
    }

    def closure2 = {
        println 'delegate: ' + delegate.class.name
    }

}

/**
 * Shows the difference between the groovy properties <b>this</b>,
 * <b>owner</b> and <b>delegate</b>.
 *
 * @author by Andre Winkler, $LastChangedBy: andrewinkler $
 * @version $LastChangedRevision: 1384 $ $LastChangedDate: 2008-09-14 02:05:09 +0200 (So, 14 Sep 2008) $
 */
class DelegateOwnerThisExample {

    public static void main(String[] args) {
        def clos = new ClassAsExample().closure

        // 'delegate' umbiegen auf ein anderes Objekt. Per Default ist das
        // 'delegate' mit der gleichen Referenz wie der 'owner' bestückt.
        clos.delegate = new DelegateOwnerThisExample()
        clos()

        def clos2 = new ClassAsExample().closure2
        clos2()
    }

}
