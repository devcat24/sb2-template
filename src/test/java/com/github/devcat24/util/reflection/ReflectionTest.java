package com.github.devcat24.util.reflection;


import com.github.devcat24.mvc.controller.DefaultController;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ServiceLoader;

@RunWith(SpringRunner.class)
//@SpringBootTest(/*webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT*/)
public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);


    @Test
    public void serviceLoaderTest() throws Exception {
        /*
        final ServiceLoader<CPService> loader = ServiceLoader.load(CPService.class);
        for (final CPService cpService : loader) {

            cpService.show();
        }*/

        ReflectionSampleBean rsBean = new ReflectionSampleBean();
        Class rsBeanClass = rsBean.getClass();
        //rsBeanClass.getName(); rsBeanClass.getPackage();

        Field[] fields = rsBeanClass.getDeclaredFields();  // rsBeanClass.getFields();       =>  from inheritance

        String [] fieldStringList = new String[fields.length];
        for(int ins=0; ins < fields.length ; ins++) {
            //for(Field field : fields){
            fields[ins].setAccessible(true);

            Annotation[] annotations = fields[ins].getAnnotations();
            String [] annotationNames = new String[annotations.length];
            for(int inx=0; inx < annotations.length ; inx ++) {
                Class<? extends Annotation> type = annotations[inx].annotationType();
                annotationNames[inx] = type.getName();
            }
            String annotationString = StringUtils.join(annotationNames, ", ");
            fieldStringList[ins] = fields[ins].getName() + " [" + annotationString + "]";
        }
        //Method [] methods = rsBeanClass.getDeclaredMethods();  // rsBeanClass.getMethods();  =>  from inheritance
        Method setAddrMethod = rsBeanClass.getDeclaredMethod("setAddress", String.class);
        setAddrMethod.setAccessible(true);
        setAddrMethod.invoke(rsBean,"New Address");

        logger.info(StringUtils.join(fieldStringList, ", "));

        // --> Reflection with generic type argument
        //
        // public <T> String getAnnotationValueFromGenericList(List<T> objects) {
        // 	working using annotation ...
        // 	   -> even though, annotation could be extracted from 'List<T> objects'
        // 	      but, if 'List<T> objects' is empty, this approach could make problem
        // 	        => just use 'passing headers by parameter' logic
        // 	if(objects != null && objects.size() > 0){
        // 		Field [] fields = FieldUtils.getAllFields(objects.iterator().next().getClass());
        // 		for(Field field : fields) {
        // 			CsvBindByPosition csvBindByPosition = field.getAnnotation(CsvBindByPosition.class);
        // 			if(csvBindByPosition != null){
        // 				System.out.println(field.getName() + ": @CsvBindByPosition(position - " + csvBindByPosition.position() + ")");
        // 			}
        // 		}
        // 	}
        // 	return "ok";
        // }
    }
}

