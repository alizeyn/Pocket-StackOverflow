package sonofman.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sonofman.ui.PluginIcons;
import sonofman.model.ParseResult;
import sonofman.network.RetrofitFactory;
import sonofman.ui.BaseToolWindow;
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
        } else {
            BaseToolWindowFactory.ProjectService projectService = ServiceManager.getService(e.getProject(), BaseToolWindowFactory.ProjectService.class);
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
                        public void onResponse(Call<List<ParseResult>> call, Response<List<ParseResult>> response) {
                            System.out.println(response.body().size());
                            resultListView.updateData(response.body());
//                            baseToolWindow.updateView();
                        }

                        @Override
                        public void onFailure(Call<List<ParseResult>> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
        }
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
        e.getPresentation().setIcon(PluginIcons.SON_OF_MAN);
    }
}
