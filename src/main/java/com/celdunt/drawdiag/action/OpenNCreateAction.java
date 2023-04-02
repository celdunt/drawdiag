package com.celdunt.drawdiag.action;

import com.celdunt.drawdiag.file.DiagramReader;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.vfs.VirtualFile;
import net.sourceforge.plantuml.SourceStringReader;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static com.celdunt.drawdiag.action.CreateNCreateAction.saveDiagramAsImage;

public class OpenNCreateAction extends AnAction {
    /**
     *
     * @param e Carries information on the invocation place
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        openAndCreateDiagram(e);
    }

    /**
     *
     * @param e Context of the action
     */
    private void openAndCreateDiagram(@NotNull AnActionEvent e) {
        Logger logger = Logger.getInstance(CreateNCreateAction.class);

        VirtualFile diagram = DiagramReader.getChooserPath(e);

        if (!diagram.getPath().endsWith(".plantuml")) {
            logger.info("Error, you are choose wrong file!");
            return;
        }

        try {
            SourceStringReader reader = new SourceStringReader(DiagramReader.openDiagramFile(diagram));

            VirtualFile outputPath = DiagramReader.getChooserPath(e);
            saveDiagramAsImage(outputPath, reader);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
