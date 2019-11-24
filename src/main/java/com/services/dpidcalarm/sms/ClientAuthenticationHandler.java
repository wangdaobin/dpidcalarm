package com.services.dpidcalarm.sms;
import org.codehaus.xfire.MessageContext;
import org.codehaus.xfire.handler.AbstractHandler;
import org.jdom.Element;

/**
 * @Description：短信发送模块客户端认证处理器
 * @Author：zhangtao
 * @Date：2019/11/24 0002 15:40:51
 */
public class ClientAuthenticationHandler extends AbstractHandler
{
    private String username = null;

    private String password = null;

    public ClientAuthenticationHandler(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void invoke(MessageContext context) throws Exception
    {
        Element el = new Element("header");
        context.getOutMessage().setHeader(el);
        Element auth = new Element("AuthenticationToken");
        Element username_el = new Element("username");
        username_el.addContent(this.username);
        Element password_el = new Element("password");
        password_el.addContent(this.password);
        auth.addContent(username_el);
        auth.addContent(password_el);
        el.addContent(auth);
    }
}