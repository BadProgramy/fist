package website.psuti.fist.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import website.psuti.fist.model.User;
import website.psuti.fist.scheduler.SendMessageScheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class SendMessageEmailConstant {
    //2ой хешь мап (ключ - загаловок; значение - тело сообщения)
    private static HashMap<User, HashMap<String, String>> nonSendingMessage;

    static {
        if (nonSendingMessage == null) {
            nonSendingMessage = new HashMap<>();
        }
    }

    public static void addSendMessage(User user, HashMap<String, String> message) {
        nonSendingMessage.put(user, message);
    }

    public static HashMap<User, HashMap<String, String>> getNonSendingMessage() {
        return nonSendingMessage;
    }

   public static void clearNonSendingMessage() {
        nonSendingMessage.clear();
   }

   public static void removeNonSendingMessage(User user) {
        nonSendingMessage.remove(user);
   }
}
