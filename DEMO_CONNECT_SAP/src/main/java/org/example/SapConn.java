package org.example;


/**
 * @Author: Wriprin
 * @Date: 2022/11/25 17:04
 * @Version 1.0
 */
public class SapConn {
    // SAP server
    private String JCO_ASHOST;
    // SAP system number
    private String JCO_SYSNR;
    // SAP client
    private String JCO_CLIENT;
    // SAP user name
    private String JCO_USER;
    // SAP user password
    private String JCO_PASSWD;
    // SAP language
    private String JCO_LANG;
    // MAX connection
    private String JCO_POOL_CAPACITY;
    // MAX thread
    private String JCO_PEAK_LIMIT;
    // SAP ROUTER
    private String JCO_SAPROUTER;
    public SapConn(String JCO_ASHOST, String JCO_SYSNR, String JCO_CLIENT, String JCO_USER,
                   String JCO_PASSWD, String JCO_LANG, String JCO_POOL_CAPACITY, String JCO_PEAK_LIMIT,
                   String JCO_SAPROUTER) {
        this.JCO_ASHOST = JCO_ASHOST;
        this.JCO_SYSNR = JCO_SYSNR;
        this.JCO_CLIENT = JCO_CLIENT;
        this.JCO_USER = JCO_USER;
        this.JCO_PASSWD = JCO_PASSWD;
        this.JCO_LANG = JCO_LANG;
        this.JCO_POOL_CAPACITY = JCO_POOL_CAPACITY;
        this.JCO_PEAK_LIMIT = JCO_PEAK_LIMIT;
        this.JCO_SAPROUTER = JCO_SAPROUTER;
    }

    public SapConn() {
    }

    public String getJCO_ASHOST() {
        return JCO_ASHOST;
    }

    public void setJCO_ASHOST(String JCO_ASHOST) {
        this.JCO_ASHOST = JCO_ASHOST;
    }

    public String getJCO_SYSNR() {
        return JCO_SYSNR;
    }

    public void setJCO_SYSNR(String JCO_SYSNR) {
        this.JCO_SYSNR = JCO_SYSNR;
    }

    public String getJCO_CLIENT() {
        return JCO_CLIENT;
    }

    public void setJCO_CLIENT(String JCO_CLIENT) {
        this.JCO_CLIENT = JCO_CLIENT;
    }

    public String getJCO_USER() {
        return JCO_USER;
    }

    public void setJCO_USER(String JCO_USER) {
        this.JCO_USER = JCO_USER;
    }

    public String getJCO_PASSWD() {
        return JCO_PASSWD;
    }

    public void setJCO_PASSWD(String JCO_PASSWD) {
        this.JCO_PASSWD = JCO_PASSWD;
    }

    public String getJCO_LANG() {
        return JCO_LANG;
    }

    public void setJCO_LANG(String JCO_LANG) {
        this.JCO_LANG = JCO_LANG;
    }

    public String getJCO_POOL_CAPACITY() {
        return JCO_POOL_CAPACITY;
    }

    public void setJCO_POOL_CAPACITY(String JCO_POOL_CAPACITY) {
        this.JCO_POOL_CAPACITY = JCO_POOL_CAPACITY;
    }

    public String getJCO_PEAK_LIMIT() {
        return JCO_PEAK_LIMIT;
    }

    public void setJCO_PEAK_LIMIT(String JCO_PEAK_LIMIT) {
        this.JCO_PEAK_LIMIT = JCO_PEAK_LIMIT;
    }

    public String getJCO_SAPROUTER() {
        return JCO_SAPROUTER;
    }

    public void setJCO_SAPROUTER(String JCO_SAPROUTER) {
        this.JCO_SAPROUTER = JCO_SAPROUTER;
    }

    @Override
    public String toString() {
        return "SapConn{" +
                "JCO_ASHOST='" + JCO_ASHOST + "'" +
        ", JCO_SYSNR='" + JCO_SYSNR + "'" +
        ", JCO_CLIENT='" + JCO_CLIENT + "'" +
        ", JCO_USER='" + JCO_USER + "'" +
        ", JCO_PASSWD='" + JCO_PASSWD + "'" +
        ", JCO_LANG='" + JCO_LANG + "'" +
        ", JCO_POOL_CAPACITY='" + JCO_POOL_CAPACITY + "'" +
        ", JCO_PEAK_LIMIT='" + JCO_PEAK_LIMIT + "'" +
        ", JCO_SAPROUTER='" + JCO_SAPROUTER + "'" +
        '}';
    }
}
