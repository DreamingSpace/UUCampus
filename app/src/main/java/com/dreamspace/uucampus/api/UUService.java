package com.dreamspace.uucampus.api;


import com.dreamspace.uucampus.model.AllGoodsCommentRes;
import com.dreamspace.uucampus.model.GetOrderListRes;
import com.dreamspace.uucampus.model.IdleCollectionRes;
import com.dreamspace.uucampus.model.Labels;
import com.dreamspace.uucampus.model.api.AddGoodsCollectionRes;
import com.dreamspace.uucampus.model.api.AddGoodsCommentRes;
import com.dreamspace.uucampus.model.api.AddIdleCollectionRes;
import com.dreamspace.uucampus.model.api.AddIdleCommentRes;
import com.dreamspace.uucampus.model.api.AddShopCollectionRes;
import com.dreamspace.uucampus.model.api.AddShopCommentRes;
import com.dreamspace.uucampus.model.api.AllCategoryRes;
import com.dreamspace.uucampus.model.api.AllGoodsCollectionRes;
import com.dreamspace.uucampus.model.api.Card;
import com.dreamspace.uucampus.model.api.CategoryReq;
import com.dreamspace.uucampus.model.api.CheckUpdateRes;
import com.dreamspace.uucampus.model.api.CommitReportReq;
import com.dreamspace.uucampus.model.api.CommitSuggestionRes;
import com.dreamspace.uucampus.model.api.CommonStatusRes;
import com.dreamspace.uucampus.model.api.ContentReq;
import com.dreamspace.uucampus.model.api.CreateCategoryRes;
import com.dreamspace.uucampus.model.api.CreateGoodsReq;
import com.dreamspace.uucampus.model.api.CreateGoodsRes;
import com.dreamspace.uucampus.model.api.CreateIdleReq;
import com.dreamspace.uucampus.model.api.CreateIdleRes;
import com.dreamspace.uucampus.model.api.CreateLocationRes;
import com.dreamspace.uucampus.model.api.CreateOrderReq;
import com.dreamspace.uucampus.model.api.CreateOrderRes;
import com.dreamspace.uucampus.model.api.CreateShopCategoryRes;
import com.dreamspace.uucampus.model.api.CreateShopDiscountRes;
import com.dreamspace.uucampus.model.api.CreateShopReq;
import com.dreamspace.uucampus.model.api.CreateShopRes;
import com.dreamspace.uucampus.model.api.DeleteAccessTokenRes;
import com.dreamspace.uucampus.model.api.GetAdRes;
import com.dreamspace.uucampus.model.api.GetIdleInfoRes;
import com.dreamspace.uucampus.model.api.GoodsInfoRes;
import com.dreamspace.uucampus.model.api.IdleAllCommentRes;
import com.dreamspace.uucampus.model.api.IdleInfoRes;
import com.dreamspace.uucampus.model.api.LikeGoodsRes;
import com.dreamspace.uucampus.model.api.LikeIdleRes;
import com.dreamspace.uucampus.model.api.LocationAllRes;
import com.dreamspace.uucampus.model.api.LoginReq;
import com.dreamspace.uucampus.model.api.LoginRes;
import com.dreamspace.uucampus.model.api.MyGoodsRes;
import com.dreamspace.uucampus.model.api.NameReq;
import com.dreamspace.uucampus.model.api.OrderDetail;
import com.dreamspace.uucampus.model.api.PayOrderReq;
import com.dreamspace.uucampus.model.api.QnRes;
import com.dreamspace.uucampus.model.api.RegisterReq;
import com.dreamspace.uucampus.model.api.RegisterRes;
import com.dreamspace.uucampus.model.api.ReportRes;
import com.dreamspace.uucampus.model.api.ResetReq;
import com.dreamspace.uucampus.model.api.SearchGoodsRes;
import com.dreamspace.uucampus.model.api.SearchIdleRes;
import com.dreamspace.uucampus.model.api.SearchShopRes;
import com.dreamspace.uucampus.model.api.SendVerifyReq;
import com.dreamspace.uucampus.model.api.ShopAllCommentRes;
import com.dreamspace.uucampus.model.api.ShopAllGroupRes;
import com.dreamspace.uucampus.model.api.ShopCollectionRes;
import com.dreamspace.uucampus.model.api.ShopDiscountRes;
import com.dreamspace.uucampus.model.api.ShopInfoRes;
import com.dreamspace.uucampus.model.api.SuggestionRes;
import com.dreamspace.uucampus.model.api.UpdateGoodsReq;
import com.dreamspace.uucampus.model.api.UpdateIdleReq;
import com.dreamspace.uucampus.model.api.UpdateShopReq;
import com.dreamspace.uucampus.model.api.UpdateUserInfoReq;
import com.dreamspace.uucampus.model.api.UserInfoRes;
import com.dreamspace.uucampus.model.api.WeiXinBindReq;
import com.dreamspace.uucampus.model.api.WeiXinBindRes;
import com.dreamspace.uucampus.model.api.WeiXinLoginReq;
import com.dreamspace.uucampus.model.api.WeiXinLoginRes;
import com.dreamspace.uucampus.model.api.WeiXinRegisterReq;
import com.google.gson.JsonElement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.RestMethod;


/**
 * Created by Administrator on 2015/8/20 0020.
 */
public interface UUService {
    //DELETE方法默认不支持含body，自定义DELETE方法
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @RestMethod(value = "DELETE" , hasBody = true)
    public @interface DELETE{
        String value();
    }

    //创建七牛上传凭证
    @POST("/static/token/")
    void createQiNiuToken(Callback<QnRes> cb);

    //给指定手机发送短信验证码
    @POST("/auth/code/")
    void sendVerifyCode(@Body SendVerifyReq req, Callback<Response> cb);

    //用户
    //创建用户访问凭证(登陆)
    @POST("/auth/login/")
    void createAccessToken(@Body LoginReq req, Callback<LoginRes> cb);

    //微信绑定已有用户
    @POST("/auth/weixin/bind/")
    void weiXinBind(@Body WeiXinBindReq req, Callback<WeiXinBindRes> cb);

    //微信创建新用户
    @POST("/auth/weixin/register/")
    void weiXinRegister(@Body WeiXinRegisterReq req, Callback<Response> cb);

    //微信登录
    @POST("/auth/weixin/login/")
    void weiXinLogin(@Body WeiXinLoginReq req, Callback<WeiXinLoginRes> cb);

    //删除用户访问凭证(注销)
    @DELETE("/auth/logout/")
    void deleteAccessToken(Callback<DeleteAccessTokenRes> cb);

    //用户创建
    @POST("/user/")
    void register(@Body RegisterReq req, Callback<RegisterRes> cb);

    //用户信息更新
    @PUT("/user/")
    void updateUserInfo(@Body UpdateUserInfoReq req, Callback<CommonStatusRes> cb);

    //用户密码重置
    @PUT("/user/reset_password/")
    void resetPassword(@Body ResetReq req, Callback<Response> cb);

    //用户信息查看（自己）
    @GET("/user/")
    void getUserInfo(Callback<UserInfoRes> cb);

    //用户信息查看（其他用户）
    @GET("/user{user_id}")
    void getOtherUserInfo(@Path("user_id") String user_id, Callback<UserInfoRes> cb);


    //店铺
    //店铺创建
    @POST("/shop/")
    void createShop(@Body CreateShopReq req, Callback<CreateShopRes> cb);

    //店铺信息更新
    @PUT("/shop/{shop_id}")
    void updateShop(@Path("shop_id") String shop_id, @Body UpdateShopReq req, Callback<Response> cb);

    //店铺删除
    @DELETE("/shop/{shop_id}")
    void deleteShop(@Path("shop_id") String shop_id, Callback<Response> cb);

    //店铺信息查看
    @GET("/shop/{shop_id}")
    void getShopInfo(@Path("shop_id") String shop_id, Callback<ShopInfoRes> cb);

    //店铺搜索
    @GET("/shop/search/")
    void searchShop(@Query("keyword") String keyword, @Query("order")String order,@Query("category")String category,@Query("page") int page,@Query("location")String location, Callback<SearchShopRes> cb);

    //店铺评论添加
    @POST("/shop/{shop_id}/comment/")
    void addComment(@Path("shop_id") String shop_id, @Body ContentReq req, Callback<AddShopCommentRes> cb);

    //店铺所有评论查看
    @GET("/shop/{shop_id}/comment/{page}")
    void getShopComment(@Path("shop_id") String shop_id, @Path("page") int page, Callback<ShopAllCommentRes> cb);

    //店铺评论删除
    @DELETE("/shop/{shop_id}/comment/{shop_comment_id}")
    void deleteShopComment(@Path("shop_id") String shop_id, @Path("shop_comment_id") String shop_comment_id, Callback<Response> cb);

    //店铺收藏添加
    @POST("/shop/collection/{shop_id}")
    void addCollection(@Path("shop_id") String shop_id, Callback<AddShopCollectionRes> cb);

    //个人店铺收藏查看
    @GET("/shop/collection/{page}")
    void getPersonShopCollection(@Path("page") int page, Callback<ShopCollectionRes> cb);

    //店铺收藏删除
    @DELETE("/shop/collection/{shop_id}")
    void deleteShopCollection(@Path("shop_id") String shop_id, Callback<CommonStatusRes> cb);

    //店内分类创建
    @POST("/shop/{shop_id}/group/")
    void createShopCategory(@Path("shop_id") String shop_id, @Body NameReq req, Callback<CreateShopCategoryRes> cb);

    //店内分类修改
    @PUT("/shop/{shop_id}/group/{group_id}")
    void modifyShopCategory(@Path("shop_id") String shop_id, @Path("group_id") String group_id, @Body NameReq req, Callback<Response> cb);

    //获取店内所有分类
    @GET("/shop/{shop_id}/group/")
    void getShopCategory(@Path("shop_id") String shop_id, Callback<ShopAllGroupRes> cb);

    //店内分类删除
    @DELETE("/shop/{shop_id}/group/{group_id}")
    void deleteShopCategory(@Path("shop_id") String shop_id, @Path("group_id") String group_id, Callback<Response> cb);

    //店内优惠创建
    @POST("/shop/{shop_id/discount/")
    void createShopDiscount(@Path("shop_id") String shop_id, @Body ContentReq req, Callback<CreateShopDiscountRes> cb);

    //店内优惠修改
    @PUT("/shop/{shop_id}/discount_id")
    void modifyShopDiscount(@Path("shop_id") String shop_id, @Body ContentReq req, Callback<Response> cb);

    //列出店内所有优惠
    @GET("/shop/{shop_id}/discount/")
    void getShopDiscount(@Path("shop_id") String shop_id, Callback<ShopDiscountRes> cb);

    //店内优惠删除
    @DELETE("/shop/{shop_id}/discount_id")
    void deleteShopDiscount(@Path("shop_id") String shop_id, Callback<Response> cb);


    //商品
    //商品创建
    @POST("/goods/")
    void createGoods(@Body CreateGoodsReq req, Callback<CreateGoodsRes> cb);

    //查看自己的商品
    @GET("/goods/list/")
    void getMyGoods(@Query("page") int page, @Query("is_active") String is_active, @Query("group") String group, Callback<MyGoodsRes> cb);

    //上信息更新
    @PUT("/goods/{goods_id}")
    void updateGoods(@Path("goods_id") String goods_id, @Body UpdateGoodsReq req, Callback<Response> cb);

    //商品删除
    @DELETE("/goods/{goods_id}")
    void deleteGoods(@Path("goods_id") String goods_id, Callback<Response> cb);

    //商品查看
    @GET("/goods/{goods_id}")
    void getGoodsInfo(@Path("goods_id") String goods_id, Callback<GoodsInfoRes> cb);

    //商品搜索
    @GET("/goods/search/")
    void searchGoods(@Query("keyword")String keyword,@Query("order")String order,
                     @Query("category")String category,@Query("label")String label,
                     @Query("group")String group,@Query("shop_id") String shop_id,
                     @Query("page") int page,@Query("location")String location,Callback<SearchGoodsRes>cb);

    //商品点赞
    @POST("/goods/{goods_id}/like/")
    void likeGoods(@Path("goods_id") String goods_id, Callback<LikeGoodsRes> cb);

    //商品取消点赞
    @DELETE("/goods/{goods_id}/like/")
    void unLikeGoods(@Path("goods_id") String goods_id, Callback<Response> cb);

    //商品评论添加
    @POST("/goods/{goods_id}/comment/")
    void addGoodsComment(@Path("goods_id") String goods_id, @Body ContentReq req, Callback<AddGoodsCommentRes> cb);

    //商品所有评论查看
    @GET("/goods/{goods_id}/comment/{page}")
    void getAllGoodsComment(@Path("goods_id") String goods_id, @Path("page") int page, Callback<AllGoodsCommentRes> cb);

    //商品评论删除
    @DELETE("/goods/{goods_id}/comment/{goods_comment_id}")
    void deleteGoodsComment(@Path("goods_id") String goods_id, @Path("goods_comment_id") String goods_comment_id, Callback<Response> cb);

    //商品有用添加
    @POST("/goods/{goods_id}/comment/{goods_comment_id}/useful/")
    void addGoodsUseful(@Path("goods_id")String goods_id,@Path("goods_comment_id")String goods_comment_id,Callback<CommonStatusRes> cb);

    //商品有用取消
    @DELETE("/goods/{goods_id}/comment/{goods_comment_id}/useful/")
    void cancelGoodsUseful(@Path("goods_id")String goods_id,@Path("goods_comment_id")String goods_comment_id,Callback<CommonStatusRes> cb);

    //商品收藏添加
    @POST("/goods/collection/{goods_id}")
    void addGoodsCollection(@Path("goods_id")String  goods_id,Callback<AddGoodsCollectionRes>cb);

    //个人商品收藏查看
    @GET("/goods/collection/{page}")
    void getAllGoodsCollection(@Path("page") int page, Callback<AllGoodsCollectionRes> cb);

    //商品收藏删除
    @DELETE("/goods/collection/{goods_id}")
    void deleteGoodsCollection(@Path("goods_id")String goods_id,Callback<CommonStatusRes>cb);

    //闲置
    //闲置创建
    @POST("/idle/")
    void createIdle(@Body CreateIdleReq req, Callback<CreateIdleRes> cb);

    //查看自己的闲置
    @GET("/idle/list/")
    void getIdleList(@Query("page")int page,@Query("is_active")int is_active,Callback<
            IdleInfoRes>cb);

    //闲置信息更新
    @PUT("/idle/{idle_id}")
    void updateIdleInfo(@Path("idle_id")String idle_id,@Body UpdateIdleReq req,Callback<CommonStatusRes>cb);

    //闲置删除
    @DELETE("/idle/{idle_id}")
    void deleteIdle(@Path("idle_id")String idle_id,Callback<CommonStatusRes>cb);

    //闲置查看
    @GET("/idle/{idle_id}")
    void getIdleInfo(@Path("idle_id")String idle_id,Callback<GetIdleInfoRes>cb);

    //闲置搜索
    @GET("/idle/search/")
    void searchIdle(@Query("keyword")String keyword,@Query("order") String order, @Query("category") String category,@Query("page")int page,@Query("location")String location,Callback<SearchIdleRes>cb);

    //闲置点赞
    @POST("/idle/{idle_id}/like/")
    void likeIdle(@Path("idle_id") String idle_id, Callback<LikeIdleRes> cb);

    //闲置取消点赞
    @DELETE("/idle/{idle_id}/like/")
    void unLikeIdle(@Path("idle_id") String idle_id, Callback<Response> cb);

    //闲置评论添加
    @POST("/idle/{idle_id}/comment/")
    void addIdleComment(@Path("idle_id") String idle_id, @Body ContentReq req, Callback<AddIdleCommentRes> cb);

    //闲置所有评论查看
    @GET("/idle/{idle_id}/comment/{page}")
    void getIdleComment(@Path("idle_id") String idle_id, @Path("page") int page, Callback<IdleAllCommentRes> cb);

    //闲置评论删除
    @DELETE("/idle/{idle_id}/comment/{idle_comment_id}")
    void deleteIdleComment(@Path("idle_id") String idle_id, @Path("idle_comment_id") String idle_comment_id, Callback<Response> cb);

    //评论有用添加
    @POST("/idle/{idle_id}/comment/{idle_comment_id}/useful/")
    void addIdleCommentUseful(@Path("idle_id")String idle_id,@Path("idle_comment_id")String idle_comment_id,Callback<Response> cb);

    //评论有用取消
    @DELETE("/idle/{idle_id}/comment/{idle_comment_id}/useful/")
    void cancelIdleCommentUseful(@Path("idle_id")String idle_id,@Path("idle_comment_id")String idle_comment_id,Callback<Response> cb);


    //闲置收藏添加
    @POST("/idle/collection/{idle_id}")
    void addIdleCollection(@Path("idle_id")String idle_id,Callback<AddIdleCollectionRes>cb);

    //闲置收藏删除
    @DELETE("/idle/collection/{idle_id}")
    void deleteIdleCollection(@Path("idle_id")String idle_id,Callback<Response>cb);


    //个人闲置收藏查看
    @GET("/idle/collection/{page}")
    void getIdleCollection(@Path("page")int page,Callback<IdleCollectionRes> cb);

    //闲置收藏删除
    @DELETE("/idle/collection/{idle_id}")
    void idleCollectionDelete(@Path("idle_id")String idle_id,Callback<CommonStatusRes> cb);

//校区
    //校区创建
    @POST("/location/")
    void createLocation(@Body NameReq req, Callback<CreateLocationRes> cb);

    //校区修改
    @PUT("/location/{location_id}")
    void modifyLocation(@Path("location_id") String location_id, @Body NameReq req, Callback<Response> cb);

    //获取所有校区
    @GET("/location/")
    void getAllLocation(Callback<LocationAllRes> cb);

    //校区删除
    @DELETE("/location/{location_id}")
    void deleteLocation(Callback<Response> cb);

    //分类
    //类目创建
    @POST("/category/")
    void createCategory(@Body CategoryReq req, Callback<CreateCategoryRes> cb);

    //类目修改
    @PUT("/category/{category_id}")
    void modifyCategory(@Body CategoryReq req, Callback<Response> cb);

    //获取所有闲置类目
    @GET("/category/idle/")
    void getAllIdleCategory(Callback<AllCategoryRes> cb);

    //获取所有店铺类目
    @GET("/category/shop/")
    void getAllShopCategory(Callback<AllCategoryRes> cb);

    //类目删除
    @DELETE("/category/{category_id}")
    void deleteCategory(Callback<Response> cb);


    //意见、报告、更新等
    //意见提交
    @POST("/suggestion/")
    void commitSuggestion(@Body ContentReq req, Callback<CommitSuggestionRes> cb);

    //查看所有意见
    @GET("/suggestion/")
    void getSuggestion(Callback<SuggestionRes> cb);

    //举报提交
    @POST("/report/")
    void commitReport(@Body CommitReportReq req,Callback<ReportRes>cb);

    //检查更新
    @GET("/check_update/{version}")
    void checkUpdate(@Path("version") float version, Callback<CheckUpdateRes> cb);

    @GET("/label/")
    void getLabels(@Query("category") String category,Callback<Labels> cb);

    @GET("/card/")
    void checkCard(Callback<Card> cb);

    //广告
    @GET("/advertisement/")
    void getAd(Callback<GetAdRes> cb);
    //订单
    @POST("/user/order/")
    void createOrder(@Body CreateOrderReq orderReq,Callback<CreateOrderRes> cb);

    @POST("/user/order/pay/{order_id}/")
    void payOrder(@Path("order_id")String order_id,@Body PayOrderReq payOrderReq,Callback<JsonElement> cb);

    @GET("/user/orders/")
    void getOrderList(@Query("page")int page,Callback<GetOrderListRes> cb);

    @GET("/user/order/{order_id}/")
    void getOrderDetail(@ Path("order_id")String order_id,Callback<OrderDetail> cb);

    @DELETE("/user/order/{order_id}/")
    void cancelOrder(@Path("order_id")String order_id,Callback<CommonStatusRes>cb);
}
