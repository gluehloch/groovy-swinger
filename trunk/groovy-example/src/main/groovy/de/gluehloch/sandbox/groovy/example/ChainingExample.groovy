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
        commands.reverse().each {command ->
            chainToCall = command.curry(chainToCall)
        }
        chainToCall()
    }

    def closureChain() {
    	assert 'three*two*one* Andre *one*two*three' == three {two {one({' Andre '})}}

    	def myChain = three.curry(two.curry(one.curry({' Andre '})))
    	assert 'three*two*one* Andre *one*two*three' == myChain()

    	println chain([one,two,three])
    	// TODO: Die folgende Bedingung kann von der Methode #chain nicht
    	// erfüllt werden.
        //assert 'three*two*one* null *one*two*three' == chain([one,two,three])
    }

    def curryExample() {
        def concat = { x, y ->
            x + delegate.call() + y
        }
        concat.delegate = { " " }
        assert concat("Hello", "World") == "Hello World"

        // Chained Closure (Something like a curry).
        def hello = { x -> concat("Hello", x) }
        assert hello("World") == "Hello World"

        // The same processing but with curry.
        hello = concat.curry("Hello")
        assert hello("World") == "Hello World"

        hello = concat.curry("Hello")
        hello.delegate = { "," }
        assert hello("World") == "Hello,World"

        hello = { x -> concat("Hello", x) }
        hello.delegate = { "," }
        assert hello("World") == "Hello World"
    }

}
