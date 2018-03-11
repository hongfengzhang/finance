package com.waben.stock.applayer.promotion.reference.fallback;

import com.waben.stock.applayer.promotion.reference.manage.MenuReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.manage.MenuDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MenuReferenceFallback implements MenuReference {

    @Override
    public Response<List<MenuDto>> menusByRole(Long role) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }
}
