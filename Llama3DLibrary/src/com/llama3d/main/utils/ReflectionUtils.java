package com.llama3d.main.utils;

import java.lang.reflect.*;

/**
 * 
 * @author Réal Gagnon ©1998-2012
 * @see <a href="http://www.rgagnon.com/javadetails/java-0038.html">Real's How
 *      To</a>
 * 
 */

public class ReflectionUtils {

    // ===================================================================
    // Public Static Methods
    // ===================================================================

    public static Object getValueOf(Object clazz, String lookingForValue) throws Exception {

        Field field = clazz.getClass().getField(lookingForValue);
        Class<?> clazzType = field.getType();

        if (clazzType.toString().equals("double")) {
            // ======== Double Value ========
            return field.getDouble(clazz);
        } else if (clazzType.toString().equals("float")) {
            // ======== Float Value ========
            return field.getFloat(clazz);
        } else if (clazzType.toString().equals("int")) {
            // ======== Integer Value ========
            return field.getInt(clazz);
        }

        return field.get(clazz);
    }

}