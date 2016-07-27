package springtest;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

@Controller
public class DemoController {

    private static List<SseEmitter> emitters = Collections.synchronizedList(new ArrayList<>());
    private HttpServletRequest request;
    private HttpServletResponse response;

    @ModelAttribute
    public void setBase(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @RequestMapping("/index.do")
    public String index() {
        return "index";
    }

    class SendThread implements Runnable {
        private Message message;

        public SendThread(Message message) {
            this.message = message;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
            List<SseEmitter> waitRemoveList = new ArrayList<>();
            Iterator<SseEmitter> iter = emitters.iterator();
            while (iter.hasNext()) {
                SseEmitter emitter = iter.next();
                System.out.println(emitter.hashCode() + "===准备发送1消息" + message);
                try {
                    emitter.send(message,MediaType.TEXT_PLAIN);
                    System.out.println(emitter.hashCode() + "===消息发送成功");
                } catch (Exception e) {
                    System.out.println("给客户端发送消息失败" + emitter);
                    System.out.println(emitter.hashCode() + "===客户端移除");
                    waitRemoveList.add(emitter);
                }
            }
            waitRemoveList.forEach(e -> emitters.remove(e));
            System.out.println(emitters.size() + " 客户端个数");
        }
    }

    @ResponseBody
    @RequestMapping("/add.do")
    public Message addData(String msg) {
        Message message = new Message();
        message.setMessage(msg);
        message.setFrom(request.getRemoteHost());
        Thread thread = new Thread(new SendThread(message));
        thread.start();
        System.out.println("............return");
        return message;
    }

    @RequestMapping(path = "/pull.do", method = RequestMethod.GET)
    public SseEmitter pull() {
        SseEmitter emitter = new SseEmitter((long) (600 * 1000));
        System.out.println(emitter.hashCode() + "启动sseEmitter");
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitters.add(emitter);
        System.out.println("现在客户端个数：" + emitters.size());
        return emitter;
    }

    @RequestMapping("/json.do")
    @ResponseBody
    public Message json(String msg) {
        Message message = new Message();
        message.setFrom(request.getRemoteHost());
        message.setMessage(msg);
        return message;
    }
}
