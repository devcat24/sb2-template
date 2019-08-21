// test env -> http://jsbin.com

// 1. let & const
//var myName =  'Max';            // used 'let' instead of var
let myName =  'Max';
//const myName =  'Max';

console.log(myName);
myName = 'Manu';
console.log(myName);
function printMyName(name) {
    console.log(name);
}
printMyName('Max');


// 2. arrow function
// 'const' can be used as meaning of 'function' in es6
// case#1 - one no parameter
//const printMyName2 = name => {
// case#2 - no parameter
//const printMyName2 = () => {
//const printMyName2 = (name) => {
// case#3 - multiple parameters
const printMyName2 = (name, age) => {
    console.log(name, age);
};
printMyName2('Manu', 28);
// case#4 - return values
// const multiply = (number) => {
//   return number * 2;
// }
// case#5 - return values : shorten expression
//const multiply = (number) => number * 2;
const multiply = number => number * 2;
console.log(multiply(2));


// 3. exports & imports
//  a. default exports
// import person from './person.js'
// import prs from './person.js'
//  b. named exports (not supported every browser)
// import {smth} from './utility.js'
// import {smth as Smth} from './utility.js'
// import * as bundled from './utility.js'


// 4. classes
//  a. features: Property + Method
class SimplePerson {
    constructor (name){
        if(name === undefined) {
            this.name = 'John';
        }	else {
            this.name = name;
        }

    }
    call () {
        console.log('call from Person : ' + this.name);
    }
}

//  b. invoking class
let p01 = new SimplePerson('Max');
p01.call();
const myPerson = new SimplePerson();
myPerson.call();
console.log(myPerson.name);



//  c. inheritance
// class Person extends Master {
//    // ...
// }

//  d. examples
class Human {
    constructor() {
        this.gender = 'male - inherited from Human';
    }
    printGender() {
        console.log(this.gender);
    }
}
class Person extends Human{
    constructor() {
        super();
        this.name = 'Max - extends Human';
        //this.gender = 'female';
    }
    printMyName() {
        console.log(this.name);
    }

}
const person02 = new Person();
person02.printMyName();
person02.printGender();


// 5. classes, properties & methods (es7)
//    should select 'ES6/Babel' option for execution
// noinspection JSUnusedGlobalSymbols
class Human02 {
    // es6
    constructor() {
        this.gender = 'male';
    }
    // noinspection JSUnusedGlobalSymbols
    printGender() {
        console.log(this.gender);
    }

    // es7
    // gender = 'male';
    // printGender = () => {
    //     console.log(this.gender);
    // }
}
class Person02 extends Human{
    // es6
    constructor() {
        super();
        this.name = 'Max';
        // noinspection JSUnusedGlobalSymbols
        this.gender = 'female';
    }

    printMyName() {
        console.log(this.name);
    }

    // es7
    // name = 'Max';
    // gender = 'female';
    // printMyName = () => {
    //    console.log(this.name);
    // }

}
const person03 = new Person02();
person03.printMyName();
person03.printGender();


// 6. Spread & Rest Operators
//    should select 'ES6/Babel' option for execution
//    spread operator
// noinspection JSUnusedGlobalSymbols
const numbers01 = [1, 2, 3];
const addNumbers = [numbers01, 4];
const newNumbers = [...numbers01, 4];
console.log(addNumbers);
console.log(newNumbers);
const person01 = {
    name: 'Max'
};
const newPerson = {
    ...person01,
    age: 28
};
console.log(newPerson);
// rest operator (converts 'function arguments' to array object)
const filterFunc = (...args) => {
    //  return args.sort();
    return args.filter(el => el === 1 );
};
console.log(filterFunc(1,3,2));


// 7. Destructuring
//    should select 'ES6/Babel' option for execution
// noinspection JSUnusedGlobalSymbols
//		const numbers02 = [1, 2, 3];
// [num1, num3] = numbers;
// noinspection JSUnusedAssignment
//		[num1, , num3] = numbers02;
//		console.log(num1, num3);


// 8. Reference and Primitive Types Refresher
//primitive type assignment(value copied - call by value)
const number = 1;
// noinspection UnnecessaryLocalVariableJS
const num2 = number;
console.log(num2);
//object type assignment#1 (pointer copied - call by reference)
const person = {
    name: 'Max'
};
const secondPerson = person;
person.name = 'Manu';
console.log(secondPerson);
//object type assignment#2 (using spread operator - call by value)
const human = {
    name: 'Max'
};
const secondHuman = {
    ...human
};
human.name = 'Manu';
console.log(secondHuman);


// 9. Refershing Array Functions
const numbers = [1, 2, 3];
const doubleNumArray = numbers.map((num) => {
    return num * 2;
});
console.log(numbers);
console.log(doubleNumArray);

