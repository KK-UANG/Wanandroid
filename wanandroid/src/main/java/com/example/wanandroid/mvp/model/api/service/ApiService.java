package com.example.wanandroid.mvp.model.api.service;

import com.example.wanandroid.mvp.model.api.entity.Article;
import com.example.wanandroid.mvp.model.api.entity.ArticleInfo;
import com.example.wanandroid.mvp.model.api.entity.HomeBanner;
import com.example.wanandroid.mvp.model.api.entity.HotKey;
import com.example.wanandroid.mvp.model.api.entity.Navigation;
import com.example.wanandroid.mvp.model.api.entity.Tab;
import com.example.wanandroid.mvp.model.api.entity.User;
import com.example.wanandroid.mvp.model.api.entity.WanAndroidResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    /**
     * 首页轮播图
     * @return
     */
    @GET("banner/json")
    Observable<WanAndroidResponse<List<HomeBanner>>> onHomeBanner();

    /**
     * 首页文章列表
     * @param page 页码
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<WanAndroidResponse<ArticleInfo>> onHomeArticles(@Path("page") int page);

    /**
     * 置顶文章
     */
    @GET("article/top/json")
    Observable<WanAndroidResponse<List<Article>>> onTopArticles();

    /**
     * 项目分类
     */
    @GET("project/tree/json")
    Observable<WanAndroidResponse<List<Tab>>> onProjectTab();

    /**
     * 项目列表数据
     * @param page
     * @param cid
     * @return
     */
    @GET("project/list/{page}/json")
    Observable<WanAndroidResponse<ArticleInfo>> onProjects(@Path("page") int page, @Query("cid") int cid);

    /**
     * 获取公众号列表
     * https://wanandroid.com/wxarticle/chapters/json
     */
    @GET("wxarticle/chapters/json")
    Observable<WanAndroidResponse<List<Tab>>> onWxList();

    /**
     * 查看某个公众号历史数据
     * https://wanandroid.com/wxarticle/list/408/1/json
     */
    @GET("wxarticle/list/{id}/{page}/json")
    Observable<WanAndroidResponse<ArticleInfo>> onWxArticleList(@Path("id") int id, @Path("page") int page);


    /**
     * 体系数据
     */
    @GET("tree/json")
    Observable<WanAndroidResponse<List<Tab>>> onSystem();

    /**
     * 知识体系下的文章
     * @param page 页码
     * @param cid 分类的id
     */
    @GET("article/list/{page}/json")
    Observable<WanAndroidResponse<ArticleInfo>> onSystemArticles(@Path("page") int page, @Query("cid") int cid);

    /**
     * 导航数据
     * @return
     */
    @GET("/navi/json")
    Observable<WanAndroidResponse<List<Navigation>>> onNavigation();

    /**
     * 广场列表数据
     * https://wanandroid.com/user_article/list/0/json
     * GET请求
     * 页码拼接在url上从0开始
     * 可能出现返回列表数据<每页数据，因为有自见的文章被过滤掉了。
     */
    @GET("user_article/list/{page}/json")
    Observable<WanAndroidResponse<ArticleInfo>> onSquareArticles(@Path("page") int page);

    /**
     * 搜索热词
     */
    @GET("/hotkey/json")
    Observable<WanAndroidResponse<List<HotKey>>> onHotKeys();

    /**
     * 搜索
     */
    @POST("/article/query/{page}/json")
    @FormUrlEncoded
    Observable<WanAndroidResponse<ArticleInfo>> onSearch(@Path("page") int page, @Field("k") String key);

    /**
     * 登录
     * @param  username 账号 password 密码
     * @return
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<WanAndroidResponse<User>> onLogin(@Field("username") String username, @Field("password") String password);

    /**
     * 退出登录
     * http://www.wanandroid.com/user/logout/json
     */
    @GET("user/logout/json")
    Observable<WanAndroidResponse> onLogout();

    /**
     * 注册
     * @param username 用户名
     * @param password 密码
     * @param repassword 确认密码
     * @return
     */
    @POST("user/register")
    @FormUrlEncoded
    Observable<WanAndroidResponse<User>> onRegister(@Field("username") String username, @Field("password") String password, @Query("repassword") String repassword);

}



