package plugin;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class SearchToolWindowFactory implements ToolWindowFactory {


    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

        ResultListWindow searchToolWindow = new ResultListWindow(toolWindow);
        searchToolWindow.updateData();

        ProjectService projectService = ServiceManager.getService(project, ProjectService.class);
        projectService.setSearchToolWindow(searchToolWindow);

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(searchToolWindow.getContent(), "Son of Man", false);
        toolWindow.getContentManager().addContent(content);
    }
    public static class ProjectService {

        private ResultListWindow resultListWindow;

        public ResultListWindow getSearchToolWindow() {
            return resultListWindow;
        }

        void setSearchToolWindow(ResultListWindow resultListWindow) {
            this.resultListWindow = resultListWindow;
        }
    }
}
