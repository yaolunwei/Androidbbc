package com.bigoat.bbc.sample;

import com.bigoat.bbc.sample.autoArg.Persion;
import com.bigoat.bbc.sample.permission.PermissionActivity;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        System.out.println("String: " + Integer.class.isPrimitive());
        System.out.println("String: " + int.class.isPrimitive());
        System.out.println("String: " + Float.class.isPrimitive());
        System.out.println("String: " + float.class.isPrimitive());
        System.out.println("String: " + Double.class.isPrimitive());
        boolean b;
        try {
            b = ((Class) Integer.class.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            b = false;
        }

        System.out.println("b: " + b);
    }
}