package com.celdunt.drawdiag.file;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;

import static com.intellij.openapi.actionSystem.AnAction.getEventProject;

public class DiagramReader {

    /**
     *
     * @param diagramFile Diagram file in VirtualFile form
     * @return Text diagram file
     * @throws IOException If there is an error reading from or writing to the data stream
     */
    public static String openDiagramFile(VirtualFile diagramFile) throws IOException {
        if (!diagramFile.getPath().endsWith(".plantuml")) {
            System.out.println("Wrong File!");
            return "";
        }
        return new String(diagramFile.contentsToByteArray());
    }

    /**
     *
     * @param e Context of the action
     * @return Selected diagram file to open
     */
    public static VirtualFile getChooserPath(AnActionEvent e) {
        FileChooserDescriptor descriptor = FileChooserDescriptorFactory.createSingleFileDescriptor();
        Project project = getEventProject(e);

        return FileChooser.chooseFile(descriptor, project, null);
    }
}
