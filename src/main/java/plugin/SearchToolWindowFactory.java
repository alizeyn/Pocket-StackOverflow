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

        SearchToolWindow searchToolWindow = new SearchToolWindow(toolWindow);

        ProjectService projectService = ServiceManager.getService(project, ProjectService.class);
        projectService.setSearchToolWindow(searchToolWindow);

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(searchToolWindow.getContent(), "Son Of Man", false);
        toolWindow.getContentManager().addContent(content);
    }
    public static class ProjectService {

        private SearchToolWindow gerritToolWindow;

        public SearchToolWindow getSearchToolWindow() {
            return gerritToolWindow;
        }

        void setSearchToolWindow(SearchToolWindow gerritToolWindow) {
            this.gerritToolWindow = gerritToolWindow;
        }
    }
}
