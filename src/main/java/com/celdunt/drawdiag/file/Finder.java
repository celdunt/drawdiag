package com.celdunt.drawdiag.file;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Finder {
    private PsiDirectory directory;
    final private List<PsiJavaFile> firstPsiJavaFiles = new ArrayList<>();

    public List<PsiJavaFile> psiJavaFiles;

    public Finder(Project project, VirtualFile projectFileDirectory) {
        directory = PsiManager.getInstance(project).findDirectory(projectFileDirectory);
    }

    /**
     * Searching for class files and destroy duplicates
     */
    public void find() {
        findJavaFiles();
        destroyDuplicates();
    }

    /**
     * Searching for java class files and destroy duplicates
     */
    private void findJavaFiles() {
        for (PsiDirectory psiDirectory : directory.getSubdirectories()) {
            for (PsiFile psiFile : psiDirectory.getFiles()) {
                if (psiFile.getName().endsWith("class")) {
                    if (psiFile instanceof PsiJavaFile) {
                        firstPsiJavaFiles.add((PsiJavaFile) psiFile);
                    }
                }
            }
            directory = psiDirectory;
            findJavaFiles();
        }
    }

    /**
     * Destroy duplicates
     */
    private void destroyDuplicates() {
        psiJavaFiles = new ArrayList<>(new HashSet<>(firstPsiJavaFiles));

        for (int i = 0; i < psiJavaFiles.size(); i++) {
            for (int j = i + 1; j < psiJavaFiles.size(); j++) {
                if (psiJavaFiles.get(i).getName().equals(psiJavaFiles.get(j).getName())) {
                    psiJavaFiles.remove(j--);
                }
            }
        }
    }

}
