import com.intellij.icons.AllIcons;
import com.intellij.ide.BrowserUtil;
import com.intellij.ide.actions.MacEmojiAndSymbolsInputAction;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;
import model.ParseResult;
import network.RetrofitFactory;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;

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
            RetrofitFactory.getInstance()
                    .getSearchRetrofit()
                    .searchStackOverflow(selectedText)
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                String result = response.body();
                                System.out.println(result);
                                Messages.showMessageDialog(result, "result", );
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

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
