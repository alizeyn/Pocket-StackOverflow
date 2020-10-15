package sonofman.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sonofman.model.ParseResult;
import sonofman.network.RetrofitFactory;
import sonofman.ui.BaseToolWindow;
import sonofman.ui.CenterMessageView;
import sonofman.ui.PluginIcons;
import sonofman.ui.ResultListView;

import java.util.List;

public class SearchAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        String selectedText = null;
        if (editor != null) {
            selectedText = editor.getSelectionModel().getSelectedText();
        }
        if (selectedText == null || selectedText.isEmpty()) {
            Messages.showErrorDialog("You must select a text to search", "Error");
        } else if (selectedText.length() < 20) {
            Messages.showErrorDialog("Please consider search With more than 20 characters", "Error");
        } else {
            Project project = e.getProject();

            handleToolWindowVisibility(project);

            BaseToolWindowFactory.ProjectService projectService = ServiceManager.getService(project,
                    BaseToolWindowFactory.ProjectService.class);

            BaseToolWindow baseToolWindow = projectService.getBaseToolWindow();
            baseToolWindow.removeAll();

            ResultListView resultListView = new ResultListView();
            baseToolWindow.addView(resultListView.getContent());

            resultListView.setProgressView();
            baseToolWindow.updateView();

            RetrofitFactory.getInstance()
                    .getSearchRetrofit()
                    .searchStackOverflow(selectedText)
                    .enqueue(new Callback<List<ParseResult>>() {
                        @Override
                        public void onResponse(@NotNull Call<List<ParseResult>> call,
                                               @NotNull Response<List<ParseResult>> response) {

                            List<ParseResult> responseBody = response.body();

                            if (response.isSuccessful() && responseBody != null) {
                                resultListView.updateData(responseBody);
                                Messages.showInfoMessage(response.message(), "Success");
                            } else {
                                String errorMessage = responseBody == null
                                        ? "No response received"
                                        : response.message();

                                Messages.showErrorDialog(errorMessage, "Server Error");
                                baseToolWindow.removeAll();
                                baseToolWindow.addView(new CenterMessageView().getContentHolder());
                                baseToolWindow.updateView();
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<List<ParseResult>> call, @NotNull Throwable t) {

                            t.printStackTrace();

                            ApplicationManager.getApplication().invokeLater(() -> {
                                Messages.showErrorDialog(t.getMessage(), "Connection Error");
                                baseToolWindow.removeAll();
                                baseToolWindow.addView(new CenterMessageView().getContentHolder());
                                baseToolWindow.updateView();
                            });
                        }
                    });
        }
    }

    private void handleToolWindowVisibility(Project project) {
        ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("StackOverflow");
        if (toolWindow != null && !toolWindow.isVisible()) {
            toolWindow.show(null);
        }
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
        e.getPresentation().setIcon(PluginIcons.SON_OF_MAN);
    }
}
