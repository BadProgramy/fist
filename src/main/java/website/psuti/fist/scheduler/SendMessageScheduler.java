package website.psuti.fist.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;
import website.psuti.fist.configuration.Sender;
import website.psuti.fist.constant.SendMessageEmailConstant;
import website.psuti.fist.model.User;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@Component
public class SendMessageScheduler {
    @Autowired
    private ApplicationContext applicationContext;

    public final Logger logger = LoggerFactory.getLogger(SendMessageScheduler.class);

    //каждые 5 мин проверяяет, что нет сообщений для отправки
    @Scheduled(fixedDelay = 30000)
    public void sendRepeatMessage() {
        if (!SendMessageEmailConstant.getNonSendingMessage().isEmpty()) {
            for (Map.Entry keyUser : SendMessageEmailConstant.getNonSendingMessage().entrySet()) {
                for (Map.Entry valueMessage : ((HashMap<String, String>)keyUser.getValue()).entrySet()) {
                    try {
                        Sender sender = new Sender();
                        sender.send(String.valueOf(valueMessage.getKey()),
                                String.valueOf(valueMessage.getValue()),
                                ((User)keyUser.getKey()).getUsername());
                        SendMessageEmailConstant.removeNonSendingMessage((User) keyUser.getKey());
                        logger.info("Я отправил сообщение");
                        break;
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            }
            //SendMessageEmailConstant.clearNonSendingMessage();
        } else {
            ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor = applicationContext.getBean(ScheduledAnnotationBeanPostProcessor.class);
            scheduledAnnotationBeanPostProcessor.postProcessBeforeDestruction(applicationContext.getBean(SendMessageScheduler.class), "scheduler");
            logger.info("Закрыл scheduler");
        }
    }
}
