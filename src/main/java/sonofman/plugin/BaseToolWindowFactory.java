package sonofman.plugin;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import sonofman.ui.BaseToolWindow;
import sonofman.ui.CenterMessageView;

public class BaseToolWindowFactory implements ToolWindowFactory {


    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

        BaseToolWindow baseToolWindow = new BaseToolWindow(toolWindow);
        CenterMessageView centerMessage = new CenterMessageView();
        baseToolWindow.addView(centerMessage.getContentHolder());

        ProjectService projectService = ServiceManager.getService(project, ProjectService.class);
        projectService.setBaseToolWindow(baseToolWindow);

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(baseToolWindow.getContent(), "Son of Man", false);
        toolWindow.getContentManager().addContent(content);
    }

    public static class ProjectService {

        private BaseToolWindow baseToolWindow;

        public BaseToolWindow getBaseToolWindow() {
            return baseToolWindow;
        }

        void setBaseToolWindow(BaseToolWindow baseToolWindow) {
            this.baseToolWindow = baseToolWindow;
        }
    }
}
