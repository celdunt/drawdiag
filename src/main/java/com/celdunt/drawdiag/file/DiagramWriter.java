package com.celdunt.drawdiag.file;

import java.io.IOException;
import java.io.OutputStream;

public class DiagramWriter {
    private final OutputStream outputStream;

    public DiagramWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * Writing the starting text of the diagram
     */
    public void start() {
        write("@startuml");
        write("");
        write("title Class Diagram \\n");
        write("");
    }

    /**
     *
     * @param str String to writing
     */
    private void write(String str) {
        String toWrite = str + "\n";

        try {
            outputStream.write(toWrite.getBytes());
            outputStream.flush();
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     *
     * @param clazz Clazz file, processed class file
     */
    public void startClass(Clazz clazz) {
        write(clazz.getType() + " " + clazz.getName() + " {");
    }

    /**
     * Writing class fields
     * @param clazz Clazz file, processed class file
     */
    public void fields(Clazz clazz) {
        for (String field : clazz.getFields()) {
            write(field);
        }
    }

    /**
     * Writing class methods
     * @param clazz Clazz file, processed class file
     */
    public void methods(Clazz clazz) {
        for (String method : clazz.getMethods()) {
            write(method);
        }
    }

    /**
     * Writing the ending of the class text
     */
    public void endClass() {
        write("}");
        write("");
    }

    /**
     * Writing the ending text of the diagram
     */
    public void end() {
        write("");
        write("@enduml");
    }

    /**
     * Writing the starting text of the relations
     */
    public void startRelations() {
        write("");
    }

    /**
     * Writing extend relation
     * @param clazz Clazz file, processed class file
     */
    public void extend(Clazz clazz) {
        for (String extend : clazz.getExtendsReferenceList()) {
            write(clazz.getName() + " --|> " + extend);
        }
    }

    /**
     * Writing implement relation
     * @param clazz Clazz file, processed class file
     */
    public void implement(Clazz clazz) {
        for (String implement : clazz.getImplementsReferenceList()) {
            write(clazz.getName() + " ..|> " + implement);
        }
    }

    /**
     * Writing association relation
     * @param clazz Clazz file, processed class file
     */
    public void association(Clazz clazz) {
        for (String association : clazz.getAssociationsList()) {
            write(clazz.getName() + " --> " + association);
        }
    }
}
