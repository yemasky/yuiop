package com.web.base;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
public abstract class BaseController {
	protected void check() {
	}

	protected void service() {
	}
	
	@RequestMapping("/view2/{courseId}")
	protected void release() {
	}

	
	public BaseController(@PathVariable("courseId") Integer courseId,
			Map<String, Object> model)  {
		this.check();
		this.service();
		this.release();
	}
}
