package sonofman.util;

import com.intellij.ui.JBColor;

public class HtmlTweak {

    public static String refineHtmlResponse(String response) {

        //TODO: what if there is <code> tag in the real code !?
        String result = response.replace("<pre>", "<pre class='codeblock'>")
                .replaceAll("<code>(?!.*</pre>)", "<code class='inlinecode'>");

        String text = result.replace("\\n", "");
        JBColor color = new JBColor(JBColor.WHITE, JBColor.BLACK);
        String hexColor = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());

        String codeBlockStyle = ".codeblock{font-size:xx-small; background-color:" + hexColor + ";padding:5px;margin:5px;}";
        String contentHolderStyle = "img{max-width:200px;}";
        String inlineCodeStyle = String.format(".inlinecode{background-color:%s;}", hexColor);

        return String.format("<html><style>" + codeBlockStyle + inlineCodeStyle + contentHolderStyle + "</style>%s</html>", text);
    }
}
