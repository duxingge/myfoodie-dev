package com.example.service.imp;

import com.example.mapper.CarouselMapper;
import com.example.pojo.Carousel;
import com.example.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarouselServiceImp implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;


    @Override
    public List<Carousel> getAll() {
        return carouselMapper.selectAll();
    }
}
