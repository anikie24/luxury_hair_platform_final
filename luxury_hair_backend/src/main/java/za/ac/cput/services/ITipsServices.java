package za.ac.cput.services;

import za.ac.cput.domain.Tips;

import java.util.List;

public interface ITipsServices extends IService<Tips,Integer> {
    List<Tips>getall();
}