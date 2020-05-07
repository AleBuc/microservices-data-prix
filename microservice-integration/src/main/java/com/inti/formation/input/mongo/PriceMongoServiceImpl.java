package com.inti.formation.input.mongo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author CestVianney
 * @author AlexandreBuc
 */

@Component
@Service
public class PriceMongoServiceImpl implements PriceMongoService {

    @Autowired
    private PriceMongoRepository priceRepository;


    @Override
    public PriceMongo register(PriceMongo price) {
        return priceRepository.save(price);
    }
}
