package com.proa.app.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proa.app.entities.Client;

//sin eutreka
//@FeignClient(name = "micro-client", url = "http://localhost:8081/client")
@FeignClient(name = "micro-client")
public interface IFeignClientM {
	
	@GetMapping("/client/id")
	Client selectById(@RequestParam long id);

}
