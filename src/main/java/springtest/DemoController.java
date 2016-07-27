package springtest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

	private static List<SseEmitter> emitters = new ArrayList<>();
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

	@ResponseBody
	@RequestMapping("/add.do")
	public Message addData(String msg) {
		Message message = new Message();
		message.setMessage(msg);
		message.setFrom(request.getRemoteHost());
		Iterator<SseEmitter> iter = emitters.iterator();
		while(iter.hasNext()){
			SseEmitter emitter = iter.next();
			System.out.println(emitter.hashCode() + "===准备发送消息"+message);
			try {

				emitter.send(message);
				System.out.println(emitter.hashCode() + "===消息发送成功");
			} catch (Exception e) {
				System.out.println("给客户端发送消息失败"+emitter);
				//emitter.complete();
				iter.remove();
				//emitters.remove(emitter);
				//e.printStackTrace();
				System.out.println(emitter.hashCode() + "===客户端移除");
			}

		}
		System.out.println(emitters.size()+" 客户端个数");
		return message;
	}

	@RequestMapping(path = "/pull.do", method = RequestMethod.GET)
	public SseEmitter pull() {
		SseEmitter emitter = new SseEmitter((long) (60 * 1000));
		System.out.println(emitter.hashCode() + "启动sseEmitter");
		emitter.onCompletion(() -> emitters.remove(emitter));
		// emitter.onTimeout(() -> System.out.println("timeout........"));
		emitters.add(emitter);
		System.out.println("现在客户端个数："+emitters.size());
		return emitter;
	}

	@RequestMapping("/json.do")
	@ResponseBody
	public Map<String, Object> json() {
		Map<String, Object> result = new HashMap<>();
		result.put("msg", new Date());
		return result;
	}
}
