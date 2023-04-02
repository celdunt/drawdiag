package com.celdunt.drawdiag.file;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassHandler {
    private final List<PsiClass> psiClasses = new ArrayList<>();
    public final List<Clazz> clazzes = new ArrayList<>();

    public ClassHandler(List<PsiJavaFile> psiJavaFiles) {
        psiJavaFilesToPsiClasses(psiJavaFiles);
        psiClassesToClazzes();
    }

    /**
     *
     * @param psiJavaFiles Class files in raw form
     */
    private void psiJavaFilesToPsiClasses(List<PsiJavaFile> psiJavaFiles) {
        for (PsiJavaFile psiJavaFile : psiJavaFiles) {
            psiClasses.addAll(List.of(psiJavaFile.getClasses()));
        }
    }

    /**
     *  Convert Classes files in raw form to Clazz files
     */
    private void psiClassesToClazzes() {
        for (PsiClass psiClass : psiClasses) {
            clazzes.add(new Clazz()
                    .setName(getPsiClassName(psiClass))
                    .setType(getPsiClassType(psiClass))
                    .setFields(getPsiClassFields(psiClass))
                    .setMethods(getPsiClassMethods(psiClass))
                    .setExtendsReferenceList(psiClass.getExtendsList())
                    .setImplementsReferenceList(psiClass.getImplementsList())
                    .setAssociationsReferenceList(getPsiClassFields(psiClass), getPsiClassMethods(psiClass),getPsiClassName(psiClass), psiClasses));
        }
    }

    /**
     *
     * @param psiClass Class file in raw form
     * @return Class fields in the form of a string array
     */
    private List<String> getPsiClassFields(PsiClass psiClass) {
        List<PsiField> psiFields = List.of(psiClass.getFields());
        List<String> fields = new ArrayList<>();
        for (PsiField psiField : psiFields) {
            String type = psiField.getTypeElement() == null? "construct" :
                    psiField.getTypeElement().toString().replace("PsiTypeElement", "").replace(":", "");
            fields.add(psiField.getName() + " : " + type);
        }
        return fields;
    }

    /**
     *
     * @param psiClass Class file in raw form
     * @return Class methods in the form of a string array
     */
    private List<String> getPsiClassMethods(PsiClass psiClass) {
        List<PsiMethod> psiMethods = List.of(psiClass.getMethods());
        List<String> methods = new ArrayList<>();
        for (PsiMethod psiMethod : psiMethods) {
            String type = psiMethod.getReturnTypeElement() == null? "construct" :
                    psiMethod.getReturnTypeElement().toString().replace("PsiTypeElement", "").replace(":", "");
            methods.add(psiMethod.getName() + "() : " + type);
        }
        return methods;
    }

    /**
     *
     * @param psiClass Class file in raw form
     * @return Class name
     */
    private String getPsiClassName(PsiClass psiClass) {
        return psiClass.getName();
    }

    /**
     *
     * @param psiClass Class file in raw form
     * @return Class type
     */
    private String getPsiClassType(PsiClass psiClass) {
        return psiClass.isInterface()? "interface":
                psiClass.isEnum()? "enum":
                        Objects.requireNonNull(psiClass.getModifierList()).
                                toString().contains("abstract")? "abstract": "class";
    }
}
