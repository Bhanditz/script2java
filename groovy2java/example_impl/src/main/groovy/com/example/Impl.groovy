package com.example

public class Impl {

    public void doSmth(String con1, String con2) {
        Iface iface = new Migration();
        // Populate the object with values using the setter methods
        System.out.println(iface.migrate(con1, con2));
    }

}

public class Migration implements Iface {

    @Override
    String migrate(String sourceConnectionString, String resultConnectionString) {
        "Migration done with conns: " + sourceConnectionString + resultConnectionString
    }
}
