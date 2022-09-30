package com.example.warehousemanagement.truck.graphql;

import com.example.warehousemanagement.core.http.PageableResponse;
import com.example.warehousemanagement.truck.service.TruckService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TruckGraphqlController {
    @Autowired
    private TruckService truckService;

    @QueryMapping
    public PageableResponse<Truck>
}
