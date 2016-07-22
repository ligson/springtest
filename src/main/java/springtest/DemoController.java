package springtest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

	private List<SseEmitter> emitters = new ArrayList<>();
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
		System.out.println(request.isAsyncSupported());
		System.out.println(request.isAsyncStarted());
		Message message = new Message();
		message.setMessage(msg);
		message.setFrom(request.getRemoteHost());
		for (SseEmitter emitter : emitters) {
			System.out.println(emitter.hashCode() + "===invoke===");
			try {

				emitter.send(message, MediaType.APPLICATION_JSON);
			} catch (IOException e) {
				emitter.complete();
				e.printStackTrace();
			}
		}

		return message;
	}

	@RequestMapping(path = "/pull.do", method = RequestMethod.GET)
	public SseEmitter pull() {
		SseEmitter emitter = new SseEmitter((long) (60 * 1000));
		System.out.println(emitter.hashCode() + "===start");
		emitter.onCompletion(() -> System.out.println("competion......."));
		// emitter.onTimeout(() -> System.out.println("timeout........"));
		try {
			System.out.println("okkkkkkkkkk..");
			SseEventBuilder eventBuilder = SseEmitter.event().name("message").id("1").data("receive...",
					MediaType.TEXT_PLAIN);
			emitter.send(eventBuilder);
			// emitter.complete();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		emitters.addAll(emitters);
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
