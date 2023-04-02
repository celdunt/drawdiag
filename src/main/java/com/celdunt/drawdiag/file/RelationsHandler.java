package com.celdunt.drawdiag.file;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaCodeReferenceElement;
import com.intellij.psi.PsiReferenceList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RelationsHandler {

    /**
     *
     * @param referenceList Reference class list in the raw form
     * @return Reference class list as a string array
     */
    public static List<String> defineReferenceList(PsiReferenceList referenceList) {
        List<String> returnable = new ArrayList<>();

        for (PsiJavaCodeReferenceElement element : referenceList.getReferenceElements()) {
            returnable.add(element.getReferenceName());
        }

        return returnable;
    }

    /**
     *
     * @param fields Class fields
     * @param methods Class methods
     * @param className Class name
     * @param psiClasses Class in the row form
     * @return Associations reference class list as a string array
     */
    public static List<String> defineAssociationsList(List<String> fields, List<String> methods, String className,List<PsiClass> psiClasses) {
        List<String> returnable = new ArrayList<>();

        for (String field : fields) {
            if (!field.endsWith(className)) {
                for (PsiClass psiClass : psiClasses) {
                    String psiClassName = Objects.requireNonNull(psiClass.getName());
                    if (field.endsWith(psiClassName)) {
                        returnable.add(psiClassName);
                    }
                }
            }
        }

        for (String method : methods) {
            if (!method.endsWith(className)) {
                for (PsiClass psiClass : psiClasses) {
                    String psiClassName = Objects.requireNonNull(psiClass.getName());
                    if (method.endsWith(psiClassName)) {
                        returnable.add(psiClassName);
                    }
                }
            }
        }

        return returnable;
    }

}
