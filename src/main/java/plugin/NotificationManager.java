package plugin;

import com.intellij.notification.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;

import javax.swing.*;

public class NotificationManager {

    public static final NotificationGroup GROUP_DISPLAY_ID_INFO =
            new NotificationGroup("Son of Man",
                    NotificationDisplayType.TOOL_WINDOW, true);


    public static void showMyMessage(String message, NotificationType notificationType, int expireTime) {

        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {

                Notification notification = GROUP_DISPLAY_ID_INFO.createNotification(message, notificationType);
                Project[] projects = ProjectManager.getInstance().getOpenProjects();
                Notifications.Bus.notify(notification, projects[0]);

                new Timer(expireTime, actionEvent -> {
                    notification.expire();
                }).start();
            }
        });
    }

}
