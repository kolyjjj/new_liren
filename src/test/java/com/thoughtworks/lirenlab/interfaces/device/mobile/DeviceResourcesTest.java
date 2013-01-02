package com.thoughtworks.lirenlab.interfaces.device.mobile;

import com.thoughtworks.lirenlab.interfaces.device.facade.DeviceServiceFacade;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DeviceResourcesTest {

    @Test
    public void can_register_device_push_token() throws Exception {
        DeviceServiceFacade deviceServiceFacade = mock(DeviceServiceFacade.class);
        DeviceResources deviceResources = new DeviceResources(deviceServiceFacade);
        DeviceRegisterCommand command = new DeviceRegisterCommand();
        String deviceId = "1234";
        command.setDeviceToken("some token");

        Response response = deviceResources.register(deviceId, command);

        verify(deviceServiceFacade).registerDevice(deviceId, command.getDeviceToken());
        assertThat(response.getStatus(), is(200));
    }
}