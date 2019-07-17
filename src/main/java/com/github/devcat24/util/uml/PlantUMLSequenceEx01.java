package com.github.devcat24.util.uml;

// PlantUML online editor : https://liveuml.com/
// Official doc sequence diagram document :http://plantuml.com/ko/sequence-diagram
// Generate UML image with CLI -> plantuml 'src.txt' -o 'output-directory'


/**
 * @startuml
 *
 *
 * header Page Header
 * footer Page %page% of %lastpage%
 *
 * title Example Sequence Diagram
 *
 * autonumber
 * Alice -> "Bob()" : Hello
 * "Bob()" -> "This is very\nlong" as Long
 *  ' You can also declare:
 *  ' "Bob()" -> Long as "This is very\nlong"
 *  Long --> "Bob()" : ok with doubleline arrow
 *
 *
 *  ||25||
 *
 *  == New Transaction ==
 *  participant Joe << (C,#66ccff) >>
 *
 *  autonumber 20
 *  Alice -[#blue]> "Joe" : Welcome
 *  Joe -> Joe : This is self msg
 *      note right: this is note
 * @enduml
 */
public class PlantUMLSequenceEx01 {
}
