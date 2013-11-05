//package org.aau.quase;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.python.core.PyObject;
//import org.python.util.PythonInterpreter;
//
///**
// * Object factory implementation that is defined
// * in a generic fashion.
// *
// */
//
//public class JythonObjectFactory {
//    private static JythonObjectFactory instance = null;
//    private static PyObject pyObject = null;
//
//    protected JythonObjectFactory() {
//
//    }
//    /**
//     * Create a singleton object. Only allow one instance to be created
//     */
//    public static JythonObjectFactory getInstance(){
//        if(instance == null){
//            instance = new JythonObjectFactory();
//        }
//
//        return instance;
//    }
//
//    /**
//     * The createObject() method is responsible for the actual creation of the
//     * Jython object into Java bytecode.
//     */
//    public static Object createObject(Object interfaceType, String moduleName){
//        Object javaInt = null;
//        // Create a PythonInterpreter object and import our Jython module
//        // to obtain a reference.
//        PythonInterpreter interpreter = new PythonInterpreter();
//        interpreter.exec("from lib." + moduleName + " import " + moduleName);
//
//        pyObject = interpreter.get(moduleName);
//
//        try {
//            // Create a new object reference of the Jython module and
//            // store into PyObject.
//            PyObject newObj = pyObject.__call__();
//            // Call __tojava__ method on the new object along with the interface name
//            // to create the java bytecode
//            javaInt = newObj.__tojava__(Class.forName(interfaceType.toString().substring(
//                    interfaceType.toString().indexOf(" ")+1,
//                    interfaceType.toString().length())));
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(JythonObjectFactory.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return javaInt;
//    }
//
//}
package com.example;

import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PySystemState;

/**
 * Jython Object Factory using PySystemState
 */
public class JythonObjectFactory {

    private final Class interfaceType;
    private final PyObject klass;

    // Constructor obtains a reference to the importer, module, and the class name
    public JythonObjectFactory(PySystemState state, Class interfaceType, String moduleName, String className) {
        this.interfaceType = interfaceType;
        PyObject importer = state.getBuiltins().__getitem__(Py.newString("__import__"));
        PyObject module = importer.__call__(Py.newString(moduleName));
        klass = module.__getattr__(className);
        System.err.println("module=" + module + ",class=" + klass);
    }

    // This constructor passes through to the other constructor
    public JythonObjectFactory(Class interfaceType, String moduleName, String className) {
        this(new PySystemState(), interfaceType, moduleName, className);
    }

    // All of the followng methods return
    // a coerced Jython object based upon the pieces of information
    // that were passed into the factory. The differences are
    // between them are the number of arguments that can be passed
    // in as arguents to the object.

    public Object createObject() {
        return klass.__call__().__tojava__(interfaceType);
    }


    public Object createObject(Object arg1) {
        return klass.__call__(Py.java2py(arg1)).__tojava__(interfaceType);
    }

    public Object createObject(Object arg1, Object arg2) {
        return klass.__call__(Py.java2py(arg1), Py.java2py(arg2)).__tojava__(interfaceType);
    }

    public Object createObject(Object arg1, Object arg2, Object arg3)
    {
        return klass.__call__(Py.java2py(arg1), Py.java2py(arg2),
                Py.java2py(arg3)).__tojava__(interfaceType);
    }

    public Object createObject(Object args[], String keywords[]) {
        PyObject convertedArgs[] = new PyObject[args.length];
        for (int i = 0; i < args.length; i++) {
            convertedArgs[i] = Py.java2py(args[i]);
        }

        return klass.__call__(convertedArgs, keywords).__tojava__(interfaceType);
    }

    public Object createObject(Object... args) {
        return createObject(args, Py.NoKeywords);
    }

}