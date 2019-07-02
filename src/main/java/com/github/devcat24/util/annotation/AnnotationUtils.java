package com.github.devcat24.util.annotation;

import com.opencsv.bean.CsvBindByPosition;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;

public class AnnotationUtils {

    public <T> String getAnnotationValueFromGenericList(List<T> objects) {
        // working using annotation ...
        //    -> even though, annotation could be extracted from 'List<T> objects'
        //       but, if 'List<T> objects' is empty, this approach could make problem
        //         => just use 'passing headers by parameter' logic
        if(objects != null && objects.size() > 0){
            // Annotation[] cAnn = objects.iterator().next().getClass().getAnnotations();
            // Method [] methods = objects.iterator().next().getClass().getDeclaredMethods();
            Field[] fields = FieldUtils.getAllFields(objects.iterator().next().getClass());
            // Field [] fields = objects.iterator().next().getClass().getFields();
            for(Field field : fields) {
                System.out.println("Field Name: " + field.getName());

                // Annotation[] annotations = field.getDeclaredAnnotations();
                // for(Annotation annotation : annotations) {
                //    System.out.println("   > " + annotation.toString());
                //}
                CsvBindByPosition csvBindByPosition = field.getAnnotation(CsvBindByPosition.class);
                if(csvBindByPosition != null){
                    System.out.println(field.getName() + ": @CsvBindByPosition(position - " + csvBindByPosition.position() + ")");
                }
            }
        }

        return "ok";
    }


}
