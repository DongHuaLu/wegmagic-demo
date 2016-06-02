import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

public class _1cdsnbbs implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来
//    	System.out.println(page.getHtml());
//        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
//        page.putField("title", page.getHtml().xpath("//td[@class='title']/a/text()").links().all());
//    	List<String> all = page.getHtml().xpath("//tr[@class='zebra']/td[@class='title']/a/text()]").all();
//    	for(String a : all){
//    		System.out.println(a);
//    	}
    	List<Selectable> nodes = page.getHtml().xpath("//table[@class='table_list']//td[@class='title']").nodes();
//    	List<Selectable> nodes4 = page.getHtml().xpath("//table[@class='table_list']/tr/td[position()=4]").nodes();
//    	for(Selectable node : nodes4){
//    		System.out.println(node.toString());
//    	}
    	for(Selectable node : nodes){
//    		System.out.println(node.toString());
    		System.out.println(node.xpath("//a/text()").toString());
    	}
//        if (page.getResultItems().get("name") == null) {
//            //skip this page
//            page.setSkip(true);
//        }
//        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));

        // 部分三：从页面发现后续的url地址来抓取
        page.addTargetRequests(page.getHtml().links().regex("http://bbs\\.csdn\\.net/forums/Java\\?page=\\d+").all());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        Spider.create(new _1cdsnbbs())
                //从"https://github.com/code4craft"开始抓
                .addUrl("http://bbs.csdn.net/forums/Java")
                //开启5个线程抓取
                .thread(5)
                //启动爬虫
                .run();
    }
}