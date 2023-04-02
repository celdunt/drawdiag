package com.celdunt.drawdiag.file;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.NoSuchElementException;

public class DiagramGenerator {
    final public String FILE_EXTENSION = "plantuml";
    private final Project project;
    private final Finder finder;
    private final List<Clazz> classes;

    public DiagramGenerator(Project project, VirtualFile projectDirectory) {
        this.project = project;
        finder = new Finder(project, projectDirectory);
        classes = getClassesFromProject();
    }

    /**
     *
     * @return Created or opened diagram file
     * @throws IOException If there is an error reading from or writing to the data stream
     */
    public VirtualFile getOutputFile() throws IOException {
        String outputFileName = createOutputFileName(project.getName());
        return project.getBaseDir().findOrCreateChildData(this, outputFileName);
    }

    /**
     *
     * @param name Diagram name
     * @return Full diagram name with file extension
     */
    private String createOutputFileName(String name) {
        return name + "." + FILE_EXTENSION;
    }

    /**
     * Generating the diagram and saving it to a file
     */
    public void generate() {
        VirtualFile outputFile;

        try {
            outputFile = getOutputFile();
            OutputStream outputStream = outputFile.getOutputStream(this);

            generateDiagram(outputStream);

            outputStream.close();
        } catch (NoSuchElementException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *
     * @return list of clazzes
     */
    private List<Clazz> getClassesFromProject() {
        finder.find();
        return new ClassHandler(finder.psiJavaFiles).clazzes;
    }

    /**
     *
     * @param outputStream Data stream diagram file
     */
    private void generateDiagram(OutputStream outputStream) {
        DiagramWriter diagramWriter = new DiagramWriter(outputStream);
        diagramWriter.start();

        for (Clazz clazz : classes) {
            diagramWriter.startClass(clazz);
            diagramWriter.fields(clazz);
            diagramWriter.methods(clazz);
            diagramWriter.endClass();
        }

        diagramWriter.startRelations();

        for (Clazz clazz : classes) {
            diagramWriter.extend(clazz);
            diagramWriter.implement(clazz);
            diagramWriter.association(clazz);
        }

        diagramWriter.end();
    }
}

