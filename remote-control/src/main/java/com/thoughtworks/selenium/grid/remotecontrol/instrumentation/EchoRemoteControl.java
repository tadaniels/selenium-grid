package com.thoughtworks.selenium.grid.remotecontrol.instrumentation;

import com.thoughtworks.selenium.grid.remotecontrol.SelfRegisteringRemoteControl;
import com.thoughtworks.selenium.grid.remotecontrol.RegistrationInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Fake Selenium Remote Control echoing received request. Useful for debugging and testing purposes.
 */
public class EchoRemoteControl extends SelfRegisteringRemoteControl implements HttpRequestProcessor {

    private static final Log logger = LogFactory.getLog(SimplisticHttpServer.class);

    public EchoRemoteControl(RegistrationInfo registrationInfo) {
        super(registrationInfo);
    }

    public void launch(String[] args) throws Exception {
        new SimplisticHttpServer(5555, this).start();
    }

    public static void main(String[] args) throws Exception {
        final RegistrationInfo registrationInfo;
        final EchoRemoteControl remoteControl;

        registrationInfo = new RegistrationInfo("http://localhost:4444", "*firefox", "localhost", "5555");        
        remoteControl = new EchoRemoteControl(registrationInfo);
        remoteControl.register();
        remoteControl.ensureUnregisterOnShutdown();
        remoteControl.launch(new String[0]);
    }


    public Response process(Request request) {
        logger.info("Got: '" + request.body() + "'");
        return new Response(request.body());
    }

}
