package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.MyProductDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity MyProduct and its DTO MyProductDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MyProductMapper {

    @Mapping(source = "myCart.id", target = "myCartId")
    @Mapping(source = "myOrders.id", target = "myOrdersId")
    MyProductDTO myProductToMyProductDTO(MyProduct myProduct);

    List<MyProductDTO> myProductsToMyProductDTOs(List<MyProduct> myProducts);

    @Mapping(source = "myCartId", target = "myCart")
    @Mapping(source = "myOrdersId", target = "myOrders")
    MyProduct myProductDTOToMyProduct(MyProductDTO myProductDTO);

    List<MyProduct> myProductDTOsToMyProducts(List<MyProductDTO> myProductDTOs);

    default MyCart myCartFromId(Long id) {
        if (id == null) {
            return null;
        }
        MyCart myCart = new MyCart();
        myCart.setId(id);
        return myCart;
    }

    default MyOrders myOrdersFromId(Long id) {
        if (id == null) {
            return null;
        }
        MyOrders myOrders = new MyOrders();
        myOrders.setId(id);
        return myOrders;
    }
}
