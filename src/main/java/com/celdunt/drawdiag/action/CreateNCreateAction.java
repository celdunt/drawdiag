package com.celdunt.drawdiag.action;

import com.celdunt.drawdiag.file.DiagramGenerator;
import com.celdunt.drawdiag.file.DiagramReader;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import net.sourceforge.plantuml.SourceStringReader;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class CreateNCreateAction extends AnAction {
    /**
     *
     * @param e Class AnActionEven object, contains info about the context of the action
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        createAndCreateDiagram(e);
    }

    /**
     *
     * @param e Context of the action
     */
    private void createAndCreateDiagram(@NotNull AnActionEvent e) {
        Project project = e.getRequiredData(PlatformDataKeys.PROJECT);
        VirtualFile projectDirectory = e.getRequiredData(PlatformDataKeys.PROJECT_FILE_DIRECTORY);

        DiagramGenerator diagramGenerator = new DiagramGenerator(project, projectDirectory);
        diagramGenerator.generate();

        try {
            SourceStringReader reader = new SourceStringReader(DiagramReader.openDiagramFile(diagramGenerator.getOutputFile()));

            VirtualFile outputPath = DiagramReader.getChooserPath(e);
            saveDiagramAsImage(outputPath, reader);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    /**
     *
     * @param directory Location for saving the graphical representation of the diagram
     * @param reader File containing information about the textual representation of the diagram
     * @throws IOException If there is an error reading from or writing to the data stream
     */
    public static void saveDiagramAsImage(VirtualFile directory, SourceStringReader reader) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        reader.outputImage(output);

        byte[] byteArray = output.toByteArray();

        ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);

        BufferedImage image = ImageIO.read(bis);

        ImageIO.write(image, "png", new File(directory.getPath() + "\\diagram.png"));
    }
}
