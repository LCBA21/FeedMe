package com.lcba.service;

import com.lcba.Request.AddCartItemRequest;
import com.lcba.model.Cart;
import com.lcba.model.CartItem;
import com.lcba.model.Food;
import com.lcba.model.User;
import com.lcba.repository.CartItemRepository;
import com.lcba.repository.CartRepository;
import com.lcba.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemRepository cartItemRepository;


    @Autowired
    private FoodService foodService;


    @Override
    public CartItem addItemToCart(AddCartItemRequest request, String jwt) throws Exception {
       User user=userService.findUserByJwtToken(jwt);
       Food food=foodService.findFoodById(request.getId());
       Cart cart=cartRepository.findByCustomerId(user.getId());

       for (CartItem cartItem:cart.getItems()){
           if(cartItem.getFood().equals(food)){
               int newQuantity=cartItem.getQuantity()+request.getQuantity();
               return updateCartItemQuantity(cartItem.getId(),newQuantity);

           }
       }
       CartItem newCartItem=new CartItem();

       newCartItem.setFood(food);
       newCartItem.setCart(cart);
       newCartItem.setQuantity(request.getQuantity());
       newCartItem.setIngredients(request.getIngredients());
       newCartItem.setTotalPrice(request.getQuantity()*food.getPrice());

       CartItem savedCartItem=cartItemRepository.save(newCartItem);
       cart.getItems().add(savedCartItem);


        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItem=cartItemRepository.findById(cartItemId);
        if (cartItem.isEmpty()){
            throw new Exception("Cart Item Not Found");
        }

        CartItem item=cartItem.get();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice()*quantity);


        return cartItemRepository.save(item);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);


        Cart cart=cartRepository.findByCustomerId(user.getId());

        Optional<CartItem> cartItemOptional=cartItemRepository.findById(cartItemId);
        if (cartItemOptional.isEmpty()){
            throw new Exception("Cart Item Not Found");
        }

        CartItem item=cartItemOptional.get();
        cart.getItems().remove(item);


        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotal(Cart cart) throws Exception {
        Long total=0L;
        for (CartItem cartItem:cart.getItems()){
            total=cartItem.getFood().getPrice()*cartItem.getQuantity();
        }

        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {

        Optional<Cart> optionalCart=cartRepository.findById(id);
        if (optionalCart.isEmpty()){
            throw new Exception("Cart Not Found With Id "+id);
        }
        return optionalCart.get();
    }

    @Override
    public Cart findCartByUserId(String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        return cartRepository.findByCustomerId(user.getId());
    }

    @Override
    public Cart clearCart(String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        Cart cart=findCartByUserId(jwt);
        cart.getItems().clear();

        return cartRepository.save(cart);
    }
}
