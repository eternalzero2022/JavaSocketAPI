package org.example.server.data;
import java.util.ArrayList;
/**
 * url表，用来记录url数据。使用单例模式
 *通过链表的方式实现
 */
public class UrlTable {
    private static final UrlTable instance = new UrlTable();
    private ArrayList<Url> urls;
    private UrlTable()
    {
        urls = new ArrayList<Url>();
        Url node1 = new Url("./headers.txt");
        node1.set_New_url(new Url("/ServerResources/headers.txt"), Url.Status.temporary);
        urls.add(node1);
        Url node2 = new Url("./Earth.png");
        node2.set_New_url(new Url("/ServerResources/Earth.png"), Url.Status.permanent);
        urls.add(node2);
        Url node3 = new Url("./AirportTakeOff.mp3");
        node3.set_New_url(new Url("/ServerResources/AirportTakeOff.mp3"), Url.Status.permanent);
        urls.add(node3);
        Url node4 = new Url("./Basil.jpeg");
        node4.set_New_url(new Url("/ServerResources/Basil.jpeg"), Url.Status.permanent);
        urls.add(node4);
    }
    public static UrlTable getInstance()
    {
        return instance;
    }
    public boolean addUrl(String url)
    {
        if(getUrl(url)!=null)
            return false;
        urls.add(new Url(url));
        return true;
    }
    public Url getUrl(String url)
    {
        for(Url url1:urls)
        {
            if(url1.get_url().equals(url))
                return url1;
        }
        return null;
    }
    /**
     * Url中包含url信息，Is_Valid表示是否被移动至新地址，如果没有则表明源地址有效，IsValid设置为true
     *如果被移动至新地址，则表明源地址无效，IsValid设置为false，新地址为New_url，Status表示是否为永久移动
     *find_new_url()函数用于查找最终的url，通过链表查询实现
     */
    public static class Url{
        private String url;
        private Url New_url;
        private boolean Is_Valid;
        public enum Status{
            temporary,
            permanent
        }
        Url(String url){
            this.url = url;
            this.New_url = null;
            this.Is_Valid = true;
            this.status = null;
        }
        private Status status;
        public String get_url(){
            return url;
        }
        public Url get_New_url(){
            return New_url;
        }
        public boolean get_Is_Valid(){
            return Is_Valid;
        }
        public Status get_Status(){
            return status;
        }
        public void set_url(String url){
            this.url = url;
        }
        public void set_New_url(Url New_url,Status status){
            this.New_url = New_url;
            this.Is_Valid = false;
            this.status = status;
        }
        public Url find_new_url(){
            Url url = this;
            while(url.get_New_url() != null){
                url = url.get_New_url();
            }
            return url;
        }
    }
}
