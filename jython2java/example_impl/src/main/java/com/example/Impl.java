package com.example;

public class Impl {

    public void doSmth(String con1, String con2) {
        // Obtain an instance of the object factory
        JythonObjectFactory factory = new JythonObjectFactory(
                Iface.class, "lib", "Migration");

        // Call the createObject() method on the object factory by
        // passing the Java interface and the name of the Jython module
        // in String format. The returning object is casted to the the same
        // type as the Java interface and stored into a variable.
        Iface iface = (Iface) factory.createObject();
        // Populate the object with values using the setter methods
        System.out.println(iface.migrate(con1, con2));
    }

}