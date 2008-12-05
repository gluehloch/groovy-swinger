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

/**
 * Example for Groovy Chaining (or a decorator?)
 *
 * @author by Andre Winkler, $LastChangedBy: andrewinkler $
 * @version $LastChangedRevision: 1384 $ $LastChangedDate: 2008-09-14 02:05:09 +0200 (So, 14 Sep 2008) $
 */
class ChainingExample{

    def one = { c = {} ->
        'one*' + c.call() + '*one'
	}

	def two = { c = {} ->
        'two*' + c.call() + '*two'
	}

	def three = { c = {} ->
        'three*' + c.call() + '*three'
	}

    def chain(List commands) {
        def chainToCall = {}
        commands.reverse().each {command->
            chainToCall = command.curry(chainToCall)
        }
        chainToCall()
    }

    def example_1() {
    	assert 'three*two*one* Andre *one*two*three' == three {two {one({' Andre '})}}

    	def myChain = three.curry(two.curry(one.curry({' Andre '})))
    	assert 'three*two*one* Andre *one*two*three' == myChain()
    }

}