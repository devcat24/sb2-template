let numArr = [1, 2, 3, 4, 5, 6, 7, 8, 9 ,10];

console.log('add new to last(push): ' + numArr.push(300)         + '(array size)      after push(mutable): ' + numArr);
console.log('extract from last(pop): ' + numArr.pop()            + '                after extract(mutable): ' + numArr);
console.log('add new to first(unshift): ' + numArr.unshift(200)  + '(array size)  after extract(mutable): ' + numArr);
console.log('extract from first(shift): ' + numArr.shift()       + '             after extract(mutable): ' + numArr);

let evnNum = [];
for (let counter=0; counter < numArr.length; counter++) {
    if(numArr[counter] % 2 === 0){
        evnNum.push(numArr[counter]);
    }
}
console.log('Classic Loop: ' + evnNum);

let oddNum = [];
numArr.forEach( s => {
    if(s%2 !== 0){
        oddNum.push(s);
    }
});
console.log('ForEach: ' +oddNum);

// noinspection JSUnusedAssignment
let mltNum = [];
mltNum = numArr.map( s => s * s);
console.log('Map: ' + mltNum);

let totNum = numArr.reduce( ((sum, current) => sum + current), 0);
//var totNum = numArr.reduce( ((sum, current) => { return sum + current; }), 0);
console.log('Reduce01: ' + totNum);

let posList = ["EAST", "WEST", "NORTH", "SOUTH"];
let posString = posList.reduce( (prev, curr) => prev +', '+ curr );
console.log('Reduce02: ' + posString);


let evnFilter = ( s => s%2 === 0);
let mltMap = ( s => s*s );
let sumReducer = ( (sum, curr) => sum + curr );

let totComplex = numArr.filter(evnFilter).map(mltMap).reduce(sumReducer);
console.log('Functional Interface: ' + totComplex);


let srcStrList = ['a1', 'b2', 'c3', 'd4', 'e5', 'f6', 'g7', 'h8', 'i9', 'j10'];
let slicedStrList01 = srcStrList.slice(3);
console.log('Slice param01: ' + slicedStrList01 + '        from srcStrList(immutable): ' + srcStrList);

//var slicedStrList02 = srcStrList.slice(2, 10);
let slicedStrList02 = srcStrList.slice(2, srcStrList.length);
console.log('Slice param02: ' + slicedStrList02 + '        from srcStrList(immutable): ' + srcStrList);

let replacePos = 7;
let replaceVal = '---';
let replaceStrList03 = [...srcStrList.slice(0, replacePos-1), replaceVal, ...srcStrList.slice(replacePos)];
console.log('replace with slice: ' + replaceStrList03);

let srcStrList02 = ['a1', 'b2', 'c3', 'd4', 'e5', 'f6', 'g7', 'h8', 'i9', 'j10'];
let splicedStrList01 = srcStrList02.splice(1, 3);
console.log('splicedStrList01: ' + splicedStrList01 + '        from srcStrList02(mutable): ' + srcStrList02);

let srcSortStrList01 = ['a1', 'b2', 'c3', 'd4', 'e5', 'f6', 'g7', 'h8', 'i9', 'j10'];
console.log('Joined String from Array: <' + srcSortStrList01.join(', ') + '>');

// noinspection JSCheckFunctionSignatures
let customSortedList01 = srcSortStrList01.map(s => s.substring(1)).map(s => parseInt(s)).map(s => s * 10).sort(
    (prev, next) => {
         return prev >=next;
    }
).reverse(); //.forEach( s => console.log(s));
console.log('Map with Sort & Reverse: ' + customSortedList01);

let empList = [
    {id : 1, name : "John", job : "Sales"}, 
    {id : 2, name : "Jane", job : "Manager"}, 
    {id : 3, name : "Robert", job : "Staff"},
    {id : 4, name : "Sam", job : "Sales"},
  ];
//let salesList = empList.filter(s => s.job.indexOf("Sales") > -1);
let salesList = empList.filter(s => s.job.toLowerCase() === "sales" );
console.log(salesList);

let salesNameList = salesList.map( s => {
                        return s.name;
                    //}).forEach( s => { console.log(s); });
                    });
console.log(salesNameList);

let salesNamesWithUpperCase = empList.filter( s => s.job === "Sales").map( s => s.name.toUpperCase() ) ;
console.log("Map with Filter: " + salesNamesWithUpperCase);

let hasManager = empList.some( s => s.job.toLowerCase() === "manager");
console.log("Some: " + hasManager);




