package plugin;

import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.util.ui.JBUI;
import lombok.extern.slf4j.Slf4j;
import model.ParseResult;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ResultListWindow extends SimpleToolWindowPanel {

    private final JPanel contentHolder;
    private final JBScrollPane scrollPanel;

    public ResultListWindow(ToolWindow toolWindow) {

        super(true, false);

        contentHolder = new JPanel();
        BoxLayout boxLayout = new BoxLayout(contentHolder, BoxLayout.Y_AXIS);

        contentHolder.setLayout(boxLayout);

        Border border = JBUI.Borders.empty(16);
        contentHolder.setBorder(border);

        scrollPanel = new JBScrollPane(contentHolder,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setName("scrollPane");

        Arrays.asList(scrollPanel.getComponents()).forEach(c -> {
            System.out.println(c.getName());
        });

        System.out.println("HERE IS THE REST OF THEM ---->> \n\n\n\n\n");
        Arrays.asList(contentHolder.getComponents()).forEach(c -> {
            System.out.println(c.getName());
        });

        contentHolder.add(new CenterMessage().getContentHolder());
    }

    public JBScrollPane getContent() {
        return scrollPanel;
    }

    //Test function
//    public void updateData() {
//
//        contentHolder.removeAll();
//
//        for (int i = 0; i < 3; i++) {
//
//            SearchItemUi itemUi = new SearchItemUi(i);
//            itemUi.setQuestionTitle("How to implement Android Pull-to-Refresh");
//            itemUi.setQuestion("<p>In Android applications such as Twitter (official app), when you encounter a ListView, you can pull it down (and it will bounce back when released) to refresh the content.</p>\\n\\n<p>I wonder what is the best way, in your opinion, to implement that?</p>\\n\\n<p>Some possibilities I could think of:</p>\\n\\n<ol>\\n<li>An item on top of the ListView - however I don't think scrolling back to item position 1 (0-based) with animation on the ListView is an easy task.</li>\\n<li>Another view outside the ListView - but I need to take care of moving the ListView position down when it is pulled, and I'm not sure if we can detect if the drag-touches to the ListView still really scroll the items on the ListView.</li>\\n</ol>\\n\\n<p>Any recommendations?</p>\\n\\n<p>P.S. I wonder when the official Twitter app source code is released. It has been mentioned that it will be released, but 6 months has passed and we haven't heard about it since then.</p>\\n");
//            itemUi.setAnswer("<p>Finally, Google released an official version of the pull-to-refresh library! </p>\n\n<p>It is called <code>SwipeRefreshLayout</code>, inside the support library, and the documentation is <a href=\"https://developer.android.com/reference/android/support/v4/widget/SwipeRefreshLayout.html\" rel=\"noreferrer\"><strong>here</strong></a>:</p>\n\n<ol>\n<li><p>Add <a href=\"codelink\" <code>SwipeRefreshLayout</code></a> as a parent of view which will be treated as a pull to refresh the layout. (I took <code>ListView</code> as an example, it can be any <code>View</code> like <code>LinearLayout</code>, <code>ScrollView</code> etc.)</p>\n\n<pre><code>&lt;android.support.v4.widget.SwipeRefreshLayout\n    android:id=\"@+id/pullToRefresh\"\n    android:layout_width=\"match_parent\"\n    android:layout_height=\"wrap_content\"&gt;\n    &lt;ListView\n        android:id=\"@+id/listView\"\n        android:layout_width=\"match_parent\"\n        android:layout_height=\"match_parent\"/&gt;\n&lt;/android.support.v4.widget.SwipeRefreshLayout&gt;\n</code></pre></li>\n<li><p>Add a listener to your class</p>\n\n<pre><code>protected void onCreate(Bundle savedInstanceState) {\n    final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);\n    pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {\n        @Override\n        public void onRefresh() {\n            refreshData(); // your code\n            pullToRefresh.setRefreshing(false);\n        }\n    });\n}\n</code></pre></li>\n</ol>\n\n<p>You can also call <code>pullToRefresh.setRefreshing(true/false);</code> as per your requirement.</p>\n\n<p><strong>UPDATE</strong></p>\n\n<p>Android support libraries have been deprecated and have been replaced by AndroidX. The link to the new library can be found <a href=\"https://developer.android.com/reference/androidx/swiperefreshlayout/widget/SwipeRefreshLayout\" rel=\"noreferrer\"><strong>here</strong></a>.</p>\n\n<p>Also, you need to add the following dependency to your project:</p>\n\n<pre><code>implementation 'androidx.swiperefreshlayout:swiperefreshlayout:1.0.0'\n</code></pre>\n\n<p><strong>OR</strong></p>\n\n<p>You can go to Refactor>>Migrate to AndroidX and Android Studio will handle the dependencies for you.</p>\n");
//            JPanel panel = itemUi.getContent();
//            panel.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//            contentHolder.add(panel);
//            contentHolder.add(new JSeparator());
//        }
//    }

    public void updateData(List<ParseResult> resultList) {

        contentHolder.removeAll();

        resultList.forEach(result -> {
            SearchItemUi itemUi = new SearchItemUi(result);
            JPanel panel = itemUi.getContent();
            panel.setAlignmentX(Component.LEFT_ALIGNMENT);

            contentHolder.add(panel);
            contentHolder.add(new JSeparator());
        });
    }

    public void setProgressView() {

        contentHolder.removeAll();
        contentHolder.add(new ProgressView().getContentHolder());
    }
}
