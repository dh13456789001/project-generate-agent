package cn.huat.duaicodemother.controller;

import cn.huat.duaicodemother.common.BaseResponse;
import cn.huat.duaicodemother.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public BaseResponse<String> health() {

        return ResultUtils.success("ok");
    }


}
