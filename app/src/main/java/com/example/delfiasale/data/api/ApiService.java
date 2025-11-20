package com.example.delfiasale.data.api;

import com.example.delfiasale.data.model.GroupDto;
import com.example.delfiasale.data.model.ItemDto;
import com.example.delfiasale.data.model.SaleRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @GET("groups")
    Call<List<GroupDto>> getGroups();

    @GET("groups/{groupId}/items")
    Call<List<ItemDto>> getItemsByGroup(@Path("groupId") long groupId);

    @POST("sales")
    Call<Void> submitSale(@Body SaleRequest request);
}
