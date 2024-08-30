package net.hwyz.iov.cloud.tsp.rvc.service.facade.mp;

import cn.hutool.json.JSONUtil;
import net.hwyz.iov.cloud.tsp.rvc.api.contract.request.ControlRequest;
import net.hwyz.iov.cloud.tsp.rvc.service.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 远程车辆控制相关手机接口测试类
 *
 * @author hwyz_leo
 */
@AutoConfigureMockMvc
public class RvcMpControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    private final String path = "/mp/rvc";

    @Test
    @Order(1)
    @DisplayName("寻车")
    public void testFindVehicle() throws Exception {
        ControlRequest request = new ControlRequest();
        request.setVin(vin);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(path + "/action/findVehicle")
                        .headers(newHttpHeader())
                        .content(JSONUtil.toJsonStr(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
