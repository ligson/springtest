package springtest;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    private static List<Message> messages = Collections.synchronizedList(new ArrayList<>());

    private SseEmitter sseEmitter = new SseEmitter();

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
            List<SseEmitter> waitRemoveList = new ArrayList<>();
            Iterator<SseEmitter> iter = emitters.iterator();
            while (iter.hasNext()) {
                SseEmitter emitter = iter.next();
                System.out.println(emitter.hashCode() + "===准备发送1消息" + message);
                try {
                    emitter.send(message);
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
        try {
            System.out.println("add Data response:" + response.getOutputStream().hashCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message message = new Message();
        message.setMessage(msg);
        message.setFrom(request.getRemoteHost());
        Thread thread = new Thread(new SendThread(message));
        thread.start();
        System.out.println("............return");
        messages.add(message);
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

    @RequestMapping(path = "/pull2.do", method = RequestMethod.GET)
    public void pull2() {
        response.setContentType("text/event-stream;charset=UTF-8");
        try {
            response.getWriter().print("data:" + new Date() + "\n\n");
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/pull3.do")
    public SseEmitter pull3() throws Exception {
        sseEmitter.send("message");
        sseEmitter.send("datga");
        return sseEmitter;
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
