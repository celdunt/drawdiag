package com.celdunt.drawdiag.file;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiReferenceList;

import java.util.ArrayList;
import java.util.List;

public class Clazz {
    private String type;
    private String name;
    private List<String> fields = new ArrayList<>();
    private List<String> methods = new ArrayList<>();
    private List<String> extendsList = new ArrayList<>();
    private List<String> implementsList = new ArrayList<>();
    private List<String> associationsList = new ArrayList<>();

    /**
     *
     * @return Class type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @return Class name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return Class fields in the form of a string array
     */
    public List<String> getFields() {
        return fields;
    }

    /**
     *
     * @return Class methods in the form of a string array
     */
    public List<String> getMethods() {
        return methods;
    }

    /**
     *
     * @return Extends reference in the form of a string array
     */
    public List<String> getExtendsReferenceList() {
        return extendsList;
    }

    /**
     *
     * @return Implements reference in the form of a string array
     */
    public List<String> getImplementsReferenceList() {
        return implementsList;
    }

    /**
     *
     * @return Associations reference in the form of a string array
     */
    public List<String> getAssociationsList() {
        return associationsList;
    }

    /**
     *
     * @param type Class type
     * @return This clazz object
     */
    public Clazz setType(String type) {
        this.type = type;
        return this;
    }

    /**
     *
     * @param name Class name
     * @return This clazz object
     */
    public Clazz setName(String name) {
        this.name = name;
        return this;
    }

    /**
     *
     * @param fields Class fields in the form of a string array
     * @return This clazz object
     */
    public Clazz setFields(List<String> fields) {
        this.fields = fields;
        return this;
    }

    /**
     *
     * @param methods Class methods in the form of a string array
     * @return This clazz object
     */
    public Clazz setMethods(List<String> methods) {
        this.methods = methods;
        return this;
    }

    /**
     *
     * @param extendsList Extends reference list in a raw form
     * @return This clazz object
     */
    public Clazz setExtendsReferenceList(PsiReferenceList extendsList) {
        this.extendsList = RelationsHandler.defineReferenceList(extendsList);
        return this;
    }

    /**
     *
     * @param implementsList Implements reference list in a raw form
     * @return This clazz object
     */
    public Clazz setImplementsReferenceList(PsiReferenceList implementsList) {
        this.implementsList = RelationsHandler.defineReferenceList(implementsList);
        return this;
    }

    /**
     *
     * @param fields Class fields
     * @param methods Class methods
     * @param className Class name
     * @param psiClasses Classes in a raw form
     * @return This clazz object
     */
    public Clazz setAssociationsReferenceList(List<String> fields, List<String> methods, String className,List<PsiClass> psiClasses) {
        this.associationsList = RelationsHandler.defineAssociationsList(fields, methods, className, psiClasses);
        return this;
    }
}
