package com.artur.shop.order.service;

import com.artur.shop.order.model.Shipment;
import com.artur.shop.order.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipmentService {
    private final ShipmentRepository shipmentRepository;

    public List<Shipment> getShipments(){
        return shipmentRepository.findAll();
    }
}
