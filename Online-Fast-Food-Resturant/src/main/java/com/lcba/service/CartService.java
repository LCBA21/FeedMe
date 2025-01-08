package com.lcba.service;

import com.lcba.Request.AddCartItemRequest;
import com.lcba.model.Cart;
import com.lcba.model.CartItem;
import com.lcba.model.User;
import org.springframework.stereotype.Service;

@Service
public interface CartService {

    public CartItem addItemToCart(AddCartItemRequest request, String jwt)throws Exception;

    public CartItem updateCartItemQuantity(Long cartItemId,int quantity)throws Exception;

    public Cart removeItemFromCart(Long cartItemId,String jwt)throws Exception;

    public Long calculateCartTotal(Cart cart)throws Exception;

    public Cart findCartById(Long id)throws Exception;

    public Cart findCartByUserId(String jwt)throws Exception;

    public Cart clearCart(String jwt)throws Exception;







}
