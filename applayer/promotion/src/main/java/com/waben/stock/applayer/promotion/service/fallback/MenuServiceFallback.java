package com.waben.stock.applayer.promotion.service.fallback;

import com.waben.stock.applayer.promotion.service.manage.MenuService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.manage.MenuDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MenuServiceFallback implements MenuService {

    @Override
    public Response<List<MenuDto>> menusByRole(Long role) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }
}
