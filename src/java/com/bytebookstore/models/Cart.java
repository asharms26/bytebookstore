/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytebookstore.models;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author mbenso14
 */
public class Cart {
    private final ArrayList<CartItem> cartItems = new ArrayList<>();
    private boolean inital_synch = false;

    public void clear() {
        cartItems.clear();
    }

    public boolean isInital_synch() {
        return inital_synch;
    }

    public void setInital_synch(boolean inital_synch) {
        this.inital_synch = inital_synch;
    }
    
    public double getTotalCost() {
        double cost = 0;
        for(CartItem c : cartItems) {
            cost = cost + c.getQuantity() * c.getPrice();
        }
        
        return cost;
    }
    
    public double getFinalCost() {
        double cost = 0;
        
        for(CartItem c: cartItems) {
            if(c.isPurchased()) {
                cost = cost + c.getQuantity() * c.getPrice();
            }
        }
        
        return cost;
    }
    
    public int getItemCount() {
        return cartItems.size();
    }

    public void addCartItem(String ISBN, String title, String firstname, String lastname, double price) {
        
        for(CartItem c : cartItems) {
            if (c.getISBN() != null && c.getISBN().contains(ISBN)) {                
                c.setQuantity(c.getQuantity() + 1);
                return;
            }
        }
        
        CartItem cartItem = new CartItem();

        cartItem.setISBN(ISBN);
        cartItem.setQuantity(1);
        cartItem.setLastName(lastname);
        cartItem.setFirstName(firstname);
        cartItem.setTitle(title);
        cartItem.setPrice(price);
        
        cartItems.add(cartItem);
    }

        public void addCartItem(String ISBN, String title, String firstname, String lastname, double price, int quantity) {
        
        for(CartItem c : cartItems) {
            if (c.getISBN() != null && c.getISBN().contains(ISBN)) {                
                c.setQuantity(c.getQuantity() + quantity);
                return;
            }
        }
        
        CartItem cartItem = new CartItem();

        cartItem.setISBN(ISBN);
        cartItem.setQuantity(quantity);
        cartItem.setLastName(lastname);
        cartItem.setFirstName(firstname);
        cartItem.setTitle(title);
        cartItem.setPrice(price);
        
        cartItems.add(cartItem);
    }
    
    public void deleteCartItem(String ISBN) {
        Iterator<CartItem> iter = cartItems.iterator();
        
        while(iter.hasNext()) {
            CartItem c = iter.next();
            if(c.getISBN().equals(ISBN)){
                iter.remove();
            }
        }
    }
    
    public CartItem getCartItem(int iItemIndex) {
        CartItem cartItem = null;
        if(cartItems.size()>iItemIndex) {
            cartItem = (CartItem) cartItems.get(iItemIndex);
        }
        return cartItem;
    }
    
    public CartItem getCartItem(String ISBN) { 
        for(CartItem c : cartItems) {
            if (c.getISBN() != null && c.getISBN().contains(ISBN)) {
                return c;
            }
        }
        
        return null;
    }
}