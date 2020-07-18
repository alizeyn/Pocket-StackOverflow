import com.esotericsoftware.minlog.Log;
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
import org.snakeyaml.engine.v2.api.lowlevel.Parse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
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
            RetrofitFactory.getInstance()
                    .getSearchRetrofit()
                    .searchStackOverflow(selectedText)
                    .enqueue(new Callback<List<ParseResult>>() {
                        @Override
                        public void onResponse(Call<List<ParseResult>> call, Response<List<ParseResult>> response) {
                            System.out.println(response.body().size());
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
