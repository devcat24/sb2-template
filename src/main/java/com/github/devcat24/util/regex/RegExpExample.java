package com.github.devcat24.util.regex;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Wikipedia - Regular expression
//   > https://en.wikipedia.org/wiki/Regular_expression
//
//
//  ----- Character classes -----
//  \d          digit, '[0-9]'
//  \D          non-digit, '[^0-9]'
//  \s          whitespace character, '[ \t\n\x0B\f\r]'
//  \S          non-whitespace character, '[^\s]'
//  \w          word character, '[a-zA-Z_0-9]'
//  \W          non-word character, [^\w]
// 	\\D+         1+ non-digit characters
// 	(\\d+)       1+ digit characters in a capture group
//
//  ----- Quantifiers -----
// 	?            0 (or) 1 occurrence   ex. 'colou?r' matches with 'color' and 'colour'
// 	*            0+ occurrence         ex. 'ab*c' matches 'ac', 'abc', 'abbbc'
// 	+            1+ occurrence         ex. 'ab+c' matches 'abc', 'abbc' (not 'ac' !)
// 	{n}          'n' times occurrence  ex. 'ap{2}le' matches 'apple'
//  {n,}         'n+' times occurrence
//  {n,m}        'n' ~ 'm-1' times occurrence
//
//  ----- Meta-characters -----
// 	|            or                    ex. 'a|b*' matches 'a', 'b', 'bbb'
//  ()           grouping
// 	^            beginning of line
// 	$            ending of line
// 	.            matches any single character except new line
// 	[:upper:]    uppercase character    ex. '[[:upper:]ab] matches 'A', 'a', 'B', 'b'
// 	[:lower:]    lowercase character
// 	[:print:]    visible character & space character
// 	[:alpha:]    alphabetic character
// 	[:digit:]    Digits
// 	[:alnum:]    alphabetic & digit character
//
//  ----- Combination examples -----
// 	.*           0+ any character
//  \d{n}
//  (cat|dog|camel)
//
//
//
//  http://www.ocpsoft.org/tutorials/regular-expressions/java-visual-regex-tester/
//    > Visual Regx Tester
//
//
// Regular Expression in JavaScript
//  var filter = /[a-z]+/;
//  if (filter.test("a") == true) {
//      alert("success");
//  } else {
//      alert("fail");
//  }
@Slf4j
public class RegExpExample {
    public void regExp01(){
        regExam01();
        regExam02();
        regExam03();
    }

    public void regExam01(){
        String regExp = "\\w+@\\w+\\.\\w+(\\.\\w+)?";
        String data = "angel@gmail.com";
        boolean result = Pattern.matches(regExp, data);
        log.info("regExam01: " + result);
    }

    public void regExam02(){
        String data = "hello1234regular789expression2345";
        String regexStr = "^\\D+(\\d+).*";
        Pattern pattern = Pattern.compile(regexStr);
        Matcher matcher = pattern.matcher(data);

        if (matcher.find()) {
            log.info("regExam02: " + matcher.group(1));
        }
    }

    public void regExam03(){
        String data = "Tracking login information : User Id=10010. Password=3334. 3:30pm.";
        String regexStr = "((Id=)|(Password=))(\\d+)";
        Pattern pattern = Pattern.compile(regexStr);
        Matcher matcher = pattern.matcher(data);

        StringBuffer rtn = new StringBuffer();
        while(matcher.find()){
            log.info("regExam03: masking > " + matcher.group(2) );
            matcher.appendReplacement(rtn, matcher.group(1) + "*masked*");
        }
        matcher.appendTail(rtn);
        log.info("regExam03: result > " + rtn);

    }
}
