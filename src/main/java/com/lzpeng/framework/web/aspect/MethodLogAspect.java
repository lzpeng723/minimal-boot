/**
 * 
 */
package com.lzpeng.framework.web.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzpeng.project.monitor.domain.MethodLog;
import com.lzpeng.project.monitor.repository.MethodLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

/**
 * AOP 面向切面编程
 * @author 李志鹏
 *
 */
@Slf4j
@Aspect
@Component
public class MethodLogAspect {

	@Autowired
	private MethodLogRepository methodLogRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Around("execution(* com.lzpeng..controller.*Controller.*(..)) || @annotation(com.lzpeng.framework.annotation.MethodLog)")
	public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
		String methodDef = pjp.getSignature().toLongString();
		Stream<Object> stream = Stream.of(pjp.getArgs()).filter(arg -> !(arg instanceof HttpServletResponse) && !(arg instanceof MultipartFile));
		String parameters = objectMapper.writeValueAsString(stream.toArray(Object[]::new));
		log.info("开始执行 {}", methodDef);
		log.info("参数 {}", parameters);
		Instant startTime = Instant.now();
		Object object = pjp.proceed();
		Instant endTime = Instant.now();
		// 执行方法所耗费的时间 毫秒
		long ms = Duration.between(startTime, endTime).toMillis();
		log.info("耗时 {}ms", ms);
		MethodLog methodLog = new MethodLog();
		methodLog.setMethodDef(methodDef);
		methodLog.setParameters(parameters);
		methodLog.setCostTime(ms);
		methodLogRepository.save(methodLog);
		return object;
	}

}
