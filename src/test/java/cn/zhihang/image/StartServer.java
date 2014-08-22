package cn.zhihang.image;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;

import com.google.common.collect.Lists;

/**
 * 使用 Jetty运行Web应用
 * 
 * @author cnlyml
 * @date 2014-8-21
 */
public class StartServer {

    public static final int PORT = 8080;
    public static final String CONTEXT = "/cnlymlImage";
    public static final String[] TLD_JAR_NAMES = { "spring-webmvc" };
    public static final String DEFAULT_WEBAPP_PATH = "src/main/webapp";

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        System.out.println("[提示] 服务器正在启动中");
        // 在Spring启动前，设置profile的环境变量
        System.setProperty("spring.profiles.active", "development");

        // 启动Jetty
        Server server = createServerInSource(PORT, CONTEXT);
        setTldJarNames(server, TLD_JAR_NAMES);
        try {
            server.start();
            long endTime = System.currentTimeMillis();
            System.out.println("[提示] 服务器启动用时:" + (endTime - startTime) + "毫秒");
            System.out
                    .println("[提示] 服务器运行在 http://localhost:" + PORT + CONTEXT);
            System.out.println("[提示] 敲击回车键快速重启服务器");

            // 等待用户输入回车重载应用
            while (true) {
                char c = (char) System.in.read();
                if (c == '\n') {
                    reloadContext(server);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * 创建开发运行的Jetty Servers
     * 
     * @param port
     * @param contextPath
     * @return
     */
    private static Server createServerInSource(int port, String contextPath) {
        Server server = new Server();

        // 设置在JVM退出时关闭Jetty的钩子
        server.setStopAtShutdown(true);

        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(port);

        // 解决Windows下重复启动Jetty不报告端口冲突的问题
        connector.setReuseAddress(false);
        server.setConnectors(new Connector[] { connector });

        WebAppContext webAppContext = new WebAppContext(DEFAULT_WEBAPP_PATH,
                contextPath);

        // 修改webdefault.xml，解决Windows下Jetty Lock住静态文件的问题.
        webAppContext.setDefaultsDescriptor("webdefault-windows.xml");

        server.setHandler(webAppContext);

        return server;
    }

    /**
     * 快速重启application，重载target/classes与target/test-classes
     * 
     * @throws Exception
     */
    private static void reloadContext(Server server) throws Exception {
        WebAppContext context = (WebAppContext) server.getHandler();

        System.out.println("[提示] 服务器重载中");
        context.stop();

        WebAppClassLoader classLoader = new WebAppClassLoader(context);
        classLoader.addClassPath("target/classes");
        classLoader.addClassPath("target/test-classes");
        context.setClassLoader(classLoader);

        context.start();

        System.out.println("[提示] 服务器重启完毕");
    }

    /**
     * 设置除jstl-*.jar外其他含tld文件的jar包的名称. jar名称不需要版本号，如sitemesh, shiro-web
     */
    private static void setTldJarNames(Server server, String... jarNames) {
        WebAppContext context = (WebAppContext) server.getHandler();
        List<String> jarNameExprssions = Lists.newArrayList(
                ".*/jstl-[^/]*\\.jar$", ".*/.*taglibs[^/]*\\.jar$");
        for (String jarName : jarNames) {
            jarNameExprssions.add(".*/" + jarName + "-[^/]*\\.jar$");
        }

        context.setAttribute(
                "org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
                StringUtils.join(jarNameExprssions, '|'));

    }

}
